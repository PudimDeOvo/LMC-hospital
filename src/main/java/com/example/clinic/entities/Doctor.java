package com.example.clinic.entities;

import java.util.Set;
import java.util.HashSet;

public class Doctor extends User{
    private String specialty;
    public Set<String> verifiedHealthPlans = new HashSet<>();

    public Doctor(String username, String password, String name, int age, String specialty, Set<String> verifiedHealthPlans) {
        super(username, password, name, age);
        this.specialty = specialty;
        this.verifiedHealthPlans = verifiedHealthPlans;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

}
