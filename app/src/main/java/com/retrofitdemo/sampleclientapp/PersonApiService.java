package com.retrofitdemo.sampleclientapp;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface PersonApiService {
    @GET("/person")
    void retrieveAll(Callback<Persons> callback);

    @FormUrlEncoded
    @POST("/person/specific")
    void retrieveSpecific(@Field("user_name") String userName, Callback<Person> callback);
}
