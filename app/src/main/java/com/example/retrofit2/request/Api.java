package com.example.retrofit2.request;

import com.example.retrofit2.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL="https://jsonplaceholder.typicode.com/";
    @GET("posts")
    Call<List<UserModel>> getHeroes();
}