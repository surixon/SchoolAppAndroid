package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import app.admin.schoolappcentral.entities.Schoolprofile;

/**
 * Created by admin on 11/02/2017.
 */

public class SchoolProfileResponse extends APIResponse
{
    @SerializedName("Response")
    private Schoolprofile schoolProfile;

    public Schoolprofile getSchoolProfile() {
        return schoolProfile;
    }

    public void setSchoolProfile(Schoolprofile schoolProfile) {
        this.schoolProfile = schoolProfile;
    }
}
