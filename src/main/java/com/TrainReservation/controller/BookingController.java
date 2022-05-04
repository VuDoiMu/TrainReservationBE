package com.TrainReservation.controller;

import com.TrainReservation.dto.booking.CreateBookingRequestDTO;
import com.TrainReservation.dto.booking.CreateBookingRequestGuestDTO;
import com.TrainReservation.dto.booking.UpdateBookingRequestDTO;
import com.TrainReservation.payload.response.ResponseObject;
import com.TrainReservation.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController(value = "/ap1/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    //get all booking for admin
    @GetMapping(value = "/get")
    public ResponseEntity<ResponseObject> getAllBooking(){
        return bookingService.getAllBooking();
    }

    //get details of a booking with id
    @GetMapping(value = "/search/{id}")
    public ResponseEntity<ResponseObject> findBookingById(@PathVariable Long id){
       return bookingService.findByID(id);
    }

    //get all booking of an user
    @GetMapping(value = "/searchByUser/")
    public ResponseEntity<ResponseObject> findBookingByUser(){
        return bookingService.findAllBookingByUser();
    }

    //create new booking
    @PostMapping(value = "/add")
    public ResponseEntity<ResponseObject> addBooking(@RequestBody CreateBookingRequestDTO bookingRequestDTO){
        return bookingService.addBooking(bookingRequestDTO);
    }

    //create new booking by guest
    @PostMapping(value = "/add/guest")
    public ResponseEntity<ResponseObject> addBookingGuest(@RequestBody CreateBookingRequestGuestDTO bookingRequestGuestDTO){
        return bookingService.addBookingGuest(bookingRequestGuestDTO);
    }

    //update booking by id
    @PutMapping(value = "/management/edit")
    public ResponseEntity<ResponseObject> updateBooking(@PathVariable long id, @RequestBody UpdateBookingRequestDTO updateBookingRequestDTO){
        return bookingService.updateBooking(id, updateBookingRequestDTO);
    }

    //delet booking by id
    @PatchMapping(value = "/management/delete")
    public ResponseEntity<ResponseObject> removeBooking(@PathVariable long id){
        return bookingService.removeBooking(id);
    }

    @GetMapping("/management/allrevenue")
    public ResponseEntity<ResponseObject> getAllRevenue(){
        return bookingService.getAllRevenue();
    }

    @GetMapping("/management/partialrevenue")
    public ResponseEntity<ResponseObject> getPartialRevenue(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
        return bookingService.getPartialRevenue(start, end);
    }

}
