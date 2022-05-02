package com.TrainReservation.dto.ticket;

import com.TrainReservation.entity.Seat;
import com.TrainReservation.entity.Ticket;
import com.TrainReservation.support.TicketType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter

public class TicketDetailDTO {
    private long ticketId;
    private Double ticketPrice;
    private Seat seat;
    private String ticketDeparture;
    private String ticketDestination;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Double travelDuration;
    private boolean isRoundTrip;
    private TicketType type;

    public TicketDetailDTO(Ticket ticket) {
        this.ticketId = ticket.getTicketID();
        this.ticketPrice = ticket.getTicketPrice();
        this.seat = ticket.getSeat();
        this.ticketDeparture = ticket.getTicketDepature();
        this.ticketDestination = ticket.getTicketDestination();
        this.departureDate = ticket.getDepatureDate();
        this.arrivalDate = ticket.getArrivalDate();
        this.travelDuration = ticket.getTravelDuration();
        this.isRoundTrip = ticket.isRoundTrip();
        this.type = ticket.getType();
    }
}
