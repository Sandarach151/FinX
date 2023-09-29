package com.example.finx.Model;

import java.util.ArrayList;

public class UserDatabase extends Database{
    private final ArrayList<User> users;

    public UserDatabase() {
        this.users = new ArrayList<>();
    }

    public void add(User user) {
        users.add(user);
    }

    @Override
    public User find(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    @Override
    public String toString() {
        StringBuilder userDBString = new StringBuilder();
        for(User user: users){
            userDBString.append(user.toString()).append("\n");
        }
        return userDBString.toString();
    }

}
