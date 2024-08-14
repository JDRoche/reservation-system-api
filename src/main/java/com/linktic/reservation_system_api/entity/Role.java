package com.linktic.reservation_system_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linktic.reservation_system_api.util.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a role in the system, such as ADMIN or USER.
 * Each role defines a set of permissions for the users who have this role.
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The name of the role, represented as an enum value.
     */
    @Enumerated(EnumType.STRING)
    private UserRole name;

    /**
     * The users who have been assigned this role.
     * A role can be assigned to multiple users.
     */
    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User> users;

}
