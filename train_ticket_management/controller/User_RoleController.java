package com.example.train_ticket_management.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.train_ticket_management.entity.Role;
import com.example.train_ticket_management.entity.User;
import com.example.train_ticket_management.payload.request.SignupRequest;
import com.example.train_ticket_management.payload.response.ResponseObject;
import com.example.train_ticket_management.service.RoleService;
import com.example.train_ticket_management.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class User_RoleController {
    private final UserService userService;

    private final RoleService roleService;


    @GetMapping("/v1/management/users")
    public ResponseEntity<ResponseObject> getUser() {
        return userService.getAllUsers();
    }


    @GetMapping("/v1/user/detail/{id}")
    public ResponseEntity<ResponseObject> userDetails(@PathVariable Long id) {
        return userService.getUserDetail(id);
    }

    @PostMapping("/v1/register")
    public ResponseEntity<ResponseObject> userRegister(@RequestBody SignupRequest signupRequest) {
        User user = new User(signupRequest);
        return userService.saveUser(user);
    }

    @DeleteMapping("/v1/management/user/delete/{id}")
    public ResponseEntity<ResponseObject> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @PostMapping("/v1/management/user/add")
    public ResponseEntity<ResponseObject> saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/v1/user/edit/{id}")
    public ResponseEntity<ResponseObject> updateUserById(@PathVariable Long id, @RequestBody User updatedUserDetail) {
        return userService.editUserByID(id, updatedUserDetail);
    }

    @PostMapping("/v1/management/role/add")
    public ResponseEntity<ResponseObject> saveRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    @PostMapping("/v1/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearrer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();

                User user = userService.getUser(username);

                String access_token  = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() +10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", user.getRole().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);


                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}