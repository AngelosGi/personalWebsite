package io.anggi.personalwebsite.controller;

import io.anggi.personalwebsite.dto.EducationDTO;
import io.anggi.personalwebsite.mapper.ResumeMapper;
import io.anggi.personalwebsite.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/education")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @GetMapping
    public List<EducationDTO> getAllEducations() {
        return educationService.getAllEducations().stream()
                .map(ResumeMapper::toEducationDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EducationDTO getEducationById(@PathVariable Long id) {
        return ResumeMapper.toEducationDTO(educationService.getEducationById(id));
    }

    @PostMapping
    public EducationDTO createEducation(@RequestBody EducationDTO educationDTO) {
        return ResumeMapper.toEducationDTO(educationService.saveEducation(ResumeMapper.toEducationEntity(educationDTO)));
    }

    @PutMapping("/{id}")
    public EducationDTO updateEducation(@PathVariable Long id, @RequestBody EducationDTO educationDTO) {
        educationDTO.setId(id);
        return ResumeMapper.toEducationDTO(educationService.saveEducation(ResumeMapper.toEducationEntity(educationDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
    }
}