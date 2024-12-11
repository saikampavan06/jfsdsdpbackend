package com.yourpackage.educationalresourcelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.yourpackage.educationalresourcelibrary.model.Resource;
import com.yourpackage.educationalresourcelibrary.model.Application;
import com.yourpackage.educationalresourcelibrary.repository.ResourceRepository;
import com.yourpackage.educationalresourcelibrary.repository.ApplicationRepository;
import com.yourpackage.educationalresourcelibrary.service.ResourceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    // Endpoint for uploading a resource
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadResource(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("tags") String tags,
            @RequestParam("publicationDate") String publicationDate,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "coverPhoto", required = false) MultipartFile coverPhoto) {
        try {
            resourceService.saveResource(title, description, category, tags, publicationDate, file, coverPhoto);
            return ResponseEntity.ok(Map.of("message", "Resource uploaded successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error uploading resource"));
        }
    }

    // Endpoint for fetching all resources
    @GetMapping("/resources")
    public ResponseEntity<List<Resource>> getResources() {
        List<Resource> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }

    // Endpoint for fetching a resource by ID
    @GetMapping("/resources/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        return resourceRepository.findById(id)
            .map(resource -> ResponseEntity.ok(resource))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for applying for a resource
    @PostMapping("/apply")
    public ResponseEntity<String> applyForResource(
            @RequestParam("username") String username,
            @RequestParam("resourceTitle") String resourceTitle) {
        Application application = new Application();
        application.setUsername(username);
        application.setResourceTitle(resourceTitle);
        application.setApplicationDateTime(LocalDateTime.now());

        applicationRepository.save(application);

        return ResponseEntity.ok("Application submitted successfully!");
    }
}
