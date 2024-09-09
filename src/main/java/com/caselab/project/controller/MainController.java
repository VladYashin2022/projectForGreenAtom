package com.caselab.project.controller;

import com.caselab.project.entity.File;
import com.caselab.project.repository.FileRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    @Autowired
    private FileRepo fileRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/rosatom/post-file")
    public long addFile(@RequestBody File file) {
        File.encodeFileBase64(file);
        log.info("New row: {}", fileRepo.save(file));
        return file.getId();
    }

    @SneakyThrows
    @GetMapping("/rosatom/get-all-files")
    public List<File> getAllFiles() {
        List<File> files = fileRepo.findAll();
        if (files.isEmpty()){
            throw new FileNotFoundException("File not found");
        } else {
            files.forEach(File::decodeFileBase64);
            return files;
        }
    }

    @GetMapping("/rosatom")
    public File getFile(@RequestParam long id) {
        File.decodeFileBase64(fileRepo.findById(id).get());
        return fileRepo.findById(id).orElseThrow(() -> new RuntimeException("No row found"));
    }

    @DeleteMapping("/rosatom")
    public String deleteFile(@RequestParam long id) {
        if(fileRepo.existsById(id)) {
            fileRepo.deleteById(id);
            return "success";
        } else {
            return "file not found";
        }
    }

    @DeleteMapping("/rosatom/delete-all")
    public String deleteAllFiles() {
        fileRepo.deleteAll();
        return "all files deleted";
    }

    @PutMapping("/rosatom/change-file")
    public String updateFile(@RequestBody File file) {
        if(!fileRepo.existsById(file.getId())) {
            return "not found";
        }
        return fileRepo.save(file).toString();
    }
}
