package com.TrainReservation.dto.booking;

import com.TrainReservation.support.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateBookingRequestDTO {
    private BookingStatus status;
    private Long[] ticketIds;
}
