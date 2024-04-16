package com.monapp.monapp.Controller;

import com.monapp.monapp.Model.User;
import com.monapp.monapp.Repository.UserRepo;
import com.monapp.monapp.Service.AuthenticationService;
import com.monapp.monapp.auth.AuthenticationRequest;
import com.monapp.monapp.auth.AuthenticationResponse;

import com.monapp.monapp.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
   private final UserRepo personnelRepo;

   @PostMapping(value = "login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request)
    {
        return ResponseEntity.ok(authService.login(request));


    }



    @PostMapping(value = "register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));

    }



    @Autowired
    private AuthenticationService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String login = authentication.getName();
        Optional<User> userOptional = userService.getUserByUsername(login);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }





}

