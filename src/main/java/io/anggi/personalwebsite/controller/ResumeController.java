package io.anggi.personalwebsite.controller;

import io.anggi.personalwebsite.dto.ResumeDTO;
import io.anggi.personalwebsite.mapper.ResumeMapper;
import io.anggi.personalwebsite.model.Resume;
import io.anggi.personalwebsite.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/{id}")
    public ResumeDTO getResumeById(@PathVariable Long id) {
        return ResumeMapper.toResumeDTO(resumeService.getResumeById(id));
    }

    @GetMapping
    public List<ResumeDTO> getAllResumes() {
        return resumeService.getAllResumes().stream().map(ResumeMapper::toResumeDTO).collect(Collectors.toList());
    }

    @PostMapping
    public ResumeDTO createResume(@RequestBody ResumeDTO resumeDTO) {
        Resume resume = ResumeMapper.toResumeEntity(resumeDTO);
        Resume savedResume = resumeService.saveResume(resume);
        return ResumeMapper.toResumeDTO(savedResume);
    }

    @PutMapping("/{id}")
    public ResumeDTO updateResume(@PathVariable Long id, @RequestBody ResumeDTO resumeDTO) {
        Resume resume = ResumeMapper.toResumeEntity(resumeDTO);
        resume.setId(id); // Ensure the entity has the correct ID
        Resume updatedResume = resumeService.saveResume(resume);
        return ResumeMapper.toResumeDTO(updatedResume);
    }

    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable Long id) {
        resumeService.deleteResume(id);
    }

}
