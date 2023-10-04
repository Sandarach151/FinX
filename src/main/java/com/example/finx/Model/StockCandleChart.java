package com.example.finx.Model;

import java.time.Instant;
import java.util.ArrayList;

public class StockCandleChart {
    private ArrayList<Double> closePrices;

    private ArrayList<Double> highPrices;

    private ArrayList<Double> lowPrices;

    private ArrayList<Double> openPrices;

    private ArrayList<Instant> timestamps;

    public StockCandleChart(ArrayList<Double> closePrices, ArrayList<Double> highPrices, ArrayList<Double> lowPrices, ArrayList<Double> openPrices, ArrayList<Integer> timestamps) {
        this.closePrices = closePrices;
        this.highPrices = highPrices;
        this.lowPrices = lowPrices;
        this.openPrices = openPrices;
        this.timestamps = new ArrayList<>();
        for(Integer unixTime : timestamps){
            this.timestamps.add(Instant.ofEpochSecond(unixTime));
        }
    }

    public ArrayList<Double> getPriceValues() {
        ArrayList<Double> priceValues = new ArrayList<>();
        for(int i=0; i<closePrices.size(); i++){
            priceValues.add((closePrices.get(i)+ openPrices.get(i))/2);
        }
        return priceValues;
    }

    public ArrayList<Double> getClosePrices() {
        return closePrices;
    }

    public ArrayList<Double> getHighPrices() {
        return highPrices;
    }

    public ArrayList<Double> getLowPrices() {
        return lowPrices;
    }

    public ArrayList<Double> getOpenPrices() {
        return openPrices;
    }

    public ArrayList<Instant> getTimestamps() {
        return timestamps;
    }
}
