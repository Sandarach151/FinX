package com.example.finx.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompanyNewsDatabase {
    private String symbol;

    private ArrayList<NewsArticle> newsDB;

    public CompanyNewsDatabase(String symbol) {
        this.newsDB = new ArrayList<>();
        this.symbol = symbol;
    }

    public void addNews(NewsArticle news){
        newsDB.add(news);
    }

    public ArrayList<NewsArticle> getNewsDB() {
        return newsDB;
    }
}
