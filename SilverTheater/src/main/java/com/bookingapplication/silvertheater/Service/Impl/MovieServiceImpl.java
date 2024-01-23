package com.bookingapplication.silvertheater.Service.Impl;

import com.bookingapplication.silvertheater.Model.Movie;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.bookingapplication.silvertheater.Repository.MovieRepository;
import com.bookingapplication.silvertheater.Service.MovieService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${movie.api.key}")
    private String apiKey;

    @Value("${movie.api.base-url}")
    private String apiBaseUrl;
    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Override
    public List<Movie> getAllMovies() throws GlobalException {
        try {
            return movieRepository.findAll();
        } catch (Exception exception) {
            logger.error("Error getting movies");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public Movie getMovieById(Long movieId) throws GlobalException {
        try {
            return movieRepository.findById(movieId).get();
        } catch (Exception exception) {
            logger.error("Error getting movie with id: {}", movieId);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
    @Override
    public Movie getMovieByTitle(String title) throws GlobalException {
        try {
            return movieRepository.findByTitle(title);
        } catch (Exception exception) {
            logger.error("Error getting movie with title: {}", title);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public void createMovie(Movie movie) throws GlobalException {
        try {
            movieRepository.save(movie);
        } catch (Exception exception) {
            logger.error("Error adding movie: {}", movie.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public Movie updateMovie(Movie movie) throws GlobalException {
        try {
            // Fetch the existing movie from the database
            Movie existingMovie = movieRepository.findByTitle(movie.getTitle());
            if (existingMovie == null) {
                throw new EntityNotFoundException("Movie with title " + movie.getTitle() + " not found.");
            }

            // Update the fields of the existing movie
           // existingMovie.setBackgroundImg(movie.getBackgroundImg());
            existingMovie.setDuration(movie.getDuration());
            existingMovie.setGenre(movie.getGenre());
            existingMovie.setReleaseDate(movie.getReleaseDate());

            // Save the updated movie back to the database
            return movieRepository.save(existingMovie);
        } catch (EntityNotFoundException e) {
            // Handle the EntityNotFoundException here, you can log it or rethrow it as needed
            throw e;
        } catch (Exception ex) {
            // Handle other exceptions here, you can log them or wrap them in your custom GlobalException
            throw new GlobalException("An error occurred while updating the movie.", ex);
        }
    }


    @Override
    public void deleteMovie(String title) throws GlobalException {
        try {
            Movie movie = movieRepository.findByTitle(title);
            movieRepository.deleteById(movie.getMovieID());
        } catch (EntityNotFoundException e) {
            // Handle the EntityNotFoundException here, you can log it or rethrow it as needed
            throw e;
        } catch (Exception ex) {
            // Handle other exceptions here, you can log them or wrap them in your custom GlobalException
            throw new GlobalException("An error occurred while deleting the movie.", ex);
        }
    }

    @Override
    public List<Movie> getLatestMovieByReleaseDate() throws GlobalException{
        try {
            return movieRepository.findLatestMovieByReleaseDate();
        } catch (Exception exception) {
            logger.error("Error getting latest movie");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public List<Movie> findUpcomingMoviesByReleaseDate()throws GlobalException {
        try {
            return movieRepository.findUpcomingMoviesByReleaseDate();
        } catch (Exception exception) {
            logger.error("Error getting upcoming movies");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }


    @Override
    public String fetchBackdropPath(String movieTitle) throws GlobalException {
        try {
            String encodedMovieTitle = URLEncoder.encode(movieTitle, StandardCharsets.UTF_8.toString());
            String url = apiBaseUrl + "/search/movie";
            HttpHeaders headers = new HttpHeaders();
            headers.set("api_key", apiKey);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("api_key", apiKey)
                    .queryParam("query", encodedMovieTitle);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String backdropPath = jsonNode.path("results").get(0).path("poster_path").asText();

                // Set the backdropPath field in the Movie entity
                Movie movie = movieRepository.findByTitle(movieTitle);
                if (movie != null) {
                    movie.setBackgroundImg(backdropPath);
                    movieRepository.save(movie);
                }

                return backdropPath;
            } else {
                logger.error("Failed to fetch backdrop for movie: {}", movieTitle);
                throw new GlobalException("Failed to fetch backdrop for movie: ", new IOException());
            }
        } catch (Exception exception) {
            logger.error("Error fetching backdrop for movie: {}", movieTitle, exception);
            throw new GlobalException("Error fetching backdrop for movie: " + movieTitle, exception);
        }
    }

}
