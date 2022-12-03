package com.dippola.relaxtour.community.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityProfileChange extends AppCompatActivity {

    private ImageView img;
    private TextView addPic, count, error;
    private Button deletePic, ok, back;
    private EditText editNickname;
    private RelativeLayout load;
    private Uri imageUri;
    private FirebaseAuth auth;
    private String beforeNickname;
    private boolean isChangePic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_profile_change);

        setInit();
        setUserData();
        onClickDeletePic();
        onClickAddPic();
        onClickOk();
        onClickBack();
    }

    private void setInit() {
        auth = FirebaseAuth.getInstance();
        img = findViewById(R.id.community_profile_change_img);
        addPic = findViewById(R.id.community_profile_change_photobtn);
        back = findViewById(R.id.community_profile_change_back);
        count = findViewById(R.id.community_profile_change_count);
        deletePic = findViewById(R.id.community_profile_change_cancel_img);
        ok = findViewById(R.id.community_profile_change_okbtn);
        editNickname = findViewById(R.id.community_profile_change_edit_nickname);
        error = findViewById(R.id.community_profile_change_errortext);
        load = findViewById(R.id.community_profile_change_load);
        load.setVisibility(View.GONE);
    }

    private void onClickBack() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUserData() {
        DatabaseHandler databaseHandler = new DatabaseHandler(CommunityProfileChange.this);
        UserModel userModel = new UserModel();
        userModel = databaseHandler.getUserModel();
        if (userModel.getImageurl() == null || userModel.getImageurl().length() == 0 || userModel.getImageurl().equals("null")) {
            Glide.with(CommunityProfileChange.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
        } else {
            Glide.with(CommunityProfileChange.this).load(userModel.getImageurl()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
        }
        if (userModel.getNickname() != null) {
            editNickname.setText(userModel.getNickname());
            beforeNickname = userModel.getNickname();
        }
    }

    private void onClickDeletePic() {
        deletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChangePic = true;
                imageUri = null;
                Glide.with(CommunityProfileChange.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(100)).into(img);
            }
        });
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
                    Glide.with(CommunityProfileChange.this).load(imageUri).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                } else {
                    imageUri = null;
                    Toast.makeText(CommunityProfileChange.this, "Maximum image capacity available for selection is 5 MB", Toast.LENGTH_SHORT).show();
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
                            if (!beforeNickname.equals(editNickname.getText().toString())) {
                                checkNicknameAready();
                            } else {
                                uploadPic1();
                            }
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
                        uploadPic1();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

    private void uploadPic1() {
        if (imageUri != null) {//add or change
            FirebaseStorage.getInstance().getReference().child("userimages/" + auth.getCurrentUser().getEmail()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    if (listResult.getItems().size() == 0) {
                        uploadPic2(null);
                    } else {
                        String itemname = "";
                        for (StorageReference storageReference : listResult.getItems()) {
                            itemname = storageReference.getName().toString();
                            storageReference.delete();
                        }
                        uploadPic2(itemname.substring(itemname.length() - 1));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {//no have or delete
            FirebaseStorage.getInstance().getReference().child("userimages/" + auth.getCurrentUser().getEmail()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    if (listResult.getItems().size() == 0) {
                        updateUserInServer(null);
                    } else {
                        for (StorageReference storageReference : listResult.getItems()) {
                            storageReference.delete();
                        }
                        updateUserInServer(null);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void uploadPic2(String lastword) {
        StorageReference storageReference;
        if (lastword == null || lastword.equals("1")) {
            storageReference = FirebaseStorage.getInstance().getReference().child("userimages/" + auth.getCurrentUser().getEmail() + "/" + auth.getCurrentUser().getEmail() + "2");
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
                    Toast.makeText(CommunityProfileChange.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (lastword.equals("2")) {
            storageReference = FirebaseStorage.getInstance().getReference().child("userimages/" + auth.getCurrentUser().getEmail() + "/" + auth.getCurrentUser().getEmail() + "1");
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
                    Toast.makeText(CommunityProfileChange.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateUserInServer(String uri) {
        UserModel userModel = new UserModel();
        userModel.setNickname(editNickname.getText().toString());
        if (uri != null && uri.length() != 0) {
            isChangePic = true;
            userModel.setImageurl(uri);
        } if (uri == null) {
            userModel.setImageurl("");
        }
        RetrofitClient.getApiService().updateUser(auth.getCurrentUser().getUid(), userModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    DatabaseHandler databaseHandler = new DatabaseHandler(CommunityProfileChange.this);
                    databaseHandler.updateUserProfile(editNickname.getText().toString(), uri, auth.getCurrentUser().getUid());
                    goToAuth();
                } else {
                    Log.d("CommunityMain>>>", "2: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(CommunityProfileChange.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                FirebaseStorage.getInstance().getReference().listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        if (listResult.getItems().size() != 0) {
                            for(StorageReference storageReference : listResult.getItems()) {
                                FirebaseStorage.getInstance().getReference().child("userimages/" + auth.getCurrentUser().getEmail() + "/" + storageReference.getName()).delete();
                            }
                        }
                        goToAuth();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CommunityProfileChange.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void goToAuth() {
        Toast.makeText(CommunityProfileChange.this, "Create Profile Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CommunityProfileChange.this, CommunityAuth.class);
        if (beforeNickname.equals(editNickname.getText().toString())) {
            intent.putExtra("isNicknameChange", false);
        } else {
            intent.putExtra("isNicknameChange", true);
        }
        intent.putExtra("isChangePic", isChangePic);
        setResult(CommunityAuth.FROM_CHANGE_PROFILE, intent);
        finish();
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
}
