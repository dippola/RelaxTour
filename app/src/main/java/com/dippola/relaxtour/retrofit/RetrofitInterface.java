package com.dippola.relaxtour.retrofit;

import com.dippola.relaxtour.community.auth.userscommunity.UserCommentWithPageModel;
import com.dippola.relaxtour.community.main.detail.AddHitModel;
import com.dippola.relaxtour.retrofit.model.PostCommentModel;
import com.dippola.relaxtour.retrofit.model.PostDetailWithComments;
import com.dippola.relaxtour.retrofit.model.PostModelDetail;
import com.dippola.relaxtour.retrofit.model.PostsViewWitPages;
import com.dippola.relaxtour.retrofit.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("user/{id}/")
    Call<List<UserModel>> getUser(
            @Path("id") int userId,
            @Header("key") String appkey
    );

    @POST("user/create/")
    Call<UserModel> createUser(
            @Body UserModel userModel,
            @Header("key") String appkey
    );

    @PUT("user/{uid}/update/")
    Call<UserModel> updateUser(
            @Path("uid") String userUid,
            @Body UserModel userModel,
            @Header("key") String appkey
    );

    @DELETE("user/{uid}/delete/")
    Call<String> deleteUser(
            @Path("uid") String userUid,
            @Header("key") String appkey
    );

    @GET("user/searchnickname/{nickname}/")
    Call<List<UserModel>> searchNickname(
            @Path("nickname") String nickname,
            @Header("key") String appkey
    );

    @GET("user/searchemail/{email}/")
    Call<List<UserModel>> searchEmail(
            @Path("email") String email,
            @Header("key") String appkey
    );

    @GET("user/{id}/community/")
    Call<String> getUserCommunityState(
            @Path("id") int id,
            @Header("key") String appkey
    );

    @GET("user/{id}/community/posts/page={page}/")
    Call<PostsViewWitPages> getUserCommunityPostsPage(
            @Path("id") int id,
            @Path("page") int page,
            @Header("key") String appkey
    );

    @GET("user/{id}/community/posts/category={category}/page={page}/")
    Call<PostsViewWitPages> getUserCommunityCategoryPage(
            @Path("id") int id,
            @Path("category") String category,
            @Path("page") int page,
            @Header("key") String appkey
    );

    @GET("user/{id}/community/comments/page={page}/")
    Call<UserCommentWithPageModel> getUserCommentAll(
            @Path("id") int id,
            @Path("page") int page,
            @Header("key") String appkey
    );

    @GET("user/{id}/community/likes/page={page}/")
    Call<PostsViewWitPages> getUserCommunityLikePostsPage(
            @Path("id") int id,
            @Path("page") int page,
            @Header("key") String appkey
    );



    @GET("posts/page={page}/")
    Call<PostsViewWitPages> getMainPageAll(
            @Path("page") int page,
            @Header("key") String key
    );

    @GET("posts/category={category}/page={page}/")
    Call<PostsViewWitPages> getMainPageCategory(
            @Path("category") String category,
            @Path("page") int page,
            @Header("key") String appkey
    );


    @PUT("post/{pk}/")
    Call<PostDetailWithComments> getPost(
            @Path("pk") int pk,
            @Body AddHitModel willAddHit,
            @Header("key") String appkey
    );

    @PUT("post/{pk}/like/set={id}/")
    Call<String> setLike(
            @Path("pk") int pk,
            @Path("id") int id,
            @Header("key") String appkey
    );

    @POST("post/create={id}/")
    Call<PostModelDetail> createPost(
            @Path("id") int id,
            @Body PostModelDetail mainModel,
            @Header("key") String appkey
    );

    @PUT("post/{pk}/update/")
    Call<String> updateMain(
            @Path("pk") int pk,
            @Body PostModelDetail mainModel,
            @Header("key") String appkey
    );

    @DELETE("post/{pk}/delete/")
    Call<String> deleteMain(
            @Path("pk") int pk,
            @Header("key") String appkey
    );

    @POST("post/{pk}/comment/create={id}/")
    Call<PostCommentModel> createComment(
            @Path("pk") int pk,
            @Path("id") int id,
            @Body PostCommentModel postCommentModel,
            @Header("key") String appkey
    );

    @GET("post/{pk}/comments/page={page}/")
    Call<List<PostCommentModel>> getMainComment(
            @Path("pk") int pk,
            @Path("page") int page,
            @Header("key") String appkey
    );

    @GET("post/{pk}/comments/")
    Call<List<PostCommentModel>> getPostAllComments(
            @Path("pk") int pk,
            @Header("key") String appkey
    );

    @GET("post/{pk}/comments/more={lastid}/")
    Call<List<PostCommentModel>> getMainCommentMore(
            @Path("pk") int pk,
            @Path("lastid") int lastid,
            @Header("key") String appkey
    );

    @DELETE("post/{pk}/comment/{id}/delete/")
    Call<String> deleteComment(
            @Path("pk") int pk,
            @Path("id") int id,
            @Header("key") String appkey
    );

}
