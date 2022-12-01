package com.dippola.relaxtour.community.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.auth.CommunityAuth;
import com.dippola.relaxtour.community.main.write.CommunityWrite;
import com.dippola.relaxtour.community.signIn.CommunityProfileCreate;
import com.dippola.relaxtour.community.signIn.CommunitySignIn;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.MainModelView;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
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

public class CommunityMain extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    private FirebaseAuth auth;
    private ImageView authicon;
    private ProgressBar iconload;
    public static RecyclerView recyclerView;
    public static ShimmerFrameLayout itemload;
    public static ConstraintLayout pagebox;
    private FloatingActionButton fabmain;
    private ConstraintLayout fab1, fab2;

    private RelativeLayout fbg;

    private MainAdapter adapter;

    public static final int FROM_SIGNIN = 105;
    public static final int FROM_CREATE_PROFILE = 106;
    public static final int FROM_AUTH = 107;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main);

        auth = FirebaseAuth.getInstance();
        databaseHandler = new DatabaseHandler(CommunityMain.this);

        setInit();
        setImageAuthIcon();
        onClickAuth();
        loadCommunity();
        Animation show = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.fab_open);
        Animation close = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.fab_close);
        onClickFloating(show, close);
    }

    private void setInit() {
        authicon = findViewById(R.id.community_main_auth);
        iconload = findViewById(R.id.community_main_iconload);
        recyclerView = findViewById(R.id.community_main_recyclerview);
        recyclerView.setVisibility(View.INVISIBLE);
        itemload = findViewById(R.id.community_main_load_item);
        itemload.startShimmer();
        pagebox = findViewById(R.id.community_main_page_box);
        pagebox.setVisibility(View.GONE);

        fabmain = findViewById(R.id.community_main_fabmain);
        fab1 = findViewById(R.id.community_main_fab1);
        fab1.setVisibility(View.GONE);
        fab2 = findViewById(R.id.community_main_fab2);
        fab2.setVisibility(View.GONE);
        fbg = findViewById(R.id.community_main_floating_background);
        fbg.setVisibility(View.GONE);
    }

    private void onClickFloating(Animation show, Animation close) {
        show.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fab1.setVisibility(View.VISIBLE);
                fab2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        close.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fabmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fbg.getVisibility() == View.GONE) {
                    fab1.startAnimation(show);
                    fab2.startAnimation(show);
                    fbg.setVisibility(View.VISIBLE);
                } else {
                    fab1.startAnimation(close);
                    fab2.startAnimation(close);
                    fbg.setVisibility(View.GONE);
                }
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunityMain.this, CommunityWrite.class));
                fab1.startAnimation(close);
                fab2.startAnimation(close);
                fbg.setVisibility(View.GONE);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab1.startAnimation(close);
                fab2.startAnimation(close);
                fbg.setVisibility(View.GONE);
            }
        });
        fbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab1.startAnimation(close);
                fab2.startAnimation(close);
                fbg.setVisibility(View.GONE);
            }
        });
    }

    private void setImageAuthIcon() {
        if (auth.getCurrentUser() == null) {
            Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nulluser)).transform(new CircleCrop()).into(authicon);
            iconload.setVisibility(View.GONE);
        } else {
            if (databaseHandler.getUserModel().getImageurl() == null || databaseHandler.getUserModel().getImageurl().length() == 0 || databaseHandler.getUserModel().getImageurl().equals("null")) {
                Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                iconload.setVisibility(View.GONE);
            } else {
                Glide.with(CommunityMain.this).load(databaseHandler.getUserModel().getImageurl()).transform(new CircleCrop()).into(authicon);
                iconload.setVisibility(View.GONE);
            }
        }
    }

    private void onClickAuth() {
        authicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() == null) {
                    launcher.launch(new Intent(CommunityMain.this, CommunitySignIn.class));
                } else {
                    launcher.launch(new Intent(CommunityMain.this, CommunityAuth.class));
                }
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == FROM_SIGNIN) {//when sign up
                if (result.getData().getBooleanExtra("need_create_profile", false)) {
                    Intent intent = new Intent(CommunityMain.this, CommunityProfileCreate.class);
                    intent.putExtra("from", "main");
                    launcher.launch(intent);
                } else if (result.getData().getBooleanExtra("isSignIn", false)) {
                    iconload.setVisibility(View.VISIBLE);
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService().getUser(new DatabaseHandler(CommunityMain.this).getUserModel().getId());
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getImageurl().length() != 0) {
                                    Glide.with(CommunityMain.this).load(response.body().get(0).getImageurl()).transform(new CircleCrop()).into(authicon);
                                } else {
                                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                                }
                                iconload.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                }
            } else if (result.getResultCode() == FROM_CREATE_PROFILE) {
                if (result.getData().getBooleanExtra("isCreatePic", false)) {
                    iconload.setVisibility(View.VISIBLE);
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService().getUser(new DatabaseHandler(CommunityMain.this).getUserModel().getId());
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getImageurl().length() != 0) {
                                    Glide.with(CommunityMain.this).load(response.body().get(0).getImageurl()).transform(new CircleCrop()).into(authicon);
                                } else {
                                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                                }
                                iconload.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                } else {
                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                }
            } else if (result.getResultCode() == FROM_AUTH) {
                if (result.getData().getBooleanExtra("isChangePic", false)) {
                    iconload.setVisibility(View.VISIBLE);
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService().getUser(new DatabaseHandler(CommunityMain.this).getUserModel().getId());
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getImageurl().length() != 0) {
                                    Glide.with(CommunityMain.this).load(response.body().get(0).getImageurl()).transform(new CircleCrop()).into(authicon);
                                } else {
                                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                                }
                                iconload.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                }
                if (result.getData().getBooleanExtra("isSignout", false)) {
                    if (auth.getCurrentUser() == null) {
                        iconload.setVisibility(View.VISIBLE);
                        Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nulluser)).transform(new CircleCrop()).into(authicon);
                        iconload.setVisibility(View.GONE);
                    }
                }
                if (result.getData().getBooleanExtra("isDeleteUser", false)) {
                    if (auth.getCurrentUser() == null) {
                        iconload.setVisibility(View.VISIBLE);
                        Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nulluser)).transform(new CircleCrop()).into(authicon);
                        iconload.setVisibility(View.GONE);
                    }
                }
            }
        }
    });


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (auth.getCurrentUser() != null) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("CommunityLogin>>>", "firebaseAuthWithGoogle:" + account.getId());
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
                                        Log.d("CommunityMain>>>", "firestore delete user successed");
                                        FirebaseStorage.getInstance().getReference().child("userimages/kmj654649@gmail.coma").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                            @Override
                                            public void onSuccess(ListResult listResult) {
                                                if (listResult.getItems().size() != 0) {
                                                    for (StorageReference storageReference : listResult.getItems()) {
                                                        Log.d("CommunityMain>>>", "filename: " + storageReference.getName());
                                                        storageReference.delete();
                                                    }
                                                } else {
                                                    Log.d("CommunityMain>>>", "size0");
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
                                                Toast.makeText(CommunityMain.this, "delete success", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Log.d("CommunityMain>>>", "2: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("CommunityMain>>>", "user delete user failed");
                                }
                            });
                        }
                    });
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("CommunityLogin>>>", "Google sign in failed", e);
                }
            } else {
                Toast.makeText(CommunityMain.this, "auth null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadCommunity() {
        List<MainModelView> lists = new ArrayList<>();
        RetrofitClient.getApiService().getMainPage(1).enqueue(new Callback<List<MainModelView>>() {
            @Override
            public void onResponse(Call<List<MainModelView>> call, Response<List<MainModelView>> response) {
                if (response.isSuccessful()) {
                    lists.addAll(response.body());
                    setRecyclerView(lists);
                    finishedLoad();
                }
            }

            @Override
            public void onFailure(Call<List<MainModelView>> call, Throwable t) {

            }
        });
    }

    private void setRecyclerView(List<MainModelView> lists) {
        adapter = new MainAdapter(CommunityMain.this, lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommunityMain.this));
        recyclerView.setAdapter(adapter);
    }

    private void startLoad() {
        itemload.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        pagebox.setVisibility(View.INVISIBLE);
    }

    private void finishedLoad() {
        recyclerView.setVisibility(View.VISIBLE);
        pagebox.setVisibility(View.VISIBLE);
        itemload.setVisibility(View.GONE);
    }

}
