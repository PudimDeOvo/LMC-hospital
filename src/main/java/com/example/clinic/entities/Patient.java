package com.example.clinic.entities;

public class Patient extends User{
    private String healthPlan;
    private int age;

    public Patient(String username, String password, String name, int age, String healthPlan) {
        super(username, password, name);
        this.healthPlan = healthPlan;
        this.age = age;
    }

    public String getHealthPlan() {
        return healthPlan;
    }

    public void setHealthPlan(String healthPlan) {
        this.healthPlan = healthPlan;
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int age){
        this.age = age;
    }
}
