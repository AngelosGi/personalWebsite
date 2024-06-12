package io.anggi.personalwebsite.controller;

import io.anggi.personalwebsite.dto.ResumeDTO;
import io.anggi.personalwebsite.mapper.ResumeMapper;
import io.anggi.personalwebsite.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/{id}")
    public ResumeDTO getResumeById(@PathVariable Long id) {
        return ResumeMapper.toResumeDTO(resumeService.getResumeById(id));
    }


}
