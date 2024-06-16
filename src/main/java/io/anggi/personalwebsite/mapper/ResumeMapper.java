package io.anggi.personalwebsite.mapper;

import io.anggi.personalwebsite.dto.*;
import io.anggi.personalwebsite.model.*;

import java.util.stream.Collectors;

public class ResumeMapper {

    public static ResumeDTO toResumeDTO(Resume resume) {
        ResumeDTO dto = new ResumeDTO();
        dto.setId(resume.getId());
        dto.setName(resume.getName());
        dto.setJobTitle(resume.getJobTitle());
        dto.setLocation(resume.getLocation());
        dto.setPortfolioUrl(resume.getPortfolioUrl());
        dto.setGithubUrl(resume.getGithubUrl());
        dto.setEmail(resume.getEmail());
        dto.setPhone(resume.getPhone());
        dto.setSummary(resume.getSummary());
        dto.setEducations(resume.getEducationList().stream().map(ResumeMapper::toEducationDTO).collect(Collectors.toList()));
        dto.setExperiences(resume.getExperienceList().stream().map(ResumeMapper::toExperienceDTO).collect(Collectors.toList()));
        return dto;
    }

    public static Resume toResumeEntity(ResumeDTO dto) {
        Resume resume = new Resume();
        resume.setId(dto.getId());
        resume.setName(dto.getName());
        resume.setJobTitle(dto.getJobTitle());
        resume.setLocation(dto.getLocation());
        resume.setPortfolioUrl(dto.getPortfolioUrl());
        resume.setGithubUrl(dto.getGithubUrl());
        resume.setEmail(dto.getEmail());
        resume.setPhone(dto.getPhone());
        resume.setSummary(dto.getSummary());
        resume.setEducationList(dto.getEducations().stream().map(ResumeMapper::toEducationEntity).collect(Collectors.toList()));
        resume.setExperienceList(dto.getExperiences().stream().map(ResumeMapper::toExperienceEntity).collect(Collectors.toList()));
        return resume;
    }


    public static EducationDTO toEducationDTO(Education education) {
        EducationDTO dto = new EducationDTO();
        dto.setId(education.getId());
        dto.setInstitution(education.getInstitution());
        dto.setDuration(education.getStartDate() + " - " + education.getEndDate());
        dto.setDegree(education.getDegree());
        dto.setMajor(education.getMajor());
        dto.setMinor(education.getMinor());
        dto.setGpa(education.getGpa());
        return dto;
    }

    public static ExperienceDTO toExperienceDTO(Experience experience) {
        ExperienceDTO dto = new ExperienceDTO();
        dto.setId(experience.getId());
        dto.setTitle(experience.getJobTitle());
        dto.setDuration(experience.getStartDate() + " - " + experience.getEndDate());
        dto.setOrganization(experience.getCompany());
        dto.setResponsibilities(experience.getDescription().lines().collect(Collectors.toList()));
        return dto;
    }

    public static Education toEducationEntity(EducationDTO dto) {
        Education entity = new Education();
        entity.setId(dto.getId());
        entity.setInstitution(dto.getInstitution());
        entity.setDegree(dto.getDegree());
        entity.setMajor(dto.getMajor());
        entity.setMinor(dto.getMinor());
        entity.setGpa(dto.getGpa());
        String[] duration = dto.getDuration().split(" - ");
        entity.setStartDate(duration[0]);
        entity.setEndDate(duration[1]);
        return entity;
    }

    public static Experience toExperienceEntity(ExperienceDTO dto) {
        Experience entity = new Experience();
        entity.setId(dto.getId());
        entity.setJobTitle(dto.getTitle());
        entity.setCompany(dto.getOrganization());
        String[] duration = dto.getDuration().split(" - ");
        entity.setStartDate(duration[0]);
        entity.setEndDate(duration[1]);
        entity.setDescription(String.join("\n", dto.getResponsibilities()));
        return entity;
    }
}