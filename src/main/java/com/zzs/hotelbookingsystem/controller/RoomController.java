package com.zzs.hotelbookingsystem.controller;

import com.zzs.hotelbookingsystem.dto.BookingResponse;
import com.zzs.hotelbookingsystem.dto.RoomResponse;
import com.zzs.hotelbookingsystem.entity.BookedRoom;
import com.zzs.hotelbookingsystem.entity.Room;
import com.zzs.hotelbookingsystem.service.BookingService;
import com.zzs.hotelbookingsystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final BookingService bookingService;

    @PostMapping("/rooms/add-new-room")
    public ResponseEntity<RoomResponse> addNewRoom(@RequestParam String roomType,
                                                   @RequestParam int roomPrice,
                                                   @RequestParam MultipartFile photo) throws SQLException, IOException {
        Room room = roomService.addNewRoom(roomType, roomPrice, photo);
        RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomType(), room.getRoomPrice());
        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/rooms/get-room-types")
    public List<String> getAllRoomTypes() {
       List<String> roomTypes = roomService.getAllRoomTypes();
       return roomTypes;
    }

    @GetMapping("/rooms/get-all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            byte[] photoByte = roomService.getRoomPhotoByRoomId(room.getRoomId());
            if(photoByte != null && photoByte.length > 0) {
                String base64String = Base64.getEncoder().encodeToString(photoByte);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64String);
                roomResponses.add(roomResponse);

            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = bookingService.getAllBookingsByRoomId(room.getRoomId());
        List<BookingResponse> bookingResponses = bookings.
                stream().
                map(booking -> new BookingResponse(booking.getBookingId(), booking.getCheckInDate(), booking.getCheckOutDate(), booking.getBookingConfirmationCode())).toList();
        byte[] photoByte = null;
        Blob photoBlob = room.getPhoto();
        if(photoBlob != null) {
            try{
                photoByte = photoBlob.getBytes(1, (int) photoBlob.length());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new RoomResponse(room.getRoomId(), room.getRoomType(), room.getRoomPrice(), photoByte, bookingResponses );
    }


    @PutMapping("/rooms/update-room/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable int roomId,
                                                   @RequestParam(required = false) String roomType,
                                                   @RequestParam(required = false) int roomPrice,
                                                   @RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {
        byte[] photoByte = photo.getBytes();
        Blob photoBlob = new SerialBlob(photoByte);
        Room room = roomService.updateRoom(roomId, roomType, roomPrice, photoByte);
        room.setPhoto(photoBlob);
        RoomResponse roomResponse = getRoomResponse(room);
        return ResponseEntity.ok(roomResponse);

    }


    @DeleteMapping("rooms/delete-room/{roomId}")
    public ResponseEntity<Void> deleteRoomByRoomId(@PathVariable int roomId) {
        roomService.deleteRoom(roomId);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("rooms/get-room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomByRoomId(@PathVariable int roomId) {
        Optional<Room> room = roomService.getRoomById(roomId);
        if(room.isPresent()) {
            room.map(theRoom -> {
                RoomResponse roomResponse = getRoomResponse(theRoom);
                return ResponseEntity.ok(Optional.of(roomResponse));
            });
        }
        return ResponseEntity.ok(Optional.empty());
    }
}
