package io.anggi.personalwebsite.dto;
import io.anggi.personalwebsite.model.EducationEmbeddable;
import io.anggi.personalwebsite.model.ExperienceEmbeddable;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Data
public class ResumeDTO {
    private Long id;
    private String name;
    private String jobTitle;
    private String location;
    private String portfolioUrl;
    private String linkedinUrl;
    private String githubUrl;
    private String email;
    private String phone;
    private String summary;

    private List<EducationEmbeddable> educationEntries = new ArrayList<>();
    private List<ExperienceEmbeddable> experienceEntries = new ArrayList<>();
    private List<String> skills = new ArrayList<>();
}
