package com.TrainReservation.dto.seat;


import com.TrainReservation.entity.Seat;
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

    public SeatDetailDTO(Seat seat){
        this.seatId = seat.getSeatId();
        this.seatNumber = seat.getSeatNumber();
        this.train = seat.getTrain();
    }
}
