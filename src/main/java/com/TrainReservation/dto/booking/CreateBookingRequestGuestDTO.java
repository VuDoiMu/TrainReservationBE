package com.TrainReservation.dto.booking;

import com.TrainReservation.support.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateBookingRequestGuestDTO {
    private BookingStatus status;
    private long ticketId;
    private String guestEmail;
    private String guestPhone;

}
