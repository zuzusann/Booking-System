package com.zzs.hotelbookingsystem.service;

import com.zzs.hotelbookingsystem.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    Room addNewRoom(String roomType, int roomPrice, MultipartFile photo) throws IOException, SQLException;


    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    byte[] getRoomPhotoByRoomId(int roomId) throws SQLException;

    void deleteRoom(int roomId);

    Room updateRoom(int roomId, String roomType, int roomPrice, byte[] photoByte);

    Optional<Room> getRoomById(int roomId);
}
