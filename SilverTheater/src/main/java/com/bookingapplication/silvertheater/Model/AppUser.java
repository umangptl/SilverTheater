package com.bookingapplication.silvertheater.Model;

import com.bookingapplication.silvertheater.Model.Enum.AppUserRole;
import com.bookingapplication.silvertheater.Model.Enum.MembershipType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appUserID;
    private String userName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole role; // Member, TheaterEmployee
    @Enumerated(EnumType.STRING)
    private MembershipType membershipType; // Regular or Premium (15$)
    private int rewardsPoints;

    public void addRewardPoints(int pointsToAdd) {
        // Add the specified points to the user's reward points
        this.rewardsPoints += pointsToAdd;
    }


}