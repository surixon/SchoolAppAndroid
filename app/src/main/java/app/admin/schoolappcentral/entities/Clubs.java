package app.admin.schoolappcentral.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 09/02/2017.
 */

public class Clubs
{

    @SerializedName("Id")
    private String id;
    private String name;
    private String userid;
    @SerializedName("contect")
    private String contact;
    private String email;
    private String image;
    private Boolean issubscribed;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getcontact() {
        return contact;
    }

    public void setcontact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getIssubscribed() {
        return issubscribed;
    }

    public void setIssubscribed(Boolean issubscribed) {
        this.issubscribed = issubscribed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
