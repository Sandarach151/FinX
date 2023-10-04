package com.example.finx.Others;

import com.example.finx.Model.CompanyNewsDatabase;
import com.example.finx.Model.NewsArticle;
import com.example.finx.Model.StockCandleChart;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class FinnhubHandler {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        System.out.println(getCompanyNews("AAPL").getNewsDB().get(0));
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
        String result = sendGET(String.format("https://finnhub.io/api/v1/quote?symbol=%s&token=cjap77hr01qji1gtqj3gcjap77hr01qji1gtqj40", symbol));
        String[] parsedResult = result.split(",");
        Double[] info = new Double[3];
        info[0] = Double.valueOf(parsedResult[0].substring(5));
        info[1] = Double.valueOf(parsedResult[1].substring(4));
        info[2] = Double.valueOf(parsedResult[2].substring(5));
        return info;
    }

    public static NewsArticle getRandNews() throws IOException {
        String result = sendGET("https://finnhub.io/api/v1/news?category=general&token=cjap77hr01qji1gtqj3gcjap77hr01qji1gtqj40");
        String[] news = result.split("},");
        int pos = (int) (1+Math.floor(Math.random()*(news.length-2)));
        String[] categories = news[pos].split(",\"");
        NewsArticle ans = new NewsArticle();
        ans.setHeadline(categories[2].substring(11, categories[2].length()-1));
        ans.setImageURL(categories[4].substring(8, categories[4].length()-1));
        ans.setPublishTime(Instant.ofEpochSecond(Integer.parseInt(categories[1].substring(10))));
        ans.setSummary(categories[7].substring(10, categories[7].length()-1));
        ans.setUrl(categories[8].substring(6, categories[8].length()-1));
        return ans;
    }

    public static CompanyNewsDatabase getCompanyNews(String symbol) throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDate elevenMonthsAgo = currentDate.minusMonths(11);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentFormattedDate = currentDate.format(formatter);
        String elevenFormattedDate = elevenMonthsAgo.format(formatter);
        String result = sendGET(String.format("https://finnhub.io/api/v1/company-news?symbol=%s&from=%s&to=%s&token=cjap77hr01qji1gtqj3gcjap77hr01qji1gtqj40", symbol, elevenFormattedDate, currentFormattedDate));
        String[] news = result.split("},");
        news = Arrays.copyOfRange(news, 1, Math.min(news.length-1, 30));
        CompanyNewsDatabase database = new CompanyNewsDatabase(symbol);
        for(int i=0; i<news.length; i++){
            String[] categories = news[i].split(",\"");
            NewsArticle ans = new NewsArticle();
            ans.setHeadline(categories[2].substring(11, categories[2].length()-1));
            ans.setImageURL(categories[4].substring(8, categories[4].length()-1));
            ans.setPublishTime(Instant.ofEpochSecond(Integer.parseInt(categories[1].substring(10))));
            ans.setSummary(categories[7].substring(10, categories[7].length()-1));
            ans.setUrl(categories[8].substring(6, categories[8].length()-1));
            database.addNews(ans);
        }
        return database;
    }

    public static StockCandleChart getCandles(String symbol) throws IOException {
        String result = sendGET(String.format("https://finnhub.io/api/v1/stock/candle?symbol=%s&resolution=D&from=%d&to=%d&token=cjap77hr01qji1gtqj3gcjap77hr01qji1gtqj40", symbol, Instant.now().getEpochSecond()-28930000, Instant.now().getEpochSecond()));
        String[] cur = result.split(",\"");
        cur[0] = cur[0].substring(2);
        cur[6] = cur[6].substring(0, cur[6].length()-1);
        for(int i=0; i<cur.length; i++){
            cur[i] = cur[i].substring(4, cur[i].length()-1);
            System.out.println(cur[i]);
        }
        String[] rawClosePrices = cur[0].split(",");
        ArrayList<Double> closePrices = new ArrayList<>();
        for(int i=0; i<rawClosePrices.length; i++){
            closePrices.add(Double.parseDouble(rawClosePrices[i]));
        }
        String[] rawHighPrices = cur[1].split(",");
        ArrayList<Double> highPrices = new ArrayList<>();
        for(int i=0; i<rawHighPrices.length; i++){
            highPrices.add(Double.parseDouble(rawHighPrices[i]));
        }
        String[] rawLowPrices = cur[2].split(",");
        ArrayList<Double> lowPrices = new ArrayList<>();
        for(int i=0; i<rawLowPrices.length; i++){
            lowPrices.add(Double.parseDouble(rawLowPrices[i]));
        }
        String[] rawOpenPrices = cur[3].split(",");
        ArrayList<Double> openPrices = new ArrayList<>();
        for(int i=0; i<rawOpenPrices.length; i++){
            openPrices.add(Double.parseDouble(rawOpenPrices[i]));
        }
        String[] rawTimestamps = cur[5].split(",");
        ArrayList<Integer> timestamps = new ArrayList<>();
        for(int i=0; i<rawTimestamps.length; i++){
            timestamps.add(Integer.parseInt(rawTimestamps[i]));
        }
        return new StockCandleChart(closePrices, highPrices, lowPrices, openPrices, timestamps);
    }


}