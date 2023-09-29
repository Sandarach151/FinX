package com.example.finx.Model;

import javafx.util.Pair;

import java.util.ArrayList;

public class User implements Trader{
    private String username;

    private String password;

    private Double balance;

    private ArrayList<Stock> stocks;

    public User(String username, String password, Double balance, ArrayList<Stock> stocks) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.stocks = stocks;
    }

    public int getHoldings(String symbol) {
        int ans = 0;
        for(Stock stock : stocks){
            if(stock.getSymbol().equals(symbol)){
                if(stock.getBS().equals('B')){
                    ans+=stock.getAmt();
                }
                else{
                    ans-=stock.getAmt();
                }
            }
        }
        return ans;
    }

    public boolean buy(String symbol, int numberOfStocks, double buyPrice) {
        double totalPrice = numberOfStocks * buyPrice;
        if (totalPrice <= balance) {
            stocks.add(new Stock(symbol, numberOfStocks, buyPrice, 'B'));
            this.balance -= totalPrice;
            return true;
        }
        else {
            return false; // Insufficient balance
        }
    }

    public boolean sell(String symbol, int numberOfStocks, double sellPrice){
        if(this.getHoldings(symbol)>=numberOfStocks){
            stocks.add(new Stock(symbol, numberOfStocks, sellPrice, 'S'));
            this.balance+=numberOfStocks*sellPrice;
            return true;
        }
        else {
            return false;
        }
    }

    public double getInvestment(String symbol){
        double ans = 0;
        for(Stock stock : stocks){
            if(stock.getSymbol().equals(symbol)){
                if(stock.getBS().equals('B')){
                    ans+=stock.getPrice()*stock.getAmt();
                }
                if(stock.getBS().equals('S')){
                    ans-=stock.getPrice()*stock.getAmt();
                }
            }
        }
        return ans;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        StringBuilder userString = new StringBuilder(this.username + ", " + this.password + ", " + this.balance + "\n[");
        for(Stock stock: this.stocks){
            userString.append(stock.toString()).append("----");
        }
        userString.append("]");
        return userString.toString();
    }
}
