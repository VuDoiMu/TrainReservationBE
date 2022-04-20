package com.example.train_ticket_management.payload.response;

import lombok.Data;

@Data
public class ResponseObject {
    private String status;
    private String message;
    private Object data;

    public ResponseObject() {

    }

    public ResponseObject(String status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
