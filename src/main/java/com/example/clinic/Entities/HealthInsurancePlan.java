package com.example.clinic.Entities;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum HealthInsurancePlan {
    BASIC_PLUS("Basic Plus Plan",
            MedicalSpecialty.CARDIOLOGY,
            MedicalSpecialty.DERMATOLOGY,
            MedicalSpecialty.ENDOCRINOLOGY,
            MedicalSpecialty.GYNECOLOGY_OBSTETRICS,
            MedicalSpecialty.PEDIATRICS,
            MedicalSpecialty.PSYCHIATRY,
            MedicalSpecialty.OPHTHALMOLOGY,
            MedicalSpecialty.OTORHINOLARYNGOLOGY
    ),

    PREMIUM_HEALTH("Premium Health Plan",
            MedicalSpecialty.CARDIOLOGY,
            MedicalSpecialty.DERMATOLOGY,
            MedicalSpecialty.ENDOCRINOLOGY,
            MedicalSpecialty.GASTROENTEROLOGY,
            MedicalSpecialty.GERIATRICS,
            MedicalSpecialty.GYNECOLOGY_OBSTETRICS,
            MedicalSpecialty.NEUROLOGY,
            MedicalSpecialty.ONCOLOGY,
            MedicalSpecialty.ORTHOPEDICS,
            MedicalSpecialty.PEDIATRICS,
            MedicalSpecialty.PSYCHIATRY,
            MedicalSpecialty.GENERAL_SURGERY,
            MedicalSpecialty.UROLOGY,
            MedicalSpecialty.RADIOLOGY,
            MedicalSpecialty.ANESTHESIOLOGY
    ),

    EXECUTIVE_TOTAL("Executive Total Plan",
            MedicalSpecialty.values() // All specialties
    );

    private final String name;
    private final Set<MedicalSpecialty> coveredSpecialties;

    HealthInsurancePlan(String name, MedicalSpecialty... specialties) {
        this.name = name;
        this.coveredSpecialties = EnumSet.of(specialties[0], specialties);
    }

    public String getName() {
        return name;
    }

    public Set<MedicalSpecialty> getCoveredSpecialties() {
        return Collections.unmodifiableSet(coveredSpecialties);
    }

    public boolean coversSpecialty(MedicalSpecialty specialty) {
        return coveredSpecialties.contains(specialty);
    }
}
