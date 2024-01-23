package com.bookingapplication.silvertheater.Model;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long movieID;
    private String title;
    private int duration; // In minutes
    private String genre; //Single word
    @Temporal(TemporalType.DATE)
    private Date releaseDate; // Format: YYYY-MM-DD
    @NotNull // Adding @NotNull annotation to ensure backgroundImg is not null
    private String backgroundImg; // Background image URL

}
