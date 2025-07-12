package com.example.clinic.Entities.User;

public abstract class User {
    // credentials
    private String username;

    // info
    private String name;

    protected User(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
