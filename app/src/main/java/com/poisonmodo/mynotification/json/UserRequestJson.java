package com.poisonmodo.mynotification.json;

import com.google.gson.annotations.SerializedName;

public class UserRequestJson {
    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;
}
