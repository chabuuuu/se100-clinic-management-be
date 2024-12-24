package com.se100.clinic_management.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("api/files")
public class UploadController {

  private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

  public UploadController() throws IOException {
    // Tạo thư mục lưu trữ nếu chưa tồn tại
    Files.createDirectories(fileStorageLocation);
  }

  // 1. API để upload file
  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
      Path targetLocation = fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      // Tạo link tải file
      String downloadLink = ServletUriComponentsBuilder.fromCurrentContextPath()
          .path("api/files/download/")
          .path(fileName)
          .toUriString();

      return ResponseEntity.ok(downloadLink);

    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Could not upload file: " + e.getMessage());
    }
  }

  // 2. API để download file
  @GetMapping("/download/{fileName}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
    try {
      Path filePath = fileStorageLocation.resolve(fileName).normalize();

      Resource resource = new UrlResource(filePath.toUri());

      if (!resource.exists()) {
        return ResponseEntity.notFound().build();
      }

      String contentType = Files.probeContentType(filePath);
      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
          .body(resource);

    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(null);
    }
  }
}
