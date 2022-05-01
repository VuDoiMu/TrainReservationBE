package com.TrainReservation.support;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@RequiredArgsConstructor
@Data
public class DateTime {
    private Date date;
    private Time time;
}
