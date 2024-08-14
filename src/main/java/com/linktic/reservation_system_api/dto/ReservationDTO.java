package com.linktic.reservation_system_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data Transfer Object for Reservation entity.
 * Used for transferring reservation data between different layers of the application.
 */
@Data
@NoArgsConstructor
public class ReservationDTO {

    /**
     * The date and time when the reservation was created.
     */
    private LocalDateTime bookingDate;

    /**
     * The date when the reservation is scheduled.
     */
    private LocalDateTime reservationDate;


    /**
     * The id of the user who made the reservation.
     */
    private Long userId;

    /**
     * The ids of the rooms associated with the reservation.
     */
    private Set<Long> roomIds;
}
