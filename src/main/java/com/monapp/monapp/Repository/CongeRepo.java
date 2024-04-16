package com.monapp.monapp.Repository;

import com.monapp.monapp.Model.Conge;
import com.monapp.monapp.Model.User;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Optional;

@Repository
public interface CongeRepo extends JpaRepository<Conge,Long> {

    Optional<Conge> findById(Long congeId);
}
