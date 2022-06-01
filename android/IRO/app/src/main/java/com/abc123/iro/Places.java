package com.abc123.iro;

public class Places {
    public String image;
    public String name;
    public String place;
    public String contact;
    public double lat;
    public double lng;

    public Places(String image, String name, String place, String contact, double lat, double lng)
    {
        this.image = image;
        this.name = name;
        this.place = place;
        this.contact = contact;
        this.lat = lat;
        this.lng = lng;
    }

    public String getImage()
    {
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
