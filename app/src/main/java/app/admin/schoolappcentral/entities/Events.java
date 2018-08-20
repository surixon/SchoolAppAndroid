package app.admin.schoolappcentral.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 09/02/2017.
 */

public class Events
{
    private long id;
    @SerializedName("eventdate")
    private String eventdatetime;
    private String eventtype;
    private String title;
    private String detail;
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventdatetime() {
        return eventdatetime;
    }

    public void setEventdatetime(String eventdatetime) {
        this.eventdatetime = eventdatetime;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
