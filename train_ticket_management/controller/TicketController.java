package com.example.train_ticket_management.controller;

import com.example.train_ticket_management.entity.Ticket;
import com.example.train_ticket_management.payload.response.ResponseObject;
import com.example.train_ticket_management.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/api/v1")
public class TicketController {

    private TicketService ticketService;

   // public TicketController(TicketService ticketService) {
   //     this.ticketService = ticketService;
    //}

    //get all ticket
    @GetMapping(value = "/ticket/")
    public ResponseEntity<ResponseObject> getAllTicket(){;
        return ticketService.getAllTicket();
    }

    //get ticket by id
    @GetMapping(value = "/ticket/{id}")
    public ResponseEntity<ResponseObject> findTicketById(@PathVariable Long id) {
        return ticketService.findTicketById(id);
    }

    //get ticket by seat
    @GetMapping(value = "/ticket/seat")

    //add ticket
    @PostMapping(value = "/management/ticket/add")
    public ResponseEntity<ResponseObject> creatTicket(@RequestBody Ticket ticket) {
        return ticketService.addTicket(ticket);
    }

    //remove ticket by id
    @DeleteMapping(value = "/management/ticket/remove/{id}")
    public ResponseEntity<ResponseObject> removeTicket(@PathVariable Long id) {
        return ticketService.removeTicketById(id);
    }

    //update ticket by id
    @PutMapping(value = "/management/ticket/update/{id}")
    public ResponseEntity<ResponseObject> updateTicket(@PathVariable Long id, @RequestBody Ticket updatedTicketDetail) {
        return ticketService.updateTicketById(id, updatedTicketDetail);

    }

    //get searched ticket
    @GetMapping(value = "/ticket/filter")
    public ResponseEntity<ResponseObject> showFilteredTicket(@RequestParam("ticketDestination") String ticketDestination,
                                           @RequestParam("ticketDeparture") String ticketDeparture,
                                           @RequestParam("depatureDate") Date departureDate,
                                           @RequestParam("arrivalDate") Date arrivaDate) {
        return ticketService.findAllByTicketDestinationAndTicketDepatureAndArrivalDateAndDepatureDate(
                ticketDestination,
                ticketDeparture,
                arrivaDate,
                departureDate);
    }

}

