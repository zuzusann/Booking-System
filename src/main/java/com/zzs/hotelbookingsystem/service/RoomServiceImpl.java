package com.zzs.hotelbookingsystem.service;

import com.zzs.hotelbookingsystem.entity.Room;
import com.zzs.hotelbookingsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    @Override
    public Room addnewRoom(MultipartFile photo, String roomType, int roomPrice) {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if(photo!=null){
            byte[] photoByte = null;
            try {
                photoByte = photo.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Blob photoBlob = null;
            try {
                photoBlob = new SerialBlob(photoByte);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }
}
