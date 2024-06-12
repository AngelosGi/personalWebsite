package io.anggi.personalwebsite;

import io.anggi.personalwebsite.model.Education;
import io.anggi.personalwebsite.model.Experience;
import io.anggi.personalwebsite.model.Resume;
import io.anggi.personalwebsite.repository.EducationRepository;
import io.anggi.personalwebsite.repository.ExperienceRepository;
import io.anggi.personalwebsite.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DummyDataLoader implements CommandLineRunner {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public void run(String... args) throws Exception {
        Resume resume = new Resume();
        resume.setName("John Doe");
        resume.setJobTitle("Software Developer");
        resume.setLocation("New York, NY");
        resume.setPortfolioUrl("https://johndoeportfolio.com");
        resume.setGithubUrl("https://github.com/johndoe");
        resume.setEmail("john.doe@example.com");
        resume.setPhone("+1 (123) 456-7890");
        resume.setSummary("Experienced software developer with a passion for creating scalable web applications.");

        Education education = new Education();
        education.setInstitution("University of Example");
        education.setDegree("Bachelor of Science");
        education.setMajor("Computer Science");
        education.setMinor("Mathematics");
        education.setGpa(3.7);
        education.setStartDate("2015");
        education.setEndDate("2019");
        education.setResume(resume);

        Experience experience = new Experience();
        experience.setJobTitle("Software Engineer");
        experience.setCompany("Tech Company XYZ");
        experience.setStartDate("2019");
        experience.setEndDate("Present");
        experience.setDescription("Developed RESTful APIs using Spring Boot.\nImplemented front-end features using AngularJS.\nMaintained and optimized database queries in MySQL.");
        experience.setResume(resume);

        resume.setEducationList(Arrays.asList(education));
        resume.setExperienceList(Arrays.asList(experience));

        resumeRepository.save(resume);
    }
}
