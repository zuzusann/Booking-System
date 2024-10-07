package com.zzs.hotelbookingsystem.controller;


import com.zzs.hotelbookingsystem.dto.RoomResponse;
import com.zzs.hotelbookingsystem.entity.Room;
import com.zzs.hotelbookingsystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/room/add/new_room")
    public ResponseEntity<RoomResponse> addNewRoom (@RequestParam("photo")MultipartFile photo,
                                                    @RequestParam("roomType")String roomType,
                                                    @RequestParam("roomPrice")int roomPrice) {
        Room savedRoom = roomService.addnewRoom(photo, roomType, roomPrice);
        RoomResponse roomResponse = new RoomResponse(savedRoom.getRoomId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
        return ResponseEntity.ok(roomResponse);
    }
}
