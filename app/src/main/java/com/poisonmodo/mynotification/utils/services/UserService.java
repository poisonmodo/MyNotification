package com.poisonmodo.mynotification.utils.services;

import com.poisonmodo.mynotification.json.UserRequestJson;
import com.poisonmodo.mynotification.json.UserResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Call<UserResponseJson> login(@Body UserRequestJson param);
}
