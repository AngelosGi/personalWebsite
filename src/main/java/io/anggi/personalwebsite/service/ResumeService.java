package io.anggi.personalwebsite.service;

import io.anggi.personalwebsite.exception.ResourceNotFoundException;
import io.anggi.personalwebsite.model.Resume;
import io.anggi.personalwebsite.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    // Retrieves all resume records
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    // Retrieves a specific resume record by ID, throws exception if not found
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
    }

    // Saves a new or existing resume record
    public Resume saveResume(Resume resume) {
        return resumeRepository.save(resume);
    }

    // Deletes a resume record by ID, throws exception if not found
    public void deleteResume(Long id) {
        resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
        resumeRepository.deleteById(id);
    }

    // Fetches the first resume from the database, returns a new Resume instance if not found
    public Resume getResume() {
        return resumeRepository.findById(1L).orElse(new Resume());
    }
}
