package com.poisonmodo.mynotification;

public class Constants {
    private String BASEURL ="https://eb7b-111-95-106-190.ap.ngrok.io/";
    private String APIURL = getBASEURL() +"api/v1/";

    public Constants() {

    }

    public String getBASEURL() {
        return BASEURL;
    }

    public void setBASEURL(String BASEURL) {
        this.BASEURL = BASEURL;
    }

    public String getAPIURL() {
        return APIURL;
    }

    public void setAPIURL(String APIURL) {
        this.APIURL = APIURL;
    }
}
