package com.example.clinic.Database.userDatabase;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public abstract class Database {
    HashMap<String, String> credentials = new HashMap<String, String>();
    protected final String FILE_PATH;

    protected Database(String FILE_PATH){
        this.FILE_PATH = FILE_PATH;
        loadCredentials(FILE_PATH);
    }

    protected void loadCredentials(String FILE_PATH){
        credentials.clear(); //limpa dados antigos antes de recarregar

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

    public HashMap<String, String> getCredentials(){
        return credentials;
    }

    //pra atualizar quando o cliente editar os dados
    public void reloadCredentials(){
        loadCredentials(FILE_PATH);
    }
}
