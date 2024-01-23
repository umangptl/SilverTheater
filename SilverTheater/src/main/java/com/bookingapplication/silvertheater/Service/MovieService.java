package com.bookingapplication.silvertheater.Service;

import com.bookingapplication.silvertheater.Model.Movie;
import com.bookingapplication.silvertheater.exception.GlobalException;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovies() throws GlobalException;

    Movie getMovieById(Long movieId) throws GlobalException;

    void createMovie(Movie movie) throws GlobalException;

    Movie updateMovie(Movie movie) throws GlobalException;

    void deleteMovie(String title) throws GlobalException;

    List<Movie> getLatestMovieByReleaseDate() throws GlobalException;

    List<Movie> findUpcomingMoviesByReleaseDate()throws GlobalException;

    String fetchBackdropPath(String movieTitle) throws GlobalException;

    Movie getMovieByTitle(String title) throws GlobalException;

}
