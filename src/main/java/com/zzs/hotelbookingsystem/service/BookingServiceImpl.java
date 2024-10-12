package com.zzs.hotelbookingsystem.service;

import com.zzs.hotelbookingsystem.entity.BookedRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(int roomId) {
        return null;
    }
}
