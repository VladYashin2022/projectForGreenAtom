package com.caselab.project.controller;

import com.caselab.project.entity.File;
import com.caselab.project.repository.FileRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private FileRepo fileRepo;

    @Autowired
    public MainController(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    //Добавление файла
    @PostMapping("/rosatom/post-file")
    public long addFile(@RequestBody File file) {
        File.encodeFileBase64(file);
        file.setCreation_date(new Date().toString());
        fileRepo.save(file);
        return file.getId();
    }

    //Получение всех файлов
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

    //Получение определенного файла по id
    @GetMapping("/rosatom")
    public File getFile(@RequestParam long id) {
        File.decodeFileBase64(fileRepo.findById(id).get());
        return fileRepo.findById(id).orElseThrow(() -> new RuntimeException("No row found"));
    }

    //Удаление файла по id
    @DeleteMapping("/rosatom")
    public String deleteFile(@RequestParam long id) {
        if(fileRepo.existsById(id)) {
            fileRepo.deleteById(id);
            return "success";
        } else {
            return "file not found";
        }
    }

    //Удаление всех файлов в БД
    @DeleteMapping("/rosatom/delete-all")
    public String deleteAllFiles() {
        fileRepo.deleteAll();
        return "all files deleted";
    }

    //Изменение файла
    @PutMapping("/rosatom/change-file")
    public String updateFile(@RequestBody File file) {
        if(!fileRepo.existsById(file.getId())) {
            return "not found";
        }
        return fileRepo.save(file).toString();
    }
}
