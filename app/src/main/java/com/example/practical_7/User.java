package com.example.practical_7;

public class User {
    public String username;
    public String password;

    public User() {}
    public User(String u, String pwd)
    {
        username = u;
        password = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
