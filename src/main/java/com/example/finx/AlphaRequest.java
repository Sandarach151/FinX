package com.example.finx;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AlphaRequest {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String[] APIKeys = {"OPRV36UP2D7FZDDZ", "05H3YY29U7DPZFZS", "Z5BEOGD5Y2IT2FL8", "9WWO9X4VCT5TUSMX", "LJE8C7NLRRLEKL8C", "XWFCP6OBQ4IX684C"};

    private static int numCalls = (int) Math.floor(Math.random()*6);

    public static void main(String[] args) throws IOException {
        getCurrencyPrice("BTC", "USD");
    }

    private static ArrayList<String> sendGET(String GET_URL) throws IOException, IllegalArgumentException {
        numCalls++;
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            ArrayList<String> result = new ArrayList<>();
            String line;
            while((line = br.readLine()) != null){
                result.add(line);
            }
            return result;
        } else {
            System.out.println("GET request did not work.");
            throw new IllegalArgumentException();
        }
    }
    public static double getCurrencyPrice(String fromCurrency, String toCurrency) throws IOException, IllegalArgumentException {
        System.out.println(numCalls);
        String getResult = sendGET(String.format("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s&to_currency=%s&apikey=%s", fromCurrency, toCurrency, APIKeys[numCalls%6])).get(6);
        Double currencyPrice = Double.parseDouble(getResult.substring(29, getResult.length()-2));
        return currencyPrice;
    }

}