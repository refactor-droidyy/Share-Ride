package com.example.needhelp;

public class RequestRecievedrejected {
    public String id;
    public String type;

    public RequestRecievedrejected(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RequestRecievedrejected(String id, String type) {
        this.id = id;
        this.type = type;
    }
}
