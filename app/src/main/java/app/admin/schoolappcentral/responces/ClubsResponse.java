package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.admin.schoolappcentral.entities.Clubs;

/**
 * Created by admin on 01/16/2017.
 */

public class ClubsResponse extends APIResponse
{
    @SerializedName("Response")
    private ArrayList<Clubs> clubsArrayList;

    public ArrayList<Clubs> getClubsArrayList() {
        return clubsArrayList;
    }

    public void setClubsArrayList(ArrayList<Clubs> clubsArrayList) {
        this.clubsArrayList = clubsArrayList;
    }
}
