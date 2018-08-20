package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rishav on 10/4/18.
 */

public class StringDemo {
    @SerializedName("status")
    public String status;
    @SerializedName("Error")
    public Error Error;
    @SerializedName("Response")
    public String Response;

    public static class Error {
        @SerializedName("Code")
        public String Code;
        @SerializedName("Message")
        public String Message;
        @SerializedName("DisplayMessage")
        public String DisplayMessage;
    }
}
