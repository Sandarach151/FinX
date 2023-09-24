package com.example.finx.Model;

import javafx.util.Pair;
import java.util.ArrayList;

public class UserDB {
    private ArrayList<User> users;

    public UserDB() {
        this.users = new ArrayList<>();
    }

    // Add a user to the user database
    public void addUser(User user) {
        users.add(user);
    }

    // Find a user by username
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        StringBuilder userDBString = new StringBuilder();
        for(User user: users){
            userDBString.append(user.toString()).append("\n");
        }
        return userDBString.toString();
    }

    // Optional: You can add more methods for listing users, checking balances, etc.
}
