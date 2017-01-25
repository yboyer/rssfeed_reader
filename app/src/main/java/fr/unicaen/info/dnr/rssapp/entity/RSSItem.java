package fr.unicaen.info.dnr.rssapp.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an RSS item.
 */
public class RSSItem {
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    private long id;
    private Date pubDate;
    private String content;
    private String description;
    private String link;
    private long feedId;

    /**
     * Set the id
     * @param id The id
     * @return This
     */
    public RSSItem setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Get the id
     * @return The id
     */
    public long getId() {
        return id;
    }

    /**
     * Set the content
     * @param content The content
     * @return This
     */
    public RSSItem setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Get the content
     * @return The content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Set the description
     * @param description The descrption
     * @return This
     */
    public RSSItem setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the description
     * @return The description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the link
     * @param link The link
     * @return This
     */
    public RSSItem setLink(String link) {
        this.link = link;
        return this;
    }

    /**
     * Get the link
     * @return The link
     */
    public String getLink() {
        return this.link;
    }

    /**
     * Set the publication date as a Date
     * @param pubDate The publication date
     * @return This
     */
    public RSSItem setPubDate(Date pubDate) {
        this.pubDate = pubDate;
        return this;
    }

    /**
     * Set the publicationdate as a String
     * @param pubDate the publication date
     * @return This
     */
    public RSSItem setStringPubDate(String pubDate) {
        try {
            this.pubDate = this.dateFormatter.parse(pubDate);
        } catch (ParseException e) {
            this.pubDate = null;
        }
        return this;
    }

    /**
     * Get the publication date as a Date
     * @return The publication date as a Date
     */
    public Date getPubDate() {
        return this.pubDate;
    }

    /**
     * Get the publication date as a String
     * @return The publication date as a String
     */
    public String getStringPubDate() {
        return this.dateFormatter.format(this.pubDate);
    }

    /**
     * Set the feed id
     * @param feedId The feed id
     * @return This
     */
    public RSSItem setFeedId(long feedId){
        this.feedId = feedId;
        return this;
    }

    /**
     * Get the feed id
     * @return The feed id
     */
    public long getFeedId() {
        return this.feedId;
    }

    public String toString() {
        return "{\n" +
            "  \"id\":\"" + this.getId() + "\",\n" +
            "  \"description\":\"" + this.getDescription() + "\",\n" +
            "  \"content\":\"" + this.getContent() + "\",\n" +
            "  \"link\":\"" + this.getLink() + "\",\n" +
            "  \"pubDate\":\"" + this.getStringPubDate() + "\",\n" +
            "  \"feedId\":\"" + this.getFeedId() + "\"\n" +
            "}";
    }
}
