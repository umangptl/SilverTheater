package com.bookingapplication.silvertheater.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "multiplexes", uniqueConstraints = {@UniqueConstraint(columnNames = {"Name", "Location"})})
public class Multiplex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long multiplexID;
    private String name;
    private String location;

}