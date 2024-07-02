package io.anggi.personalwebsite.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String company;
    private String startDate;
    private String endDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    @ToString.Exclude
    private Resume resume;

    @ElementCollection
    @Column(length = 1000)
    private List<String> responsibilities;

//    @ElementCollection
//    private List<String> skills;
}
