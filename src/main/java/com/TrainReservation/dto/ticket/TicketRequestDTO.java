package com.TrainReservation.dto.ticket;

import com.TrainReservation.entity.Seat;
import com.TrainReservation.support.TicketType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class TicketRequestDTO {
    @NotNull
    private Double ticketPrice;

    @NotNull
    private Seat seat;

    @NotNull
    private String ticketDeparture;

    @NotNull
    private String ticketDestination;

    @NotNull
    private LocalDate departureDate;

    @NotNull
    private LocalDate arrivalDate;

    @NotNull
    private Double travelDuration;

    @NotNull
    private boolean isRoundTrip;

    @NotNull
    private TicketType type;
}
