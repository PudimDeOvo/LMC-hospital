package com.example.clinic.entities;

public class Pacient extends User{
    private String healthPlan;

    public Pacient(String username, String password, String name, int age, String healthPlan) {
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
