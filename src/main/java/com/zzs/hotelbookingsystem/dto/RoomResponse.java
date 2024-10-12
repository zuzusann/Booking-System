package com.zzs.hotelbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private int roomId;
    private String roomType;
    private int roomPrice;
    private String photo;
    private List<BookingResponse> bookings;

    public RoomResponse(int roomId, String roomType, int roomPrice) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    //byte[] data type is a good choice for representing image data in  DTO class in Spring Boot. It's efficient, direct, compatible, and can help to protect the data from security vulnerabilities.
    public RoomResponse(int roomId, String roomType, int roomPrice, byte[] photoByte, List<BookingResponse> bookings) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.photo = photoByte != null ? Base64.getEncoder().encodeToString(photoByte) : null;
        this.bookings = bookings;
    }
}
