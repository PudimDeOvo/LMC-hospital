package com.example.clinic.Database;

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

    public boolean checkCredentials(String username, String password){

        HashMap<String, String> credentials = getInstance().getCredentials();

        if (credentials.get(username) != null) {
            return credentials.get(username).equals(password);
        }
        return false;
    }
}
