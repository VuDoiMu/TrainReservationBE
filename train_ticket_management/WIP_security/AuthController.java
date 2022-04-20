package com.example.train_ticket_management.controller;

import com.example.train_ticket_management.payload.request.LoginRequest;
import com.example.train_ticket_management.payload.request.SignupRequest;
import com.example.train_ticket_management.payload.response.ResponseObject;
import com.example.train_ticket_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseObject> registerAccount(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.registerAccount(signupRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseObject> authenticateAccount(HttpServletRequest request,
                                                              @Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateAccount(request, loginRequest);
    }

}
