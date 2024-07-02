package io.anggi.personalwebsite.service;

import io.anggi.personalwebsite.exception.ResourceNotFoundException;
import io.anggi.personalwebsite.model.Resume;
import io.anggi.personalwebsite.repository.ResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {

    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);

    @Autowired
    private ResumeRepository resumeRepository;


    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    public Resume getResumeById(Long id) {
        logger.info("Fetching resume with id: {}", id);
        return resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
    }


    public Resume saveResume(Resume resume) {
        logger.info("Saving resume: {}", resume);
        if (resume.getEducationList() != null) {
            resume.getEducationList().forEach(education -> education.setResume(resume));
        }
        if (resume.getExperienceList() != null) {
            resume.getExperienceList().forEach(experience -> {
                experience.setResume(resume);
                logger.info("Experience responsibilities: {}", experience.getResponsibilities());
            });
        }
        return resumeRepository.save(resume);
    }

    public void deleteResume(Long id) {
        logger.info("Deleting resume with id: {}", id);
        resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
        resumeRepository.deleteById(id);
    }

}
