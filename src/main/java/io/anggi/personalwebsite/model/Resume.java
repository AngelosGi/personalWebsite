package io.anggi.personalwebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Education> educationList;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Experience> experienceList;
}
