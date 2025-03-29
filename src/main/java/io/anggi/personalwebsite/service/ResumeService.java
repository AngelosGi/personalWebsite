package io.anggi.personalwebsite.service;

import io.anggi.personalwebsite.exception.ResourceNotFoundException;
import io.anggi.personalwebsite.model.Resume;
import io.anggi.personalwebsite.repository.ResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResumeService {

    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);

    @Autowired
    private ResumeRepository resumeRepository;


    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Resume getResumeById(Long id) {
        logger.info("Fetching resume with id: {}", id);
        return resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
    }

    @Transactional
    public Resume saveResume(Resume resume) {
        logger.info("Saving resume: {}", resume);

        return resumeRepository.save(resume);
    }

    @Transactional
    public void deleteResume(Long id) {
        logger.info("Deleting resume with id: {}", id);
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
        resumeRepository.delete(resume);
    }

}
