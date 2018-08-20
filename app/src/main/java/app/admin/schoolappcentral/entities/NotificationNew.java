package app.admin.schoolappcentral.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 01/18/2017.
 */

public class NotificationNew {
    @SerializedName("Response")
    public List<Response> Response;
    @SerializedName("Status")
    public String Status;
    @SerializedName("Error")
    public Error Error;

    public static class Response {
        @SerializedName("message")
        public String message;
        @SerializedName("type")
        public String type;
        @SerializedName("event_name")
        public String event_name;
        @SerializedName("created")
        public String created_date;
    }

    public static class Error {
        @SerializedName("DisplayMessage")
        public String DisplayMessage;
        @SerializedName("Message")
        public String Message;
        @SerializedName("Code")
        public String Code;
    }

 /*   private long id;
    private String notification;
    @SerializedName("notificationdate")
    private String createddate;
    @SerializedName("createdbyname")
    private String createdby;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }*/
}
