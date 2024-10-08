package com.zzs.hotelbookingsystem.controller;


import com.zzs.hotelbookingsystem.dto.RoomResponse;
import com.zzs.hotelbookingsystem.entity.Room;
import com.zzs.hotelbookingsystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/add/new_room")
    public ResponseEntity<RoomResponse> addNewRoom (@RequestParam("photo")MultipartFile photo,
                                                    @RequestParam("roomType")String  roomType,
                                                    @RequestParam("roomPrice")int roomPrice) {
        Room savedRoom = roomService.addnewRoom(photo, roomType, roomPrice);
        RoomResponse roomResponse = new RoomResponse(savedRoom.getRoomId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/roomTypes")
    public List<String> getAllRoomTypes() {
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/get_all_rooms")
    public ResponseEntity<List<RoomResponse>> get_ALL_Rooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getRoomId());
            if(photoBytes != null && photoBytes.length> 0) {
                String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room, base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    private RoomResponse getRoomResponse(Room room, String base64Photo) {

        RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomType(), room.getRoomPrice(), room.getBookings().isEmpty() ,base64Photo, null);
        return roomResponse;
    }
}
