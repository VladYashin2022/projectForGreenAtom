package com.caselab.project.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileBase64;
    private String encodedString = Base64.getEncoder().encodeToString(fileBase64.getBytes());
    private String decodedString = Base64.getDecoder().decode(encodedString).toString();

    @Column(unique = true, nullable = false)
    private String title;

    private String creation_date;
    private String description;

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
