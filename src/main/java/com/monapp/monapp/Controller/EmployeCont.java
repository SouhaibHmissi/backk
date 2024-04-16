package com.monapp.monapp.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.monapp.monapp.util.FileUploadUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import com.monapp.monapp.Model.Conge;
import com.monapp.monapp.Model.Notification;
import com.monapp.monapp.Repository.CongeRepo;
import com.monapp.monapp.Service.employeService;
import com.monapp.monapp.Service.NotService;
import com.monapp.monapp.Service.CongeTriggerService;
import com.monapp.monapp.Service.EmailService;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.nio.file.*;

import java.io.IOException;
import java.util.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/employe")

public class EmployeCont {

    private final employeService employeService;
    private final NotService notService;
    private final EmailService emailService;
    private final CongeTriggerService congeTriggerService;
    @Autowired
    private CongeRepo congeRepo;


    @Autowired
    public EmployeCont(employeService employeService, NotService notService, EmailService emailService, CongeTriggerService congeTriggerService) {
        this.employeService = employeService;
        this.notService = notService;
        this.emailService = emailService;
        this.congeTriggerService = congeTriggerService;
    }

    @GetMapping("/afficherconges")
    public List<Conge> affichertousConges() {
        return employeService.afficherconges();
    }

    @GetMapping("/affichernotification")
    public List<Notification> affichernotifications() {
        return notService.affichernotifications();
    }


    @PostMapping("/ajouterconge")
    public ResponseEntity<Conge>  ajouterConge( @RequestParam("file") MultipartFile file,@RequestParam("conge") String conge) throws MessagingException {
        try {

            /*Conge conges = new Conge(conge.getDuree(), conge.getType(), conge.getMotif(), conge.getDate_debut(),
                   conge.getDate_fin(),conge.getStatut(),conge.getUser(), conge.getSolde());*/
            Conge conges = new ObjectMapper().readValue(conge, Conge.class);

            String fileName = null;
            if (file != null) {
                fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                conges.setFile(fileName);
            }
            // Save user to database
            conges.setDate_demande(new Date());
            conges.setStatut("En_Attente");
            Conge savedConge = congeRepo.save(conges);
            // Save image file if multipartFile is not null
            if (file != null) {
                String uploadDir = "user-photos/" ;
                FileUploadUtil.saveFile(uploadDir, fileName, file);
            }
            congeTriggerService.createCongeTrigger();
            return ResponseEntity.ok(savedConge);

//             Notification not = notService.getLatestNotification();
//             String sender = notService.getSenderEmailFromNotification(not.getSender_id());
//            String recipient = notService.getRecipEmailFromNotification(not.getRecipient_id());
//           String subject = not.getSubject();
//          String body = not.getContent();
//        emailService.sendEmail(sender, recipient, subject, body);
            // Appel de l'API uploadPdf avec l'ID du congé nouvellement ajouté
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/images/{userId}/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long userId, @PathVariable String fileName) throws IOException {

        Optional<Conge> userOptional = congeRepo.findById(userId);
        if (userOptional.isEmpty()) {
            String errorMessage = "User not found with ID: " + userId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage.getBytes());
        }
        // Construct the path to the image file
        String filePath = "user-photos/" + userId + "/" + fileName;
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            String errorMessage = "Image not found for user ID: " + userId + " and file name: " + fileName;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage.getBytes());
        }

        // Read the image file as bytes
        byte[] imageData = Files.readAllBytes(path);

        // Set content type header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type as needed

        // Serve the image data as a response
        return ResponseEntity.ok().headers(headers).body(imageData);
    }
}

//    private static final String UPLOAD_DIR = "src/main/resources/static/images/";
//
//
//    @PostMapping("/uploadPdf/{id}")
//    @ResponseBody
//    public String uploadPdfFile(@RequestParam("file") MultipartFile file, @PathVariable("id") int id) {
//        if (file.isEmpty()) {
//            return "Please select a file to upload";
//        }
//        try {
//            // Convert MultipartFile to byte[]
//            byte[] fileBytes = file.getBytes();
//
//            // Mettre à jour les données du fichier dans l'objet Conge
//            Optional<Conge> optionalConge = congeRepo.findById(id);
//            if (optionalConge.isPresent()) {
//                Conge conge = optionalConge.get();
//                conge.setFile(fileBytes);
//                congeRepo.save(conge);
//                return "File uploaded successfully: " + file.getOriginalFilename();
//            } else {
//                return "Conge with ID " + id + " not found";
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Failed to upload file";
//        }
//    }


//    @GetMapping("auth/getImage/{imageName}")
//    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName) throws IOException {
//        String imageDirectory = "src/main/resources/static/";
//        Path imagePath = Paths.get(imageDirectory, imageName);
//
//        // Read the image file into a byte array
//        byte[] imageBytes = Files.readAllBytes(imagePath);
//
//        // Determine the media type of the image
//        MediaType mediaType = MediaType.IMAGE_JPEG; // Assuming JPEG format
//
//        // Create the response entity with image bytes and appropriate headers
//        return ResponseEntity.ok()
//                .contentType(mediaType)
//                .body(imageBytes);
//    }
//
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {
//// Check if multipartFile is not null before
//        Conge conge = new Conge();
//        String fileName = null;
//        if (multipartFile != null) {
//            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//            conge.setFile(fileName);
//        }
//        // Save user to database
//        Conge savedUser = congeRepo.save(conge);
//// Save image file if multipartFile is not null
//        if (multipartFile != null) {
//            String uploadDir = "user-photos/" + savedUser.getId();
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//        }
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//
//    }



