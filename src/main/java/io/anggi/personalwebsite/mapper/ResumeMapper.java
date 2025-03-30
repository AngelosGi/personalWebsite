package io.anggi.personalwebsite.mapper;

import io.anggi.personalwebsite.dto.*;
import io.anggi.personalwebsite.model.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ResumeMapper {

    public static ResumeDTO toResumeDTO(Resume resume) {
        if (resume == null) {
            return null;
        }
        ResumeDTO dto = new ResumeDTO();
        dto.setId(resume.getId());
        dto.setName(resume.getName());
        dto.setJobTitle(resume.getJobTitle());
        dto.setLocation(resume.getLocation());
        dto.setPortfolioUrl(resume.getPortfolioUrl());
        dto.setLinkedinUrl(resume.getLinkedinUrl());
        dto.setGithubUrl(resume.getGithubUrl());
        dto.setEmail(resume.getEmail());
        dto.setPhone(resume.getPhone());
        dto.setSummary(resume.getSummary());

        dto.setEducationEntries(resume.getEducationEntries());
        dto.setExperienceEntries(resume.getExperienceEntries());
        dto.setSkills(new ArrayList<>(resume.getSkills()));

        return dto;
    }

    public static Resume toResumeEntity(ResumeDTO dto) {
        if (dto == null) {
            return null;
        }
        Resume resume = new Resume();
        resume.setId(dto.getId());
        resume.setName(dto.getName());
        resume.setJobTitle(dto.getJobTitle());
        resume.setLocation(dto.getLocation());
        resume.setPortfolioUrl(dto.getPortfolioUrl());
        resume.setLinkedinUrl(dto.getLinkedinUrl());
        resume.setGithubUrl(dto.getGithubUrl());
        resume.setEmail(dto.getEmail());
        resume.setPhone(dto.getPhone());
        resume.setSummary(dto.getSummary());

        resume.setEducationEntries(dto.getEducationEntries());
        resume.setExperienceEntries(dto.getExperienceEntries());
        resume.setSkills(new ArrayList<>(dto.getSkills()));

        return resume;
    }

    
}
