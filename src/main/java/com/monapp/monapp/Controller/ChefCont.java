package com.monapp.monapp.Controller;

import com.monapp.monapp.Model.Conge;
import com.monapp.monapp.Model.User;
import com.monapp.monapp.Service.EmailService;

import com.monapp.monapp.Service.chefService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/chef")
public class ChefCont {
    @Autowired
    private chefService chefService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/refuserconge/{congeId}")
    public ResponseEntity<Conge> refuserConge(@PathVariable Long congeId) {
        Conge conge = chefService.refuseConge(congeId);
        User user = chefService.getuser(congeId);
        String recipient = user.getEmail();
        String sender = user.getEmail();
        String subject = "Validation congé";
        String body = "Votre demande de congé a été Refusé.";
        try {
            emailService.sendEmail(sender, recipient, subject, body);
            return ResponseEntity.ok(conge);
        } catch (MessagingException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    private static final String UPLOAD_DIR = "monapp/src/main/resources/static/";

    @PostMapping("/confirmerconge/{congeId}")
    public ResponseEntity<Conge> confirmerConge(@PathVariable Long congeId) {
        Conge conge = chefService.confirmerConge(congeId);
        User user = chefService.getuser(congeId);
        String recipient = user.getEmail();
        String sender = user.getEmail();
        String subject = "Validation congé";
        String body = "Votre demande de congé a été Confirmé.";
        try {
            emailService.sendEmail(sender, recipient, subject, body);
            return ResponseEntity.ok(conge);
        } catch (MessagingException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




//    @PostMapping("auth/createAd")
//    public String createAd(
//            @RequestParam("adsImages") MultipartFile[] adsImages
//    ) throws IOException {
//        String uploadDirectory = "monapp/src/main/resources/static/images/ads";
//        String adsImagesString = "";
//
//        for (MultipartFile imageFile : adsImages) {
//            adsImagesString += imageService.saveImageToStorage(uploadDirectory, imageFile) + ",";
//        }
//
//        return adsImagesString;
//
//    }
}