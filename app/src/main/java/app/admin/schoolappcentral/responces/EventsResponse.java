package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.admin.schoolappcentral.entities.Events;

/**
 * Created by admin on 01/16/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 09/02/2017.
 */

public class EventsResponse {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Error")
    @Expose
    private Error error;
    @SerializedName("Response")
    @Expose
    private List<Response> response = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }
    public class Response {

        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("event_name")
        @Expose
        private String eventName;
        @SerializedName("event_date")
        @Expose
        private String eventDate;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

    }

    public class Error {

        @SerializedName("Code")
        @Expose
        private String code;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("DisplayMessage")
        @Expose
        private String displayMessage;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDisplayMessage() {
            return displayMessage;
        }

        public void setDisplayMessage(String displayMessage) {
            this.displayMessage = displayMessage;
        }

    }
}