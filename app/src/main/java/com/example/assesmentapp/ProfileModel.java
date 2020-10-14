package com.example.assesmentapp;

public class ProfileModel {
    private String name;
    private String address;
    private String email;
    private String phone;
    private String hobbies;

    ProfileModel(){

    }
    public ProfileModel(String name,String address,String email,String phone,String hobbies){
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.hobbies = hobbies;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "name = " + name ;
    }
}
