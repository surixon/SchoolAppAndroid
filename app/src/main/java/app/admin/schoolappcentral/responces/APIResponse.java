package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 3/9/2015.
 */
public class APIResponse
{
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Error")
    @Expose
    private Error error;

    @SerializedName("Notification")
    @Expose
    private String Message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
