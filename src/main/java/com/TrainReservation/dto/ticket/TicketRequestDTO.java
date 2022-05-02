package com.TrainReservation.dto.ticket;

import com.TrainReservation.support.TicketType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class TicketRequestDTO {
    @NotNull
    private Double ticketPrice;

    @NotNull
    private int seatId;

    @NotNull
    private String ticketDeparture;

    @NotNull
    private String ticketDestination;

    @NotNull
    private LocalDateTime departureDate;

    @NotNull
    private LocalDateTime arrivalDate;

    @NotNull
    private Double travelDuration;

    @NotNull
    private boolean isRoundTrip;

    @NotNull
    private TicketType type;
}
