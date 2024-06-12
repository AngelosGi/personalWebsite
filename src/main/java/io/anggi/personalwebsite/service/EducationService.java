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

}
