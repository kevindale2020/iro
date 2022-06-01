package com.abc123.iro;

public class Donations {
    public int id;
    public String image;
    public String desc;
    public double amount;
    public String date;
    public String status;

    public Donations(int id, String image, String desc, double amount, String date, String status) {
        this.id = id;
        this.image = image;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
