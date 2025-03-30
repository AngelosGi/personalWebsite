package io.anggi.personalwebsite.mapper;

import io.anggi.personalwebsite.dto.ResumeDTO;
import io.anggi.personalwebsite.model.EducationEmbeddable; // Make sure this import is correct
import io.anggi.personalwebsite.model.ExperienceEmbeddable; // Make sure this import is correct
import io.anggi.personalwebsite.model.Resume;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList; // Needed for skills list creation

import static org.junit.jupiter.api.Assertions.*;

class ResumeMapperTest {

    @Test
    void shouldMapResumeEntityToDto() {
        // 1. Arrange: Create a sample Resume entity
        Resume resumeEntity = new Resume();
        resumeEntity.setId(1L);
        // Use setName instead of setFullName if that's the field in Resume entity
        resumeEntity.setName("Test User");
        resumeEntity.setJobTitle("Test Job");
        resumeEntity.setLocation("Test Location");
        resumeEntity.setPortfolioUrl("portfolio.example.com");
        resumeEntity.setLinkedinUrl("linkedin.example.com");
        resumeEntity.setGithubUrl("github.example.com");
        resumeEntity.setEmail("test@example.com");
        // Use setPhone instead of setPhoneNumber if that's the field in Resume entity
        resumeEntity.setPhone("123-456-7890");
        resumeEntity.setSummary("A test summary.");

        // Instantiate using default constructor and setters
        EducationEmbeddable edu1 = new EducationEmbeddable();
        edu1.setInstitution("Test University");
        edu1.setDegree("B.S. Comp Sci");
        edu1.setStartDate("2020"); // Adjust field names/values as needed
        edu1.setEndDate("2024");
        // edu1.setMajor("Computer Science"); // Add other fields if necessary for mapping/testing
        // edu1.setGpa(3.8);

        // Instantiate using default constructor and setters
        ExperienceEmbeddable exp1 = new ExperienceEmbeddable();
        exp1.setCompany("Test Company");
        exp1.setJobTitle("Software Intern");
        exp1.setStartDate("2023"); // Adjust field names/values as needed
        exp1.setEndDate("2023");
        exp1.setDescription("Worked on tests.");
        exp1.setResponsibilities(List.of("Wrote unit tests", "Debugged code"));

        // Use setEducationEntries and setExperienceEntries if those are the correct setters
        resumeEntity.setEducationEntries(List.of(edu1));
        resumeEntity.setExperienceEntries(List.of(exp1));
        resumeEntity.setSkills(Arrays.asList("Java", "Spring", "Testing"));

        // 2. Act: Call the static mapper method
        ResumeDTO resumeDTO = ResumeMapper.toResumeDTO(resumeEntity);

        // 3. Assert: Check if the DTO fields match the entity fields
        assertNotNull(resumeDTO);
        assertEquals(resumeEntity.getId(), resumeDTO.getId());
        assertEquals(resumeEntity.getName(), resumeDTO.getName()); // Check correct field name
        assertEquals(resumeEntity.getJobTitle(), resumeDTO.getJobTitle());
        assertEquals(resumeEntity.getLocation(), resumeDTO.getLocation());
        assertEquals(resumeEntity.getPortfolioUrl(), resumeDTO.getPortfolioUrl());
        assertEquals(resumeEntity.getLinkedinUrl(), resumeDTO.getLinkedinUrl());
        assertEquals(resumeEntity.getGithubUrl(), resumeDTO.getGithubUrl());
        assertEquals(resumeEntity.getEmail(), resumeDTO.getEmail());
        assertEquals(resumeEntity.getPhone(), resumeDTO.getPhone()); // Check correct field name
        assertEquals(resumeEntity.getSummary(), resumeDTO.getSummary());

        // Assert collections (check size and potentially first element)
        assertNotNull(resumeDTO.getEducationEntries());
        assertEquals(1, resumeDTO.getEducationEntries().size());
        // Adjust assertion based on actual fields in EducationEmbeddable if needed
        assertEquals("Test University", resumeDTO.getEducationEntries().get(0).getInstitution());

        assertNotNull(resumeDTO.getExperienceEntries());
        assertEquals(1, resumeDTO.getExperienceEntries().size());
        // Adjust assertion based on actual fields in ExperienceEmbeddable if needed
        assertEquals("Test Company", resumeDTO.getExperienceEntries().get(0).getCompany());

        assertNotNull(resumeDTO.getSkills());
        assertEquals(3, resumeDTO.getSkills().size());
        assertTrue(resumeDTO.getSkills().contains("Testing"));
    }

    @Test
    void shouldMapResumeDtoToEntity() {
        // 1. Arrange: Create a sample ResumeDTO
        ResumeDTO resumeDTO = new ResumeDTO();
        resumeDTO.setId(2L);
        resumeDTO.setName("DTO User");
        resumeDTO.setJobTitle("DTO Job");
        resumeDTO.setLocation("DTO Location");
        resumeDTO.setPortfolioUrl("dto-portfolio.example.com");
        resumeDTO.setLinkedinUrl("dto-linkedin.example.com");
        resumeDTO.setGithubUrl("dto-github.example.com");
        resumeDTO.setEmail("dto@example.com");
        resumeDTO.setPhone("987-654-3210");
        resumeDTO.setSummary("A DTO summary.");

        // Instantiate using default constructor and setters
        EducationEmbeddable eduDto = new EducationEmbeddable();
        eduDto.setInstitution("DTO University");
        eduDto.setDegree("M.S. Testing");
        eduDto.setStartDate("2024");
        eduDto.setEndDate("2025");

        // Instantiate using default constructor and setters
        ExperienceEmbeddable expDto = new ExperienceEmbeddable();
        expDto.setCompany("DTO Company");
        expDto.setJobTitle("Tester");
        expDto.setStartDate("2024");
        expDto.setEndDate("2024");
        expDto.setDescription("Tested DTOs.");
        expDto.setResponsibilities(List.of("Ran tests"));

        resumeDTO.setEducationEntries(List.of(eduDto));
        resumeDTO.setExperienceEntries(List.of(expDto));
        resumeDTO.setSkills(new ArrayList<>(Arrays.asList("JUnit", "Mockito"))); // Use ArrayList constructor

        // 2. Act: Call the static mapper method
        Resume resumeEntity = ResumeMapper.toResumeEntity(resumeDTO);

        // 3. Assert: Check if the entity fields match the DTO fields
        assertNotNull(resumeEntity);
        assertEquals(resumeDTO.getId(), resumeEntity.getId());
        assertEquals(resumeDTO.getName(), resumeEntity.getName());
        assertEquals(resumeDTO.getJobTitle(), resumeEntity.getJobTitle());
        assertEquals(resumeDTO.getLocation(), resumeEntity.getLocation());
        assertEquals(resumeDTO.getPortfolioUrl(), resumeEntity.getPortfolioUrl());
        assertEquals(resumeDTO.getLinkedinUrl(), resumeEntity.getLinkedinUrl());
        assertEquals(resumeDTO.getGithubUrl(), resumeEntity.getGithubUrl());
        assertEquals(resumeDTO.getEmail(), resumeEntity.getEmail());
        assertEquals(resumeDTO.getPhone(), resumeEntity.getPhone());
        assertEquals(resumeDTO.getSummary(), resumeEntity.getSummary());

        assertNotNull(resumeEntity.getEducationEntries());
        assertEquals(1, resumeEntity.getEducationEntries().size());
        assertEquals("DTO University", resumeEntity.getEducationEntries().get(0).getInstitution());

        assertNotNull(resumeEntity.getExperienceEntries());
        assertEquals(1, resumeEntity.getExperienceEntries().size());
        assertEquals("DTO Company", resumeEntity.getExperienceEntries().get(0).getCompany());

        assertNotNull(resumeEntity.getSkills());
        assertEquals(2, resumeEntity.getSkills().size());
        assertTrue(resumeEntity.getSkills().contains("JUnit"));
    }

    @Test
    void shouldHandleNullInput() {
        // Test null entity to DTO
        assertNull(ResumeMapper.toResumeDTO(null), "Mapping null entity should return null DTO");

        // Test null DTO to entity
        assertNull(ResumeMapper.toResumeEntity(null), "Mapping null DTO should return null entity");
    }
}
