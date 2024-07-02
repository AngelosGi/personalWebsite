package io.anggi.personalwebsite.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;
    private String institution;
    private String startDate;
    private String endDate;
    private String major;
    private String minor;
    private double gpa;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    @ToString.Exclude
    private Resume resume;
}
