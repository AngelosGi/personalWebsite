package io.anggi.personalwebsite.controller;

import io.anggi.personalwebsite.dto.ExperienceDTO;
import io.anggi.personalwebsite.mapper.ResumeMapper;
import io.anggi.personalwebsite.model.Experience;
import io.anggi.personalwebsite.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @GetMapping("/{id}")
    public ExperienceDTO getExperienceById(@PathVariable Long id) {
        return ResumeMapper.toExperienceDTO(experienceService.getExperienceById(id));
    }

    @GetMapping("/all")
    public List<ExperienceDTO> getAllExperiences() {
        return experienceService.getAllExperiences().stream()
                .map(ResumeMapper::toExperienceDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/edit")
    public ExperienceDTO createExperience(@RequestBody ExperienceDTO experienceDTO) {
        Experience experience = ResumeMapper.toExperienceEntity(experienceDTO);
        Experience savedExperience = experienceService.saveExperience(experience);
        return ResumeMapper.toExperienceDTO(savedExperience);
    }

    @PutMapping("/edit/{id}")
    public ExperienceDTO updateExperience(@PathVariable Long id, @RequestBody ExperienceDTO experienceDTO) {
        // Fetch the existing experience to maintain the resume reference
        Experience existingExperience = experienceService.getExperienceById(id);
        Experience experience = ResumeMapper.toExperienceEntity(experienceDTO);
        experience.setId(id);
        experience.setResume(existingExperience.getResume()); // Maintain the resume reference
        Experience updatedExperience = experienceService.saveExperience(experience);
        return ResumeMapper.toExperienceDTO(updatedExperience);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
    }
}
