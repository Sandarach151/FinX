package com.example.finx.Model;

import java.time.LocalDateTime;

public class Stock {
    private String symbol;
    private Integer amt;
    private Double price;
    private Character BS;
    private LocalDateTime transactionDate;
    public Stock(String symbol, Integer amt, Double price, Character BS) {
        this.symbol = symbol;
        this.amt = amt;
        this.price = price;
        this.BS = BS;
        this.transactionDate = LocalDateTime.now();
    }

    public Stock(String symbol, Integer amt, Double price, Character BS, LocalDateTime date) {
        this.symbol = symbol;
        this.amt = amt;
        this.price = price;
        this.BS = BS;
        this.transactionDate = date;
    }

    @Override
    public String toString() {
        return "{"+this.symbol+", "+this.amt+", "+this.price+", "+this.BS+", "+this.transactionDate+"}";
    }

    public static Stock fromString(String stockString) {
        try {
            stockString = stockString.substring(1, stockString.length()-1);
            String[] parts = stockString.split(", ");

            String currency = parts[0];
            Integer amt = Integer.parseInt(parts[1]);
            Double price = Double.parseDouble(parts[2]);
            Character BS = parts[3].charAt(0);
            LocalDateTime date = LocalDateTime.parse(parts[4]);

            return new Stock(currency, amt, price, BS, date);
        } catch (Exception e) {
            // Handle any parsing errors or invalid format
            e.printStackTrace();
            return null; // Return null to indicate failure
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getAmt() {
        return amt;
    }

    public Double getPrice() {
        return price;
    }

    public Character getBS() {
        return BS;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}
