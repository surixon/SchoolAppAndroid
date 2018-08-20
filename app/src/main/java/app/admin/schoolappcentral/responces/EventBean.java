package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rishav on 11/4/18.
 */

public class EventBean {
    @Expose
    @SerializedName("Response")
    public List<Response> Response;
    @Expose
    @SerializedName("Status")
    public String Status;
    @Expose
    @SerializedName("Error")
    public Error Error;

    public static class Response {
        @Expose
        @SerializedName("event_date")
        public String event_date;
        @Expose
        @SerializedName("event_name")
        public String event_name;
        @Expose
        @SerializedName("event_id")
        public String event_id;
    }

    public static class Error {
        @Expose
        @SerializedName("DisplayMessage")
        public String DisplayMessage;
        @Expose
        @SerializedName("Message")
        public String Message;
        @Expose
        @SerializedName("Code")
        public String Code;
    }

    /**
     * Status : true
     * Error : {"Code":"0","Message":"success","DisplayMessage":""}
     * Response : [{"event_id":"7","event_name":"republic day","event_date":"26/01/2018"},{"event_id":"6","event_name":"new year","event_date":"01/01/2018"},{"event_id":"5","event_name":"good friday","event_date":""},{"event_id":"4","event_name":"diwali","event_date":"05/04/2018"},{"event_id":"8","event_name":"sports","event_date":"05/04/2018"},{"event_id":"9","event_name":"quizzz","event_date":"05/04/2018"},{"event_id":"10","event_name":"Prize distribution","event_date":"18/04/2018"}]
     */
/*
    public String Status;
    public ErrorBean Error;
    public List<ResponseBean> Response;

    public static class ErrorBean {
        *//**
     * Code : 0
     * Message : success
     * DisplayMessage :
     *//*

        public String Code;
        public String Message;
        public String DisplayMessage;
    }

    public static class ResponseBean {
        *//**
     * event_id : 7
     * event_name : republic day
     * event_date : 26/01/2018
     *//*

        public String event_id;
        public String event_name;
        public String event_date;
    }*/

/*
    @SerializedName("Status")
    public String Status;
    @SerializedName("Error")
    public Error Error;
    @SerializedName("Response")
    public List<Response> Response;

    public static class Error {
        @SerializedName("Code")
        public String Code;
        @SerializedName("Message")
        public String Message;
        @SerializedName("DisplayMessage")
        public String DisplayMessage;
    }

    public static class Response {
        @SerializedName("event_id")
        public String event_id;
        @SerializedName("event_name")
        public String event_name;
        @SerializedName("event_date")
        public String event_date;
    }*/
}
