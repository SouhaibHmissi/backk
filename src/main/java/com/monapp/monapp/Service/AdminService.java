package com.monapp.monapp.Service;
import com.monapp.monapp.Model.Role;
import com.monapp.monapp.Model.User;
import com.monapp.monapp.Repository.UserRepo;
import com.monapp.monapp.auth.AuthenticationResponse;
import com.monapp.monapp.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {


        private final JwtService jwtService;

        private final UserRepo userRepository;
        private final PasswordEncoder passwordEncoder;
        @Autowired
        private UserRepo personnelRepo;



    public AdminService(JwtService jwtService, UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> afficherchefs() {
        return userRepository.findAllByRole(Role.CHEF);
    }

    public List<User> afficheremployes() {
        return userRepository.findAllByRole(Role.EMPLOYE);
    }

    public Optional<User> afficherpersonnel(Long id) {
        return userRepository.findById(id);
    }


    public AuthenticationResponse ajouterPersonnel(RegisterRequest request) {
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
       User user =User.builder()

                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .service(request.getService())
                .tel(request.getTel())
                .statut(request.getStatut())
                .nbr_enfant(request.getNbr_enfant())
                .sexe(request.getSexe())
                .email(request.getEmail())
                .id(id)
                .role(role)
                .build();
        // user.setPassword(passwordEncoder.encode(generatedPassword));

        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }





    public long countChefs() {
        return personnelRepo.countByRole(Role.CHEF);
    }
    public long countEmployes() {
        return personnelRepo.countByRole(Role.EMPLOYE);
    }
















    public void supprimerpersonnel (Long iduser){
            userRepository.deleteById(iduser);
        }
        @Transactional
        public User modifierpersonnel (User updateUser, Long id){
            User user = userRepository.findById(id).orElse(null);
            user.setNom(updateUser.getNom());
            user.setPrenom(updateUser.getPrenom());
            user.setService(updateUser.getService());
            user.setStatut(updateUser.getStatut());
            user.setNbr_enfant(updateUser.getNbr_enfant());
            user.setEmail(updateUser.getEmail());
            user.setMatricule(updateUser.getMatricule());
            user.setSexe(updateUser.getSexe());
            user.setRole(updateUser.getRole());
            user.setLogin(updateUser.getLogin());
            return userRepository.save(user);
        }


        private static String generateMatricule(Long userId){
            Long matriculeValue = userId % 10000;
            return String.format("%04d", matriculeValue);
        }
    }


