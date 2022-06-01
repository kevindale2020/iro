package com.abc123.iro;

public class Items {
    public int id;
    public String image;
    public String name;
    public String category;
    public int qty;
    public double price;

    public Items(int id, String image, String name, String category, int qty, double price) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.category = category;
        this.qty = qty;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
