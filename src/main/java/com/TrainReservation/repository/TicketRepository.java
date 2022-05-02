package com.TrainReservation.repository;

import com.TrainReservation.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
      List<Ticket> findAllByArrivalDate(Date arrDate);
      List<Ticket> findAllByTicketDepature(String ticketDep);
      List<Ticket> findAllByTicketDestination(String ticketDes);
      Optional<Ticket> findBySeat(int seatid);
     List<Ticket> findAllByTicketDestinationAndTicketDepatureAndArrivalDateAndDepatureDate(String des, String dep, LocalDateTime arrDate, LocalDateTime depDate);
     Boolean existsBySeat(int seatId);

}
