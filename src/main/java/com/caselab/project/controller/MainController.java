package com.caselab.project.controller;

import com.caselab.project.entity.File;
import com.caselab.project.repository.FileRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    @Autowired
    private FileRepo fileRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/rosatom/post-file")
    public String addCat(@RequestBody File file) throws JsonProcessingException {
        log.info("New row: {}", fileRepo.save(file));
        return "success";
    }

    @SneakyThrows
    @GetMapping("/rosatom/get-all-files")
    public List<File> getAllCats() {
        return fileRepo.findAll();
    }

    @GetMapping("/rosatom")
    public File getFile(@RequestParam int id) {
        return fileRepo.findById(id).orElseThrow(() -> new RuntimeException("No row found"));
    }

    @DeleteMapping("/rosatom")
    public String deleteFile(@RequestParam int id) {
        if(fileRepo.existsById(id)) {
            fileRepo.deleteById(id);
            return "success";
        } else {
            return "file not found";
        }
    }

    @PutMapping("/rosatom/change-file")
    public String updateCat(@RequestBody File file) {
        if(!fileRepo.existsById(file.getId())) {
            return "not found";
        }
        return fileRepo.save(file).toString();
    }
}
