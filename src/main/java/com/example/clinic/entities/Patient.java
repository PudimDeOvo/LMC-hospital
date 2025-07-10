package com.example.clinic.entities;

public class Patient extends User{
    private HealthInsurancePlan healthPlan;
    private int age;

    public Patient(String username, String password, String name, int age, HealthInsurancePlan healthPlan) {
        super(username, password, name);
        this.healthPlan = healthPlan;
        this.age = age;
    }

    public HealthInsurancePlan getHealthPlan() {
        return healthPlan;
    }

    public void setHealthPlan(HealthInsurancePlan healthPlan) {
        this.healthPlan = healthPlan;
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int age){
        this.age = age;
    }
}
