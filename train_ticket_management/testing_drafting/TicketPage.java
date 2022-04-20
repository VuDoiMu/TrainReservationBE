package com.example.train_ticket_management.testing_drafting;

import org.springframework.data.domain.Sort;

public class TicketPage {
    private int pageNumber = 0;
    private int pageSide = 10;
    private Sort.Direction sortDir = Sort.Direction.ASC;
    private String sortBy = "ticketId";

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSide() {
        return pageSide;
    }

    public void setPageSide(int pageSide) {
        this.pageSide = pageSide;
    }

    public Sort.Direction getSortDir() {
        return sortDir;
    }

    public void setSortDir(Sort.Direction sortDir) {
        this.sortDir = sortDir;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
