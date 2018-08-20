package app.admin.schoolappcentral.responces;

import java.util.List;

/**
 * Created by rishav on 9/4/18.
 */

public class SchoolProfile {
    public String Status;
    public Error Error;
    public List<Response> Response;

    public static class Error {
        public String Code;
        public String Message;
        public String DisplayMessage;
    }

    public static class Response {
        public String id;
        public String name;
        public String address;
        public String city;
        public String pincode;
        public String contactnumber;
        public String email;
        public String weburl;
        public String facebook;
        public String twitter;
        public String linkdin;
        public String googleplus;
        public String image;
        public String detail;
        public String instagram;
    }}

