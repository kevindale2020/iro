package com.abc123.iro;

public class Events {
    public int id;
    public String userImage;
    public String name;
    public String role;
    public String date;
    public String eventImage;
    public String title;
    public String desc;

    public Events(int id, String userImage, String name, String role, String date, String eventImage, String title, String desc) {
        this.id = id;
        this.userImage = userImage;
        this.name = name;
        this.role = role;
        this.date = date;
        this.eventImage = eventImage;
        this.title = title;
        this.desc = desc;
    }

    public int getId() {
        return id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
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
}
