package com.example.clinic.Database.UserDatabase;

import com.example.clinic.Entities.MedicalSpecialty;
import com.example.clinic.Entities.User.Doctor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

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

    public void updateDoctor(Doctor oldData, String[] newData){
        StringBuilder fileContent = new StringBuilder();
        boolean check = false;

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/database/DoctorDatabase.csv"))) {
            String header = br.readLine();
            if (header != null) {
                fileContent.append(header).append("\n");
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] existingData = line.split(",");

                if (existingData.length > 0 && existingData[0].trim().equals(oldData.getUsername())) {
                    String[] updatedData = new String[existingData.length];
                    for(int i = 0; i < existingData.length; i++){
                        if(i < 3 && i < newData.length){
                            updatedData[i] = newData[i];
                        }
                        else{
                            updatedData[i] = existingData[i];
                        }
                    }
                    fileContent.append(String.join(",", updatedData)).append("\n");
                    check = true;
                } else {
                    fileContent.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }

        if(check){
            try(FileWriter fw = new FileWriter("src/main/database/DoctorDatabase.csv")) {
                fw.write(fileContent.toString());
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.err.println("Doctor with username " + oldData.getUsername() + " not found.");
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
}
