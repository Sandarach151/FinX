package com.example.finx.Others;

import com.example.finx.Model.User;
import com.example.finx.Model.UserDB;
import com.example.finx.Model.Stock;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DBHandler {
    private static final String USER_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/com/example/finx/CSV/users.csv";

    private static final String CURRENT_USER_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/com/example/finx/CSV/currentUser.csv";

    public static UserDB loadUsers() {
        UserDB database = new UserDB();
        try {
            Scanner scanner = new Scanner(new File(USER_FILE_PATH));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.isEmpty()){
                    break;
                }
                String[] parts = line.split(", ");
                String username = parts[0];
                String password = parts[1];
                Double balance = Double.valueOf(parts[2]);
                String allstocksString = scanner.nextLine();
                allstocksString = allstocksString.substring(1, allstocksString.length()-1);
                String[] allstocksArr = allstocksString.split("----");
                ArrayList<Stock> stocks = new ArrayList<>();
                for(String stockString: allstocksArr){
                    if(!stockString.equals("")) stocks.add(Stock.fromString(stockString));
                }
                User user = new User(username, password, balance, stocks);
                database.addUser(user);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        return database;
    }

    public static boolean insertUser(String username, String password, Double balance, ArrayList<Stock> stocks){
        try {
            if(loadUsers().findUserByUsername(username)==null) {
                FileWriter fileWriter = new FileWriter(USER_FILE_PATH, true); // Append mode
                PrintWriter printWriter = new PrintWriter(fileWriter);

                // Format the user data as a CSV line
                StringBuilder formattedLine = new StringBuilder(username + ", " + password + ", " + balance);
                formattedLine.append("\n[");
                for (Stock stock : stocks) {
                    formattedLine.append(stock.toString()).append("----");
                }
                formattedLine.append("]");
                printWriter.println(formattedLine);
                printWriter.close();
                return true;
            }
            else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setCurrentUser(String username){
        try {
            FileWriter fileWriter = new FileWriter(CURRENT_USER_FILE_PATH);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(username);
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getCurrentUser(){
        User currentUser = null;
        try {
            Scanner scanner = new Scanner(new File(CURRENT_USER_FILE_PATH));
            if(scanner.hasNext()){
                currentUser = DBHandler.loadUsers().findUserByUsername(scanner.next());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        return currentUser;
    }

    public static void printUsers(UserDB database){
        try {
            FileWriter fileWriter = new FileWriter(USER_FILE_PATH); // Append mode
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(database.toString());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}













