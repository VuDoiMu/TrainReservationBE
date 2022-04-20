package com.example.train_ticket_management.service;

import com.example.train_ticket_management.entity.Role;
import com.example.train_ticket_management.entity.User;
import com.example.train_ticket_management.exception.ResourceNotFoundException;
import com.example.train_ticket_management.payload.response.ResponseObject;
import com.example.train_ticket_management.repository.RoleRepository;
import com.example.train_ticket_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Service @RequiredArgsConstructor @Transactional
public class UserService implements UserDetailsService {

    @Autowired
   private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseObject> getAllUsers(){
        return ResponseEntity.ok(new ResponseObject("ok", "Return all user info", userRepository.findAll()));
    }

    public ResponseEntity<ResponseObject> getUserDetail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));
        return ResponseEntity.ok(new ResponseObject("ok", "Query user successfully", user));
    }

    public ResponseEntity<ResponseObject> editUserByID(Long id, User updatedUserDetails){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id not found " +id));

        user.setUserEmail(updatedUserDetails.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(updatedUserDetails.getUserPassword()));
        user.setUserPhone(updatedUserDetails.getUserPhone());
        user.setUserFirstName(updatedUserDetails.getUserFirstName());
        user.setUserLastName(updatedUserDetails.getUserLastName());
        user.setUpdateDate(Timestamp.from(Instant.now()));

        userRepository.save(user);

        return ResponseEntity.ok(new ResponseObject("ok", "User Updated", ""));
    }

    public ResponseEntity<ResponseObject> deleteUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id not found " +id));

        userRepository.deleteById(id);

        return ResponseEntity.ok(new ResponseObject("ok", "User deleted",""));
    }

    public User getUser(String username){
        User user = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username not found " +username));

        return user;
    }

    public ResponseEntity<ResponseObject> saveUser(User user){
        if (userRepository.existsByUserName(user.getUserName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "Username has been taken!", ""));
        }
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/management/user/add").toUriString());
        return ResponseEntity.created(uri).body(new ResponseObject("ok", "New user added!", userRepository.save(user)));
    }

    public void addRoleToUser(String userName, String roleName){
        User user = userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("Username: "+ userName+ " not found!" ));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role: "+ roleName+ " not found!" ));

        user.getRole().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username not found!"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), authorities);
    }
}
