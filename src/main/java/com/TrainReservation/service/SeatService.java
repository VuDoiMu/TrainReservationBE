package com.TrainReservation.service;

import com.TrainReservation.entity.Seat;
import com.TrainReservation.exception.ResourceNotFoundException;
import com.TrainReservation.payload.response.ResponseObject;
import com.TrainReservation.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    SeatRepository seatRepository;

    //get all seats
    public ResponseEntity<ResponseObject> getAllSeats(){
        List<Seat> seatList = seatRepository.findAll();
        return ResponseEntity.ok(new ResponseObject("ok", "Return all seats", seatList));
    }

    //get all seats in 1 train
    public ResponseEntity<ResponseObject> getAllSeatOnTrain(int train){
        List<Seat> trainSeats = seatRepository.findAllByTrain(train);
        if(trainSeats.size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", "Train not found", ""));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("ok", "List of seats on the train number: "+train, trainSeats) );
    }

    //get a seat by id
    public ResponseEntity<ResponseObject> getSeatById(@PathVariable int id){
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat with id " +id+ " does not exist!"));
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("ok", "Seat found!", seat));
    }

    //add a seat
    public ResponseEntity<ResponseObject> addSeat(Seat seat){
        boolean exist = seatRepository.existsBySeatNumberAndTrain(seat.getSeatNumber(), seat.getTrain());
        if(exist){
            ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "This seat is arealdy exist!", ""));
        }
        seatRepository.save(seat);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("error", "New seat added!", seat));
    }

    //update a seat
    public ResponseEntity<ResponseObject> updateSeat(int seatID, Seat updatedSeatDetails){
        Seat seat = seatRepository.findById(seatID)
                        .orElseThrow(() -> new ResourceNotFoundException("Seat with id " +seatID+ " does not exist!"));
        seat.setTrain(updatedSeatDetails.getTrain());
        seat.setSeatNumber(updatedSeatDetails.getSeatNumber());
        return ResponseEntity.ok(new ResponseObject("ok", "Seat details updated", seat));
    }

    //delet a seat
    public ResponseEntity<ResponseObject> removeSeat(@PathVariable int id){
        if(!seatRepository.existsById(id)){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", "Seat with id: " +id+" does not exist", ""));
        }
        seatRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Seat deleted!", "" ));
    }

}
