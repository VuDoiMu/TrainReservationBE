package com.TrainReservation.service;

import com.TrainReservation.entity.Role;
import com.TrainReservation.entity.User;
import com.TrainReservation.exception.ResourceNotFoundException;
import com.TrainReservation.payload.request.LoginRequest;
import com.TrainReservation.payload.request.SignupRequest;
import com.TrainReservation.payload.response.JwtResponse;
import com.TrainReservation.payload.response.ResponseObject;
import com.TrainReservation.repository.RoleRepository;
import com.TrainReservation.repository.UserRepository;
import com.TrainReservation.security.auth.UserDetailsImpl;
import com.TrainReservation.security.jwt.JwtUtils;
import com.TrainReservation.support.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public ResponseEntity<ResponseObject> authenticateAccount(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername().trim(), loginRequest.getPassword().trim()));

        userRepository.findByUsernameAndDeleted(loginRequest.getUsername().trim(), false)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user"));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ResponseObject("ok",
                "Authenticate successfully",
                new JwtResponse(jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles)));
    }

    public ResponseEntity<ResponseObject> registerAccount(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername().trim())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseObject("error", "Username is already taken!", ""));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail().trim())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseObject("error", "Email is already in use!", ""));
        }

        User user = new User(signUpRequest);
        user.setPassword(encoder.encode(signUpRequest.getPassword().trim()));

        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
        user.setRole(userRole);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "User registered successfully!", ""));
    }
}
