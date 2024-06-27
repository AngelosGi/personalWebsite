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
        resume.setName("Angelos Giannopoulos");
        resume.setJobTitle("Junior Software Developer");
        resume.setLocation("Thessaloniki, GR");
        resume.setPortfolioUrl("http://anggi.io/");
        resume.setLinkedinUrl("https://linkedin.com/in/anggian/");
        resume.setGithubUrl("http://github.com/AngelosGi");
        resume.setEmail("giannopoulos.ang@gmail.com");
        resume.setPhone("+30 6955151477");
        resume.setSummary("Junior software developer eager to put my knowledge into action");

        Experience experience1 = new Experience();
        experience1.setJobTitle("Technical Support Specialist");
        experience1.setCompany("Teleperformance");
        experience1.setStartDate("2023");
        experience1.setEndDate("Present");
        experience1.setDescription("Delivered technical support via phone, email, and chat for various tech products. Diagnosed and resolved software, hardware, and telecommunications issues. Collaborated with the development team to identify, document, and escalate software and platform issues by creating detailed and actionable tickets. Maintained accurate records of customer interactions using CRM software. Worked closely with team members to meet performance and quality targets. Enhanced product knowledge through ongoing training.");
        experience1.setResponsibilities(Arrays.asList(
                "Delivered technical support via phone, email, and chat for various tech products.",
                "Diagnosed and resolved software, hardware, and telecommunications issues.",
                "Collaborated with the development team to identify, document, and escalate software and platform issues by creating detailed and actionable tickets.",
                "Maintained accurate records of customer interactions using CRM software.",
                "Worked closely with team members to meet performance and quality targets.",
                "Enhanced product knowledge through ongoing training."
        ));
        experience1.setResume(resume);

        Experience experience2 = new Experience();
        experience2.setJobTitle("IT Infrastructure and Operations Manager");
        experience2.setCompany("Fototypies.gr");
        experience2.setStartDate("2012");
        experience2.setEndDate("2023");
        experience2.setDescription("Managed IT infrastructure, including custom software applications. Handled software updates, system integrations, and troubleshooting. Trained employees on software usage, providing ongoing technical support. Collaborated with vendors and IT teams for software customization. Oversaw store operations, including inventory control and employee management.");
        experience2.setResponsibilities(Arrays.asList(
                "Managed IT infrastructure, including custom software applications.",
                "Handled software updates, system integrations, and troubleshooting.",
                "Trained employees on software usage, providing ongoing technical support.",
                "Collaborated with vendors and IT teams for software customization.",
                "Oversaw store operations, including inventory control and employee management."
        ));
        experience2.setResume(resume);

        Experience experience3 = new Experience();
        experience3.setJobTitle("Store Manager, E-shop");
        experience3.setCompany("Laventzakis' Ceramics");
        experience3.setStartDate("2009");
        experience3.setEndDate("2011");
        experience3.setDescription("Managed e-shop and retail store operations. Handled customer service, order processing, and inventory control. Streamlined retail and wholesale order processes.");
        experience3.setResponsibilities(Arrays.asList(
                "Managed e-shop and retail store operations.",
                "Handled customer service, order processing, and inventory control.",
                "Streamlined retail and wholesale order processes."
        ));
        experience3.setResume(resume);

        Experience experience4 = new Experience();
        experience4.setJobTitle("Technical Support, Computers and networks");
        experience4.setCompany("TripleW Internet Gaming Station");
        experience4.setStartDate("2007");
        experience4.setEndDate("2009");
        experience4.setDescription("Managed IT infrastructure for 50 PCs and multiple servers. Managed software applications, games and platforms (ERP, Steam, Blizzard, CD keys). Troubleshot, coordinated software and game updates, patches, and system integrations. Trained employees on software usage and provided ongoing support and guidance.");
        experience4.setResponsibilities(Arrays.asList(
                "Managed IT infrastructure for 50 PCs and multiple servers.",
                "Managed software applications, games and platforms (ERP, Steam, Blizzard, CD keys).",
                "Troubleshot, coordinated software and game updates, patches, and system integrations.",
                "Trained employees on software usage and provided ongoing support and guidance."
        ));
        experience4.setResume(resume);

        Education education1 = new Education();
        education1.setInstitution("Datalabs.edu, ACTA SA - Spin Off Aristotle University of Thessaloniki");
        education1.setDegree("Diploma in IT");
        education1.setMajor("Full stack Developer - Certificate");
        education1.setStartDate("March 2023");
        education1.setEndDate("Present");
        education1.setResume(resume);

        Education education2 = new Education();
        education2.setInstitution("EPAL El. Venizelou");
        education2.setDegree("General upper secondary school certificate");
        education2.setMajor("IT Applications Technician");
        education2.setStartDate("2006");
        education2.setEndDate("2009");
        education2.setResume(resume);

        resume.setEducationList(Arrays.asList(education1, education2));
        resume.setExperienceList(Arrays.asList(experience1, experience2, experience3, experience4));

        resumeRepository.save(resume);
    }
}
