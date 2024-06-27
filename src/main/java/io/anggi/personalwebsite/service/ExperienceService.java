package io.anggi.personalwebsite.service;

import io.anggi.personalwebsite.exception.ResourceNotFoundException;
import io.anggi.personalwebsite.model.Experience;
import io.anggi.personalwebsite.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    // Retrieves all experience records
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    // Retrieves a specific experience record by ID, throws exception if not found
    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + id));
    }

    // Saves a new or existing experience record
    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    // Deletes an experience record by ID, throws exception if not found
    public void deleteExperience(Long id) {
        experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + id));
        experienceRepository.deleteById(id);
    }
}
