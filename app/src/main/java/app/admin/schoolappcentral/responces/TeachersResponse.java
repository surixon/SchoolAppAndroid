package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.admin.schoolappcentral.entities.Teacher;

/**
 * Created by admin on 01/16/2017.
 */

public class TeachersResponse extends APIResponse
{
    @SerializedName("Response")
    private ArrayList<Teacher> teacherArrayList;

    public ArrayList<Teacher> getTeacherArrayList() {
        return teacherArrayList;
    }

    public void setTeacherArrayList(ArrayList<Teacher> teacherArrayList) {
        this.teacherArrayList = teacherArrayList;
    }
}
