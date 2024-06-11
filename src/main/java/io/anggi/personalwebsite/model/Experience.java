package io.anggi.personalwebsite.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String duration;
    private String organization;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}