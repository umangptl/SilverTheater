package com.bookingapplication.silvertheater.Service;

import com.bookingapplication.silvertheater.Model.Multiplex;
import com.bookingapplication.silvertheater.exception.GlobalException;

import java.util.List;

public interface MultiplexService {
    void createMultiplex(Multiplex multiplex) throws GlobalException;

    Multiplex getMultiplexByID(Long multiplexID) throws GlobalException;

    List <Multiplex>  getMultiplexByLocation(String location) throws GlobalException;

    List<Multiplex> getAllMultiplex() throws GlobalException;

    void deleteMutiplex(Multiplex multiplex) throws GlobalException;

    List<String> getAllMultiplexLocations() throws GlobalException;

    Multiplex updateMultiplex(Multiplex multiplex) throws GlobalException;
}
