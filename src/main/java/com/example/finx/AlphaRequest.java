package com.example.finx;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class AlphaRequest {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        sendGET("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=BTC&to_currency=CNY&apikey=demo");
    }

    private static void sendGET(String GET_URL) throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder result= new StringBuilder();
            String line;
            while((line = br.readLine()) != null){
                result.append(line);
                result.append("\n");
            }
            System.out.println(result.toString());
        } else {
            System.out.println("GET request did not work.");
        }

    }

}