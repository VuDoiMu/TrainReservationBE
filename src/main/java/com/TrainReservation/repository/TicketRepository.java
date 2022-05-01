package com.TrainReservation.repository;

import com.TrainReservation.entity.Seat;
import com.TrainReservation.entity.Ticket;
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
