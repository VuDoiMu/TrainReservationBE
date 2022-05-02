package com.TrainReservation.service;

import com.TrainReservation.dto.ticket.TicketDetailDTO;
import com.TrainReservation.dto.ticket.TicketRequestDTO;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;


    //get all ticket
    public ResponseEntity<ResponseObject> getAllTicket(){
        List<Ticket> allTicket = ticketRepository.findAll();
        return ResponseEntity.ok(new ResponseObject("ok", "Returning all tickets", allTicket));
    }

    //find ticket of a seat
    public ResponseEntity<ResponseObject> getTicketBySeat(int seatId){
        Ticket ticket = ticketRepository.findBySeat(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ticket with seat id: " +seatId));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Ticket details found!" ,new TicketDetailDTO(ticket)));
    }

    //find ticket by id
    public ResponseEntity<ResponseObject> findTicketById(@PathVariable long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " does not exist!"));
        return ResponseEntity.ok(new ResponseObject("ok", "Ticket with id "+id+" found", new TicketDetailDTO(ticket)));
    }

    //add new ticket (ROLE=ADMIN)
    public ResponseEntity<ResponseObject> addTicket(TicketRequestDTO ticketRequestDTO){
        boolean exist = ticketRepository.existsBySeat(ticketRequestDTO.getSeatId());
        if(exist){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "Ticket is already exist!", ""));
        }
        Seat seat = seatRepository.findById(ticketRequestDTO.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat with does not exist with this id: "+ticketRequestDTO.getSeatId()));
        Ticket ticket = new Ticket(ticketRequestDTO, seat);
        ticketRepository.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "New ticket added created", new TicketDetailDTO(ticket)));
    }



    //remove ticket (ROLE=ADMIN)
    public ResponseEntity<ResponseObject> removeTicketById(@PathVariable long id){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ticket with seat id: " +id));

        ticketRepository.delete(ticket);

        return ResponseEntity.ok(new ResponseObject("ok", "Ticket removed!", ""));
    }

    //update ticket (ROLE=ADMIN)
    public ResponseEntity<ResponseObject> updateTicketById(@PathVariable long id, TicketRequestDTO updatedTicketDetail){

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " does not exist!"));

        Seat seat = seatRepository.findById(updatedTicketDetail.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat with does not exist with this id: "+updatedTicketDetail.getSeatId()));

        ticket.setTicketDepature(updatedTicketDetail.getTicketDeparture());
        ticket.setTicketPrice(updatedTicketDetail.getTicketPrice());
        ticket.setSeat(seat);
        ticket.setTicketDestination(updatedTicketDetail.getTicketDestination());
        ticket.setArrivalDate(updatedTicketDetail.getArrivalDate());
        ticket.setDepatureDate(updatedTicketDetail.getDepartureDate());
        ticket.setRoundTrip(updatedTicketDetail.isRoundTrip());
        ticket.setTravelDuration(updatedTicketDetail.getTravelDuration());

        Ticket updatedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(new ResponseObject("ok", "Ticket Updated", new TicketDetailDTO(updatedTicket)));
    }

    //get ticket with 4 criterias
    public ResponseEntity<ResponseObject> findAllByTicketDestinationAndTicketDepatureAndArrivalDateAndDepatureDate(String ticketDestination, String ticketDeparture, LocalDateTime arrivaDate, LocalDateTime departureDate) {
        List<Ticket> ticketList = ticketRepository.findAllByTicketDestinationAndTicketDepatureAndArrivalDateAndDepatureDate(ticketDestination, ticketDeparture, arrivaDate, departureDate);
        return ResponseEntity.ok(new ResponseObject("ok", "List of ticket by 4 criterials", ticketList));
    }
}
