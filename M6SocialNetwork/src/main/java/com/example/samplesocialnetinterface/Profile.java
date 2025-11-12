package com.example.samplesocialnetinterface;

public class Profile {
    private String name;
    private String status;
    private String quote;
    private String imagePath;


    public Profile(String name, String status, String quote, String imagePath) {
        this.name = name;
        this.status = status;
        this.quote = quote;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
    public void setQuote(String quote) { this.quote = quote; }
    public String getQuote() { return quote; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getImagePath() { return imagePath; }
}