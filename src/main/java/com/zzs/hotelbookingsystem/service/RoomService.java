package com.zzs.hotelbookingsystem.service;

import com.zzs.hotelbookingsystem.dto.RoomResponse;
import com.zzs.hotelbookingsystem.entity.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface RoomService {


    Room addnewRoom(MultipartFile photo, String roomType, int roomPrice);

    List<String> getAllRoomTypes();
    
    List<Room> getAllRooms();

    byte[] getRoomPhotoByRoomId(int roomId) throws SQLException;
}
