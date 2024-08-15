package com.linktic.reservation_system_api.service;

import com.linktic.reservation_system_api.dto.Response;
import com.linktic.reservation_system_api.entity.Room;

import java.util.List;

/**
 * Service for handling business logic related to rooms.
 */
public interface RoomService {

    /**
     * Retrieves a list of all rooms available in the database.
     *
     * @return an {@link Response} containing the list of Room objects.
     */
    public Response<List<Room>> getAllRooms();
}
