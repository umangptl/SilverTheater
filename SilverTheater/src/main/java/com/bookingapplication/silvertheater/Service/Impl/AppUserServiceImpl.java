package com.bookingapplication.silvertheater.Service.Impl;

import com.bookingapplication.silvertheater.Model.AppUser;
import com.bookingapplication.silvertheater.Repository.AppUserRepository;
import com.bookingapplication.silvertheater.Service.AppUserService;
import com.bookingapplication.silvertheater.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);

    @Override
    public AppUser loadUserByUsername(String username) throws GlobalException {
        try {
            return appUserRepository.findByUserName(username);
        } catch (Exception exception) {
            logger.error("Error getting user with username: {}", username);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public void createAppUser(AppUser appUser) throws GlobalException {
        try {
            appUserRepository.save(appUser);
        } catch (Exception exception) {
            logger.error("Error adding user: {}", appUser.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public void updateAppUser(AppUser appUser) throws GlobalException {
        try {
            if (appUser==null) {
                throw new IllegalArgumentException("Appuser is null; it cannot be updated.");
            }
            appUserRepository.save(appUser);
        } catch (Exception exception) {
            logger.error("Error updating user: {}", appUser.toString());
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

    @Override
    public AppUser getbyUsername(String userName) throws GlobalException {
        try {
            return appUserRepository.findByUserName(userName);
        } catch (Exception exception) {
            logger.error("Error getting user with id: {}", userName);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
    @Override
    public AppUser getbyID(long appUserID) throws GlobalException{
        try {
            return appUserRepository.findById(appUserID).get();
        } catch (Exception exception) {
            logger.error("Error getting user with id: {}", appUserID);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }
    @Override
    public AppUser getbyEmail(String email) throws GlobalException{
        try {
            return appUserRepository.findByEmail(email);
        } catch (Exception exception) {
            logger.error("Error getting user with email: {}", email);
            throw new GlobalException(exception.getMessage(), exception);
        }
    }

}
