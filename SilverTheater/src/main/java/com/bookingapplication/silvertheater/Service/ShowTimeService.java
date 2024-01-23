package com.bookingapplication.silvertheater.Service;

import com.bookingapplication.silvertheater.Model.ShowTime;
import com.bookingapplication.silvertheater.exception.GlobalException;

import java.util.List;

public interface ShowTimeService {
    ShowTime getShowtimeById(Long showtimeId) throws GlobalException;

    List<ShowTime> getAllShowTimes() throws GlobalException;

    List<ShowTime> getShowtimeByMovieId(Long movieId) throws GlobalException;

    void addShowtime(ShowTime showtime) throws GlobalException;

    ShowTime updateShowtime(ShowTime showtime) throws GlobalException;

    void deleteShowtime(Long showtimeId) throws GlobalException;

    List<ShowTime> getLatestShowTimeBydate() throws GlobalException;

    List<ShowTime> getShowtimeByMultiplexLocation(String location) throws GlobalException;

    List<ShowTime> getShowTimesByMultiplexAndMovieTitle(String multiplexName, String movieTitle) throws GlobalException;

    Long getCountOfShowtimeIDsForMovieWithTitle(long movieID) throws GlobalException;
}
