package com.example.coursework1;

import javafx.scene.image.Image;


public class Food {
    String name,type,img;
    double price;
    int stock;
    int sales;
    boolean promotion;

    public Food(String name, double price, int stock, String type,int sales,String url) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.stock = stock;
        this.img = url;
        this.sales = sales;
    }
    public Food(String name, double price, int stock, String type,int sales,String url,boolean promotion) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.stock = stock;
        this.img = url;
        this.sales = sales;
        this.promotion=promotion;
    }

    public Food(String name) {
        this.name = name;
    }

    public boolean isPromotion() {
        return promotion;
    }

    public String getImg() {
        return img;
    }
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSales() {
        return sales;
    }
}
