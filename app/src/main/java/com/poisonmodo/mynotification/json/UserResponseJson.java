package com.poisonmodo.mynotification.json;

import com.google.gson.annotations.SerializedName;

public class UserResponseJson {
    @SerializedName("statuscode")
    public int statuscode;

    @SerializedName("message")
    public String message;
}
