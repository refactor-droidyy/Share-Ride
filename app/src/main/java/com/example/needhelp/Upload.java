package com.example.needhelp;

public class Upload {
    private String from;
    public String username_item;
    private String to;
    private String description;
    private String time;
    private String email;
    private String id;
    private String imageURL;

    public Upload(String from, String username_item, String to, String description, String time, String email, String id, String imageURL) {
        this.from = from;
        this.username_item = username_item;
        this.to = to;
        this.description = description;
        this.time = time;
        this.email = email;
        this.id = id;
        this.imageURL = imageURL;
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

    public Upload(){

    }

    public String getUsername() {
        return username_item;
    }

    public void setUsername(String username) {
        this.username_item = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
