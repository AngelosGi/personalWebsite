package io.anggi.personalwebsite.service;

import io.anggi.personalwebsite.model.Experience;
import io.anggi.personalwebsite.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id).orElse(null);
    }

    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }


}
