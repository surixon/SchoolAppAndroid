package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.admin.schoolappcentral.entities.Files;

/**
 * Created by admin on 01/16/2017.
 */

public class FilesResponse extends APIResponse
{
    @SerializedName("Response")
    private ArrayList<Files> filesArrayList;

    public ArrayList<Files> getFilesArrayList() {
        return filesArrayList;
    }

    public void setFilesArrayList(ArrayList<Files> filesArrayList) {
        this.filesArrayList = filesArrayList;
    }
}
