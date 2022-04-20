package com.example.train_ticket_management.service;

import com.example.train_ticket_management.entity.Role;
import com.example.train_ticket_management.payload.response.ResponseObject;
import com.example.train_ticket_management.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public ResponseEntity<ResponseObject> addRole(Role role){
        if(roleRepository.existsByName(role.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseObject("error", "Role existed!", ""));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "Role created!", roleRepository.save(role)));
    }
}
