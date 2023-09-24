package com.example.finx.Model;

import javafx.util.Pair;

import java.util.ArrayList;

public class User {
    private String username;

    private String password;

    private Double balance;

    private Integer BTCHoldings;

    private Integer ETHHoldings;

    private ArrayList<Stock> stocks;

    public User(String username, String password, Double balance, Integer BTCHoldings, Integer ETHHoldings, ArrayList<Stock> stocks) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.BTCHoldings = BTCHoldings;
        this.ETHHoldings = ETHHoldings;
        this.stocks = stocks;
    }

    public boolean buy(String currency, int numberOfStocks, double buyPrice) {
        double totalPrice = numberOfStocks * buyPrice;
        if (totalPrice <= balance) {
            if(currency.equals("BTC")) {
                stocks.add(new Stock(currency, numberOfStocks, buyPrice, 'B'));
                this.BTCHoldings += numberOfStocks;
                this.balance-=totalPrice;
                return true;
            }
            if(currency.equals("ETH")){
                stocks.add(new Stock(currency, numberOfStocks, buyPrice, 'B'));
                this.ETHHoldings += numberOfStocks;
                this.balance-=totalPrice;
                return true;
            }
            return false;
        } else {
            return false; // Insufficient balance
        }
    }

    public boolean sell(String currency, int numberOfStocks, double sellPrice){
        stocks.add(new Stock(currency, numberOfStocks, sellPrice, 'S'));
        if(currency.equals("BTC")){
            if(numberOfStocks<=this.BTCHoldings){
                this.balance+=numberOfStocks*sellPrice;
                this.BTCHoldings-=numberOfStocks;
                return true;
            }
            else{
                return false;
            }
        }
        if(currency.equals("ETH")){
            if(numberOfStocks<=this.ETHHoldings){
                this.balance+=numberOfStocks*sellPrice;
                this.ETHHoldings-=numberOfStocks;
                return true;
            }
            else{
                return false;
            }
        }
        return false;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getBTCHoldings() {
        return BTCHoldings;
    }

    public void setBTCHoldings(Integer BTCHoldings) {
        this.BTCHoldings = BTCHoldings;
    }

    public Integer getETHHoldings() {
        return ETHHoldings;
    }

    public void setETHHoldings(Integer ETHHoldings) {
        this.ETHHoldings = ETHHoldings;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        StringBuilder userString = new StringBuilder(this.username + ", " + this.password + ", " + this.balance + ", " + this.BTCHoldings + ", " + this.ETHHoldings + "\n[");
        for(Stock stock: this.stocks){
            userString.append(stock.toString()).append("----");
        }
        userString.append("]");
        return userString.toString();
    }
}
