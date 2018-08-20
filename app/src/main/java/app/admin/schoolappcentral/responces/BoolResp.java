package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 03/21/2016.
 */
public class BoolResp extends APIResponse
{
    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    @SerializedName("Response")
    private Boolean aBoolean;
}
