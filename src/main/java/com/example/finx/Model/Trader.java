package com.example.finx.Model;

import java.util.ArrayList;

public interface Trader {

    public boolean buy(String symbol, int quantity, double price);

    public boolean sell(String symbol, int quantity, double price);

    public int getHoldings(String symbol);

    public double getBalance();

    public ArrayList<Stock> getStocks();
}
