package com.example.needhelp;

public class User {

     String id;
     String username_item;
     String imageURL;
     String status;
     String search;
     String email;
     String phone;

    public User(String id, String username_item, String imageURL, String status, String search, String email, String phone) {
        this.id = id;
        this.username_item = username_item;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
        this.email = email;
        this.phone = phone;
    }

    public User(){
        //default constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername_item() {
        return username_item;
    }

    public void setUsername_item(String username_item) {
        this.username_item = username_item;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
