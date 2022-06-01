package com.abc123.iro;

public class Pets {
    public int id;
    public String image;
    public String name;
    public String breed;
    public String date;
    public String age;
    public int status_id;

    public Pets(int id, String image, String name, String breed, String age, String date, int status_id) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.date = date;
        this.status_id = status_id;
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


    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getStatus_id() {
        return status_id;
    }
}
