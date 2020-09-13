package com.teamLP.truth.Users.model;

public class UserData {
    String username;
    String fullname;
    String email;
    String password;
    String phoneNumber;
    String gender;
    String owninfo;
    String age;

    public UserData() {
    }

    public UserData(String username, String fullname, String email, String password, String phoneNumber, String gender, String owninfo, String age) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.owninfo = owninfo;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOwninfo() {
        return owninfo;
    }

    public void setOwninfo(String owninfo) {
        this.owninfo = owninfo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
