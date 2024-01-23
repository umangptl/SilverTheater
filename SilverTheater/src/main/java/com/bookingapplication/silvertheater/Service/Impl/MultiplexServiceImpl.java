package com.bookingapplication.silvertheater.Service.Impl;

import com.bookingapplication.silvertheater.Model.Multiplex;
import com.bookingapplication.silvertheater.Repository.MultiplexRepository;
import com.bookingapplication.silvertheater.Service.MultiplexService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultiplexServiceImpl implements MultiplexService {

    @Autowired
    MultiplexRepository multiplexRepository;
    Logger logger = LoggerFactory.getLogger(MultiplexServiceImpl.class);

    @Override
    public void createMultiplex(Multiplex multiplex) throws GlobalException {
        try {
            multiplexRepository.save(multiplex);
        } catch (Exception exception) {
            logger.error("Error adding multiplex: {}", multiplex.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
    @Override
    public Multiplex getMultiplexByID(Long multiplexID) throws GlobalException {
        try {
            return multiplexRepository.findById(multiplexID).get();
        } catch (Exception exception) {
            logger.error("Error getting multiplex with name: {}", multiplexID);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public List <Multiplex> getMultiplexByLocation(String location) throws GlobalException {
        try {
            return multiplexRepository.findByLocation(location);
        } catch (Exception exception) {
            logger.error("Error getting multiplex with location: {}", location);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
    @Override
    public List<Multiplex> getAllMultiplex() throws GlobalException {
        try {
            return multiplexRepository.findAll();
        } catch (Exception exception) {
            logger.error("Error getting multiplex");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public void deleteMutiplex(Multiplex multiplex) throws GlobalException {
        try {
            multiplexRepository.delete(multiplex);
        } catch (Exception exception) {
            logger.error("Error deleting multiplex: {}", multiplex.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public List<String> getAllMultiplexLocations() throws GlobalException {
        try {
            return multiplexRepository.findAllLocations();
        } catch (Exception exception) {
            logger.error("Error getting all multiplex locations");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public Multiplex updateMultiplex(Multiplex multiplex) throws GlobalException {
        try {
            // Save the updated multiplex back to the database
            return multiplexRepository.save(multiplex);
        } catch (Exception exception) {
            logger.error("Error updating multiplex: {}", multiplex.toString());
            // Handle other exceptions here, you can log them or wrap them in your custom GlobalException
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

}
