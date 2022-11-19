package com.dippola.relaxtour.community.signIn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.CommunityMain;
import com.dippola.relaxtour.community.auth.CommunityAuth;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityProfileCreate extends AppCompatActivity {

    private ImageView img;
    private TextView addPic, count, skip, error;
    private Button deletePic, ok;
    private EditText editNickname;
    private RelativeLayout load;
    private Uri imageUri;
    private FirebaseAuth auth;
    private String from;
    private boolean isCreate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_profile_create);

        setInit();
        getFrom();
        setImage();
        onClickSkip();
        onClickAddPic();
        onClickOk();
        onClickDeletePic();
        editTextSetCount();
    }

    private void setInit() {
        auth = FirebaseAuth.getInstance();
        img = findViewById(R.id.community_profile_create_img);
        addPic = findViewById(R.id.community_profile_create_photobtn);
        count = findViewById(R.id.community_profile_create_count);
        skip = findViewById(R.id.community_profile_create_skip);
        deletePic = findViewById(R.id.community_profile_create_cancel_img);
        ok = findViewById(R.id.community_profile_create_okbtn);
        editNickname = findViewById(R.id.community_profile_create_edit_nickname);
        error = findViewById(R.id.community_profile_create_errortext);
        load = findViewById(R.id.community_profile_create_load);
        load.setVisibility(View.GONE);
    }

    private void getFrom() {
        from = getIntent().getStringExtra("from");
    }

    private void onClickDeletePic() {
        deletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUri = null;
                Glide.with(CommunityProfileCreate.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(100)).into(img);
            }
        });
    }

    private void setImage() {
        Glide.with(CommunityProfileCreate.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(100)).into(img);
    }

    private void onClickAddPic() {
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            String scheme = imageUri.getScheme();
            Log.d("ProfileCreate>>>", "scheme type: " + scheme);
            try {
                InputStream fileInputStream=getApplicationContext().getContentResolver().openInputStream(imageUri);
                int dataSize = fileInputStream.available();
                Log.d("ProfileCreate>>>", "size: " + dataSize);
                if (dataSize < 5242880) {
                    Glide.with(CommunityProfileCreate.this).load(imageUri).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                } else {
                    imageUri = null;
                    Toast.makeText(CommunityProfileCreate.this, "Maximum image capacity available for selection is 5 MB", Toast.LENGTH_SHORT).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onClickOk() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                error.setText("");
                editNickname.setBackground(getResources().getDrawable(R.drawable.edittext));
                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                if (editNickname.length() == 0) {
                    error.setText("Please enter your nickname.");
                    editNickname.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                } else if (!ps.matcher(editNickname.getText().toString()).matches()) {
                    error.setText("Only alphanumeric characters are allowed.");
                    editNickname.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                } else {
                    if (editNickname.length() < 4) {
                        error.setText("Nickname must be at least 4 digits.");
                        editNickname.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    } else if (editNickname.length() > 14) {
                        error.setText("Nickname Must be less than 14 digits.");
                        editNickname.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    } else {
                        if (auth.getCurrentUser() != null) {
                            load.setVisibility(View.VISIBLE);
                            checkNicknameAready();
                        }
                    }
                }
            }
        });
    }

    private void checkNicknameAready() {
        Call<List<UserModel>> call;
        call = RetrofitClient.getApiService().searchNickname(editNickname.getText().toString());
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {
                        error.setText("This nickname is already registered. Please enter a different nickname.");
                        load.setVisibility(View.GONE);
                    } else {
                        uploadPic();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

    private void editTextSetCount() {
        editNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                count.setText(editNickname.getText().toString().length() + " / 14");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void uploadPic() {
        if (imageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("userimages/" + auth.getCurrentUser().getEmail() + "/" + auth.getCurrentUser().getEmail() + "1");
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            updateUserInServer(uri.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("CommunityMain>>>", "failed");
                    Toast.makeText(CommunityProfileCreate.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            updateUserInServer(null);
        }
    }

    private void updateUserInServer(String uri) {
        UserModel userModel = new UserModel();
        userModel.setNickname(editNickname.getText().toString());
        if (uri != null && uri.length() != 0) {
            userModel.setImageurl(uri);
        }
        RetrofitClient.getApiService().updateUser(auth.getCurrentUser().getUid(), userModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    DatabaseHandler databaseHandler = new DatabaseHandler(CommunityProfileCreate.this);
                    databaseHandler.updateUserProfile(editNickname.getText().toString(), uri, auth.getCurrentUser().getUid());
                    isCreate = true;
                    Log.d("ChangeProfile>>>", "message: " + response.message());
                    goToBack();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(CommunityProfileCreate.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                FirebaseStorage.getInstance().getReference().listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        if (listResult.getItems().size() != 0) {
                            for(StorageReference storageReference : listResult.getItems()) {
                                FirebaseStorage.getInstance().getReference().child("userimages/" + auth.getCurrentUser().getEmail() + "/" + storageReference.getName()).delete();
                            }
                        }
                        goToBack();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CommunityProfileCreate.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void onClickSkip() {
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityProfileCreate.this, CommunityMain.class);
                intent.putExtra("isCreate", false);
                setResult(CommunityMain.FROM_CREATE_PROFILE, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (load.getVisibility() == View.GONE) {
            super.onBackPressed();
        }
    }

    private void hideKeyboard(View v) {
        InputMethodManager manager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void goToBack() {
        if (from.equals("main")) {
            Toast.makeText(CommunityProfileCreate.this, "Create Profile Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CommunityProfileCreate.this, CommunityMain.class);
            if (imageUri != null) {
                intent.putExtra("isCreatePic", true);
            } else {
                intent.putExtra("isCreatePic", false);
            }
            setResult(CommunityMain.FROM_CREATE_PROFILE, intent);
            finish();
        } else if (from.equals("auth")) {
            Toast.makeText(CommunityProfileCreate.this, "Create Profile Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CommunityProfileCreate.this, CommunityAuth.class);
            if (imageUri != null) {
                intent.putExtra("isCreatePic", true);
            } else {
                intent.putExtra("isCreatePic", false);
            }
            intent.putExtra("isCreate", isCreate);
            setResult(CommunityAuth.FROM_CREATE_PROFILE, intent);
            finish();
        }
    }
}
