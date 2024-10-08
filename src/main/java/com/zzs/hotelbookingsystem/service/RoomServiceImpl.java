package com.zzs.hotelbookingsystem.service;

import com.zzs.hotelbookingsystem.dto.RoomResponse;
import com.zzs.hotelbookingsystem.entity.Room;
import com.zzs.hotelbookingsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
            } catch (Exception e) {
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

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.getALLRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(int roomId) throws SQLException {
        //In Java, the Optional class is used to handle situations where a value may or may not be present.
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if(theRoom.isPresent()){
            Blob photoBlob = theRoom.get().getPhoto();
            if(photoBlob!=null){
                return photoBlob.getBytes(1,(int)  photoBlob.length());
            }
        }
        return null;
    }


}
