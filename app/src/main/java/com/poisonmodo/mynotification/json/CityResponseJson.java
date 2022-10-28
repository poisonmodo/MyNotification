package com.poisonmodo.mynotification.json;

import com.google.gson.annotations.SerializedName;
import com.poisonmodo.mynotification.models.CityModel;

import java.util.List;

public class CityResponseJson {
    @SerializedName("statuscode")
    private int statuscode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<CityModel> data ;

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CityModel> getData() {
        return data;
    }

    public void setData(List<CityModel> data) {
        this.data = data;
    }
}
