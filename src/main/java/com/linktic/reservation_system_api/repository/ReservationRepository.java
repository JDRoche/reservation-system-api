package com.linktic.reservation_system_api.repository;

import com.linktic.reservation_system_api.entity.Reservation;
import com.linktic.reservation_system_api.entity.User;
import com.linktic.reservation_system_api.util.RoomType;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing reservations with customizable filters.
 *
 * This repository provides a method to find reservations based on multiple criteria:
 * - A range of reservation dates (`startDate` and `endDate`)
 * - The type of room associated with the reservation (`roomType`)
 * - The user who made the reservation (`user`)
 *
 * If any of these parameters is null, the corresponding filter will be ignored.
 *
 * Note: The `startDate` and `endDate` parameters are cast to `timestamp` to ensure
 * correct handling of null values in the query.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    /**
     * Finds reservations that match the provided filters.
     *
     * @param startDate The start date of the reservation range. If null, this filter is ignored.
     * @param endDate The end date of the reservation range. If null, this filter is ignored.
     * @param roomType The type of room to filter by. If null, this filter is ignored.
     * @param user The user who made the reservation. If null, this filter is ignored.
     * @return A list of reservations matching the provided filters.
     */
    @Query("SELECT DISTINCT r FROM Reservation r " +
            "JOIN r.rooms room " +
            "WHERE (cast(:startDate as timestamp) IS NULL OR cast(:endDate as timestamp) IS NULL OR r.reservationDate BETWEEN :startDate AND :endDate) " +
            "AND (:roomType IS NULL OR room.type = :roomType) " +
            "AND (:user IS NULL OR r.user = :user)")
    List<Reservation> findReservationsByFilters(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("roomType") RoomType roomType,
            @Param("user") User user);

}
