package com.TrainReservation.entity;

import com.TrainReservation.support.TicketType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
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
    private LocalDateTime depatureDate;

    @Column(name = "arrivalDate", nullable = false)
    private LocalDateTime arrivalDate;

    @Column(name = "travelDuration", nullable = false)
    private Double travelDuration;

    @Column(name = "isRoundTrip", nullable = false)
    private boolean isRoundTrip;

    private TicketType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Booking booking;

}
