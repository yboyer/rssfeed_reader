package fr.unicaen.info.dnr.rssapp.entity;

import android.content.Context;

import java.util.List;

import fr.unicaen.info.dnr.rssapp.R;

/**
 * Represents an RSS feed.
 */
public class RSSFeed {
    private long id;
    private String name;
    private String url;
    private long lastupdate;
    private List<RSSItem> items;


    public RSSFeed(long id, String name, String url, long lastupdate) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.lastupdate = lastupdate;
    }

    public RSSFeed(String name, String url) {
        this(-1, name, url, 0);
    }

    public RSSFeed(long id) {
        this.id = id;
    }

    /**
     * get the id
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id
     * @param id The id
     * @return This
     */
    public RSSFeed setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Get the name
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     * @param name The name
     * @return This
     */
    public RSSFeed setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the URL
     * @return The URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL
     * @param url The URL
     * @return This
     */
    public RSSFeed setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Get the elapsed time in seconds from the last update
     * @return The elapsed time
     */
    public long getElapsedTime() {
        return (System.currentTimeMillis() / 1000) - this.lastupdate;
    }

    /**
     * Get human readable last update
     * @param context The context
     * @return The last update
     */
    public String getHumanLastUpdate(Context context) {
        long elapsedTime = getElapsedTime();

        long minute = 60;
        long hour = 60*minute;
        long time;
        String sufix;

        if (elapsedTime >= hour) {
            time = elapsedTime / hour;
            sufix = context.getResources().getString(R.string.hour);
        } else  if (elapsedTime >= minute) {
            time = elapsedTime / minute;
            sufix = context.getResources().getString(R.string.minute);
        } else {
            time = elapsedTime;
            sufix = context.getResources().getString(R.string.second);
        }

        return context.getResources().getString(R.string.last_sync) + ": " + time + " " + sufix + (time > 1 ? "s" : "");
    }

    /**
     * Get the items
     * @return The items
     */
    public List<RSSItem> getItems() {
        return this.items;
    }

    /**
     * Set the items
     * @param rssItems The items
     * @return This
     */
    public RSSFeed setItems(List<RSSItem> rssItems) {
        this.items = rssItems;
        return this;
    }

    public String toString() {
        return "{\n" +
            "  \"id\":\"" + this.getId() + "\",\n" +
            "  \"name\":\"" + this.getName() + "\",\n" +
            "  \"url\":\"" + this.getUrl() + "\"\n" +
            "}";
    }
}
