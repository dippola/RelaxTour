package com.dippola.relaxtour;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        RetrofitClient.getApiService().getUser(18).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    Log.d("UtilTest>>>", "1: " + response);
                    Log.d("UtilTest>>>", "response: " + response);
                } else {
                    Log.d("UtilTest>>>", "2: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Log.d("UtilTest>>>", "3: " + t.getMessage());
            }
        });
    }
}