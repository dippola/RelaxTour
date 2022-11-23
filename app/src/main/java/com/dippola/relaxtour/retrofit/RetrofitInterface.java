package com.dippola.relaxtour.retrofit;

import com.dippola.relaxtour.retrofit.model.MainCommentModel;
import com.dippola.relaxtour.retrofit.model.MainCommentUpdateModel;
import com.dippola.relaxtour.retrofit.model.MainModel;
import com.dippola.relaxtour.retrofit.model.MainUpdateModel;
import com.dippola.relaxtour.retrofit.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("user/{id}/")
    Call<List<UserModel>> getUser(
            @Path("id") int userId
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

    @GET("user/searchnickname/{nickname}/")
    Call<List<UserModel>> searchNickname(
            @Path("nickname") String nickname
    );

    @GET("user/searchemail/{email}/")
    Call<List<UserModel>> searchEmail(
            @Path("email") String email
    );

    @GET("posts/page={page}/")
    Call<List<MainModel>> getMainPage(
            @Path("page") int page
    );
    @GET("post/{pk}/")
    Call<MainModel> getMain(
            @Path("pk") int pk
    );
    @POST("post/create={id}/")
    Call<MainModel> createMain(
            @Path("id") int id,
            @Body MainModel mainModel
    );
    @PUT("post/{pk}/update/")
    Call<MainUpdateModel> updateMain(
            @Path("pk") int pk,
            @Body MainUpdateModel mainModel
    );
    @DELETE("post/{pk}/delete/")
    Call<String> deleteMain(
            @Path("pk") int pk
    );

    @GET("post/{pk}/comments/page={page}/")
    Call<List<MainCommentModel>> getMainComment(
            @Path("pk") int pk,
            @Path("page") int page
    );
    @POST("post/{pk}/comment/create={id}/")
    Call<MainCommentModel> createComment(
            @Path("pk") int pk,
            @Path("id") int id,
            @Body MainCommentModel mainCommentModel
    );
    @PUT("post/comment/update={id}/")
    Call<MainCommentUpdateModel> updateComment(
            @Path("id") int id,
            @Body MainCommentUpdateModel mainCommentModel
    );
    @DELETE("post/comment/delete={id}/")
    Call<String> deleteComment(
            @Path("id") int id
    );
}
