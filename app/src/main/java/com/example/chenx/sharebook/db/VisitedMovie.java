package com.example.chenx.sharebook.db;

import org.litepal.crud.LitePalSupport;

public class VisitedMovie extends LitePalSupport {

    private String name;
    private String summary;
    private String url;
    private String type;
    private String uploader;
    private String uploadtime;



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

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
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

    public String getUploader() {
        return uploader;
    }

    public String getUploadtime() {
        return uploadtime;
    }
}
