package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.admin.schoolappcentral.entities.Notification;
import app.admin.schoolappcentral.entities.NotificationNew;

/**
 * Created by admin on 01/16/2017.
 */

public class MessageResponseNew extends APIResponse
{
    @SerializedName("Response")
    private ArrayList<NotificationNew.Response> notificationArrayList;

    public ArrayList<NotificationNew.Response> getNotificationArrayList() {
        return notificationArrayList;
    }

    public void setNotificationArrayList(ArrayList<NotificationNew.Response> notificationArrayList) {
        this.notificationArrayList = notificationArrayList;
    }
}
