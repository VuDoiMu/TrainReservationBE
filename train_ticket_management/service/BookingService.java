package com.example.train_ticket_management.service;

import com.example.train_ticket_management.entity.Booking;
import com.example.train_ticket_management.entity.Ticket;
import com.example.train_ticket_management.entity.User;
import com.example.train_ticket_management.exception.ResourceNotFoundException;
import com.example.train_ticket_management.payload.response.ResponseObject;
import com.example.train_ticket_management.repository.BookingRepository;
import com.example.train_ticket_management.repository.TicketRepository;
import com.example.train_ticket_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<ResponseObject> findAllBookingByUser(User user){
        if(!userRepository.existsById(user.getUserID())){
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
    public ResponseEntity<ResponseObject> addBooking(@RequestBody Ticket ticket, @RequestBody User user, @RequestBody boolean isbooked){
        if(!userRepository.existsById(user.getUserID())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", "User not found!", ""));
        }
        if(!ticketRepository.existsById(ticket.getTicketID())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", "Ticket not found!", ""));
        }
        Booking booking = new Booking(isbooked, user, ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "New booking created!", bookingRepository.save(booking)));
    }

    //create booking for guest
    public ResponseEntity<ResponseObject> addBookingGuest(@RequestBody Ticket ticket, @RequestBody String guestEmail, @RequestBody String guestPhone, @RequestBody boolean isBooked){
        if(!ticketRepository.existsById(ticket.getTicketID())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", "Ticket not found!", ""));
        }
        Booking booking = new Booking(ticket, isBooked,guestEmail, guestPhone);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "New booking created!", bookingRepository.save(booking)));
    }

    //delet booking
    public ResponseEntity<ResponseObject> removeBooking(@PathVariable long id){
        if(!bookingRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", "Booking with id "+ id+ " not found!", ""));
        }
        bookingRepository.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Booking deleted!", ""));
    }

    //update booking
    public ResponseEntity<ResponseObject> updateBooking(@PathVariable long id, @RequestBody Booking updateBookingDetails){
        Booking booking = bookingRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Booking with id " +id+" does not exist!"));
        booking.setBookingDate(updateBookingDetails.getBookingDate());
        booking.setTicket(updateBookingDetails.getTicket());
        booking.setBooked(updateBookingDetails.isBooked());
        booking.setUser(updateBookingDetails.getUser());
        return ResponseEntity.ok(new ResponseObject("ok", "Booking updated!", bookingRepository.save(booking)));
    }




}
