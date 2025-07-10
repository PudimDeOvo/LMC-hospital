package com.example.clinic.entities;

import java.util.Set;
import java.util.HashSet;

public class Doctor extends User{
    private MedicalSpecialty specialty;
    public Set<HealthInsurancePlan> acceptedPlans = new HashSet<>();

    public Doctor(String username, String password, String name, int age, MedicalSpecialty specialty, Set<HealthInsurancePlan> verifiedHealthPlans) {
        super(username, password, name);
        this.specialty = specialty;
        this.acceptedPlans = verifiedHealthPlans;

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
