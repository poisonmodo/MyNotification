package com.poisonmodo.mynotification.json;

import com.google.gson.annotations.SerializedName;

public class UserRequestJson {
    @SerializedName("countrycode")
    public String countrycode;

    @SerializedName("phone")
    public String phone;

    @SerializedName("password")
    public String password;
}
