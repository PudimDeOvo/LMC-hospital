package com.example.clinic.Database;

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

    public boolean checkCredentials(String username, String password){

        HashMap<String, String> credentials = getInstance().getCredentials();

        if (credentials.get(username) != null) {
            return credentials.get(username).equals(password);
        }
        return false;
    }
}
