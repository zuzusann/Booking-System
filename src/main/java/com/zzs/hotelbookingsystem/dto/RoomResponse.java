package com.zzs.hotelbookingsystem.dto;

import com.zzs.hotelbookingsystem.entity.BookedRoom;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

    private int roomId;
    private String roomType;
    private int roomPrice;
    private boolean bookingAvailable = false;
    private String photo;


    public RoomResponse(int roomId, String roomType, int roomPrice ) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }


}
