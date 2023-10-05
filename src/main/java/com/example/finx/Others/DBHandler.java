package com.example.finx.Others;

import com.example.finx.Model.User;
import com.example.finx.Model.UserDatabase;
import com.example.finx.Model.Stock;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class DBHandler {
//    private static final String USER_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/com/example/finx/CSV/users.csv";
//
//    private static final String CURRENT_USER_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/com/example/finx/CSV/currentUser.csv";
//
//    private static final String CURRENT_PRICES_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/com/example/finx/CSV/currentPrices.csv";
//
//    private static final String CURRENT_STOCK_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/com/example/finx/CSV/currentStock.csv";
//
    private static final String USER_FILE_PATH = System.getProperty("user.dir") + "/CSV/users.csv";

    private static final String CURRENT_USER_FILE_PATH = System.getProperty("user.dir") + "/CSV/currentUser.csv";

    private static final String CURRENT_PRICES_FILE_PATH = System.getProperty("user.dir") + "/CSV/currentPrices.csv";

    private static final String CURRENT_STOCK_FILE_PATH = System.getProperty("user.dir") + "/CSV/currentStock.csv";

    public static UserDatabase loadUsers() {
        UserDatabase database = new UserDatabase();
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
                database.add(user);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        return database;
    }

    public static boolean insertUser(String username, String password, Double balance, ArrayList<Stock> stocks){
        try {
            if(loadUsers().find(username)==null) {
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
                currentUser = DBHandler.loadUsers().find(scanner.next());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        return currentUser;
    }

    public static void setCurrentStock(String symbol){
        try {
            FileWriter fileWriter = new FileWriter(CURRENT_STOCK_FILE_PATH);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(symbol);
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCurrentStock(){
        String currentStock = "";
        try {
            Scanner scanner = new Scanner(new File(CURRENT_STOCK_FILE_PATH));
            if(scanner.hasNext()){
                currentStock = scanner.next();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        return currentStock;
    }

    public static void printUsers(UserDatabase database){
        try {
            FileWriter fileWriter = new FileWriter(USER_FILE_PATH); // Append mode
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(database.toString());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Double[] getStockPrice(String symbol){
        Double[] result = new Double[3];
        try{
            Scanner scanner = new Scanner(new File(CURRENT_PRICES_FILE_PATH));
            if(scanner.hasNext() && Instant.now().getEpochSecond()-scanner.nextInt()<=15){
                String[] symbols = {"AAPL", "MSFT", "TSLA", "SBUX", "NFLX", "META", "BINANCE:BTCUSDT", "BINANCE:ETHUSDT"};
                Double[][] prices = new Double[8][3];
                for(int i=0; i<8; i++){
                    prices[i][0]=scanner.nextDouble();
                    prices[i][1]=scanner.nextDouble();
                    prices[i][2]=scanner.nextDouble();
                    if(symbols[i].equals(symbol)){
                        result = prices[i];
                    }
                }
                scanner.close();
            }
            else{
                scanner.close();
                String[] symbols = {"AAPL", "MSFT", "TSLA", "SBUX", "NFLX", "META", "BINANCE:BTCUSDT", "BINANCE:ETHUSDT"};
                StringBuilder sb = new StringBuilder();
                for(int i=0; i<8; i++){
                    Double[] cur = FinnhubHandler.getStockPrice(symbols[i]);
                    sb.append("\n").append(cur[0]).append(" ").append(cur[1]).append(" ").append(cur[2]);
                    if(symbols[i].equals(symbol)){
                        result = cur;
                    }
                }
                FileWriter fileWriter = new FileWriter(CURRENT_PRICES_FILE_PATH);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print(Instant.now().getEpochSecond());
                printWriter.print(sb);
                printWriter.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}













