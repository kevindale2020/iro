package com.abc123.iro;

public class MyAdoptions {
    //id = userid
    //id2 = doc_id
    //id3 = doc_status_id
    //id4 = pet_status_id
    public int id;
    public int id2;
    public int id3;
    public int id4;
    public String image;
    public String name;
    public String breed;
    public String age;
    public String status;

    public MyAdoptions(int id, int id2, int id3, int id4, String image, String name, String breed, String age, String status) {
        this.id = id;
        this.id2 = id2;
        this.id3 = id3;
        this.id4 = id4;
        this.image = image;
        this.name = name;
        this.breed = breed;
        this.age = age;
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

    public void setBreed(String gender) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
