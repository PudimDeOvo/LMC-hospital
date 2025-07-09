package com.example.clinic.entities;

public class Patient extends User{
    private String healthPlan;

    public Patient(String username, String password, String name, int age, String healthPlan) {
        super(username, password, name, age);
        this.healthPlan = healthPlan;
    }

    public String getHealthPlan() {
        return healthPlan;
    }

    public void setHealthPlan(String healthPlan) {
        this.healthPlan = healthPlan;
    }
}
