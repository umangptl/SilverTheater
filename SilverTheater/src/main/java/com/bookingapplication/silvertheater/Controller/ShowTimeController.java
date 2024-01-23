package com.bookingapplication.silvertheater.Controller;

import com.bookingapplication.silvertheater.Model.Movie;
import com.bookingapplication.silvertheater.Model.Multiplex;
import com.bookingapplication.silvertheater.Model.ShowTime;
import com.bookingapplication.silvertheater.Model.Dao.ShowTimeRequest;
import com.bookingapplication.silvertheater.Service.MovieService;
import com.bookingapplication.silvertheater.Service.MultiplexService;
import com.bookingapplication.silvertheater.Service.ShowTimeService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/showtimes")
public class ShowTimeController {

    @Autowired
    ShowTimeService showTimeService;
    @Autowired
    MovieService movieService;
    @Autowired
    MultiplexService multiplexService;

    private static final Logger logger = LoggerFactory.getLogger(ShowTimeController.class);

    @PostMapping("/addShowTime")
    public ResponseEntity<ShowTime> createShowTime(@RequestBody ShowTimeRequest Showtimerequest) throws GlobalException {
        try {
            logger.info("Received ShowTime request: {}", Showtimerequest.toString());

            // Fetch the Movie and Multiplex objects by their IDs or other unique identifiers
            logger.info("Movie ID: {}", Showtimerequest.getMovieID());
            Movie movie = movieService.getMovieById(Showtimerequest.getMovieID());

            logger.info("Movie: {}", movie.toString());
            Multiplex multiplex = multiplexService.getMultiplexByID(Showtimerequest.getMultiplexID());
            logger.info("Multiplex: {}", multiplex.toString());

            // Create a ShowTime object and associate it with Movie and Multiplex
            ShowTime newShowTime = new ShowTime(movie, multiplex, Showtimerequest.getNumbSeats(),
                    Showtimerequest.getDate(), Showtimerequest.getPrice());

            showTimeService.addShowtime(newShowTime);
            return ResponseEntity.ok(newShowTime);
        } catch (Exception exception) {
            logger.error("Error adding showtime: {}", Showtimerequest.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }


    @GetMapping("/getAllShowTimes")
    public ResponseEntity<List<ShowTime>> getAllShowtimes() throws GlobalException {
        try {
            logger.info("Getting all showtimes");
            List<ShowTime> showTimes = showTimeService.getAllShowTimes();
            logger.info("Found {} showtimes", showTimes.size());
            return ResponseEntity.ok(showTimes);
        } catch (Exception exception) {
            logger.error("Error getting showtimes", exception);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/getShowTime{id}")
    public ResponseEntity<ShowTime> getShowTimeById(@PathVariable Long id) throws GlobalException {
        try {
            logger.info("Getting showtime with id: {}", id);
            ShowTime showTime = showTimeService.getShowtimeById(id);
            return ResponseEntity.ok(showTime);
        } catch (Exception exception) {
            logger.error("Error getting showtime with id: {}", id);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/getShowTimeByMovieName/{movieTitle}")
    public ResponseEntity<List<ShowTime>> getShowtimeByMovieName(@PathVariable String movieTitle) throws GlobalException {
        try {
            logger.info("Getting showtime with movie title: {}", movieTitle);
            Movie movie = movieService.getMovieByTitle(movieTitle);
            logger.info("Found movie: {}", movie.toString());
            List<ShowTime> showTimes = showTimeService.getShowtimeByMovieId(movie.getMovieID());
            return ResponseEntity.ok(showTimes);
        } catch (Exception exception) {
            logger.error("Error getting showtime with movie title: {}", movieTitle);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }


    // return all showtimes for a multiplex but does not repeat the movie
    @GetMapping("/getShowTimeByMultiplexName/{multiplexName}")
    public ResponseEntity<List<ShowTime>> getShowtimeByMultiplexName(@PathVariable String multiplexName) {
        try {
            logger.info("Getting showtimes for multiplex: {}", multiplexName);
            List<ShowTime> showTimes = showTimeService.getShowtimeByMultiplexLocation(multiplexName);
            logger.info("Found {} showtimes for multiplex: {}", showTimes.size(), multiplexName);
            return ResponseEntity.ok(showTimes);
        } catch (Exception e) {
            logger.error("Error getting showtimes for multiplex: {}", multiplexName);
            return ResponseEntity.notFound().build();
        }
    }

    // method can be made super short if we use different methods to sort like distinct movie title and sort dates and stuff
    @GetMapping("/getShowTimes/{multiplexName}/{movieTitle}")
    public ResponseEntity<List<ShowTime>> getShowTimes(@PathVariable String multiplexName, @PathVariable String movieTitle) {
        try {
            logger.info("Getting showtimes for multiplex: {} and movie: {}", multiplexName, movieTitle);
            List<ShowTime> showTimesForMovie = showTimeService.getShowTimesByMultiplexAndMovieTitle(multiplexName, movieTitle);
            logger.info("Found {} showtimes for multiplex: {} and movie: {}", showTimesForMovie.size(), multiplexName, movieTitle);
            return ResponseEntity.ok(showTimesForMovie);
        } catch (Exception e) {
            logger.error("Error getting showtimes for multiplex: {} and movie: {}", multiplexName, movieTitle);
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/deleteShowTime/{id}")
    public ResponseEntity<Void> deleteShowTime(@PathVariable Long id) {
        try {
            logger.info("Deleting showtime with id: {}", id);
            ShowTime showTime = showTimeService.getShowtimeById(id);

            if (showTime != null) {
                showTimeService.deleteShowtime(id);
                logger.info("Deleted showtime with id: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error deleting showtime with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateShowtime/{showtimeID}")
    public ResponseEntity<Void> updateShowtime(@PathVariable Long showtimeID, @RequestBody ShowTime showTimeRequest) {
        try {
            // Retrieve the showtime by ID
            ShowTime showtimeToUpdate = showTimeService.getShowtimeById(showtimeID);

            if (showtimeToUpdate != null) {
                // Update the showtime object with the new values
                showtimeToUpdate.setMovie(showtimeToUpdate.getMovie());
                showtimeToUpdate.setMultiplex(showtimeToUpdate.getMultiplex());
                showtimeToUpdate.setNumbSeats(showtimeToUpdate.getNumbSeats());
                showtimeToUpdate.setDate(showTimeRequest.getDate());
                showtimeToUpdate.setPrice(showTimeRequest.getPrice());

                // Call a service method to save the updated showtime
                showTimeService.updateShowtime(showtimeToUpdate);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any errors and return an error response
            logger.error("Error updating showtime with id: {}", showtimeID);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/latestShowTime")
    public ResponseEntity<List<ShowTime>> getLatestShowTime() throws GlobalException {
        try {
            logger.info("Getting the latest showtime by release date");
            List<ShowTime> latestShowTimes = showTimeService.getLatestShowTimeBydate();
            logger.info("Found {} latest showtimes", latestShowTimes.size());
            return ResponseEntity.ok(latestShowTimes);
        } catch (Exception exception) {
            logger.error("Error getting latest showtimes");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
    // for stats to test yet
    @GetMapping("/countByMovieTitle/{movieTitle}")
    public ResponseEntity<Long> getCountOfShowtimeIDsForMovieWithTitle(@PathVariable String movieTitle) throws GlobalException {
        try {
            logger.info("Getting the count of showtimes for movie title: {}", movieTitle);
            long movieId = movieService.getMovieByTitle(movieTitle).getMovieID();
            long count = showTimeService.getCountOfShowtimeIDsForMovieWithTitle(movieId);
            logger.info("Found {} showtimes for movie title: {}", count, movieTitle);
            return ResponseEntity.ok(count);
        } catch (Exception exception) {
            logger.error("Error getting count of showtimes for movie title: {}", movieTitle);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

}
