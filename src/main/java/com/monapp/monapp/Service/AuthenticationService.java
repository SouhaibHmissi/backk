package com.monapp.monapp.Service;

import com.monapp.monapp.Model.Role;
import com.monapp.monapp.Model.User;
import com.monapp.monapp.Repository.UserRepo;
import com.monapp.monapp.auth.AuthenticationRequest;
import com.monapp.monapp.auth.AuthenticationResponse;
import com.monapp.monapp.auth.RegisterRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        UserDetails user = userRepository.findByLogin(request.getLogin()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }


    public AuthenticationResponse register(RegisterRequest request) {
        Long id = request.getId() != null ? Long.valueOf(request.getId()) : null;
        Role role;

        switch (request.getRole()) {
            case "ADMIN":
                role = Role.ADMIN;
                break;
            case "EMPLOYE":
                role = Role.EMPLOYE;
                break;
            case "CHEF":
                role = Role.CHEF;
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }

        // Vérifiez si le login n'est pas null avant de l'utiliser
        if (request.getLogin() == null) {
            throw new IllegalArgumentException("Login cannot be null");
        }

        // Vérifiez si le mot de passe n'est pas null avant de l'utiliser
        if (request.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        // Vérifiez si les autres champs requis ne sont pas null avant de les utiliser
        // Assurez-vous de vérifier tous les champs nécessaires pour créer un nouvel utilisateur

        User user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .service(request.getService())
                .tel(request.getTel())
                .sexe(request.getSexe())
                .nbr_enfant(request.getNbr_enfant())
                .sexe(request.getSexe())
                .email(request.getEmail())
                .id(id)
                .role(role)
                .statut(request.getStatut())
                .build();

        // Assurez-vous que l'utilisateur que vous essayez d'enregistrer n'est pas null
        if (user == null) {
            throw new IllegalArgumentException("User object cannot be null");
        }

        // Enregistrez l'utilisateur dans le référentiel
        userRepository.save(user);

        return AuthenticationResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

  /*  private String generateMatricule() {
        // Get the last matricule from the database
        String lastMatricule = userRepository.findTopByOrderByMatriculeDesc().getMatricule();
        // Increment the last matricule and return the new matricule value
        return incrementMatricule(lastMatricule);
    }

    private String incrementMatricule(String lastMatricule) {
        // Extract the numeric part of the matricule
        int number = Integer.parseInt(lastMatricule.substring(1));
        number++;

        // Format the incremented number with leading zeros
        return String.format("%04d", number);
    }*/

    @Autowired
    private UserRepo personnelRepository;


    public Optional<User> getUserByUsername(String login) {
        return personnelRepository.findByLogin(login);
    }

    public User getUserById(Long id) {
        return personnelRepository.findById((long) id).orElse(null);
    }









}














