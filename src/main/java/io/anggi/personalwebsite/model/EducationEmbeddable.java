package io.anggi.personalwebsite.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable 
@Data
@NoArgsConstructor
public class EducationEmbeddable {

    private String degree;
    private String institution;
    private String startDate;
    private String endDate;
    private String major;
    private String minor;
    private double gpa;

}