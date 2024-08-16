package com.linktic.reservation_system_api.util;

/**
 * Enum representing the various roles a user can have in the system.
 * These roles determine the user's access level and permissions.
 */
public enum UserRole {
    /**
     * ADMIN role grants full access to all system features.
     */
    ROLE_ADMIN,
    /**
     * USER role grants access to basic features, such as making reservations.
     */
    ROLE_USER
}
