package com.example.newdemo.bean;

public class Images {
    private int id;
    private int uid;
    private String  path;
    private String time;

    public Images(int id, int uid, String path, String time) {
        this.id = id;
        this.uid = uid;
        this.path = path;
        this.time = time;
    }

    public Images() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
