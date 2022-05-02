package com.TrainReservation.controller;

import com.TrainReservation.dto.ticket.TicketRequestDTO;
import com.TrainReservation.payload.response.ResponseObject;
import com.TrainReservation.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private TicketService ticketService;

   // public TicketController(TicketService ticketService) {
   //     this.ticketService = ticketService;
    //}

    //get all ticket
    @GetMapping(value = "/all")
    public ResponseEntity<ResponseObject> getAllTicket(){;
        return ticketService.getAllTicket();
    }

    //get ticket by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseObject> findTicketById(@PathVariable Long id) {
        return ticketService.findTicketById(id);
    }

    //get ticket by seat
    @GetMapping(value = "/seat/{seatID}")
    public ResponseEntity<ResponseObject> findTicketBySeat(@PathVariable int seatID){
        return ticketService.getTicketBySeat(seatID);
    }

    //add ticket
    @PostMapping(value = "/add")
    //@PreAuthorize("ROLE_ADMIN")
    public ResponseEntity<ResponseObject> creatTicket(@RequestBody TicketRequestDTO ticket) {
        return ticketService.addTicket(ticket);
    }

    //remove ticket by id
    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<ResponseObject> removeTicket(@PathVariable Long id) {
        return ticketService.removeTicketById(id);
    }

    //update ticket by id
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ResponseObject> updateTicket(@PathVariable Long id, @RequestBody TicketRequestDTO updatedTicketDetail) {
        return ticketService.updateTicketById(id, updatedTicketDetail);

    }

    //get searched ticket
    @GetMapping(value = "/filter")
    public ResponseEntity<ResponseObject> showFilteredTicket(@RequestParam("ticketDestination") String ticketDestination,
                                           @RequestParam("ticketDeparture") String ticketDeparture,
                                           @RequestParam("depatureDate") LocalDateTime departureDate,
                                           @RequestParam("arrivalDate") LocalDateTime arrivaDate) {
        return ticketService.findAllByTicketDestinationAndTicketDepatureAndArrivalDateAndDepatureDate(
                ticketDestination,
                ticketDeparture,
                arrivaDate,
                departureDate);
    }

}

