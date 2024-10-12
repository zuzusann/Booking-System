package com.zzs.hotelbookingsystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestName;
    private int guestPhoneNumber;
    private String guestEmail;
    private int numberOfGuests;
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

}
