package com.monapp.monapp.Repository;

import com.monapp.monapp.Model.Role;
import com.monapp.monapp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);

    List<User> findByRole(Role roLe);


    Optional<User> findByLogin(String login);
    User findTopByOrderByMatriculeDesc();

    List<User> findAllByRole(Role role);
    long countByRole(Role role);

}