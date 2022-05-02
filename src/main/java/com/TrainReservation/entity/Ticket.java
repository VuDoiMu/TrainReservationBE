package com.TrainReservation.entity;

import com.TrainReservation.dto.ticket.TicketRequestDTO;
import com.TrainReservation.support.TicketType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tickID", nullable = false)
    private long ticketID;

    @Column(name = "ticketPrice", nullable = false)
    private Double ticketPrice;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ticket")
    @JsonIgnore
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

    public Ticket(TicketRequestDTO ticketRequestDTO, Seat seat) {
        this.ticketPrice = ticketRequestDTO.getTicketPrice();
        this.seat = seat;
        this.ticketDepature = ticketRequestDTO.getTicketDeparture();
        this.ticketDestination = ticketRequestDTO.getTicketDestination();
        this.depatureDate = ticketRequestDTO.getDepartureDate();
        this.arrivalDate = ticketRequestDTO.getArrivalDate();
        this.travelDuration = ticketRequestDTO.getTravelDuration();
        this.isRoundTrip =ticketRequestDTO.isRoundTrip();
        this.type = ticketRequestDTO.getType();
        this.booking = null;
    }
}
