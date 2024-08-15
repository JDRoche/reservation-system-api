package com.linktic.reservation_system_api.service;

import com.linktic.reservation_system_api.dto.ReservationDTO;
import com.linktic.reservation_system_api.dto.Response;
import com.linktic.reservation_system_api.entity.Reservation;
import com.linktic.reservation_system_api.util.RoomType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing reservations.
 * Provides methods for CRUD operations on reservations.
 */
public interface ReservationService {

    /**
     * Retrieves reservations that match the provided filters.
     *
     * @param startDate The start date of the reservation range. If null, this filter is ignored.
     * @param endDate The end date of the reservation range. If null, this filter is ignored.
     * @param roomType The type of room to filter by. If null, this filter is ignored.
     * @param userEmail The email of the user who made the reservation. If null, this filter is ignored.
     * @return an {@link Response} containing the list of reservations matching the provided filters.
     */
    Response<List<Reservation>> getReservations(LocalDateTime startDate, LocalDateTime endDate, RoomType roomType, String userEmail);

    /**
     * Retrieves a reservation by its ID.
     *
     * @param id the ID of the reservation to retrieve.
     * @return an {@link Response} containing the reservation if found, or a not found message.
     */
    Response<Reservation> getReservationById(Long id);

    /**
     * Saves a new reservation.
     *
     * @param reservation the reservation to save.
     * @return an {@link Response} indicating the result of the save operation.
     */
    Response<Reservation> saveReservation(ReservationDTO reservation);

    /**
     * Updates an existing reservation.
     *
     * @param id the ID of the reservation to update.
     * @param reservation the updated reservation data.
     * @return an {@link Response} indicating the result of the update operation.
     */
    Response<Reservation> updateReservation(Long id, ReservationDTO reservation);

    /**
     * Deletes a reservation by its ID.
     *
     * @param id the ID of the reservation to delete.
     * @return an {@link Response} indicating the result of the delete operation.
     */
    Response<Void> deleteReservation(Long id);
}
