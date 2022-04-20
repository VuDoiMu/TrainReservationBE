package com.example.train_ticket_management.controller;


import com.example.train_ticket_management.entity.Seat;
import com.example.train_ticket_management.payload.response.ResponseObject;
import com.example.train_ticket_management.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1")
public class SeatController {
    @Autowired
    private SeatService seatService;

    //get all seats
    @GetMapping("/seat")
    public ResponseEntity<ResponseObject> getAllSeat(){
        return seatService.getAllSeats();
    }

    //get all seats of a train
    @GetMapping("/seat/train/{train}")
    public ResponseEntity<ResponseObject> getAllSeatOnTrain(@PathVariable int train){
        return seatService.getAllSeatOnTrain(train);
    }

    //get seat detail with id
    @GetMapping("/seat/{id}")
    public ResponseEntity<ResponseObject> getSeatById(@PathVariable int id){
        return seatService.getSeatById(id);
    }

    //add new seat
    @PostMapping("/management/seat/add")
    public ResponseEntity<ResponseObject> addSeat(@RequestBody Seat seat){
        return seatService.addSeat(seat);
    }

    //update a seat with id
    @PutMapping("/management/seat/update/{id}")
    public ResponseEntity<ResponseObject> updateSeat(@PathVariable int id, @RequestBody Seat updatedSeat){
        return seatService.updateSeat(id, updatedSeat);
    }

    //delete a seat with id
    @DeleteMapping(value = "/management/seat/delete/{id}")
    public ResponseEntity<ResponseObject> deleteSeat(@PathVariable int id){
        return seatService.removeSeat(id);
    }
}
