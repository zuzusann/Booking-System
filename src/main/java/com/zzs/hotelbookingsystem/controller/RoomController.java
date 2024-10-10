package com.zzs.hotelbookingsystem.controller;


import com.zzs.hotelbookingsystem.dto.RoomResponse;
import com.zzs.hotelbookingsystem.entity.Room;
import com.zzs.hotelbookingsystem.exception.ResourceNotFoundException;
import com.zzs.hotelbookingsystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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
       RoomResponse  roomResponse =  new RoomResponse(room.getRoomId(), room.getRoomType(), room.getRoomPrice(), room.isBookingAvailable(), base64Photo);
       return roomResponse;
    }


    //@PathVariale => used in RESTful services to capture part of the URL and map it to method parameters in a controller
    @DeleteMapping("/delete/room/{roomId}  ")
    public ResponseEntity<Void> deleteRoom(@PathVariable int roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent()
                .header("Delete Successfully")
                .build();
    }


    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable int  roomId,
                                                   @RequestParam(required = false) String roomType,
                                                   @RequestParam(required = false) int roomPrice,
                                                   @RequestParam(required = false) MultipartFile photo
                                                   ) throws SQLException, IOException {
       byte[] photoByte = photo != null && photo.isEmpty() ? photo.getBytes() : roomService.getRoomPhotoByRoomId(roomId);
        Blob photoBlob = photoByte != null && photoByte.length > 0 ? new SerialBlob(photoByte) : null;
        Room room = roomService.updateRoom(roomId, roomType, roomPrice, photoByte);
        room.setPhoto(photoBlob);
        RoomResponse roomResponse =  getRoomResponse(room, room.getRoomType());
        return ResponseEntity.ok(roomResponse);
    }


    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomByRoomId(@PathVariable int roomId) {
        Optional<Room> room = roomService.getRoomByRoomId(roomId);
        return room.map(room1 -> {
            RoomResponse roomResponse = new RoomResponse(room1.getRoomId(), room1.getRoomType(), room1.getRoomPrice());
            return ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found"));

    }



}

