package com.zzs.hotelbookingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private int roomPrice;
    private String roomType;
    private boolean bookingAvailable = false;
    @Lob
     Blob photo;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedRoom> bookings;

    public Room() {
        this.bookings  = new ArrayList<>();
    }

    public void addBookedRoom(BookedRoom bookedRoom) {
       if(bookedRoom != null) {
           bookings.add(bookedRoom);
           bookedRoom.setRoom(this);
           bookingAvailable = true;
           String bookingCode = RandomStringUtils.randomNumeric(6);
           bookedRoom.setBookingConfirmationCode(bookingCode);

       }
    }

}
