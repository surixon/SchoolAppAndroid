package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 03/23/2016.
 */
public class StringResp extends APIResponse
{
    public String getString()
    {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @SerializedName("Response")
    private String string;
}
