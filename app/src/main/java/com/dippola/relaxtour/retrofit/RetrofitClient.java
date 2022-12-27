package com.dippola.relaxtour.retrofit;

import android.content.Context;

import com.dippola.relaxtour.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    public static final String BASE_URL="http://ec2-3-101-48-97.us-west-1.compute.amazonaws.com:8080/";
    public static final String BASE_URL="http://ec2-3-101-48-97.us-west-1.compute.amazonaws.com:8080/";

    public static RetrofitInterface getApiService(Context context){
        return getInstance(context).create(RetrofitInterface.class);
    }

    private static Retrofit getInstance(Context context){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
