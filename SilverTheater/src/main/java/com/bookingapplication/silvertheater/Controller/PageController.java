package com.bookingapplication.silvertheater.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.security.access.prepost.PreAuthorize;

@Controller
public class PageController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @GetMapping("/movieDetails")
    public String showMovieDetails() {
        return "movieDetails";
    }

    @GetMapping("/movieDetails/bookTickets")
    public String showTicketsDetails() {
        return "bookTickets";
    }

    @GetMapping("/membership")
    public String showMembershipPage() {
        return "membership";
    }

    @GetMapping("/recentBookings")
    public String showRecentBookingPage() {
        return "recentBookings";
    }

    @GetMapping("/AdminManagement")
    @PreAuthorize("hasRole('THEATRE_EMPLOYEE')")
    public String showMoviesPage() {
        return "AdminManagement";
    }

    @GetMapping("/AdminManagement/updateRole")
    public String showAdminManagementPage() {
        return "updateRole";
    }

    @GetMapping("AdminManagement/bookingManagement")
    public String showBookingManagementPage() {
        return "bookingManagement";
    }

    @GetMapping("/AdminManagement/addMovie")
    public String showAddMovieForm() {
        return "addMovie";
    }
    @GetMapping("/AdminManagement/updateMovie")
    public String showUpdateMovieForm() {
        return "updateMovie";
    }

    @GetMapping("/AdminManagement/deleteMovie")
    public String showDeleteMovieForm() {
        return "deleteMovie";
    }

    @GetMapping("/AdminManagement/addMultiplex")
    public String showAddMultiplexForm() {
        return "addMultiplex";
    }
    @GetMapping("/AdminManagement/updateMultiplex")
    public String showUpdateMultiplexForm() {
        return "updateMultiplex";
    }

    @GetMapping("/AdminManagement/deleteMultiplex")
    public String showDeleteMultiplexForm() {
        return "deleteMultiplex";
    }

    @GetMapping("/AdminManagement/addShow")
    public String showAddShowForm() {
        return "addShow";
    }

    @GetMapping("/AdminManagement/updateShow")
    public String showUpdateShowForm() {
        return "updateShow";
    }

    @GetMapping("/AdminManagement/deleteShow")
    public String showDeleteShowForm() {
        return "deleteShow";
    }

    @GetMapping("/payments")
    public String showPaymentsForm() {
        return "payments";
    }

    @GetMapping("/summary")
    public String showSummaryPage() {
        return "summary";
    }
}
