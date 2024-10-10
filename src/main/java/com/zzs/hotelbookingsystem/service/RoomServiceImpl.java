package com.zzs.hotelbookingsystem.service;
import com.zzs.hotelbookingsystem.entity.Room;
import com.zzs.hotelbookingsystem.exception.ResourceNotFoundException;
import com.zzs.hotelbookingsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
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

    @Override
    public void deleteRoom(int roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        try{
            roomRepository.deleteById(roomId);
        }catch(Exception e){
            throw new RuntimeException(e);
        }


    }

    @Override
    public Room updateRoom(int roomId, String roomType, int roomPrice, byte[] photoByte) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if(roomType!=null){
            room.setRoomType(roomType);
        }
        if(roomPrice!= 0){
            room.setRoomPrice(roomPrice);
        }
        if(photoByte!=null){
            try{
                room.setPhoto(new SerialBlob(photoByte));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomByRoomId(int roomId) {
        return roomRepository.findById(roomId);
    }


}
