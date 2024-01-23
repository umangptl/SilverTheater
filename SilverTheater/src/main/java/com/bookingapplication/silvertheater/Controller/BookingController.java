package com.bookingapplication.silvertheater.Controller;

import com.bookingapplication.silvertheater.Model.*;
import com.bookingapplication.silvertheater.Model.Dao.BookingRequest;
import com.bookingapplication.silvertheater.Service.AppUserService;
import com.bookingapplication.silvertheater.Service.BookingService;
import com.bookingapplication.silvertheater.Service.ShowTimeService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;
    @Autowired
    ShowTimeService showTimeService;
    @Autowired
    AppUserService appUserService;

    private static final Logger logger = LoggerFactory.getLogger(ShowTimeController.class);

    @GetMapping("/getAllBooking")
    public ResponseEntity<List<Booking>> getAllBooking() throws GlobalException {
        try {
            logger.info("Getting all bookings");
            List<Booking> bookings = bookingService.getAllBookings();
            logger.info("Found {} bookings", bookings.size());
            return ResponseEntity.ok().body(bookings);
        } catch (Exception e) {
            logger.error("Error in getAllBooking", e);
            throw new GlobalException("Error in getAllBooking", e);
        }
    }

    @GetMapping("/getBookingById{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) throws GlobalException {
        try {
            logger.info("Getting booking with id: {}", bookingId);
            Booking booking = bookingService.getBookingById(bookingId);
            return ResponseEntity.ok().body(booking);
        } catch (Exception e) {
            logger.error("Error in getBookingById", e);
            throw new GlobalException("Error in getBookingById", e);
        }
    }

    // might need some edits to handle both user and nonuser by email
    @PostMapping("/createBooking")
    public ResponseEntity<Booking> createBookingNonUser(@RequestBody BookingRequest bookingRequest) throws GlobalException {
        try {
            logger.info("Creating BookingRequest: {}", bookingRequest.toString());
            logger.info("Booking ID: {}", bookingRequest.getShowTimeID());
            ShowTime showtime = showTimeService.getShowtimeById(bookingRequest.getShowTimeID());
            AppUser appUser = appUserService.getbyEmail(bookingRequest.getEmail());
            if(appUser != null){
                Booking booking = new Booking(appUser, showtime, bookingRequest.getTotalPrice(),
                        bookingRequest.getNumberOfTickets());
                bookingService.createBooking(booking);

                int rewardPointsToAdd = (int) (bookingRequest.getTotalPrice() * 1);
                appUser.addRewardPoints(rewardPointsToAdd);
                appUserService.updateAppUser(appUser);

                return ResponseEntity.ok().body(booking);
            }else {
                Booking booking = new Booking(bookingRequest.getEmail(), showtime, bookingRequest.getTotalPrice(),
                        bookingRequest.getNumberOfTickets());
                bookingService.createBooking(booking);
                return ResponseEntity.ok().body(booking);
            }
        } catch (Exception e) {
            logger.error("Error in createBooking", e);
            throw new GlobalException("Error in createBooking", e);
        }
    }


    @PutMapping("/updateBooking/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody Booking booking) throws GlobalException {
        try {
            logger.info("Updating booking with id: {}", bookingId);
            Booking bookingToUpdate = bookingService.getBookingById(bookingId);
            if(bookingToUpdate != null) {
                booking.setShowTime(bookingToUpdate.getShowTime());
                booking.setNumberOfTickets(bookingToUpdate.getNumberOfTickets());
                booking.setTotalPrice(bookingToUpdate.getTotalPrice());
                bookingService.updateBooking(booking);
                logger.info("Updated booking with id: {}", bookingId);
                return ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error in updateBooking", e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteBooking/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) throws GlobalException {
        try {
            logger.info("Deleting booking with id: {}", bookingId);
            Booking booking = bookingService.getBookingById(bookingId);
            if(booking != null) {
                bookingService.deleteBooking(bookingId);
                logger.info("Deleted booking with id: {}", bookingId);
                return ResponseEntity.noContent().build();
            }
            else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error in deleteBooking", e);
            return ResponseEntity.notFound().build();
        }
    }

    // for user history and active bookings
    @GetMapping("/getBookingsByAppUserId/{appUserID}")
    public ResponseEntity<List<Booking>> getBookingsByAppUserId(@PathVariable Long appUserID) throws GlobalException {
        try {
            logger.info("Getting bookings with appUserId: {}", appUserID);
            List<Booking> bookings = bookingService.getBookingsByAppUserId(appUserID);
            logger.info("Found {} bookings", bookings.size());
            return ResponseEntity.ok().body(bookings);
        } catch (Exception e) {
            logger.error("Error in getBookingsByAppUserId", e);
            throw new GlobalException("Error in getBookingsByAppUserId", e);
        }
    }

    // for employee during checkin or stats
    @GetMapping("/getBookingsByShowtimeId/{showTimeID}")
    public ResponseEntity<List<Booking>> getBookingsByShowtimeId(@PathVariable Long showTimeID) throws GlobalException {
        try {
            logger.info("Getting bookings with showTimeID: {}", showTimeID);
            List<Booking> bookings = bookingService.getBookingsByShowtimeId(showTimeID);
            logger.info("Found {} bookings", bookings.size());
            return ResponseEntity.ok().body(bookings);
        } catch (Exception e) {
            logger.error("Error in getBookingsByShowtimeId", e);
            throw new GlobalException("Error in getBookingsByShowtimeId", e);
        }
    }

    @GetMapping("/countByShowtimeId/{showtimeID}")
    public ResponseEntity<Long> countByShowtimeId(@PathVariable Long showtimeID) throws GlobalException {
        try {
            logger.info("Getting bookings with showTimeID: {}", showtimeID);
            long count = bookingService.countNumTicketsByShowTimeId(showtimeID);
            ShowTime showTime = showTimeService.getShowtimeById(showtimeID);
            int setsize = showTime.getNumbSeats();
            Long seatAvailable = (long) (setsize - (int) count);
            logger.info("Found {} bookings", seatAvailable);
            return ResponseEntity.ok().body(seatAvailable);
        } catch (Exception e) {
            logger.error("Error in countByShowtimeId", e);
            throw new GlobalException("Error in countByShowtimeId", e);
        }
    }

}