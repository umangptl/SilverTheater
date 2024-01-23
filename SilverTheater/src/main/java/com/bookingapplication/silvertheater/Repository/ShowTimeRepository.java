package com.bookingapplication.silvertheater.Repository;

import com.bookingapplication.silvertheater.Model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findByMovie_MovieID(Long movieID);

    @Query("SELECT s FROM ShowTime s WHERE s.date >= CURRENT_DATE ORDER BY s.date ASC")
    List<ShowTime> findLatestShowTimeBydate();

    @Query("SELECT s FROM ShowTime s WHERE s.multiplex.location = :multiplexName")
    List<ShowTime> findUniqueMovieTitlesByMultiplexLocation(String multiplexName);

    @Query("SELECT s FROM ShowTime s WHERE s.multiplex.location = :multiplexName AND s.movie.title = :movieTitle")
    List<ShowTime> findShowTimesByMultiplexAndMovieTitle(String multiplexName, String movieTitle);

    @Query("SELECT COUNT(s.showTimeID) FROM ShowTime s WHERE s.movie.movieID = :movieTitle")
    long countShowtimeIDsByMovieTitle(long movieTitle);

}

