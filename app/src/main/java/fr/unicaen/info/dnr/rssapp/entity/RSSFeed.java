package fr.unicaen.info.dnr.rssapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an RSS feed.
 */
public class RSSFeed {
    private long id;
    private String name;
    private String url;
    private List<RSSItem> items;


    public RSSFeed(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public RSSFeed(String name, String url) {
        this(-1, name, url);
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
