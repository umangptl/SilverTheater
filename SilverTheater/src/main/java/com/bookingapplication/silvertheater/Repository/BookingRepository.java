package com.bookingapplication.silvertheater.Repository;

import com.bookingapplication.silvertheater.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByAppUser_AppUserID(Long appUserID);
    List<Booking> findByShowTime_ShowTimeID(Long showTimeID);
    // to handle overbooking
    @Query("SELECT SUM(b.numberOfTickets) FROM Booking b WHERE b.showTime.showTimeID = :showtimeId")
    Integer sumNumberOfTicketsByShowTimeId(Long showtimeId);

}
