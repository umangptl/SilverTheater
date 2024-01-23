package com.bookingapplication.silvertheater.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long showTimeID;
    @ManyToOne
    @JoinColumn(name = "movieID")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "multiplexID")
    private Multiplex multiplex;

    private int numbSeats;

    private Date date; // Date and time of the screening

    private double price; // add discount for Tuesday before 6pm

    public ShowTime(Movie movie, Multiplex multiplex, int numbSeats, Date date, double price) {
        this.movie = movie;
        this.multiplex = multiplex;
        this.numbSeats = numbSeats;
        this.date = date;
        this.price = price;
    }
}