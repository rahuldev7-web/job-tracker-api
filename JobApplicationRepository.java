package com.kiran.jobtracker.repository;

import com.kiran.jobtracker.model.ApplicationStatus;
import com.kiran.jobtracker.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // Find by status
    List<JobApplication> findByStatus(ApplicationStatus status);

    // Find by company name (case-insensitive)
    List<JobApplication> findByCompanyContainingIgnoreCase(String company);

    // Find applications needing follow-up today or earlier
    List<JobApplication> findByFollowUpDateLessThanEqualAndStatusNot(
        LocalDate date, ApplicationStatus status
    );

    // Count by status for dashboard
    @Query("SELECT a.status, COUNT(a) FROM JobApplication a GROUP BY a.status")
    List<Object[]> countByStatus();

    // Recent applications
    List<JobApplication> findTop10ByOrderByCreatedAtDesc();

    // Find by location
    List<JobApplication> findByLocationContainingIgnoreCase(String location);
}
