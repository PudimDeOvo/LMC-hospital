package com.example.clinic.Entities.User;

import com.example.clinic.Entities.HealthInsurancePlan;
import com.example.clinic.Entities.MedicalSpecialty;

import java.util.Set;
import java.util.HashSet;

public class Doctor extends User {
    private MedicalSpecialty specialty;
    public Set<HealthInsurancePlan> acceptedPlans = new HashSet<>();
    private int stars;

    public Doctor(String username, String name, MedicalSpecialty specialty, int stars) {
        super(username, name);
        this.specialty = specialty;
        this.stars = stars;

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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
