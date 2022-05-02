package com.TrainReservation.dto.booking;

import com.TrainReservation.entity.Booking;
import com.TrainReservation.entity.Ticket;
import com.TrainReservation.entity.User;
import com.TrainReservation.support.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class BookingDetailDTO {
    private long bookingId;
    private BookingStatus status;
    private User user;
    private Set<Ticket> tickets;

    public BookingDetailDTO(Booking booking){
        this.bookingId = booking.getBookingID();
        this.status = booking.getStatus();
        this.user = booking.getUser();
        this.tickets = booking.getTickets();
    }
}
