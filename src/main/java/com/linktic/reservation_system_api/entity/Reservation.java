package com.linktic.reservation_system_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents a reservation made by a user for one or more rooms.
 * A reservation has a booking date, reservation date, and associated rooms.
 */
@Data
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The date when the reservation was made.
     */
    @Column(nullable = false)
    private LocalDateTime bookingDate;
    /**
     * The date when the reservation is scheduled.
     */
    @Column(nullable = false)
    private LocalDateTime reservationDate;

    /**
     * The user who made the reservation.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The rooms included in this reservation.
     * A reservation can include multiple rooms.
     */
    @OneToMany(mappedBy = "reservation")
    private Set<Room> room;

}
