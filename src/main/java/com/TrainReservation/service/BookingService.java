package com.TrainReservation.service;

import com.TrainReservation.dto.booking.BookingDetailDTO;
import com.TrainReservation.dto.booking.CreateBookingRequestDTO;
import com.TrainReservation.dto.booking.CreateBookingRequestGuestDTO;
import com.TrainReservation.dto.booking.UpdateBookingRequestDTO;
import com.TrainReservation.entity.Booking;
import com.TrainReservation.entity.Ticket;
import com.TrainReservation.entity.User;
import com.TrainReservation.exception.ResourceNotFoundException;
import com.TrainReservation.payload.response.ResponseObject;
import com.TrainReservation.repository.BookingRepository;
import com.TrainReservation.repository.TicketRepository;
import com.TrainReservation.repository.UserRepository;
import com.TrainReservation.support.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    //get all booking
    public ResponseEntity<ResponseObject> getAllBooking(){
        return ResponseEntity.ok(new ResponseObject("ok", "List of all booking", bookingRepository.findAll()));
    }

    //get all booking of an user
    public ResponseEntity<ResponseObject> findAllBookingByUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsernameAndDeleted(username, false)
                .orElseThrow(() -> new ResourceNotFoundException("Not found username: " + username));
        if(!userRepository.existsById(user.getId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", "User not found!", ""));
        }
        return ResponseEntity.ok(new ResponseObject("ok", "List of all booking of user: ", bookingRepository.findAllByUser(user)));
    }

    //get booking by an id
    public ResponseEntity<ResponseObject> findByID(@PathVariable long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no booking with this id!"));

        return ResponseEntity.ok(new ResponseObject("ok", "Booking found!", booking));
    }

    //create booking for logged in user
    public ResponseEntity<ResponseObject> addBooking(CreateBookingRequestDTO bookingRequestDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsernameAndDeleted(username, false)
                .orElseThrow(() -> new ResourceNotFoundException("Not found username: " + username));

        Long[] ticketIdList = bookingRequestDTO.getTicketIds();
        Set<Ticket> tickets = new HashSet<>();
        for (Long id: ticketIdList){
                Ticket ticket = ticketRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id: "+ id));
        }


        Booking booking = new Booking(bookingRequestDTO.getStatus(), user, tickets);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "New booking created!", new BookingDetailDTO(bookingRepository.save(booking))));
    }

    //create booking for guest
    public ResponseEntity<ResponseObject> addBookingGuest(CreateBookingRequestGuestDTO bookingRequestGuestDTO){

        User user = new User(bookingRequestGuestDTO.getGuestEmail(), bookingRequestGuestDTO.getGuestPhone());

        Long[] ticketIdList = bookingRequestGuestDTO.getTicketIds();
        Set<Ticket> tickets = new HashSet<>();
        for (Long id: ticketIdList){
            Ticket ticket = ticketRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id: "+ id));
            tickets.add(ticket);
        }

        Booking booking = new Booking(bookingRequestGuestDTO.getStatus(), user, tickets);
      //  for (Long id: ticketIdList){
     //       Ticket ticket = ticketRepository.findById(id)
     //               .orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id: "+ id));
     //      ticket.setBooking(booking);
    //       ticketRepository.save(ticket);
     //   }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "New booking created!", bookingRepository.save(booking)));
    }

    //delet booking
    public ResponseEntity<ResponseObject> removeBooking( long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no booking with this id!"));
        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
        return ResponseEntity.ok(new ResponseObject("ok", "Booking deleted!", new BookingDetailDTO(booking)));
    }

    //update booking
    public ResponseEntity<ResponseObject> updateBooking(long id, UpdateBookingRequestDTO updateBookingRequestDTO){
        Booking booking = bookingRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Booking with id " +id+" does not exist!"));

        Long[] ticketIdList = updateBookingRequestDTO.getTicketIds();
        Set<Ticket> tickets = new HashSet<>();
        for (Long ids: ticketIdList){
            Ticket ticket = ticketRepository.findById(ids)
                    .orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id: "+ ids));
            tickets.add(ticket);
        }

        booking.setStatus(updateBookingRequestDTO.getStatus());
        booking.setTickets(tickets);

        bookingRepository.save(booking);
        return ResponseEntity.ok(new ResponseObject("ok", "Booking updated!", new BookingDetailDTO(booking)));
    }

    public ResponseEntity<ResponseObject> getAllRevenue(){
        List<Booking> allBooking = bookingRepository.findAll();
        double revenue = 0;
        for (Booking booking: allBooking){
            revenue = revenue+ booking.getPrice();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Getting all time revenue!", revenue));
    }

    public ResponseEntity<ResponseObject> getPartialRevenue(LocalDateTime start, LocalDateTime end){
        List<Booking> allBooking = bookingRepository.findAll();
        double revenue = 0;
        for (Booking booking: allBooking){
            if (booking.getBookingDate().before(Timestamp.valueOf(end)) && booking.getBookingDate().after(Timestamp.valueOf(start)))
            revenue = revenue+ booking.getPrice();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Getting all time revenue!", revenue));
    }




}
