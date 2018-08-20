package app.admin.schoolappcentral.entities;

/**
 * Created by admin on 09/02/2017.
 */

public class Links
{
    private long id;
    private String name;
    private String url;
    private String targeturl;
    private String createdby;
    private String urldescription;
    private String urltitle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargeturl() {
        return targeturl;
    }

    public void setTargeturl(String targeturl) {
        this.targeturl = targeturl;
    }

    public String getUrldescription() {
        return urldescription;
    }

    public void setUrldescription(String urldescription) {
        this.urldescription = urldescription;
    }

    public String getUrltitle() {
        return urltitle;
    }

    public void setUrltitle(String urltitle) {
        this.urltitle = urltitle;
    }
}
