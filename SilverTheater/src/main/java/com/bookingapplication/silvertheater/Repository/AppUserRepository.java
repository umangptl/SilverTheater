package com.bookingapplication.silvertheater.Repository;

import com.bookingapplication.silvertheater.Model.AppUser;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserName(String username);

    AppUser findByEmail(String email);
}

