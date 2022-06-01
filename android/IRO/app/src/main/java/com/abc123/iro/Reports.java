package com.abc123.iro;

public class Reports {
    public int id;
    public int id2;
    public int id3;
    public int id4;
    public String userImage;
    public String name;
    public String date;
    public String location;
    public String reportImage;
    public String title;
    public String desc;
    public String status;
    public String contact;

    public Reports(int id, int id2, int id3, int id4, String userImage, String name, String contact, String date, String location, String reportImage, String title, String desc, String status) {
        this.id = id;
        this.id2 = id2;
        this.id3 = id3;
        this.id4 = id4;
        this.userImage = userImage;
        this.name = name;
        this.contact = contact;
        this.date = date;
        this.location = location;
        this.reportImage = reportImage;
        this.title = title;
        this.desc = desc;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getId2() {
        return id2;
    }

    public int getId3() {
        return id3;
    }

    public int getId4() {
        return id4;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReportImage() {
        return reportImage;
    }

    public void setReportImage(String reportImage) {
        this.reportImage = reportImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
