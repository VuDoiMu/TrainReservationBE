package com.TrainReservation.dto.seat;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SeatDetailDTO {
    private int seatId;
    private int seatNumber;
    private int train;
}
