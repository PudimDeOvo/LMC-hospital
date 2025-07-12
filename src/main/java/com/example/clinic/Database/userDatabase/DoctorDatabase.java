package com.example.clinic.Database.userDatabase;

import com.example.clinic.entities.MedicalSpecialty;
import com.example.clinic.entities.user.Doctor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import java.util.*;

public class DoctorDatabase extends Database{
    private static DoctorDatabase instance;

    private DoctorDatabase(){
        super("src/main/database/DoctorDatabase.csv");
    }

    public static DoctorDatabase getInstance(){
        if (instance == null) {
            instance = new DoctorDatabase();
        }
        return instance;
    }

    public void addNewDoctor(String[] data){
        String newDoctorData = String.join(",", data);

        try (FileWriter fw = new FileWriter("src/main/database/DoctorDatabase.csv", true)){

            fw.append(newDoctorData);
            fw.append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Doctor getDoctor(String doctorUsername){
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/database/DoctorDatabase.csv"))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length > 0 && data[0].trim().equals(doctorUsername)) {
                    String name = data[2].trim();
                    String specialtyInText = data[3].trim();
                    int stars = Integer.parseInt(data[4].trim());

                    return new Doctor(doctorUsername, name, MedicalSpecialty.valueOf(specialtyInText.toUpperCase()), stars);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null;
    }



    public boolean checkCredentials(String username, String password){
        HashMap<String, String> credentials = getCredentials();
        String storedPassword = credentials.get(username);

        return storedPassword != null && storedPassword.equals(password);
    }

    // tive que add pras consultas
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/database/DoctorDatabase.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String username = data[0].trim();
                    String name = data[2].trim();
                    String specialty = data[3].trim();
                    int stars = Integer.parseInt(data[4].trim());

                    Doctor doctor = new Doctor(username, name, MedicalSpecialty.valueOf(specialty.toUpperCase()), stars);
                    doctors.add(doctor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doctors;
    }

}
