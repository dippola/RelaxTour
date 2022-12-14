package com.dippola.relaxtour.retrofit;

import com.dippola.relaxtour.community.main.detail.AddHitModel;
import com.dippola.relaxtour.retrofit.model.PostCommentModel;
import com.dippola.relaxtour.retrofit.model.PostDetailWithComments;
import com.dippola.relaxtour.retrofit.model.PostModelDetail;
import com.dippola.relaxtour.retrofit.model.PostUpdateModel;
import com.dippola.relaxtour.retrofit.model.PostsViewWitPages;
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

    @GET("user/{id}/community/")
    Call<String> getUserCommunityState(
            @Path("id") int id
    );






    @GET("posts/page={page}/")
    Call<PostsViewWitPages> getMainPageAll(
            @Path("page") int page
    );

    @GET("posts/category={category}/page={page}/")
    Call<PostsViewWitPages> getMainPageCategory(
            @Path("category") String category,
            @Path("page") int page
    );


    @PUT("post/{pk}/")
    Call<PostDetailWithComments> getPost(
            @Path("pk") int pk,
            @Body AddHitModel willAddHit
    );

    @PUT("post/{pk}/like/set={id}/")
    Call<String> setLike(
            @Path("pk") int pk,
            @Path("id") int id
    );

    @POST("post/create={id}/")
    Call<PostModelDetail> createPost(
            @Path("id") int id,
            @Body PostModelDetail mainModel
    );

    @PUT("post/{pk}/update/")
    Call<PostUpdateModel> updateMain(
            @Path("pk") int pk,
            @Body PostUpdateModel mainModel
    );

    @DELETE("post/{pk}/delete/")
    Call<String> deleteMain(
            @Path("pk") int pk
    );

    @POST("post/{pk}/comment/create={id}/")
    Call<PostCommentModel> createComment(
            @Path("pk") int pk,
            @Path("id") int id,
            @Body PostCommentModel postCommentModel
    );

    @GET("post/{pk}/comments/page={page}/")
    Call<List<PostCommentModel>> getMainComment(
            @Path("pk") int pk,
            @Path("page") int page
    );

    @GET("post/{pk}/comments/")
    Call<List<PostCommentModel>> getPostAllComments(
            @Path("pk") int pk
    );

    @GET("post/{pk}/comments/more={lastid}/")
    Call<List<PostCommentModel>> getMainCommentMore(
            @Path("pk") int pk,
            @Path("lastid") int lastid
    );

}
