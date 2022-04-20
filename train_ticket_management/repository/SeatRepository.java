package com.example.train_ticket_management.repository;

import com.example.train_ticket_management.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findAllByTrain(int Train);
    boolean existsBySeatNumberAndTrain(int seat, int train);
}
