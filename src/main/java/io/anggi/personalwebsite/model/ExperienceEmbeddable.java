package io.anggi.personalwebsite.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable // Mark this class as embeddable
@Data
@NoArgsConstructor
public class ExperienceEmbeddable {

    private String jobTitle;
    private String company;
    private String startDate;
    private String endDate;

    @Column(columnDefinition = "TEXT") // Keep column definition if needed
    private String description;

    // We'll keep responsibilities as a simple list of strings within the experience
    @Column(length = 1000) // Optional: Define column characteristics for the strings
    private List<String> responsibilities;


}