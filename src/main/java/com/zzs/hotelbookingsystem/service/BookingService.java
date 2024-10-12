package com.zzs.hotelbookingsystem.service;

import com.zzs.hotelbookingsystem.entity.BookedRoom;

import java.util.List;

public interface BookingService {
    List<BookedRoom> getAllBookingsByRoomId(int roomId);
}
