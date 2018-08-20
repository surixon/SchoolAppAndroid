package app.admin.schoolappcentral.responces;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import app.admin.schoolappcentral.entities.Files;

/**
 * Created by admin on 01/16/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilesResponseNew {

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

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("file")
        @Expose
        private String file;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreated() {
            return created;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
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