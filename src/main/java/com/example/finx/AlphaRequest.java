package com.example.finx;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AlphaRequest {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        getCurrencyPrice("BTC", "USD");
    }

    private static ArrayList<String> sendGET(String GET_URL) throws IOException, IllegalArgumentException {
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
        String getResult = sendGET(String.format("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s&to_currency=%s&apikey=05H3YY29U7DPZFZS", fromCurrency, toCurrency)).get(6);
        Double currencyPrice = Double.parseDouble(getResult.substring(29, getResult.length()-2));
        return currencyPrice;
    }

}