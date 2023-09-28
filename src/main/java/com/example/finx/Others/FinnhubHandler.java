package com.example.finx.Others;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

public class FinnhubHandler {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        getRandNews();
    }

    private static String sendGET(String GET_URL) throws IOException, IllegalArgumentException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String result = "";
            String line;
            while((line = br.readLine()) != null){
                result+=line;
            }
            return result;
        } else {
            System.out.println("GET request did not work.");
            throw new IllegalArgumentException();
        }
    }

    public static Double[] getStockPrice(String symbol) throws IOException {
        String result = sendGET(String.format("https://finnhub.io/api/v1/quote?symbol=%s&token=%s", symbol, "cjap77hr01qji1gtqj3gcjap77hr01qji1gtqj40"));
        String[] parsedResult = result.split(",");
        Double[] info = new Double[3];
        info[0] = Double.valueOf(parsedResult[0].substring(5));
        info[1] = Double.valueOf(parsedResult[1].substring(4));
        info[2] = Double.valueOf(parsedResult[2].substring(5));
        return info;
    }

    public static String[] getRandNews() throws IOException {
        String result = sendGET("https://finnhub.io/api/v1/news?category=general&token=cjap77hr01qji1gtqj3gcjap77hr01qji1gtqj40");
        String[] news = result.split("},");
        int pos = (int) (1+Math.floor(Math.random()*(news.length-2)));
        String[] categories = news[pos].split(",\"");
        String[] ans = new String[4];
        ans[0] = categories[1].substring(10);
        ans[1] = categories[2].substring(11, categories[2].length()-1);
        ans[2] = categories[4].substring(8, categories[4].length()-1);
        ans[3] = categories[8].substring(6, categories[8].length()-1);
        return ans;
    }

}