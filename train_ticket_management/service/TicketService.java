package com.example.train_ticket_management.service;

import com.example.train_ticket_management.entity.Seat;
import com.example.train_ticket_management.entity.Ticket;
import com.example.train_ticket_management.exception.ResourceNotFoundException;
import com.example.train_ticket_management.payload.response.ResponseObject;
import com.example.train_ticket_management.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;


    //get all ticket
    public ResponseEntity<ResponseObject> getAllTicket(){
        List<Ticket> allTicket = ticketRepository.findAll();
        return ResponseEntity.ok(new ResponseObject("ok", "Returning all tickets", allTicket));
    }

    //find ticket of a seat
    public ResponseEntity<ResponseObject> getTicketBySeat(Seat seat){
        Ticket ticket = ticketRepository.findBySeat(seat);
        if (ticketRepository.existsById(ticket.getTicketID())){
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("ok", "Ticket found", ticket));
        }
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", "There is no ticket for this seat!", ""));
    }

    //find ticket by id
    public ResponseEntity<ResponseObject> findTicketById(@PathVariable long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " does not exist!"));
        return ResponseEntity.ok(new ResponseObject("ok", "Ticket with id "+id+" found", ticket));
    }

    //add new ticket (ROLE=ADMIN)
    public ResponseEntity<ResponseObject> addTicket(Ticket ticket){
        boolean exist = ticketRepository.existsBySeat(ticket.getSeat());
        if(exist){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "Ticket is already exist!", ""));
        }
        ticketRepository.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "New ticket added created", ticket));
    }



    //remove ticket (ROLE=ADMIN)
    public ResponseEntity<ResponseObject> removeTicketById(@PathVariable long id){
        try {
            ticketRepository.deleteById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Ticket Deleted", ""));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Not found ticket with id: " + e);
        }
    }

    //update ticket (ROLE=ADMIN)
    public ResponseEntity<ResponseObject> updateTicketById(@PathVariable long id, Ticket updatedTicketDetail){

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " does not exist!"));

        ticket.setTicketDepature(updatedTicketDetail.getTicketDepature());
        ticket.setTicketPrice(updatedTicketDetail.getTicketPrice());
        ticket.setSeat(updatedTicketDetail.getSeat());
        ticket.setTicketDestination(updatedTicketDetail.getTicketDestination());
        ticket.setArrivalDate(updatedTicketDetail.getArrivalDate());
        ticket.setDepatureDate(updatedTicketDetail.getDepatureDate());
        ticket.setRoundTrip(updatedTicketDetail.isRoundTrip());
        ticket.setTravelDuration(updatedTicketDetail.getTravelDuration());

        Ticket updatedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(new ResponseObject("ok", "Ticket Updated", updatedTicket));
    }

    //get ticket with 4 criterias
    public ResponseEntity<ResponseObject> findAllByTicketDestinationAndTicketDepatureAndArrivalDateAndDepatureDate(String ticketDestination, String ticketDeparture, Date arrivaDate, Date departureDate) {
        List<Ticket> ticketList = ticketRepository.findAllByTicketDestinationAndTicketDepatureAndArrivalDateAndDepatureDate(ticketDestination, ticketDeparture, arrivaDate, departureDate);
        return ResponseEntity.ok(new ResponseObject("ok", "List of ticket by 4 criterials", ticketList));
    }
}
