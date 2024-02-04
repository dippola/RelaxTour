package com.dippola.relaxtour.retrofit;

import android.content.Context;

import com.dippola.relaxtour.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static RetrofitInterface getApiService(Context context){
        return getInstance(context).create(RetrofitInterface.class);
    }

    private static Retrofit getInstance(Context context){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
