package com.kiran.jobtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Job Application Tracker API
 * Author: Kiran Rahul Pabboju
 * GitHub: github.com/rahuldev7-web
 *
 * A RESTful API to track job applications, statuses,
 * interview rounds, and follow-ups.
 */
@SpringBootApplication
public class JobTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobTrackerApplication.class, args);
    }
}
