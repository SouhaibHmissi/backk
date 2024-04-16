package com.monapp.monapp.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "service")
    private String service;

    @Column(name = "statut")
    private String statut;

    @Column(name = "nbr_enfant")
    private Integer nbr_enfant;

    @Column(name = "tel")
    private String tel;

    @Column(name = "sexe")
    private String sexe;

    @Column(name = "email")
    private String email;

    @Column(name = "matricule")
    private String matricule;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "login")
    private String login;


    @Column(name = "password")
    private String password;

    public User(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role != null) {
            // Retourner une liste d'autorisations en tant que chaînes
            return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
        } else {
            // Retourner une liste vide si le rôle est null
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

