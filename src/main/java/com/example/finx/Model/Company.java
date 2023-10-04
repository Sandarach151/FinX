package com.example.finx.Model;

import java.time.LocalDate;
import java.util.Date;

public class Company {
    private String country;

    private String currency;

    private String exchange;

    private LocalDate ipo;

    private double marketCapitalisation;

    private String name;

    private double shareOutstanding;

    private String ticker;

    private String weburl;

    private String industry;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public LocalDate getIpo() {
        return ipo;
    }

    public void setIpo(LocalDate ipo) {
        this.ipo = ipo;
    }

    public double getMarketCapitalisation() {
        return marketCapitalisation;
    }

    public void setMarketCapitalisation(double marketCapitalisation) {
        this.marketCapitalisation = marketCapitalisation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getShareOutstanding() {
        return shareOutstanding;
    }

    public void setShareOutstanding(double shareOutstanding) {
        this.shareOutstanding = shareOutstanding;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "Company{" +
                "country='" + country + '\'' +
                ", currency='" + currency + '\'' +
                ", exchange='" + exchange + '\'' +
                ", ipo=" + ipo +
                ", marketCapitalisation=" + marketCapitalisation +
                ", name='" + name + '\'' +
                ", shareOutstanding=" + shareOutstanding +
                ", ticker='" + ticker + '\'' +
                ", weburl='" + weburl + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}
