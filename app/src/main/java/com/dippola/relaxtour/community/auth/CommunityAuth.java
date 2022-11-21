package com.dippola.relaxtour.community.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.CommunityMain;
import com.dippola.relaxtour.community.Test;
import com.dippola.relaxtour.community.ImageViewer;
import com.dippola.relaxtour.community.signIn.CommunityProfileCreate;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityAuth extends AppCompatActivity {

    public static final int FROM_CHANGE_PROFILE = 100;
    public static final int FROM_CREATE_PROFILE = 99;
    public static final int FROM_ASK_SIGNOUT = 98;
    public static final int FROM_DELETE_ACCOUNT = 97;
    public static final int FROM_GOOGLE_CLIENT = 96;

    private ImageView img, provicerIcon;
    private TextView nickname, email;
    private FirebaseAuth auth;
    private RelativeLayout load;
    private Button back, editprofile;
    private ProgressBar imgload;
    private ConstraintLayout signout, deleteaccount, findPassword;
    private boolean isChangePic;
    private String provider;
    private String imageurl;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_auth);

        databaseHandler = new DatabaseHandler(CommunityAuth.this);

        setInit();
        onClickEditProfile();
        setBack();
        onClickSignOut();
        onClickDeleteAccount();
        onClickFindPassword();
    }

    private void setInit() {
        auth = FirebaseAuth.getInstance();
        img = findViewById(R.id.community_auth_img);
        nickname = findViewById(R.id.community_auth_nickname);
        email = findViewById(R.id.community_auth_email);
        back = findViewById(R.id.community_auth_goback);
        editprofile = findViewById(R.id.community_auth_change_profile);
        imgload = findViewById(R.id.community_auth_img_progressbar);
        load = findViewById(R.id.community_auth_load);
        load.setVisibility(View.GONE);
        signout = findViewById(R.id.community_auth_sign_out);
        deleteaccount = findViewById(R.id.community_auth_delete_account);
        provicerIcon = findViewById(R.id.community_auth_email_icon);
        findPassword = findViewById(R.id.community_auth_find_password_in_auth);
        setProfile();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAuth.this, ImageViewer.class);
                if (imageurl != null) {
                    intent.putExtra("url", imageurl);
                }
                startActivity(intent);
            }
        });
    }

    private void setBack() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAuth.this, CommunityMain.class);
                intent.putExtra("isChangePic", isChangePic);
                setResult(CommunityMain.FROM_AUTH, intent);
                finish();
            }
        });
    }

    private void onClickFindPassword() {
        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAuth.this, CommunityAuthResetPasswordDialog.class);
                intent.putExtra("from", "auth");
                intent.putExtra("email", auth.getCurrentUser().getEmail());
                launcher.launch(intent);
            }
        });
    }

    private void onClickSignOut() {
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAuth.this, CommunityAskSignOutDialog.class);
                intent.putExtra("email", auth.getCurrentUser().getEmail().toString());
                intent.putExtra("provider", provider);
                launcher.launch(intent);
            }
        });
    }

    private void onClickDeleteAccount() {
        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<UserModel>> call;
                call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
                call.enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(CommunityAuth.this, CommunityAskDeleteAccountDialog.class);
                            intent.putExtra("email", auth.getCurrentUser().getEmail().toString());
                            intent.putExtra("provider", response.body().get(0).getProvider());
                            launcher.launch(intent);
                            load.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void setProfile() {
//        Call<List<UserModel>> call;
//        call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
//        call.enqueue(new Callback<List<UserModel>>() {
//            @Override
//            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().get(0).getImageurl().length() != 0) {
//                        imageurl = response.body().get(0).getImageurl();
//                        Glide.with(CommunityAuth.this).load(response.body().get(0).getImageurl()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
//                    } else {
//                        Glide.with(CommunityAuth.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
//                    }
//                    if (response.body().get(0).getNickname().length() != 0) {
//                        nickname.setText(response.body().get(0).getNickname());
//                    } else {
//                        nickname.setText("nickname not set");
//                    }
//                    if (response.body().get(0).getProvider().equals("Google")) {
//                        findPassword.setVisibility(View.GONE);
//                        findPassword.setEnabled(false);
//                        provicerIcon.setBackground(getResources().getDrawable(R.drawable.google_white_icon));
//                    } else {
//                        findPassword.setVisibility(View.VISIBLE);
//                        provicerIcon.setBackground(getResources().getDrawable(R.drawable.community_auth_email_icon));
//                    }
//                    provider = response.body().get(0).getProvider();
//                    email.setText(auth.getCurrentUser().getEmail());
//                    imgload.setVisibility(View.GONE);
//                    load.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<UserModel>> call, Throwable t) {
//
//            }
//        });

        UserModel userModel = new UserModel();
        userModel = databaseHandler.getUserModel();
        if (userModel.getImageurl() == null) {
            Glide.with(CommunityAuth.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
        } else {
            Glide.with(CommunityAuth.this).load(userModel.getImageurl()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
        }
        if (userModel.getNickname() == null) {
            nickname.setText("nickname not set");
        } else {
            nickname.setText(userModel.getNickname());
        }
        if (userModel.getProvider().equals("Google")) {
            findPassword.setVisibility(View.GONE);
            findPassword.setEnabled(false);
            provicerIcon.setBackground(getResources().getDrawable(R.drawable.google_white_icon));
        } else {
            findPassword.setVisibility(View.VISIBLE);
            provicerIcon.setBackground(getResources().getDrawable(R.drawable.community_auth_email_icon));
        }
        email.setText(userModel.getEmail());
    }

    private void onClickEditProfile() {
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load.setVisibility(View.VISIBLE);

                if (nickname.getText().toString().equals("nickname not set")) {
                    Intent intent = new Intent(CommunityAuth.this, CommunityProfileCreate.class);
                    intent.putExtra("from", "auth");
                    launcher.launch(intent);
                    load.setVisibility(View.GONE);
                } else {
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getNickname().length() != 0) {
                                    launcher.launch(new Intent(CommunityAuth.this, CommunityProfileChange.class));
                                    load.setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == FROM_CHANGE_PROFILE) {
                if (result.getData().getBooleanExtra("isChangePic", false)) {
                    imgload.setVisibility(View.VISIBLE);
                    isChangePic = true;
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getImageurl().length() != 0) {
                                    imageurl = response.body().get(0).getImageurl();
                                    Glide.with(CommunityAuth.this).load(response.body().get(0).getImageurl()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                } else {
                                    imageurl = null;
                                    Glide.with(CommunityAuth.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                }
                                imgload.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {
                            Toast.makeText(CommunityAuth.this, "Image load error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if (result.getData().getBooleanExtra("isChangeNickname", false)) {
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                nickname.setText(response.body().get(0).getNickname());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {
                            Toast.makeText(CommunityAuth.this, "Nickname load error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (result.getResultCode() == FROM_CREATE_PROFILE) {
                if (result.getData().getBooleanExtra("isCreate", false)) {
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                nickname.setText(response.body().get(0).getNickname());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {
                            Toast.makeText(CommunityAuth.this, "Nickname load error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if (result.getData().getBooleanExtra("isCreatePic", false)) {
                    imgload.setVisibility(View.VISIBLE);
                    isChangePic = true;
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getImageurl().length() != 0) {
                                    imageurl = response.body().get(0).getImageurl();
                                    Glide.with(CommunityAuth.this).load(response.body().get(0).getImageurl()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                } else {
                                    Glide.with(CommunityAuth.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                }
                                imgload.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {
                            Toast.makeText(CommunityAuth.this, "Image load error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (result.getResultCode() == FROM_ASK_SIGNOUT) {
                if (result.getData().getBooleanExtra("willSignout", false)) {
                    load.setVisibility(View.VISIBLE);
                    auth.signOut();
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(CommunityAuth.this, gso);
                    googleSignInClient.signOut();
                    DatabaseHandler databaseHandler = new DatabaseHandler(CommunityAuth.this);
                    databaseHandler.deleteUserProfile();
                    Toast.makeText(CommunityAuth.this, "Signed Out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CommunityAuth.this, CommunityMain.class);
                    intent.putExtra("isSignout", true);
                    setResult(CommunityMain.FROM_AUTH, intent);
                    finish();
                }
            } else if (result.getResultCode() == FROM_DELETE_ACCOUNT) {
                if (result.getData().getBooleanExtra("willDelete", false)) {
                    load.setVisibility(View.VISIBLE);
                    if (result.getData().getStringExtra("provider").equals("email")) {
                        deleteEmailUser(result.getData().getStringExtra("password"));
                    } else {
                        deleteGoogleUser();
                    }
                }
            }
        }
    });

    private void deleteEmailUser(String password) {
        AuthCredential authCredential = EmailAuthProvider.getCredential(auth.getCurrentUser().getEmail(), password);
        auth.getCurrentUser().reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Call<String> call;
                call = RetrofitClient.getApiService().deleteUser(auth.getCurrentUser().getUid());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            FirebaseStorage.getInstance().getReference().child("userimages/" + auth.getCurrentUser().getEmail()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                @Override
                                public void onSuccess(ListResult listResult) {
                                    if (listResult.getItems().size() != 0) {
                                        for (StorageReference storageReference : listResult.getItems()) {
                                            storageReference.delete();
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("CommunityMain>>>", "error: " + e.getMessage());
                                }
                            });

                            auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    DatabaseHandler databaseHandler = new DatabaseHandler(CommunityAuth.this);
                                    databaseHandler.deleteUserProfile();
                                    Toast.makeText(CommunityAuth.this, "delete success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CommunityAuth.this, CommunityMain.class);
                                    intent.putExtra("isDeleteUser", true);
                                    setResult(CommunityMain.FROM_AUTH, intent);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("CommunityMain>>>", "onFailure");
            }
        });
    }

    private void deleteGoogleUser() {
        if (auth.getCurrentUser() != null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(CommunityAuth.this, gso);
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, FROM_GOOGLE_CLIENT);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_GOOGLE_CLIENT) {
            load.setVisibility(View.VISIBLE);
            if (auth.getCurrentUser() != null) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    auth.getCurrentUser().reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Call<String> call;
                            call = RetrofitClient.getApiService().deleteUser(auth.getCurrentUser().getUid());
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.isSuccessful()) {
                                        FirebaseStorage.getInstance().getReference().child("userimages/" + auth.getCurrentUser().getEmail()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                            @Override
                                            public void onSuccess(ListResult listResult) {
                                                if (listResult.getItems().size() != 0) {
                                                    for (StorageReference storageReference : listResult.getItems()) {
                                                        storageReference.delete();
                                                    }
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("CommunityMain>>>", "error: " + e.getMessage());
                                            }
                                        });

                                        auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                DatabaseHandler databaseHandler = new DatabaseHandler(CommunityAuth.this);
                                                databaseHandler.deleteUserProfile();
                                                Toast.makeText(CommunityAuth.this, "delete success", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(CommunityAuth.this, CommunityMain.class);
                                                intent.putExtra("isDeleteUser", true);
                                                setResult(CommunityMain.FROM_AUTH, intent);
                                                finish();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        }
                    });
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.d("CommunityLogin>>>", "Google sign in failed", e);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (load.getVisibility() == View.GONE) {
            Intent intent = new Intent(CommunityAuth.this, CommunityMain.class);
            intent.putExtra("isChangePic", isChangePic);
            setResult(CommunityMain.FROM_AUTH, intent);
            super.onBackPressed();
        }
    }

    private void test() {
        TextView test = findViewById(R.id.community_auth_title);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunityAuth.this, Test.class));
            }
        });
    }
}
