package com.monapp.monapp.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest  {
    private Long id;
    private String nom;
    private String prenom;
    private String login;
    private String password;
    private String service;
    private String statut;
    private String role;
    private String sexe;
    private String tel;
    private String email;
    private Integer nbr_enfant;
    private String matricule;

}
