package com.linktic.reservation_system_api.repository;

import com.linktic.reservation_system_api.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the Room entity.
 * Provides basic CRUD operations.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {
}
