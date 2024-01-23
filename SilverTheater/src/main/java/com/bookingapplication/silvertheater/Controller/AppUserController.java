package com.bookingapplication.silvertheater.Controller;

import com.bookingapplication.silvertheater.Model.AppUser;
import com.bookingapplication.silvertheater.Model.Enum.AppUserRole;
import com.bookingapplication.silvertheater.Model.Enum.MembershipType;
import com.bookingapplication.silvertheater.Service.AppUserService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @GetMapping("/getUser/{username}")
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username) throws GlobalException {
        try{
            logger.info("Getting user with username: {}", username);
            AppUser user = appUserService.loadUserByUsername(username);
            logger.info("Found user with username: {}", username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting user with username: {}", username);
            throw new GlobalException(exception.getMessage(), exception);
        }

    }

    @PostMapping("/addAppUser")
    public ResponseEntity<AppUser> createAppUser(@RequestBody AppUser appUser) throws GlobalException {
        try {
            logger.info("Registering User: {}", appUser);
            appUserService.createAppUser(appUser);
            logger.info("Registered User: {}", appUser);

            return new ResponseEntity<>(appUser, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error adding movie: {}", appUser.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/user/details")
    public ResponseEntity<AppUser> getCurrentUserDetails(@AuthenticationPrincipal UserDetails userDetails) throws GlobalException{
        // Now you can use userDetails to get username and roles
        try {
            //logger.info(userDetails.getUsername());
            AppUser user = appUserService.loadUserByUsername(userDetails.getUsername());
            logger.info(user.getUserName());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error checking user: ");
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
    // for employee role only
    @PutMapping("/updateAppUser/{userName}")
    public ResponseEntity<AppUser> updateAppUser(@PathVariable String userName) throws GlobalException {
        try {
            logger.info("Updating User: {}", userName);
            AppUser appUser = appUserService.getbyUsername(userName);
            String role = "THEATRE_EMPLOYEE";
            appUser.setRole(AppUserRole.valueOf(role));
            appUserService.updateAppUser(appUser);
            logger.info("Updated User: {}", appUser);
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error updating user: {}", userName);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
    // for membership type change or anything
    @PutMapping("/updateAppUserMembership/{userName}")
    public ResponseEntity<AppUser> updateAppUserMembership(@PathVariable String userName,
                                                           @RequestBody String membershipTypeString) throws GlobalException {
        try {
            logger.info("Updating User: {}", userName);
            AppUser appUser = appUserService.getbyUsername(userName);
            MembershipType membershipType = MembershipType.valueOf(membershipTypeString.toUpperCase());
            appUser.setMembershipType(membershipType);
            appUserService.updateAppUser(appUser);
            logger.info("Updated User: {}", appUser);
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error updating user: {}", userName);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @GetMapping("/getAppUser{id}")
    public ResponseEntity<AppUser> getAppUser(@PathVariable Long id) throws GlobalException {
        try {
            logger.info("Getting user with id: {}", id);
            AppUser appUser = appUserService.getbyID(id);
            logger.info("Found user with id: {}", id);
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Error getting user with id: {}", id);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

}
