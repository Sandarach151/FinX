package com.example.finx;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DBHandler {
    public static Pair<ArrayList<String>, ArrayList<String>> getUsers(){
        Scanner sc = null;
        try {
            sc = new Scanner(new File(System.getProperty("user.dir")+"/src/main/resources/com/example/finx/users.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        ArrayList<String> usernameDB = new ArrayList<>();
        ArrayList<String> passwordDB = new ArrayList<>();
        while (sc.hasNextLine()){
            String[] cur = sc.nextLine().split(", ");
            usernameDB.add(cur[0]);
            passwordDB.add(cur[1]);
        }
        return new Pair<>(usernameDB, passwordDB);
    }

    public static ArrayList<Pair<String, String>> getCurrencies(){
        Scanner sc = null;
        try {
            sc = new Scanner(new File(System.getProperty("user.dir")+"/src/main/resources/com/example/finx/currencies.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        ArrayList<Pair<String, String>> currencyDB = new ArrayList<>();
        while (sc.hasNextLine()){
            String[] cur = sc.nextLine().split(", ");
            currencyDB.add(new Pair<>(cur[0], cur[1]));
        }
        return currencyDB;
    }

    public static boolean insertUser(String username, String password){
        Pair<ArrayList<String>, ArrayList<String>> users = DBHandler.getUsers();
        ArrayList<String> usernameDB = users.getKey();
        if(usernameDB.indexOf(username)==-1){
            try {
                PrintWriter output = new PrintWriter(new FileOutputStream(System.getProperty("user.dir")+"/src/main/resources/com/example/finx/users.csv", true));
                output.print(username+", "+password+"\n");
                output.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                return false;
            }
            return true;
        }
        else{
            return false;
        }
    }
}
