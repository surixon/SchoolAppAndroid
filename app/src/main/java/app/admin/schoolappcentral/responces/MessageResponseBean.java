package app.admin.schoolappcentral.responces;

import java.util.List;

/**
 * Created by rishav on 11/4/18.
 */

public class MessageResponseBean {
    /**
     * Status : true
     * Error : {"Code":"0","Message":"success","DisplayMessage":""}
     * Response : [{"event_name":"good friday","type":"event","message":"New event has been created"},{"event_name":"diwali","type":"event","message":"New Event added"},{"event_name":"new year","type":"event","message":"New Event added"},{"event_name":"republic day","type":"event","message":"New Event added"},{"event_name":"sports","type":"event","message":"New Event added"},{"event_name":"quizzz","type":"event","message":"New Event added"},{"event_name":"Prize distribution","type":"event","message":"New Event added"},{"event_name":"annual party","type":"event","message":"New Event added"}]
     */

    public String Status;
    public ErrorBean Error;
    public List<ResponseBean> Response;

    public static class ErrorBean {
        /**
         * Code : 0
         * Message : success
         * DisplayMessage :
         */

        public String Code;
        public String Message;
        public String DisplayMessage;
    }

    public static class ResponseBean {
        /**
         * event_name : good friday
         * type : event
         * message : New event has been created
         */

        public String event_name;
        public String type;
        public String message;
    }
}
