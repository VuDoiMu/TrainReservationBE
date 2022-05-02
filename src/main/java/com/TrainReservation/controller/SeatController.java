package com.TrainReservation.controller;


import com.TrainReservation.dto.seat.SeatRequestDTO;
import com.TrainReservation.payload.response.ResponseObject;
import com.TrainReservation.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;

    //get all seats
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllSeat(){
        return seatService.getAllSeats();
    }

    //get all seats of a train
    @GetMapping("/train/{train}")
    public ResponseEntity<ResponseObject> getAllSeatOnTrain(@PathVariable int train){
        return seatService.getAllSeatOnTrain(train);
    }

    //get seat detail with id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getSeatById(@PathVariable int id){
        return seatService.getSeatById(id);
    }

    //add new seat
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addSeat(@RequestBody SeatRequestDTO seat){
        return seatService.addSeat(seat);
    }

    //update a seat with id
    @PutMapping("update/{id}")
    public ResponseEntity<ResponseObject> updateSeat(@PathVariable int id, @RequestBody SeatRequestDTO updatedSeat){
        return seatService.updateSeat(id, updatedSeat);
    }

    //delete a seat with id
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ResponseObject> deleteSeat(@PathVariable int id){
        return seatService.removeSeat(id);
    }
}
