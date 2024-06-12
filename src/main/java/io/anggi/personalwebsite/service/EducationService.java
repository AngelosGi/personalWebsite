package io.anggi.personalwebsite.service;

import io.anggi.personalwebsite.model.Education;
import io.anggi.personalwebsite.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    public List<Education> getAllEducations() {
        return educationRepository.findAll();
    }


    public Education getEducationById(Long id) {
        return educationRepository.findById(id).orElse(null);
    }

    public Education saveEducation(Education education) {
        return educationRepository.save(education);
    }

    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }
}
