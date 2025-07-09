package com.example.clinic.entities;

public enum MedicalSpecialty {
    CARDIOLOGY("Cardiology"),
    DERMATOLOGY("Dermatology"),
    ENDOCRINOLOGY("Endocrinology"),
    GASTROENTEROLOGY("Gastroenterology"),
    GERIATRICS("Geriatrics"),
    GYNECOLOGY_OBSTETRICS("Gynecology and Obstetrics"),
    NEUROLOGY("Neurology"),
    ONCOLOGY("Oncology"),
    ORTHOPEDICS("Orthopedics"),
    PEDIATRICS("Pediatrics"),
    PSYCHIATRY("Psychiatry"),
    GENERAL_SURGERY("General Surgery"),
    UROLOGY("Urology"),
    OPHTHALMOLOGY("Ophthalmology"),
    OTORHINOLARYNGOLOGY("Otorhinolaryngology"),
    RADIOLOGY("Radiology"),
    ANESTHESIOLOGY("Anesthesiology");

    private final String displayName;

    MedicalSpecialty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}