package com.monapp.monapp.Controller;

import com.monapp.monapp.Service.UserService;
import com.monapp.monapp.password.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/password")
public class UserCont {
    @Autowired
    private UserService service;
    /*
    @GetMapping("/generatePassword")
    public String generatePassword() {
        return .generatePassword(10);
    }

     */
    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

}

