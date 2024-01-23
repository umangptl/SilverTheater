package com.bookingapplication.silvertheater.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long BookingID;
    @ManyToOne
    @JoinColumn(name = "appUserID")
    private AppUser appUser;

    //non user booking
    private String email ;

    @ManyToOne
    @JoinColumn(name = "showTimeID")
    private ShowTime showTime;
    private double totalPrice;
    private int numberOfTickets;

    public Booking(AppUser appUser, ShowTime showTime, double totalPrice, int numberOfTickets) {
        this.appUser = appUser;
        this.showTime = showTime;
        this.totalPrice = totalPrice;
        this.numberOfTickets = numberOfTickets;
    }

    public Booking(String email, ShowTime showtime, double totalPrice, int numberOfTickets ) {
        this.email = email;
        this.showTime = showtime;
        this.totalPrice = totalPrice;
        this.numberOfTickets = numberOfTickets;
    }
  }