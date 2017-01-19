package fr.unicaen.info.dnr.rssapp.fr.unicaen.info.dnr.rssapp.entity;

/**
 * Created by lenaic on 19/01/2017.
 */

public class RSSFeed {

    private long id;
    private String link;

    public RSSFeed(long id, String link) {
        this.id = id;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String toString() {
        return "id : " + this.getId() + " lien : " + this.getLink();
    }
}
