package fr.unicaen.info.dnr.rssapp.entity;

import java.util.Date;

/**
 * Created by lenaic on 19/01/2017.
 */

public class RSSItem {

    private long id;
    private String message;
    private String date;

    public RSSItem(long id, String message, String date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return "id : " + this.getId() + " message : " + this.getMessage() + " date : " + this.getDate();
    }
}
