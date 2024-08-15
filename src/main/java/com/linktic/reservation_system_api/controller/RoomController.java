package com.linktic.reservation_system_api.controller;

import com.linktic.reservation_system_api.dto.Response;
import com.linktic.reservation_system_api.entity.Room;
import com.linktic.reservation_system_api.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for handling requests related to rooms.
 */
@Tag(name = "Room API", description = "API for managing rooms")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:4200")
public class RoomController {

    /**
     * Service for managing room business logic.
     */
    private final RoomService roomService;

    /**
     * Endpoint to retrieve all rooms.
     *
     * @return ResponseEntity containing the list of rooms and HTTP status.
     */
    @Operation(summary = "Get all rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Response<List<Room>>> getAllRooms() {
        Response<List<Room>> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatusCode.valueOf(rooms.getStatusCode()));
    }
}
