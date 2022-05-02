package com.TrainReservation.dto.ticket;

import com.TrainReservation.entity.Seat;
import com.TrainReservation.support.TicketType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter

public class TicketDetailDTO {
    private long ticketId;
    private Double ticketPrice;
    private Seat seat;
    private String ticketDeparture;
    private String ticketDestination;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Double travelDuration;
    private boolean isRoundTrip;
    private TicketType type;
}
