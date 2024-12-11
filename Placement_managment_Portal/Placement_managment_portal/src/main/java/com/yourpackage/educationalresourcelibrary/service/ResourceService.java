package com.yourpackage.educationalresourcelibrary.service;

import com.yourpackage.educationalresourcelibrary.model.Resource;
import com.yourpackage.educationalresourcelibrary.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    private static final String RESOURCE_DIR = "/resources/";

    public void saveResource(String title, String description, String category, String tags, String publicationDate, MultipartFile file, MultipartFile coverPhoto) throws IOException {
        Resource resource = new Resource();
        resource.setTitle(title);
        resource.setDescription(description);
        resource.setCategory(category);
        resource.setTags(tags);
        resource.setPublicationDate(LocalDate.parse(publicationDate));

        // Save file data
        if (file != null) {
            String filePath = saveFile(file);
            resource.setFileData(filePath.getBytes());
        }
        
        // Save cover photo data
        if (coverPhoto != null) {
            String coverPhotoPath = saveFile(coverPhoto);
            resource.setCoverPhoto(coverPhotoPath.getBytes());
        }

        resourceRepository.save(resource);
    }

    private String saveFile(MultipartFile file) throws IOException {
        Path path = Paths.get(RESOURCE_DIR + file.getOriginalFilename());
        Files.write(path, file.getBytes());
        return path.toString();
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
    
    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id).orElse(null); // Fetch the resource by ID
    }

    
}
