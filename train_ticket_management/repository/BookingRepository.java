package com.example.train_ticket_management.repository;

import com.example.train_ticket_management.entity.Booking;
import com.example.train_ticket_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User user);
}
