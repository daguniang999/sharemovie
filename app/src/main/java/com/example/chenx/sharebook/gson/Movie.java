package com.example.chenx.sharebook.gson;

public class Movie {

    public int id;
    public String name;
    public String summary;
    public String url;
    public String type;
    public String uploader;
    public String uploadtime;

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public int comment_number;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public int getComment_number() {
        return comment_number;
    }
}
