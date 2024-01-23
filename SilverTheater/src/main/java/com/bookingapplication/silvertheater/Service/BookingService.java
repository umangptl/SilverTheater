package com.bookingapplication.silvertheater.Service;

import com.bookingapplication.silvertheater.Model.Booking;
import com.bookingapplication.silvertheater.exception.GlobalException;

import java.util.List;

public interface BookingService {

    List<Booking> getAllBookings() throws GlobalException;

    Booking getBookingById(Long bookingId) throws GlobalException;

    void createBooking(Booking booking) throws GlobalException;

    Booking updateBooking(Booking booking) throws GlobalException;

    void deleteBooking(Long bookingId) throws GlobalException;

    List<Booking> getBookingsByAppUserId(Long appUserId) throws GlobalException;

    List<Booking> getBookingsByShowtimeId(Long showTimeID) throws GlobalException;

    long countNumTicketsByShowTimeId(Long showtimeID) throws GlobalException;
}
