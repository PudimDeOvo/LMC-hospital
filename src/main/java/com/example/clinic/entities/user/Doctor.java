package com.example.clinic.entities.user;

import com.example.clinic.entities.HealthInsurancePlan;
import com.example.clinic.entities.MedicalSpecialty;

import java.util.Set;
import java.util.HashSet;

public class Doctor extends User {
    private MedicalSpecialty specialty;
    public Set<HealthInsurancePlan> acceptedPlans = new HashSet<>();

    public Doctor(String username, String name, MedicalSpecialty specialty) {
        super(username, name);
        this.specialty = specialty;

        for (HealthInsurancePlan plan : HealthInsurancePlan.values()){
            if (plan.coversSpecialty(specialty)){
                this.acceptedPlans.add(plan);
            }
        }
    }

    public MedicalSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MedicalSpecialty specialty) {
        this.specialty = specialty;
    }

}
