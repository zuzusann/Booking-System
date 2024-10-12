package com.zzs.hotelbookingsystem.repository;

import com.zzs.hotelbookingsystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT DISTINCT r.roomType fROM Room r")
    List<String> getAllRoomTypes();
}
