package com.example.clinic.Entities.User;

import com.example.clinic.Entities.HealthInsurancePlan;

public class Patient extends User {
    private HealthInsurancePlan healthPlan;
    private int age;

    public Patient(String username, String name, int age, HealthInsurancePlan healthPlan) {
        super(username, name);
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
