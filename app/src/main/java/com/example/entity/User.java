package com.example.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/24.
 */

public class User implements Serializable {
    private boolean successful;
    private int errcode;
    private String message;
    private List<Person> data;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public List<Person> getPerson() {
        return data;
    }

    public void setPerson(List<Person> data) {
        this.data = data;
    }

    public class Person implements Serializable {
        private int USER_ID;
        private int FRIEND_ID;
        private String FRIEND_TYPE;
        private String FRIEND_TYPE_NAME;
        private String NICK_NAME;
        private int FOCUSNUM;
        private int ORDER_NO;
        private int STATUS;

        public int getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(int USER_ID) {
            this.USER_ID = USER_ID;
        }

        public int getFRIEND_ID() {
            return FRIEND_ID;
        }

        public void setFRIEND_ID(int FRIEND_ID) {
            this.FRIEND_ID = FRIEND_ID;
        }

        public String getFRIEND_TYPE() {
            return FRIEND_TYPE;
        }

        public void setFRIEND_TYPE(String FRIEND_TYPE) {
            this.FRIEND_TYPE = FRIEND_TYPE;
        }

        public String getNICK_NAME() {
            return NICK_NAME;
        }

        public void setNICK_NAME(String NICK_NAME) {
            this.NICK_NAME = NICK_NAME;
        }

        public int getFOCUSNUM() {
            return FOCUSNUM;
        }

        public void setFOCUSNUM(int FOCUSNUM) {
            this.FOCUSNUM = FOCUSNUM;
        }

        public int getORDER_NO() {
            return ORDER_NO;
        }

        public void setORDER_NO(int ORDER_NO) {
            this.ORDER_NO = ORDER_NO;
        }

        public String getFRIEND_TYPE_NAME() {
            return FRIEND_TYPE_NAME;
        }

        public void setFRIEND_TYPE_NAME(String FRIEND_TYPE_NAME) {
            this.FRIEND_TYPE_NAME = FRIEND_TYPE_NAME;
        }

        public int getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(int STATUS) {
            this.STATUS = STATUS;
        }

    }
}
