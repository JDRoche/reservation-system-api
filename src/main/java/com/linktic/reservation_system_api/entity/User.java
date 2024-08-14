package com.linktic.reservation_system_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents a user in the system.
 * Each user has a firstname, lastname, phone, email, password, and one role.
 * A user can make multiple reservations.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * First name of the user.
     */
    private String firstName;
    /**
     * Last name of the user.
     */
    private String lastName;
    /**
     * Phone of the user.
     */
    private String phone;
    /**
     * The email of the user, used for login and identification.
     */
    @Column(unique = true)
    private String email;
    /**
     * The password of the user, stored securely.
     */
    @Column(nullable = false)
    private String password;
    /**
     * The date when the user was created.
     */
    private LocalDateTime createdAt;

    /**
     * The role assigned to the user, determining their permissions.
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * The reservations made by the user.
     * A user can have multiple reservations.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations;
}
