package com.bookingapplication.silvertheater.Service;

import com.bookingapplication.silvertheater.Model.AppUser;
import com.bookingapplication.silvertheater.exception.GlobalException;

public interface AppUserService {
    AppUser loadUserByUsername(String username) throws GlobalException;

    void createAppUser(AppUser appUser) throws GlobalException;

    void updateAppUser(AppUser appUser) throws GlobalException;

    AppUser getbyUsername(String userName) throws GlobalException;

    AppUser getbyID(long appUserID)throws GlobalException;

    AppUser getbyEmail(String email) throws GlobalException;
}
