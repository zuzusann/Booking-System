package com.zzs.hotelbookingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    private String roomType;
    private int roomPrice;
    @Lob
    private Blob photo;
    private boolean roomAvailable = true;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "room")
    private List<BookedRoom> bookings;


    public Room() {
        this.bookings = new ArrayList<BookedRoom>();
    }

    public void addBookedRoom(BookedRoom bookedRoom) {
        if(!bookings.contains(bookedRoom)){
            bookings.add(bookedRoom);
            roomAvailable = false;
            String bookingCode = RandomStringUtils.randomNumeric(10);
            bookedRoom.setBookingConfirmationCode(bookingCode);
        }
    }
}
