package com.bookingapplication.silvertheater.Service.Impl;

import com.bookingapplication.silvertheater.Model.ShowTime;
import com.bookingapplication.silvertheater.Repository.ShowTimeRepository;
import com.bookingapplication.silvertheater.Service.ShowTimeService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {
    @Autowired
    ShowTimeRepository showtimeRepository;

    Logger logger = LoggerFactory.getLogger(ShowTimeServiceImpl.class);

    @Override
    public ShowTime getShowtimeById(Long showtimeId) throws GlobalException {
        try {
            return showtimeRepository.findById(showtimeId).get();
        } catch (Exception exception) {
            logger.error("Error getting showtime with id: {}", showtimeId);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public List<ShowTime> getAllShowTimes() throws GlobalException {
        try {
            return showtimeRepository.findAll();
        } catch (Exception exception) {
            logger.error("Error getting showtimes");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }


    @Override
    public List<ShowTime> getShowtimeByMovieId(Long movieId) throws GlobalException {
        try {
            return showtimeRepository.findByMovie_MovieID(movieId);
        } catch (Exception exception) {
            logger.error("Error getting showtime with movie id: {}", movieId);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public void addShowtime(ShowTime showtime) throws GlobalException {
        try {
            showtimeRepository.save(showtime);
        } catch (Exception exception) {
            logger.error("Error adding showtime: {}", showtime.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public ShowTime updateShowtime(ShowTime showtime) throws GlobalException {
        try {
            return showtimeRepository.save(showtime);
        } catch (Exception exception) {
            logger.error("Error updating showtime: {}", showtime.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public void deleteShowtime(Long showtimeId) throws GlobalException {
        try {
            showtimeRepository.deleteById(showtimeId);
        } catch (Exception exception) {
            logger.error("Error deleting showtime with id: {}", showtimeId);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public List<ShowTime> getLatestShowTimeBydate() throws GlobalException {
        try {
            return showtimeRepository.findLatestShowTimeBydate();
        } catch (Exception exception) {
            logger.error("Error getting latest showtime");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
    @Override
    public List<ShowTime> getShowtimeByMultiplexLocation(String multiplexName) throws GlobalException {
        try {
            List<ShowTime> showTimes = showtimeRepository.findUniqueMovieTitlesByMultiplexLocation(multiplexName);

            if (!showTimes.isEmpty()) {
                // Filter and collect unique movie titles
                Map<String, ShowTime> uniqueShowTimesByMovieTitle = new HashMap<>();

                for (ShowTime showTime : showTimes) {
                    uniqueShowTimesByMovieTitle.putIfAbsent(showTime.getMovie().getTitle(), showTime);
                }

                List<ShowTime> uniqueShowTimesList = new ArrayList<>(uniqueShowTimesByMovieTitle.values());

                return uniqueShowTimesList;
            } else {
                return Collections.emptyList();
            }
        } catch (Exception exception) {
            logger.error("Error getting showtime with multiplex location: {}", multiplexName);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public List<ShowTime> getShowTimesByMultiplexAndMovieTitle(String multiplexName, String movieTitle) throws GlobalException {
        try {
            List<ShowTime> showTimes = showtimeRepository.findShowTimesByMultiplexAndMovieTitle(multiplexName, movieTitle);
            // Sort the showTimes by date
            showTimes.sort(Comparator.comparing(ShowTime::getDate));
            return showTimes;
        } catch (Exception exception) {
            logger.error("Error getting showtime with multiplex location: {}", multiplexName);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public Long getCountOfShowtimeIDsForMovieWithTitle(long movieID) throws GlobalException {
        try {
            return showtimeRepository.countShowtimeIDsByMovieTitle(movieID);
        } catch (Exception exception) {
            logger.error("Error getting showtime with movie id: {}", movieID);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
}
