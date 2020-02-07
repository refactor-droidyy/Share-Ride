package com.example.needhelp;

public class Upload {
    private String from;
    public String username_item;
    private String to;
    private String description;
    private String time;
    private String email;
    private String id;
    private String imageUrl;
    private String travel_time;
    private String mode_travel;

    public Upload(String from, String username_item, String to, String description, String time, String email, String id, String imageURL) {
        this.from = from;
        this.username_item = username_item;
        this.to = to;
        this.description = description;
        this.time = time;
        this.email = email;
        this.id = id;
        this.imageUrl = imageURL;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getMode_travel() {
        return mode_travel;
    }

    public void setMode_travel(String mode_travel) {
        this.mode_travel = mode_travel;
    }

    public Upload(String from, String username_item, String to, String description, String time, String email, String id, String imageUrl, String travel_time, String mode_travel) {
        this.from = from;
        this.username_item = username_item;
        this.to = to;
        this.description = description;
        this.time = time;
        this.email = email;
        this.id = id;
        this.imageUrl = imageUrl;
        this.travel_time = travel_time;
        this.mode_travel = mode_travel;
    }

    public String getUsername_item() {
        return username_item;
    }

    public void setUsername_item(String username_item) {
        this.username_item = username_item;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Upload() {

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
