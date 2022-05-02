package com.TrainReservation.service;

import com.TrainReservation.dto.seat.SeatDetailDTO;
import com.TrainReservation.dto.seat.SeatRequestDTO;
import com.TrainReservation.entity.Seat;
import com.TrainReservation.entity.Ticket;
import com.TrainReservation.exception.ResourceNotFoundException;
import com.TrainReservation.payload.response.ResponseObject;
import com.TrainReservation.repository.SeatRepository;
import com.TrainReservation.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    TicketRepository ticketRepository;

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
        List<SeatDetailDTO> seatList = new ArrayList<>();
        for(Seat seat: trainSeats){
            seatList.add(new SeatDetailDTO(seat));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("ok", "List of seats on the train number: "+train, seatList));
    }

    //get a seat by id
    public ResponseEntity<ResponseObject> getSeatById(int id){
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat with id " +id+ " does not exist!"));
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("ok", "Seat found!", new SeatDetailDTO(seat)));
    }

    //add a seat
    public ResponseEntity<ResponseObject> addSeat(SeatRequestDTO seatRequestDTO){
        boolean exist = seatRepository.existsBySeatNumberAndTrain(seatRequestDTO.getSeatNumber(), seatRequestDTO.getTrain());
        if(exist){
            ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "This seat is arealdy exist!", ""));
        }
        Seat seat = new Seat(seatRequestDTO);
        seatRepository.save(seat);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("error", "New seat added!", new SeatDetailDTO(seat)));
    }

    //update a seat
    public ResponseEntity<ResponseObject> updateSeat(int seatID, SeatRequestDTO updatedSeatDetails){
        Seat seat = seatRepository.findById(seatID)
                        .orElseThrow(() -> new ResourceNotFoundException("Seat with id " +seatID+ " does not exist!"));

        seat.setTrain(updatedSeatDetails.getTrain());
        seat.setSeatNumber(updatedSeatDetails.getSeatNumber());

        if(updatedSeatDetails.getTicketID()!=0){
            Ticket ticket = ticketRepository.findById(updatedSeatDetails.getTicketID())
                    .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " +updatedSeatDetails.getTicketID()+ " does not exist!"));
            seat.setTicket(ticket);
        }

        return ResponseEntity.ok(new ResponseObject("ok", "Seat details updated", new SeatDetailDTO(seat)));
    }

    //delet a seat
    public ResponseEntity<ResponseObject> removeSeat( int id){
        if(!seatRepository.existsById(id)){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", "Seat with id: " +id+" does not exist", ""));
        }
        seatRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Seat deleted!", "" ));
    }

}
