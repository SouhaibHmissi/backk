package com.monapp.monapp.Controller;



import com.monapp.monapp.Repository.UserRepo;
import com.monapp.monapp.auth.AuthenticationResponse;
import com.monapp.monapp.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.monapp.monapp.Model.User;
import com.monapp.monapp.Service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins="http://localhost:4200/")
public class AdminCont {

    @Autowired
    private AdminService chef_crud;
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private AdminService adminService;
    @Autowired
private UserRepo userRepository;
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
            return ResponseEntity.ok("Login successful");

    }
    @GetMapping("/afficherchefs")
    public List<User> affichertouschefs() {
       return  chef_crud.afficherchefs();
    }


    @GetMapping("/afficheremployes")
    public List<User> affichertousemployes() {
        return  chef_crud.afficheremployes() ;
    }
    /*
    @PostMapping("/ajouterchef")
    public User ajouterchef(@RequestBody User user) {
      user  =chef_crud.ajouterpersonnel(user);
      return user;
    }
    */


    @PostMapping("/ajouteruser")
    public ResponseEntity<AuthenticationResponse> ajouteruser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(adminService.ajouterPersonnel(request));
    }


    @DeleteMapping ("/supprimeruser/{id}")
    public void supprimerchef(@PathVariable Long id) {
        chef_crud.supprimerpersonnel(id);
    }


    @GetMapping("/afficheruser/{id}")
    public Optional<User> afficherchef(@PathVariable Long id) {
        return chef_crud.afficherpersonnel(id);
    }

    @PutMapping("/modifieruser/{id}")
    public ResponseEntity<User> modifierpersonnel(@RequestBody User user, @PathVariable Long id) {
        User updateuser= chef_crud.modifierpersonnel(user,id);
        return ResponseEntity.ok(updateuser);
    }
    @GetMapping("/nbrchefs")
    public Long nombrechef()
    {
    Long  nbrchefs=  adminService.countChefs();
      return  nbrchefs;
    }
    @GetMapping("/nbremployes")
    public Long nombreemploye()
    {
        Long  nbremployes=  adminService.countEmployes();
        return  nbremployes;
    }
}
