package com.zzs.hotelbookingsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private int bookedRoomID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestName;
    private String guestEmail;
    private String guestPhone;
    private int guestNo;
    private String bookingConfirmationCode;

    public BookingResponse(int bookedRoomID, LocalDate checkInDate, LocalDate checkOutDate, String bookingConfirmationCode) {
        this.bookedRoomID = bookedRoomID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
