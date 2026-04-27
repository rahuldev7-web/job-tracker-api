package com.kiran.jobtracker.model;

/**
 * Tracks every stage of the job application lifecycle.
 */
public enum ApplicationStatus {
    BOOKMARKED,
    APPLIED,
    PHONE_SCREEN,
    TECHNICAL_INTERVIEW,
    FINAL_INTERVIEW,
    OFFER_RECEIVED,
    OFFER_ACCEPTED,
    REJECTED,
    WITHDRAWN,
    NO_RESPONSE
}
