package com.linktic.reservation_system_api.util;

/**
 * Enum representing the possible status values for a room.
 * The status indicates the current availability and state of the room.
 */
public enum RoomStatus {
    /**
     * AVAILABLE indicates that the room is available for booking.
     */
    AVAILABLE,
    /**
     * OCCUPIED indicates that the room is currently occupied by a guest.
     */
    OCCUPIED,
    /**
     * CLEANING indicates that the room is being cleaned and is temporarily unavailable.
     */
    CLEANING,
    /**
     * MAINTENANCE indicates that the room is under maintenance and cannot be booked.
     */
    MAINTENANCE,
    /**
     * RESERVED indicates that the room has been booked but is not yet occupied.
     */
    RESERVED
}
