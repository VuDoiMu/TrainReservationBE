package com.example.train_ticket_management.repository;

import com.example.train_ticket_management.entity.Seat;
import com.example.train_ticket_management.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
      List<Ticket> findAllByArrivalDate(Date arrDate);
      List<Ticket> findAllByTicketDepature(String ticketDep);
      List<Ticket> findAllByTicketDestination(String ticketDes);
      Ticket findBySeat(Seat seat);
     List<Ticket> findAllByTicketDestinationAndTicketDepatureAndArrivalDateAndDepatureDate(String des, String dep, Date arrDate, Date depDate);
     Boolean existsBySeat(Seat seat);

}
