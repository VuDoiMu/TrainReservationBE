package com.TrainReservation.dto.user;

import com.TrainReservation.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserShortInfoDTO {
    private Long id;
    private String ava;
    private String fullName;
    private String username;

    private String email;
    private String address;

    private long role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public UserShortInfoDTO(User user) {
        this.id = user.getId();
        this.ava = user.getAva();
        this.fullName = user.getFullName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.role = user.getRole().getId();
        this.createdAt = user.getCreatedDate();
        this.updatedAt = user.getUpdatedDate();
    }

}

