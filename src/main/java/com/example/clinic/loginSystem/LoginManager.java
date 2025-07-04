package com.example.clinic.loginSystem;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LoginManager {
    HashMap<String, String> credentials = new HashMap<String, String>();

    public LoginManager(){
        loadCredentials();
    }

    private void loadCredentials(){
        String FILE_PATH = "src/main/database/PacientDatabase.csv";

        try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH))) {
            String[] header = reader.readNext();

            int UsernameIndex = -1;
            int PasswordIndex = -1;

            for (int i = 0; i < header.length; i++) {
                if ("username".equalsIgnoreCase(header[i].trim())) {
                    UsernameIndex = i;
                } else if ("password".equalsIgnoreCase(header[i].trim())) {
                    PasswordIndex = i;
                }
            }

            String[] linha;
            while ((linha = reader.readNext()) != null) {
                String username = linha[UsernameIndex];
                String password = linha[PasswordIndex];
                credentials.put(username, password);
            }
        } catch (IOException | CsvValidationException e){
            e.printStackTrace();
        };
    }


    public boolean checkCredentials(String username, String password){
        if (credentials.get(username) != null) {
            return credentials.get(username).equals(password);
        }
        return false;
    }
}
