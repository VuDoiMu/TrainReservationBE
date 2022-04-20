package com.example.train_ticket_management.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatId", nullable = false)
    private int SeatId;

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    @Column(name = "train", nullable = false)
    private int train;
}
