package com.example.finaltest.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageUploadController {
    @GetMapping("getImage/{imageFile}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("imageFile") String imageFile){
        if (!imageFile.equals("") && imageFile != null){
            try{
                Path filename = Paths.get("uploads", imageFile);
                byte[] buffer = Files.readAllBytes(filename);
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return ResponseEntity.ok()
                        .contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType("image/png"))
                        .body(byteArrayResource);
            }
            catch (Exception e){
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
