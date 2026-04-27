package com.kiran.jobtracker.controller;

import com.kiran.jobtracker.model.ApplicationStatus;
import com.kiran.jobtracker.model.JobApplication;
import com.kiran.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST API Controller for Job Application Tracker
 *
 * Base URL: /api/applications
 */
@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    // ── CREATE ────────────────────────────────────
    // POST /api/applications
    @PostMapping
    public ResponseEntity<JobApplication> create(@Valid @RequestBody JobApplication app) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(app));
    }

    // ── READ ALL ──────────────────────────────────
    // GET /api/applications
    @GetMapping
    public ResponseEntity<List<JobApplication>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // ── READ ONE ──────────────────────────────────
    // GET /api/applications/{id}
    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getById(@PathVariable Long id) {
        return service.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ── UPDATE ────────────────────────────────────
    // PUT /api/applications/{id}
    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> update(
            @PathVariable Long id,
            @Valid @RequestBody JobApplication updated) {
        try {
            return ResponseEntity.ok(service.update(id, updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ── DELETE ────────────────────────────────────
    // DELETE /api/applications/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── UPDATE STATUS ─────────────────────────────
    // PATCH /api/applications/{id}/status?status=PHONE_SCREEN
    @PatchMapping("/{id}/status")
    public ResponseEntity<JobApplication> updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        try {
            return ResponseEntity.ok(service.updateStatus(id, status));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ── FILTER BY STATUS ──────────────────────────
    // GET /api/applications/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobApplication>> getByStatus(
            @PathVariable ApplicationStatus status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    // ── SEARCH BY COMPANY ─────────────────────────
    // GET /api/applications/search?company=google
    @GetMapping("/search")
    public ResponseEntity<List<JobApplication>> search(@RequestParam String company) {
        return ResponseEntity.ok(service.searchByCompany(company));
    }

    // ── FOLLOW-UPS DUE ────────────────────────────
    // GET /api/applications/followups
    @GetMapping("/followups")
    public ResponseEntity<List<JobApplication>> getFollowUps() {
        return ResponseEntity.ok(service.getFollowUps());
    }

    // ── DASHBOARD ─────────────────────────────────
    // GET /api/applications/dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        return ResponseEntity.ok(service.getDashboard());
    }
}
