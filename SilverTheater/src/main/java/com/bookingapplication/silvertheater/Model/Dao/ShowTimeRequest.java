package com.bookingapplication.silvertheater.Model.Dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShowTimeRequest {

    private long movieID;
    private long multiplexID;
    private int numbSeats;
    private Date date;
    @NotNull
    private double price;

}