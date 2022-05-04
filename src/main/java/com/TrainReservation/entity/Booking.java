package com.TrainReservation.entity;

import com.TrainReservation.support.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingID", nullable = false)
    private long bookingID;

    @Column(name = "isBooked", nullable = false)
    private BookingStatus status;

    @Column(name = "bookingDate", nullable = false)
    private Timestamp bookingDate;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private Set<Ticket> tickets;

    private Double price;


    public Booking(Set<Ticket> tickets, BookingStatus status, String guestEmail, String guestPhoneNumb) {
        this.status = status;
        this.bookingDate =Timestamp.from(Instant.now());
        this.user = new User(guestEmail, guestPhoneNumb);
        this.tickets= tickets;
    }

    public Booking(BookingStatus status, User user, Set<Ticket> tickets){
        this.status = status;
        this.bookingDate = Timestamp.from(Instant.now());
        this.tickets = tickets;
        this.user = user;
        double price = 0;
        for(Ticket ticket: tickets){
            price = price + ticket.getTicketPrice();
        }
        this.price=price;
    }

}
