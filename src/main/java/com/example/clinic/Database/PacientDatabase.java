package com.example.clinic.Database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class PacientDatabase extends Database {
    private static PacientDatabase instance;

    private PacientDatabase(){
        super("src/main/database/PacientDatabase.csv");
    }

    public static PacientDatabase getInstance(){
        if (instance == null) {
            instance = new PacientDatabase();
        }
        return instance;
    }

    public void addNewPacient(String[] data){
        String newUserData = String.join(",", data);

        try (FileWriter fw = new FileWriter("src/main/database/PacientDatabase.csv", true)){

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
