package com.monapp.monapp.Service;

import com.monapp.monapp.Model.Conge;
import com.monapp.monapp.Model.User;
import com.monapp.monapp.Repository.CongeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class chefService {
    @Autowired
  private CongeRepo congeRepo;

    public Conge refuseConge(Long congeId) {
        Optional<Conge> optionalConge = congeRepo.findById(congeId);
            Conge conge = optionalConge.get();
            conge.setStatut("Refusé");
            return congeRepo.save(conge);
    }
    public Conge confirmerConge(Long congeId) {
        Optional<Conge> optionalConge = congeRepo.findById(congeId);
        Conge conge = optionalConge.get();
        conge.setStatut("Confirmé");
        return congeRepo.save(conge);
    }
    public User getuser(Long congeId){
        Optional<Conge> optionalConge = congeRepo.findById(congeId);
        Conge conge = optionalConge.get();
        return conge.getUser();
    }



}
