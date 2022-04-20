package com.example.train_ticket_management.controller;

import com.example.train_ticket_management.entity.Booking;
import com.example.train_ticket_management.entity.Ticket;
import com.example.train_ticket_management.entity.User;
import com.example.train_ticket_management.payload.response.ResponseObject;
import com.example.train_ticket_management.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/ap1/v1/")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    //get all booking for admin
    @GetMapping(value = "/booking/get")
    public ResponseEntity<ResponseObject> getAllBooking(){
        return bookingService.getAllBooking();
    }

    //get details of a booking with id
    @GetMapping(value = "/booking/search/{id}")
    public ResponseEntity<ResponseObject> findBookingById(@PathVariable Long id){
       return bookingService.findByID(id);
    }

    //get all booking of an user
    @GetMapping(value = "/booking/user/")
    public ResponseEntity<ResponseObject> findBookingByUser(@RequestBody User user){
        return bookingService.findAllBookingByUser(user);
    }

    //create new booking
    @PostMapping(value = "/booking/add")
    public ResponseEntity<ResponseObject> addBooking(@RequestBody Ticket ticket, @RequestBody User user, @RequestBody boolean isBooked){
        return bookingService.addBooking(ticket, user, isBooked);
    }

    //create new booking by guest
    @PostMapping(value = "/unauth/booking/add")
    public ResponseEntity<ResponseObject> addBookingGuest(@RequestBody Ticket ticket, @RequestBody String guestEmail, @RequestBody String guestPhone, @RequestBody boolean isBooked){
        return bookingService.addBookingGuest(ticket, guestEmail, guestPhone, isBooked);
    }

    //update booking by id
    @PutMapping(value = "/management/booking/edit")
    public ResponseEntity<ResponseObject> updateBooking(@PathVariable long id, @RequestBody Booking updatedBookingDetails){
        return bookingService.updateBooking(id, updatedBookingDetails);
    }

    //delet booking by id
    @DeleteMapping(value = "/management/booking/delete")
    public ResponseEntity<ResponseObject> removeBooking(@PathVariable long id){
        return bookingService.removeBooking(id);
    }


}
