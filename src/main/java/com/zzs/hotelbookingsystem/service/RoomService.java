package com.zzs.hotelbookingsystem.service;

import com.zzs.hotelbookingsystem.entity.Room;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface RoomService {


    Room addnewRoom(MultipartFile photo, String roomType, int roomPrice);
}
