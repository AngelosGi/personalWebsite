package io.anggi.personalwebsite.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExperienceDTO {
    private Long id;
    private String title;
    private String duration;
    private String organization;
    private List<String> responsibilities;
//    private List<String> skills;
}
