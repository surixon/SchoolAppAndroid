package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 01/11/2017.
 */

public class LoginResponse
{
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("expires_in")
    private long expires_in;
    @SerializedName("token_type")
    private String token_type;
    @SerializedName("userName")
    private String userName;
    @SerializedName(".expires")
    private String expires;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
