package com.bookingapplication.silvertheater.Model.Dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingRequest {

    private long appUserID;
    private String email;
    private long showTimeID;
    private double totalPrice;
    private int numberOfTickets;

    public BookingRequest(String email, long showTimeID, double totalPrice, int numberOfTickets ) {
        this.email = email;
        this.showTimeID = showTimeID;
        this.totalPrice = totalPrice;
        this.numberOfTickets = numberOfTickets;
    }
}
