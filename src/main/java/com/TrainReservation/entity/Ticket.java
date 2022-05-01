package com.TrainReservation.entity;

import com.TrainReservation.support.DateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;


@Entity
@Table
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tickID", nullable = false)
    private long ticketID;

    @Column(name = "ticketPrice", nullable = false)
    private Double ticketPrice;

    @OneToOne
    private Seat seat;

    @Length(min = 3, max = 45)
    @Column(name = "tickDepature", nullable = false)
    private String ticketDepature;

    @Length(min = 3, max = 45)
    @Column(name = "tickDestination", nullable = false)
    private String ticketDestination;


    @Column(name = "departureDate", nullable = false)
    private Date depatureDate;

    @Column(name = "departureTime", nullable = false)
    private Time departureTime;

    @Column(name = "arrivalDate", nullable = false)
    private Date arrivalDate;

    @Column(name = "arrivalTime", nullable = false)
    private Time arrivalTime;

    @Column(name = "travelDuration", nullable = false)
    private Double travelDuration;

    @Column(name = "isRoundTrip", nullable = false)
    private boolean isRoundTrip;

    public Ticket(Double ticketPrice, Seat seat, String ticketDepature, String ticketDestination, DateTime departure, DateTime arrival, Double travelDuration, boolean isRoundTrip) {
        this.ticketPrice = ticketPrice;
        this.seat = seat;
        this.ticketDepature = ticketDepature;
        this.ticketDestination = ticketDestination;
        this.depatureDate = departure.getDate();
        this.departureTime = departure.getTime();
        this.arrivalDate = arrival.getDate();
        this.arrivalTime = arrival.getTime();
        this.travelDuration = travelDuration;
        this.isRoundTrip = isRoundTrip;
    }
}
