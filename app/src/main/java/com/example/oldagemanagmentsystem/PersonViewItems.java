package com.example.oldagemanagmentsystem;

public class PersonViewItems {
    private String Name;
    private String Religion;
    private String Age;
    private String Image;


    public PersonViewItems() {
    }

    public PersonViewItems(String name, String religion, String age, String image) {
        Name = name;
        Religion = religion;
        Age = age;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
