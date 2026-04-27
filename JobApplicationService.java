package com.kiran.jobtracker.service;

import com.kiran.jobtracker.model.ApplicationStatus;
import com.kiran.jobtracker.model.JobApplication;
import com.kiran.jobtracker.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class JobApplicationService {

    private final JobApplicationRepository repo;

    public JobApplicationService(JobApplicationRepository repo) {
        this.repo = repo;
    }

    // ── CRUD ──────────────────────────────────────

    public JobApplication create(JobApplication app) {
        return repo.save(app);
    }

    public List<JobApplication> getAll() {
        return repo.findAll();
    }

    public Optional<JobApplication> getById(Long id) {
        return repo.findById(id);
    }

    public JobApplication update(Long id, JobApplication updated) {
        return repo.findById(id).map(existing -> {
            existing.setCompany(updated.getCompany());
            existing.setJobTitle(updated.getJobTitle());
            existing.setLocation(updated.getLocation());
            existing.setJobUrl(updated.getJobUrl());
            existing.setStatus(updated.getStatus());
            existing.setAppliedDate(updated.getAppliedDate());
            existing.setSalaryRange(updated.getSalaryRange());
            existing.setContactName(updated.getContactName());
            existing.setContactEmail(updated.getContactEmail());
            existing.setNotes(updated.getNotes());
            existing.setInterviewRound(updated.getInterviewRound());
            existing.setFollowUpDate(updated.getFollowUpDate());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Application not found: " + id));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // ── BUSINESS LOGIC ────────────────────────────

    public JobApplication updateStatus(Long id, ApplicationStatus status) {
        return repo.findById(id).map(app -> {
            app.setStatus(status);
            return repo.save(app);
        }).orElseThrow(() -> new RuntimeException("Application not found: " + id));
    }

    public List<JobApplication> getByStatus(ApplicationStatus status) {
        return repo.findByStatus(status);
    }

    public List<JobApplication> searchByCompany(String company) {
        return repo.findByCompanyContainingIgnoreCase(company);
    }

    public List<JobApplication> getFollowUps() {
        return repo.findByFollowUpDateLessThanEqualAndStatusNot(
            LocalDate.now(), ApplicationStatus.REJECTED
        );
    }

    public List<JobApplication> getRecent() {
        return repo.findTop10ByOrderByCreatedAtDesc();
    }

    // ── DASHBOARD STATS ───────────────────────────

    public Map<String, Object> getDashboard() {
        List<JobApplication> all = repo.findAll();
        long total = all.size();

        Map<String, Long> statusCounts = new LinkedHashMap<>();
        for (ApplicationStatus s : ApplicationStatus.values()) {
            statusCounts.put(s.name(), 0L);
        }
        List<Object[]> raw = repo.countByStatus();
        for (Object[] row : raw) {
            statusCounts.put(row[0].toString(), (Long) row[1]);
        }

        long activeApplications = all.stream()
            .filter(a -> a.getStatus() != ApplicationStatus.REJECTED
                      && a.getStatus() != ApplicationStatus.WITHDRAWN
                      && a.getStatus() != ApplicationStatus.OFFER_ACCEPTED)
            .count();

        long followUpsDue = getFollowUps().size();

        Map<String, Object> dashboard = new LinkedHashMap<>();
        dashboard.put("totalApplications", total);
        dashboard.put("activeApplications", activeApplications);
        dashboard.put("followUpsDue", followUpsDue);
        dashboard.put("statusBreakdown", statusCounts);
        dashboard.put("recentApplications", getRecent());
        return dashboard;
    }
}
