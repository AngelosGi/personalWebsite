package io.anggi.personalwebsite.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;


@Entity
@Getter
@Setter
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String institution;
    private String duration;
    private String degree;
    private String major;
    private String minor;
    private double gpa;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
