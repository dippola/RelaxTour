package com.dippola.relaxtour.community.admin.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminChangeProfile extends AppCompatActivity {

    ImageView img;
    CheckBox checkBox;
    EditText editText;
    Button button;
    ProgressBar load;
    TextView error;

    int userid;
    String why;
    String userEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_change_profile);

        img = findViewById(R.id.admin_change_profile_img);
        checkBox = findViewById(R.id.admin_change_profile_checkbox);
        editText = findViewById(R.id.admin_change_profile_edittext);
        button = findViewById(R.id.admin_change_profile_button);
        load = findViewById(R.id.admin_change_profile_load);
        load.setVisibility(View.INVISIBLE);
        error = findViewById(R.id.admin_change_profile_error);

        userid = getIntent().getIntExtra("userid", 0);
        why = getIntent().getStringExtra("why");

        setDate(userid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load.setVisibility(View.VISIBLE);
                changeProfile1();
            }
        });
    }

    private void setDate(int userid) {
        RetrofitClient.getApiService(AdminChangeProfile.this).getUser(userid, getString(R.string.appkey)).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    Glide.with(img.getContext()).load(response.body().get(0).getImageurl()).into(img);
                    editText.setText(response.body().get(0).getNickname());
                    userEmail = response.body().get(0).getEmail();
                } else {
                    error.setText(response.message());
                    load.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                error.setText(t.getMessage());
                load.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void changeProfile1() {
        if (checkBox.isChecked()) {
            FirebaseStorage.getInstance().getReference().child("userimages/" + userEmail).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    if (listResult.getItems().size() != 0) {
                        for (StorageReference storageReference : listResult.getItems()) {
                            storageReference.delete();
                            error.setText("삭제성공");
                        }
                        changeProfile2();
                    }
                }
            });
        } else {
            changeProfile2();
        }
    }

    private void changeProfile2() {
        UserModel userModel = new UserModel();
        if (checkBox.isChecked()) {
            userModel.setImageurl("");
        }
        userModel.setNickname(editText.getText().toString());
        RetrofitClient.getApiService(AdminChangeProfile.this).adminUserUpdate(userid, userModel, why, getString(R.string.appkey)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminChangeProfile.this, "성공", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    error.setText(response.message());
                    load.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                error.setText(t.getMessage());
                load.setVisibility(View.INVISIBLE);
            }
        });
    }
}
