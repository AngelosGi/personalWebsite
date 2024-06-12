package io.anggi.personalwebsite.dto;

import lombok.Data;

@Data
public class EducationDTO {
    private Long id;
    private String institution;
    private String duration;
    private String degree;
    private String major;
    private String minor;
    private double gpa;
}
