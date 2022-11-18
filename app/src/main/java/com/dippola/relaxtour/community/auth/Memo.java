package com.dippola.relaxtour.community.auth;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Memo {
//    Button test10;
//    test10 = findViewById(R.id.community_main_firestore_test10);
//        test10.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Call<List<UserModel>> call;
//            call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
//            call.enqueue(new Callback<List<UserModel>>() {
//                @Override
//                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
//                    List<UserModel> model = response.body();
//                    Log.d("CommunityMain>>>", "get: " + model.get(0).getNickname());
//                }
//
//                @Override
//                public void onFailure(Call<List<UserModel>> call, Throwable t) {
//                    Log.d("CommunityMain>>>", "failed1: " + call.toString());
//                    Log.d("CommunityMain>>>", "failed2: " + t.toString());
//                }
//            });
//        }
//    });
//
//    Button test11;
//    test11 = findViewById(R.id.community_main_firestore_test11);
//        test11.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            UserModel userModel = new UserModel();
//            userModel.setEmail("kmj654649@gmail.com");
//            userModel.setImageurl("");
//            userModel.setNickname("nickname2");
//            userModel.setUid("testuid2testuid2testuid2");
//            RetrofitClient.getApiService().createUser(userModel).enqueue(new Callback<UserModel>() {
//                @Override
//                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                    if (response.isSuccessful()) {
//                        Log.d("CommunityMain>>>", "1");
//                    } else {
//                        Log.d("CommunityMain>>>", "2: " + response.errorBody().toString());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserModel> call, Throwable t) {
//                    Log.d("CommunityMain>>>", "3: " + t.toString());
//                }
//            });
//        }
//    });
//
//    Button test12 = findViewById(R.id.community_main_firestore_test12);
//        test12.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            UserModel userModel = new UserModel();
//            userModel.setNickname("changeNick");
//            RetrofitClient.getApiService().updateUser(auth.getCurrentUser().getUid(), userModel).enqueue(new Callback<UserModel>() {
//                @Override
//                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                    if (response.isSuccessful()) {
//                        Log.d("CommunityMain>>>", "1: " + response.message());
//                    } else {
//                        Log.d("CommunityMain>>>", "2: " + response.message());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserModel> call, Throwable t) {
//                    Log.d("CommunityMain>>>", "3: " + t.getMessage());
//                }
//            });
//        }
//    });
//
//    Button test13 = findViewById(R.id.community_main_firestore_test13);
//        test13.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Call<String> call;
//            call = RetrofitClient.getApiService().deleteUser("testuid2testuid2testuid2");
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    if (response.isSuccessful()) {
//                        Log.d("CommunityMain>>>", "1: " + response.message());
//                    } else {
//                        Log.d("CommunityMain>>>", "2: " + response.message());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    Log.d("CommunityMain>>>", "failed1: " + call.toString());
//                    Log.d("CommunityMain>>>", "failed2: " + t.toString());
//                }
//            });
//        }
//    });
//
//    Button test14 = findViewById(R.id.community_main_firestore_test14);
//        test14.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Call<List<UserModel>> call;
//            call = RetrofitClient.getApiService().searchNickname("changeNick");
//            call.enqueue(new Callback<List<UserModel>>() {
//                @Override
//                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
//                    if (response.isSuccessful()) {
//                        Log.d("CommunityMain>>>", "size: " + response.body().size());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<UserModel>> call, Throwable t) {
//                    Log.d("CommunityMain>>>", "3: " + t.getMessage());
//                }
//            });
//        }
//    });
//
//    Button test15 = findViewById(R.id.community_main_firestore_test15);
//        test15.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Call<List<UserModel>> call;
//            call = RetrofitClient.getApiService().searchNickname("changeNicka");
//            call.enqueue(new Callback<List<UserModel>>() {
//                @Override
//                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
//                    if (response.isSuccessful()) {
//                        Log.d("CommunityMain>>>", "size: " + response.body().size());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<UserModel>> call, Throwable t) {
//                    Log.d("CommunityMain>>>", "3: " + t.getMessage());
//                }
//            });
//        }
//    });
}
