package com.poisonmodo.mynotification;

public class Constants {
    private String BASEURL ="http://192.168.0.108:8000/";
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
