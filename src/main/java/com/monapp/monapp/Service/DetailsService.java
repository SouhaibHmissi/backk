package com.monapp.monapp.Service;

import com.monapp.monapp.Model.User;
import com.monapp.monapp.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailsService implements UserDetailsService {
    @Autowired
    private UserRepo personnelRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userOptional = personnelRepo.findByLogin(login);
        User personnel = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + login));
        return personnel;
    }}