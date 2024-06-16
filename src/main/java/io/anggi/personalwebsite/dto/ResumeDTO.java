package io.anggi.personalwebsite.dto;

import lombok.Data;
import java.util.List;

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
    private List<EducationDTO> educations;
    private List<ExperienceDTO> experiences;
}
