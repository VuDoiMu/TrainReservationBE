package com.TrainReservation.service;

import com.TrainReservation.entity.User;
import com.TrainReservation.exception.ResourceNotFoundException;
import com.TrainReservation.payload.request.ChangePasswordRequest;
import com.TrainReservation.payload.request.ResetPasswordRequest;
import com.TrainReservation.payload.request.UpdatePasswordAfterResetRequest;
import com.TrainReservation.payload.response.ResponseObject;
import com.TrainReservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private SendEmailService sendEmailService;

    public ResponseEntity<HttpStatus> resetUserPassword(ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findByEmailAndDeleted(resetPasswordRequest.getEmail().trim(), false)
                .orElseThrow(() -> new ResourceNotFoundException("Not found the user with email: " + resetPasswordRequest.getEmail()));
        int resetCode = (new Random().nextInt(900000) + 100000);
        user.setPasswordResetCode(resetCode);
        userRepository.save(user);
        sendEmailService.sendEmail(user.getEmail(), "Your code for reset your password for the account with email " + resetPasswordRequest.getEmail() + " is: " + resetCode, "DiGi World - Password Reset");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<HttpStatus> checkResetUserPasswordCode(ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findByEmailAndDeleted(resetPasswordRequest.getEmail().trim(), false)
                .orElseThrow(() -> new ResourceNotFoundException("Not found the user with email: " + resetPasswordRequest.getEmail()));
        if (!(user.getPasswordResetCode() == resetPasswordRequest.getResetCode())) {
            throw new ResourceNotFoundException("Code is not matched");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<ResponseObject> updateNewPassword(UpdatePasswordAfterResetRequest updatePasswordAfterResetRequest) {
        User user = userRepository.findByEmailAndDeleted(updatePasswordAfterResetRequest.getEmail().trim(), false)
                .orElseThrow(() -> new ResourceNotFoundException("Not found the user with email: " + updatePasswordAfterResetRequest.getEmail()));
        user.setPassword(encoder.encode(updatePasswordAfterResetRequest.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Change password successfully", ""));
    }

    public ResponseEntity<ResponseObject> updateUserCurrentPassword(ChangePasswordRequest changePasswordRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsernameAndDeleted(auth.getName(), false).get();
        boolean matchedPassword = encoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword());
        if (!matchedPassword) {
            throw new ResourceNotFoundException("Your current password is not matched");
        }
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        user.setPasswordResetCode(0);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Change password successfully", ""));
    }

}
