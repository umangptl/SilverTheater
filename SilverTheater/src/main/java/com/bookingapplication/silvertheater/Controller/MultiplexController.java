package com.bookingapplication.silvertheater.Controller;

import com.bookingapplication.silvertheater.Model.Multiplex;
import com.bookingapplication.silvertheater.Service.MultiplexService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/multiplex")
public class MultiplexController {

    @Autowired
    private MultiplexService multiplexService;
    private static final Logger logger = LoggerFactory.getLogger(MultiplexController.class);

    @PostMapping("/addMultiplex")
    public ResponseEntity<Multiplex> createMultiplex(@RequestBody Multiplex multiplex) throws GlobalException {
        try {
            logger.info("Received Multiplex: {}", multiplex);
            multiplexService.createMultiplex(multiplex);
            return new ResponseEntity<>(multiplex, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error adding multiplex: {}", multiplex.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/getMultiplexByID{id}")
    public ResponseEntity<Multiplex> getMultiplexByID(@PathVariable Long id) throws GlobalException{
        try {
            logger.info("Getting multiplex with id: {}", id);
            Multiplex multiplex = multiplexService.getMultiplexByID(id);
            logger.info("Found multiplex with id: {}", id);
            return new ResponseEntity<>(multiplex, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting multiplex with id: {}", id);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/getAllMultiplexLocations")
    public ResponseEntity<List<String>> getAllMultiplexLocations() throws GlobalException {
        try {
            logger.info("Getting all multiplex locations");
            List<String> locations = multiplexService.getAllMultiplexLocations();
            logger.info("Found {} multiplex locations", locations.size());
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting all multiplex locations");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/getMultiplexByLocation{location}")
    public ResponseEntity<List<Multiplex>> getMultiplexByLocation(@PathVariable String location) throws GlobalException{
        try {
            logger.info("Getting multiplex with location: {}", location);
            List<Multiplex> multiplex = multiplexService.getMultiplexByLocation(location);
            logger.info("Found multiplex with location: {}", location);
            return new ResponseEntity<>(multiplex, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting multiplex with location: {}", location);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/getAllMultiplex")
    public ResponseEntity<List<Multiplex>> getAllMultiplex() throws GlobalException {
        try {
            logger.info("Getting all multiplex");
            List<Multiplex> multiplexList = multiplexService.getAllMultiplex();
            logger.info("Found {} multiplex", multiplexList.size());
            return new ResponseEntity<>(multiplexList, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting multiplex");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }


    @DeleteMapping("/deleteMultiplex{id}")
    public ResponseEntity<Multiplex> deleteMultiplex(@PathVariable long id) throws GlobalException{
        try {
            logger.info("Deleting multiplexid: {}", id);
            Multiplex multiplex= multiplexService.getMultiplexByID(id);
            multiplexService.deleteMutiplex(multiplex);
            logger.info("Deleted multiplexid: {}", multiplex);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            logger.error("Error deleting multiplexid: {}", id);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    // Update a multiplex
    @PutMapping("/updateMultiplex/{id}")
    public ResponseEntity<Multiplex> updateMultiplex(@PathVariable long id, @RequestBody Multiplex multiplex) throws GlobalException{
        try {
            logger.info("Updating multiplexid: {}", id);

            Multiplex multiplexupdate = multiplexService.getMultiplexByID(id);
            if (multiplexupdate.getLocation() != null || multiplexupdate.getName() !=null) {
                multiplexupdate.setLocation(multiplex.getLocation());
                multiplexupdate.setName(multiplex.getName());

                multiplexService.updateMultiplex(multiplexupdate);
                logger.info("Updated multiplex: {}", multiplex);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception exception) {
            logger.error("Error updating multiplex: {}", id);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

}
