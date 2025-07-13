package com.example.clinic.Database.userDatabase;

import com.example.clinic.Entities.HealthInsurancePlan;
import com.example.clinic.Entities.MedicalSpecialty;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.Entities.User.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class PatientDatabase extends Database {
    private static PatientDatabase instance;

    private PatientDatabase(){
        super("src/main/database/PatientDatabase.csv");
    }

    public static PatientDatabase getInstance(){
        if (instance == null) {
            instance = new PatientDatabase();
        }
        return instance;
    }

    public void addNewPacient(String[] data){
        String newUserData = String.join(",", data);

        try (FileWriter fw = new FileWriter("src/main/database/PatientDatabase.csv", true)){

            fw.append(newUserData);
            fw.append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Patient getPatient(String patientUsername){
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/database/PatientDatabase.csv"))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length > 0 && data[0].trim().equals(patientUsername)) {
                    String name = data[2].trim();
                    String ageInText = data[3].trim();
                    String healthPlan = data[4].trim();

                    String formattedHealthPlan = healthPlan.replaceAll(" ", "_").toUpperCase();

                    return new Patient(patientUsername, name, Integer.parseInt(ageInText), HealthInsurancePlan.valueOf(formattedHealthPlan));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return null;
    }

    public boolean checkCredentials(String username, String password) {
        HashMap<String, String> credentials = getCredentials();
        String storedPassword = credentials.get(username);

        return storedPassword != null && storedPassword.equals(password);
    }
}
