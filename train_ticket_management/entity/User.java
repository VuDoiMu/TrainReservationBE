package com.example.train_ticket_management.entity;

import com.example.train_ticket_management.payload.request.SignupRequest;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false)
    private long userID;

    @Length(min=3, max = 45)
    @Column(name = "userName", nullable = false)
    private String userName;

    @Length(min=3, max = 45)
    @Column(name = "userPassword", nullable = false)
    private String userPassword;

    @Length(min=3, max = 45)
    @Column(name = "userFirstName", nullable = true)
    private String userFirstName;

    @Length(min=3, max = 45)
    @Column(name = "userLastName", nullable = true)
    private String userLastName;

    @Length(min=3, max = 45)
    @Column(name = "userEmail", nullable = true)
    private String userEmail;

    @Length(max = 10)
    @Column(name = "userPhone", nullable = true)
    private String userPhone;

    @Column(name = "createdDate", nullable = true)
    private Timestamp createdDate;

    @Column(name = "updateDate", nullable = true)
    private Timestamp updateDate;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> role = new ArrayList<>();

    public User(String guestEmail, String guestPhoneNumb){
        this.userName = "guest";
        this.userPassword = "guest";
        this.userEmail = guestEmail;
        this.userPhone = guestPhoneNumb;

    }

    public User(SignupRequest signUpRequest){
        this.userPhone = signUpRequest.getPhoneNumb();
        this.role = signUpRequest.getRole();
       this.userEmail = signUpRequest.getEmail();
       this.userName = signUpRequest.getUserName();
       this.userFirstName = signUpRequest.getFirstName();
       this.userLastName = signUpRequest.getLastName();
   }

    public User() {

    }

    public Collection<Role> getRole() {
        return role;
    }

    public void setRole(Collection<Role> role) {
        this.role = role;
    }

    public long getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp crDate) {
        this.createdDate = crDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp uptDate) {
        this.updateDate = uptDate;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


}
