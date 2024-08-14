package com.linktic.reservation_system_api.entity;

import com.linktic.reservation_system_api.util.RoomStatus;
import com.linktic.reservation_system_api.util.RoomType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents a room in the hotel or accommodation system.
 * Each room has a unique number, type, price, and status.
 * The room can be associated with a reservation.
 */
@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The unique number assigned to this room.
     */
    @Column(unique = true)
    private String roomNumber;
    /**
     * The type of the room (e.g., SINGLE, DOUBLE, SUITE).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType type;
    /**
     * The current status of the room (e.g., AVAILABLE, OCCUPIED).
     */
    @Enumerated(EnumType.STRING)
    private RoomStatus status;
    /**
     * The price per night for the room.
     */
    private BigDecimal price;

    /**
     * The reservation associated with this room, if any.
     * A room can be linked to only one reservation at a time.
     */
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
