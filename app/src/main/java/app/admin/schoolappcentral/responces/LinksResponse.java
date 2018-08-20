package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.admin.schoolappcentral.entities.Links;

/**
 * Created by admin on 01/16/2017.
 */

public class LinksResponse extends APIResponse
{
    @SerializedName("Response")
    private ArrayList<Links> linksArrayList;

    public ArrayList<Links> getLinksArrayList() {
        return linksArrayList;
    }

    public void setLinksArrayList(ArrayList<Links> linksArrayList) {
        this.linksArrayList = linksArrayList;
    }
}
