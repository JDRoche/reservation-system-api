package com.linktic.reservation_system_api.service;

import com.linktic.reservation_system_api.dto.ReservationDTO;
import com.linktic.reservation_system_api.dto.Response;
import com.linktic.reservation_system_api.entity.Reservation;
import com.linktic.reservation_system_api.entity.Room;
import com.linktic.reservation_system_api.entity.User;
import com.linktic.reservation_system_api.repository.ReservationRepository;
import com.linktic.reservation_system_api.repository.RoomRepository;
import com.linktic.reservation_system_api.repository.UserRepository;
import com.linktic.reservation_system_api.util.RoomStatus;
import com.linktic.reservation_system_api.util.RoomType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for handling reservation operations.
 * Provides methods to perform CRUD operations on reservations.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public Response<List<Reservation>> getReservations(LocalDateTime startDate, LocalDateTime endDate, RoomType roomType, Long userId) {
        log.info("Fetching all reservations");
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        }
        List<Reservation> reservations = reservationRepository.findReservationsByFilters(startDate, endDate, roomType, user);
        log.info("Fetched {} reservations", reservations.size());
        return new Response<>(true, "Reservations fetched successfully", HttpStatus.OK.value(), reservations);
    }

    @Override
    public Response<Reservation> getReservationById(Long id) {
        log.info("Fetching reservation with ID {}", id);
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            log.info("Reservation found with ID {}", id);
            return new Response<>(true, "Reservation found", HttpStatus.OK.value(), reservation.get());
        } else {
            log.warn("Reservation not found with ID {}", id);
            return new Response<>(false, "Reservation not found", HttpStatus.NOT_FOUND.value(), null);
        }
    }

    @Override
    @Transactional
    public Response<Reservation> saveReservation(ReservationDTO reservationDto) {

        log.info("Starting reservation creation for user ID: {}", reservationDto.getUserId());
        Response<Reservation> reservationResponse = validateReservation(reservationDto);
        if (!reservationResponse.getSuccess()) {
            return reservationResponse;
        }
        Reservation reservation = reservationResponse.getData();


        Reservation savedReservation = reservationRepository.save(reservation);

        savedReservation.getRooms().forEach(room -> {
            room.setStatus(RoomStatus.RESERVED);
            room.setReservation(Reservation.builder().id(reservation.getId()).build());
            roomRepository.save(room);
            log.info("Room ID: {} marked as reserved.", room.getId());
        });

        log.info("Reservation saved with ID {}", savedReservation.getId());
        return new Response<>(true, "Reservation saved successfully", HttpStatus.CREATED.value(), savedReservation);
    }

    @Override
    public Response<Reservation> updateReservation(Long id, ReservationDTO reservationDto) {
        log.info("Updating reservation with ID {}", id);
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isEmpty()) {
            log.warn("Reservation not found with ID {}", id);
            return new Response<>(false, "Reservation not found", HttpStatus.NOT_FOUND.value(), null);
        }
        Reservation reservation = reservationOpt.get();
        reservation.getRooms().forEach(room -> {
            room.setStatus(RoomStatus.AVAILABLE);
            room.setReservation(null);
            roomRepository.save(room);
            log.info("Room ID: {} marked as available.", room.getId());
        });

        Response<Reservation> reservationResponse = validateReservation(reservationDto);
        if (!reservationResponse.getSuccess()) {
            reservation.getRooms().forEach(room -> {
                room.setStatus(RoomStatus.RESERVED);
                room.setReservation(Reservation.builder().id(id).build());
                roomRepository.save(room);
                log.info("Room ID: {} marked as reserved.", room.getId());
            });
            return reservationResponse;
        }
        Reservation validatedReservation = reservationResponse.getData();

        List<Room> mutableRooms = new ArrayList<>(reservation.getRooms());
        mutableRooms.clear();
        reservation.setRooms(mutableRooms);
        reservationRepository.save(reservation);

        reservation.setReservationDate(validatedReservation.getReservationDate());
        reservation.setRooms(new ArrayList<>(validatedReservation.getRooms()));


        Reservation updatedReservation = reservationRepository.save(reservation);
        updatedReservation.getRooms().forEach(room -> {
            room.setStatus(RoomStatus.RESERVED);
            room.setReservation(Reservation.builder().id(id).build());
            roomRepository.save(room);
            log.info("Room ID: {} marked as reserved.", room.getId());
        });
        log.info("Reservation updated with ID {}", id);
        return new Response<>(true, "Reservation updated successfully", HttpStatus.OK.value(), updatedReservation);

    }

    @Override
    @Transactional
    public Response<Void> deleteReservation(Long id) {
        log.info("Deleting reservation with ID {}", id);
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isEmpty()) {
            log.warn("Reservation not found with ID {}", id);
            return new Response<>(false, "Reservation not found", HttpStatus.NOT_FOUND.value(), null);
        }
        Reservation reservation = reservationOpt.get();
        reservation.getRooms().forEach(room -> {
            room.setStatus(RoomStatus.AVAILABLE);
            room.setReservation(null);
            roomRepository.save(room);
            log.info("Room ID: {} marked as available.", room.getId());
        });

        reservation.getRooms().clear();
        reservationRepository.save(reservation);
        log.info("Rooms association cleared for reservation ID: {}", id);

        reservationRepository.deleteById(id);
        log.info("Reservation deleted with ID {}", id);
        return new Response<>(true, "Reservation deleted successfully", HttpStatus.OK.value(), null);

    }

    private Response<Reservation> validateReservation(ReservationDTO reservationDto) {

        LocalDateTime bookingDate = LocalDateTime.now();

        if (reservationDto.getReservationDate() == null) {
            log.error("Reservation date is missing for reservation");
            return new Response<>(false, "Reservation date is required", HttpStatus.BAD_REQUEST.value(), null);
        }
        if (bookingDate.isAfter(reservationDto.getReservationDate())) {
            log.error("Reservation date is before booking date for reservation");
            return new Response<>(false, "Reservation date must be after booking date", HttpStatus.BAD_REQUEST.value(), null);
        }
        if(reservationDto.getRoomIds() == null || reservationDto.getRoomIds().isEmpty()){
            log.error("Rooms can not be empty");
            return new Response<>(false, "Rooms can not be empty", HttpStatus.BAD_REQUEST.value(), null);
        }
        if(reservationDto.getUserId() == null){
            log.error("User is required");
            return new Response<>(false, "User is required", HttpStatus.BAD_REQUEST.value(), null);
        }

        Optional<User> userOpt = userRepository.findById(reservationDto.getUserId());
        if (userOpt.isEmpty()) {
            log.error("User not found with ID: {}", reservationDto.getUserId());
            return new Response<>(false, "User not found", HttpStatus.NOT_FOUND.value(), null);
        }
        User user = userOpt.get();

        Set<Optional<Room>> rooms = reservationDto.getRoomIds().stream()
                .map(roomRepository::findById)
                .collect(Collectors.toSet());

        if (rooms.stream().anyMatch(Optional::isEmpty)) {
            log.error("One or more rooms do not exist. Room IDs: {}",
                    reservationDto.getRoomIds().stream()
                            .filter(roomId -> roomRepository.findById(roomId).isEmpty())
                            .collect(Collectors.toList()));
            return new Response<>(false, "One or more rooms do not exist", HttpStatus.NOT_FOUND.value(), null);
        }

        List<Room> unavailableRooms = rooms.stream()
                .map(Optional::get)
                .filter(room -> !room.getStatus().equals(RoomStatus.AVAILABLE))
                .toList();

        if (!unavailableRooms.isEmpty()) {
            String unavailableRoomNumbers = unavailableRooms.stream()
                    .map(Room::getRoomNumber)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            log.warn("The following rooms are not available: {}", unavailableRoomNumbers);
            return new Response<>(false, "The following rooms are not available: " + unavailableRoomNumbers, HttpStatus.BAD_REQUEST.value(), null);
        }

        Reservation reservation = Reservation.builder()
                .user(user)
                .rooms(rooms.stream().map(Optional::get).toList())
                .bookingDate(bookingDate)
                .reservationDate(reservationDto.getReservationDate())
                .build();

        return new Response<>(true, "", 0, reservation);
    }
}
