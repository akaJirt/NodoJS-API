package com.example.nodojs_api;

import com.example.nodojs_api.model.Data;
import com.example.nodojs_api.model.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET("users")
    Call<User> getUser();

    @POST("users")
    Call<Data> postUser(@Body RequestBody requestBody);


    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String userId);

}

