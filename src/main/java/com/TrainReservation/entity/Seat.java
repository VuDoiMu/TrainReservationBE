package com.TrainReservation.entity;

import com.TrainReservation.dto.seat.SeatRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatId", nullable = false)
    private int SeatId;

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    @Column(name = "train", nullable = false)
    private int train;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private Ticket ticket;

    public Seat(SeatRequestDTO requestDTO) {
        this.seatNumber = requestDTO.getSeatNumber();
        this.train = requestDTO.getTrain();
        this.ticket = null;
    }
}
