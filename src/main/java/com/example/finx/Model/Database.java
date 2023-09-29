package com.example.finx.Model;

import java.util.ArrayList;

public abstract class Database {
    ArrayList<Trader> traders;

    public void add(Trader trader){
        traders.add(trader);
    };

    public abstract Trader find(String username);

    @Override
    public String toString() {
        StringBuilder userDBString = new StringBuilder();
        for(Trader trader: traders){
            userDBString.append(trader.toString()).append("\n");
        }
        return userDBString.toString();
    }
}
