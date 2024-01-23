package com.bookingapplication.silvertheater.Controller;

import com.bookingapplication.silvertheater.Model.Movie;
import com.bookingapplication.silvertheater.Service.MovieService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    // Create a new movie
    @PostMapping("/addMovie")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws GlobalException {
        try {
            logger.info("Received Movie: {}", movie);
            // Fetch the backdrop URL and set it in the movie entity
            String backdropPath = movieService.fetchBackdropPath(movie.getTitle());
            movie.setBackgroundImg(backdropPath);
            movieService.createMovie(movie);
            logger.info("Added movie: {}", movie);
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error adding movie: {}", movie.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }


    // Get all movies
    @GetMapping("/ViewMovies")
    public ResponseEntity<List<Movie>> getAllMovies() throws GlobalException{
        try {
            logger.info("Getting all movies");
            List<Movie> movies = movieService.getAllMovies();
            logger.info("Found {} movies", movies.size());
            logger.info("Movies: {}", movies);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting all movies");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/getMovie{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) throws GlobalException{
        try{
            logger.info("Getting movie with id: {}", id);
            Movie movie = movieService.getMovieById(id);
            logger.info("Found movie with id: {}", id);
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting movie with id: {}", id);
            throw new GlobalException(exception.getMessage(), exception);
        }

    }

    @GetMapping("/getMovieByTitle{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) throws GlobalException{
        try{
            logger.info("Getting movie with title: {}", title);
            Movie movie = movieService.getMovieByTitle(title);
            logger.info("Found movie with title: {}", title);
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting movie with title: {}", title);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    // Update an existing movie
    @PutMapping("/updateMovie/{title}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String title, @RequestBody Movie movie) throws GlobalException {
        try {
            logger.info("Updating movie with title: {}", title);
            // Check if the movie has a valid ID (assuming it's a primary key)
            if (movie.getTitle() == null) {
                throw new IllegalArgumentException("Movie String is null; it cannot be updated.");
            }

            logger.info("Found movie with title: {}", title);
            return new ResponseEntity<>(movieService.updateMovie(movie), HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error updating movie with title: {}", title);
            throw new GlobalException("An error occurred while updating the movie.", exception);
        }
    }


    // Delete a movie
    @DeleteMapping("/deleteMovie/{title}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable String title) throws GlobalException {
        try {
            logger.info("Deleting movie with title: {}", title);

            Movie movie = movieService.getMovieByTitle(title);
            if (movie != null) {
                movieService.deleteMovie(title);
                logger.info("Deleted movie with title: {}", title);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception exception) {
            logger.error("Error deleting movie with title: {}", title);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }


    @GetMapping("/latestMovie")
    public ResponseEntity<List<Movie>> getLatestMovie() throws GlobalException {
        try{
            logger.info("Getting the latest movie by release date");
            List<Movie> latestMovies = movieService.getLatestMovieByReleaseDate();
            logger.info("Found {} latest movies", latestMovies.size());
            return new ResponseEntity<>(latestMovies, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting the latest movie by release date");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/upcomingMovies")
    public ResponseEntity<List<Movie>> getUpcomingMovies() throws GlobalException {
        try {
            logger.info("Getting the upcoming movies by release date");
            List<Movie> upcomingMovies = movieService.findUpcomingMoviesByReleaseDate();
            logger.info("Found {} upcoming movies", upcomingMovies.size());
            return new ResponseEntity<>(upcomingMovies, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting the upcoming movies by release date");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }


}