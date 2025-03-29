package io.anggi.personalwebsite.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String jobTitle;
    private String location;
    private String portfolioUrl;
    private String linkedinUrl;
    private String githubUrl;
    private String email;
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @ElementCollection
    @CollectionTable(name = "resume_education", joinColumns = @JoinColumn(name = "resume_id"))
    private List<EducationEmbeddable> educationEntries = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "resume_experience", joinColumns = @JoinColumn(name = "resume_id"))
    @OrderColumn(name = "experience_order")
    private List<ExperienceEmbeddable> experienceEntries = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "resume_skills", joinColumns = @JoinColumn(name = "resume_id"))
    @Column(name = "skill")
    @OrderColumn(name = "skill_order")
    private List<String> skills = new ArrayList<>();
}
