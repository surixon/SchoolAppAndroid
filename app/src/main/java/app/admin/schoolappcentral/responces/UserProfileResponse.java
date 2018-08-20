package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import app.admin.schoolappcentral.entities.userprofileview;

/**
 * Created by admin on 08/02/2017.
 */

public class UserProfileResponse extends APIResponse
{
    @SerializedName("Response")
    private userprofileview response;

    public userprofileview getResponse() {
        return response;
    }

    public void setResponse(userprofileview response) {
        this.response = response;
    }
}
