package com.example.clinic.registerSystem;

import java.io.FileWriter;
import java.io.IOException;

public class SignUpManager {
    public static void storePacient(String[] data){
        String FILE_PATH = "src/main/database/PacientDatabase.csv";

        String newPacientData = String.join(",", data);

        try (FileWriter fw = new FileWriter(FILE_PATH, true)){

            fw.append(newPacientData);
            fw.append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
