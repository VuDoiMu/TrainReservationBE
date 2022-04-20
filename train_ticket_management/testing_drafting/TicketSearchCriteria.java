package com.example.train_ticket_management.testing_drafting;

import java.sql.Date;

public class TicketSearchCriteria {
    private String ticketDestination;
    private String ticketDeparture;
    private Date depatureDate;
    private Date arrivalDate;

    public Date getDepatureDate() {
        return depatureDate;
    }

    public void setDepatureDate(Date depatureDate) {
        this.depatureDate = depatureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getTicketDestination() {
        return ticketDestination;
    }

    public void setTicketDestination(String ticketDestination) {
        this.ticketDestination = ticketDestination;
    }

    public String getTicketDeparture() {
        return ticketDeparture;
    }

    public void setTicketDeparture(String ticketDeparture) {
        this.ticketDeparture = ticketDeparture;
    }
}
