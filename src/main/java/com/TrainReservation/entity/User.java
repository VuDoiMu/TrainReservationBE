package com.TrainReservation.entity;

import com.TrainReservation.dto.user.UserCreateRequestDTO;
import com.TrainReservation.payload.request.SignupRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String ava;

    private String fullName;

    private String username;

    private String email;

    private String phoneNumb;
    @JsonIgnore
    private String password;

    private String address;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Booking> bookings;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @JsonIgnore
    private int passwordResetCode;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    private LocalDateTime deletedAt;


    public User(SignupRequest signUpRequest) {
        this.fullName = signUpRequest.getFullName();
        this.username = signUpRequest.getUsername();
        this.email = signUpRequest.getEmail();
        this.address = signUpRequest.getAddress();
    }

    public User(UserCreateRequestDTO userCreateRequestDTO) {
        this.fullName = userCreateRequestDTO.getFullName();
        this.username = userCreateRequestDTO.getUsername();
        this.email = userCreateRequestDTO.getEmail();
    }

    public User(String guestEmail, String guestMobile){
        this.fullName = "Guest User!";
        this.email = guestEmail;
        this.phoneNumb = guestMobile;
    }


}
