package com.TrainReservation.controller;

import com.TrainReservation.payload.request.LoginRequest;
import com.TrainReservation.payload.request.SignupRequest;
import com.TrainReservation.payload.response.ResponseObject;
import com.TrainReservation.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseObject> registerAccount(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.registerAccount(signupRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseObject> authenticateAccount(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateAccount(loginRequest);
    }

}
