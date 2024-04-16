package com.monapp.monapp.Service;

import com.monapp.monapp.Model.User;
import com.monapp.monapp.Repository.UserRepo;
import com.monapp.monapp.password.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, UserRepo repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }
    @Autowired

    private final UserRepo repository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        //check if the current password is correct
        if(!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            throw new IllegalStateException("wrong password");
        }

        //if the new password doesn't match the confirmation password
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }
        //update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        //save the new password
        repository.save(user);
    }
    public Long getLoggedInUserId() {
        Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                // Récupérer l'ID de l'utilisateur à partir des détails de l'utilisateur
                return getUserIdFromDatabase(userDetails.getUsername()); // Vous devez implémenter cette méthode
            }
        }

        // Si aucun utilisateur n'est connecté, retournez null
        return null;
    }

    private Long getUserIdFromDatabase(String username) {
        // Implémentez cette méthode pour récupérer l'ID de l'utilisateur à partir de votre base de données
        // Vous pouvez utiliser un service ou un référentiel pour accéder à votre base de données
        // Par exemple, si vous utilisez JPA, vous pouvez injecter un référentiel utilisateur et appeler une méthode pour récupérer l'ID de l'utilisateur
        // userRepo.findByUsername(username).getId();
        return null; // Remplacez ceci par la logique réelle pour récupérer l'ID de l'utilisateur
    }


}
