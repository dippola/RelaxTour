package com.dippola.relaxtour.retrofit;

import com.dippola.relaxtour.retrofit.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("user/{uid}/")
    Call<List<UserModel>> getUser(
            @Path("uid") String userUid
    );

    @POST("user/create/")
    Call<UserModel> createUser(
            @Body UserModel userModel
    );

    @PUT("user/{uid}/update/")
    Call<UserModel> updateUser(
            @Path("uid") String userUid,
            @Body UserModel userModel
    );

    @DELETE("user/{uid}/delete/")
    Call<String> deleteUser(
            @Path("uid") String userUid
    );

    @GET("user/search/{nickname}")
    Call<List<UserModel>> searchNickname(
            @Path("nickname") String nickname
    );

    @GET("user/search/{email}")
    Call<List<UserModel>> searchEmail(
            @Path("email") String email
    );
}
