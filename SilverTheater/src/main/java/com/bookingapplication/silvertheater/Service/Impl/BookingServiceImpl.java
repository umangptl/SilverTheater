package com.bookingapplication.silvertheater.Service.Impl;

import com.bookingapplication.silvertheater.Model.Booking;
import com.bookingapplication.silvertheater.Model.ShowTime;
import com.bookingapplication.silvertheater.Repository.BookingRepository;
import com.bookingapplication.silvertheater.Repository.ShowTimeRepository;
import com.bookingapplication.silvertheater.Service.BookingService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    ShowTimeRepository showTimeRepository;
    Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    //for employee
    @Override
    public List<Booking> getAllBookings() throws GlobalException {
        try {
            return bookingRepository.findAll();
        } catch (Exception exception) {
            logger.error("Error getting bookings");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    // for employee or user to edit booking
    @Override
    public Booking getBookingById(Long bookingId) throws GlobalException {
        try {
            return bookingRepository.findById(bookingId).get();
        } catch (Exception exception) {
            logger.error("Error getting booking with id: {}", bookingId);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public void createBooking(Booking booking) throws GlobalException {
        try {
            ShowTime showTime = booking.getShowTime();
            Integer totalTicketsBooked = bookingRepository.sumNumberOfTicketsByShowTimeId(showTime.getShowTimeID());

            if (totalTicketsBooked == null) {
                totalTicketsBooked = 0; // No bookings found for this showtime
            }
            int availableSeats = showTime.getNumbSeats() - totalTicketsBooked;
            if (booking.getNumberOfTickets() <= availableSeats) {
                bookingRepository.save(booking);
            } else {
                // Handle overbooking case
                throw new GlobalException("Showtime is full please select another showtime", null);
            }
        } catch (Exception exception) {
            logger.error("Error adding booking/showtimefull: {}", booking.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public Booking updateBooking(Booking booking) throws GlobalException {
        try {
            return bookingRepository.save(booking);
        } catch (Exception exception) {
            logger.error("Error updating booking: {}", booking.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public void deleteBooking(Long bookingId) throws GlobalException {
        try {
            bookingRepository.deleteById(bookingId);
        } catch (Exception exception) {
            logger.error("Error deleting booking with id: {}", bookingId);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    // for user account (history and active bookings)
    @Override
    public List<Booking> getBookingsByAppUserId(Long appUserID) throws GlobalException {
        try {
            return bookingRepository.findByAppUser_AppUserID(appUserID);
        } catch (Exception exception) {
            logger.error("Error getting booking with id: {}", appUserID);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    // for employee during checkin
    @Override
    public List<Booking> getBookingsByShowtimeId(Long showTimeID) throws GlobalException {
        try {
            return bookingRepository.findByShowTime_ShowTimeID(showTimeID);
        } catch (Exception exception) {
            logger.error("Error getting booking with id: {}", showTimeID);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public long countNumTicketsByShowTimeId(Long showtimeID) throws GlobalException {
        try {
            Integer seats=  bookingRepository.sumNumberOfTicketsByShowTimeId(showtimeID);
            if (seats == null) {
                seats = 0; // No bookings found for this showtime
            }
            return seats;
        } catch (Exception exception) {
            logger.error("Error getting booking with id: {}", showtimeID);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
}
