package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.admin.schoolappcentral.entities.Notification;

/**
 * Created by admin on 01/16/2017.
 */

public class MessageResponse extends APIResponse
{
    @SerializedName("Response")
    private ArrayList<Notification.Response> notificationArrayList;

    public ArrayList<Notification.Response> getNotificationArrayList() {
        return notificationArrayList;
    }

    public void setNotificationArrayList(ArrayList<Notification.Response> notificationArrayList) {
        this.notificationArrayList = notificationArrayList;
    }
}
