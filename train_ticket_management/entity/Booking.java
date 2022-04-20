package com.example.train_ticket_management.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingID", nullable = false)
    private long bookingID;

    @Column(name = "isBooked", nullable = false)
    private boolean isBooked;

    @Column(name = "bookingDate", nullable = false)
    private Timestamp bookingDate;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    @OneToOne
    private Ticket ticket;

    public Booking(boolean isBooked, User user, Ticket ticket) {
        this.isBooked = isBooked;
        this.bookingDate = Timestamp.from(Instant.now());
        this.user = user;
        this.ticket = ticket;
    }

    public Booking(Ticket ticket, boolean isBooked, String guestEmail, String guestPhoneNumb) {
        this.isBooked = isBooked;
        this.bookingDate =Timestamp.from(Instant.now());
        this.user = new User(guestEmail, guestPhoneNumb);
        this.ticket = ticket;
    }

    public long getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        this.isBooked = booked;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
