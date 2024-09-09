package com.caselab.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Base64;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "files")

public class File  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String fileBase64;
    @Column(unique = true, nullable = false)
    private String title;
    @Column
    private String creation_date;
    @Column
    private String description;

    public File(String fileBase64, String title, String description) {
        this.fileBase64 = fileBase64;
        this.title = title;
        creation_date = new Date().toString();
        this.description = description;
    }

    public File() {

    }

    //метод для кодирования в Base64
    public static void encodeFileBase64(File file) {
        String encodedString = Base64.getEncoder().encodeToString(file.getFileBase64().getBytes());
        file.setFileBase64(encodedString);
    }

    //метод для декодирования
    public static void decodeFileBase64(File file) {
        byte[] decodedBytes = Base64.getDecoder().decode(file.getFileBase64());
        file.setFileBase64(new String(decodedBytes));
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creation_date='" + creation_date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


