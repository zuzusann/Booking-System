package com.zzs.hotelbookingsystem.service;

import com.zzs.hotelbookingsystem.entity.Room;
import com.zzs.hotelbookingsystem.exception.InternalServerException;
import com.zzs.hotelbookingsystem.exception.ResourceNotFoundException;
import com.zzs.hotelbookingsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
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
    public Room addNewRoom(String roomType, int roomPrice, MultipartFile photo) {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if(!photo.isEmpty()){
            byte[] photoBytes = null;
            try {
                photoBytes = photo.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Blob photoBlob = null;
            try {
                photoBlob = new SerialBlob(photoBytes);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.getAllRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(int roomId) throws SQLException {
        Optional<Room> room = roomRepository.findById(roomId);
        if(room.isPresent()){
            Blob photoBlob = room.get().getPhoto();
            if(photoBlob != null){
                return photoBlob.getBytes(1, (int) photoBlob.  length());
            }
        }
        return null;
    }

    @Override
    public void deleteRoom(int roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if(room.isPresent()){
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public Room updateRoom(int roomId, String roomType, int roomPrice, byte[] photoByte) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));
        if(roomType != null){
            room.setRoomType(roomType);
        }
        if(roomPrice > 0){
            room.setRoomPrice(roomPrice);
        }
        if(photoByte != null && photoByte.length > 0){
            try{
               room.setPhoto(new SerialBlob(photoByte));
            }catch(SQLException e){
                throw new InternalServerException("Error updating room");
            }
        }
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(int roomId) {
        return roomRepository.findById(roomId);
    }


}
