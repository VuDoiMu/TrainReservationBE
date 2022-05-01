package com.TrainReservation.repository;

import com.TrainReservation.entity.Booking;
import com.TrainReservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User user);
}
