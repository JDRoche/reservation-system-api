package com.linktic.reservation_system_api.service;

import com.linktic.reservation_system_api.dto.Response;
import com.linktic.reservation_system_api.entity.Room;
import com.linktic.reservation_system_api.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    @Override
    public Response<List<Room>> getAllRooms() {
        log.info("Fetching all rooms");
        List<Room> rooms = roomRepository.findAll();
        log.info("Fetched {} rooms", rooms.size());
        return new Response<>(true, "Rooms fetched successfully", HttpStatus.OK.value(), rooms);
    }
}
