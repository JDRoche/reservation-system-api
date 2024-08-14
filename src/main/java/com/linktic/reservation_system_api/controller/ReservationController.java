package com.linktic.reservation_system_api.controller;

import com.linktic.reservation_system_api.dto.ReservationDTO;
import com.linktic.reservation_system_api.dto.Response;
import com.linktic.reservation_system_api.entity.Reservation;
import com.linktic.reservation_system_api.service.ReservationService;
import com.linktic.reservation_system_api.util.RoomType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for managing reservations.
 * Provides endpoints to perform CRUD operations on reservations.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * Retrieves reservations that match the provided filters.
     *
     * @param startDate The start date of the reservation range. Optional parameter.
     * @param endDate The end date of the reservation range. Optional parameter.
     * @param roomType The type of room to filter by. Optional parameter.
     * @param userId The ID of the user who made the reservation. Optional parameter.
     * @return A ResponseEntity containing the list of reservations matching the provided filters.
     */
    @Operation(summary = "Get reservations with filters", description = "Retrieve reservations based on optional filters such as reservation date range, room type, and user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reservation.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Response<List<Reservation>>> getReservations(
            @Parameter(description = "Start date for filtering reservations", example = "2024-08-14T00:00:00")
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,

            @Parameter(description = "End date for filtering reservations", example = "2024-09-14T23:59:59")
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,

            @Parameter(description = "Type of room for filtering reservations")
            @RequestParam(required = false) RoomType roomType,

            @Parameter(description = "User ID for filtering reservations")
            @RequestParam(required = false) Long userId) {
        Response<List<Reservation>> response = reservationService.getReservations(startDate, endDate, roomType, userId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    /**
     * Gets a reservation by its ID.
     *
     * @param id the ID of the reservation.
     * @return the reservation with the specified ID or a not found message.
     */
    @Operation(summary = "Get reservation by ID", description = "Retrieve a reservation by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation found"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Response<Reservation>> getReservationById(@PathVariable Long id) {
        Response<Reservation> response = reservationService.getReservationById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    /**
     * Creates a new reservation.
     *
     * @param reservation the reservation to be created.
     * @return the created reservation with a success message.
     */
    @Operation(summary = "Create a new reservation", description = "Create a new reservation.")
    @ApiResponse(responseCode = "201", description = "Reservation created successfully")
    @PostMapping
    public ResponseEntity<Response<Reservation>> createReservation(@RequestBody ReservationDTO reservation) {
        Response<Reservation> response = reservationService.saveReservation(reservation);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    /**
     * Updates an existing reservation.
     *
     * @param id the ID of the reservation to be updated.
     * @param reservation the updated reservation details.
     * @return the updated reservation with a success message.
     */
    @Operation(summary = "Update an existing reservation", description = "Update the details of an existing reservation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Response<Reservation>> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO reservation) {
        Response<Reservation> response = reservationService.updateReservation(id, reservation);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param id the ID of the reservation to be deleted.
     * @return a success message if the reservation was deleted.
     */
    @Operation(summary = "Delete a reservation", description = "Delete a reservation by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reservation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteReservation(@PathVariable Long id) {
        Response<Void> response = reservationService.deleteReservation(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
