package com.monapp.monapp.Service;

import com.monapp.monapp.Model.Conge;
import com.monapp.monapp.Repository.CongeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
@Service
public class employeService {
    @Autowired
    private CongeRepo congeRepo;
    @Autowired
    private AdminService chef_crud;



//    public String storeFile(MultipartFile file) throws IOException {
//        // Normalize file name
//        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//
//        // Copy file to the target location
//        Path targetLocation = Paths.get(uploadDir + "/" + fileName);
//        Files.copy(file.getInputStream(), targetLocation);
//
//        return fileName;
   // }
//@Value("${upload.dir}")
//private String UPLOAD_DIR;
//
//    public String uploadPdfFile(MultipartFile file, int congeId) {
//        if (file.isEmpty()) {
//            return "Please select a file to upload";
//        }
//        try {
//            // Get the filename and build the local file path
//            String filename = file.getOriginalFilename();
//            String filepath = UPLOAD_DIR + File.separator + filename;
//
//            // Save the file to the static directory
//            File destinationFile = new File(filepath);
//            file.transferTo(destinationFile);
//
//            // Mettre à jour le chemin du fichier dans l'objet Conge
//            Optional<Conge> optionalConge = congeRepo.findById(congeId);
//            if (optionalConge.isPresent()) {
//                Conge conge = optionalConge.get();
//                conge.setFile(filepath);
//                congeRepo.save(conge);
//                return "File uploaded successfully: " + filepath;
//            } else {
//                return "Conge with ID " + congeId + " not found";
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Failed to upload file";
//        }
//    }

    public Conge ajouterconge(Conge conge) {
        return this.congeRepo.save(conge);}
    public List<Conge> afficherconges(){
        return congeRepo.findAll();
    }

    public Optional<Conge> afficherconge(Long id){ return congeRepo.findById(id);}

}
