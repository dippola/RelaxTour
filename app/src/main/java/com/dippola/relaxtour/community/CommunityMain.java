package com.dippola.relaxtour.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.auth.CommunityAuth;
import com.dippola.relaxtour.community.signIn.CommunityProfileCreate;
import com.dippola.relaxtour.community.signIn.CommunitySignIn;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityMain extends AppCompatActivity {

    private RelativeLayout load;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ImageView authicon;
    private ProgressBar iconload;

    //test
    private Button gosignin;

    private DatabaseHandler databaseHandler;

    public static final int FROM_SIGNIN = 105;
    public static final int FROM_CREATE_PROFILE = 106;
    public static final int FROM_AUTH = 107;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setDatabaseHandler();

        setInit();
        setImageAuthIcon();
        onClickAuth();

        goToSignInButton();

//        checkPremium();
//        checkAuth();
        test();
    }

    private void setInit() {
        load = findViewById(R.id.community_main_load);
        load.setVisibility(View.VISIBLE);
        authicon = findViewById(R.id.community_main_auth);
        iconload = findViewById(R.id.community_main_iconload);
    }

    private void setImageAuthIcon() {
        if (auth.getCurrentUser() == null) {
            Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nulluser)).transform(new CircleCrop()).into(authicon);
            iconload.setVisibility(View.GONE);
        } else {
            Call<List<UserModel>> call;
            call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
            call.enqueue(new Callback<List<UserModel>>() {
                @Override
                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().get(0).getImageurl().length() != 0) {
                            Log.d("CommunityMain>>>", "1");
                            Glide.with(CommunityMain.this).load(response.body().get(0).getImageurl()).transform(new CircleCrop()).into(authicon);
                        } else {
                            Log.d("CommunityMain>>>", "2");
                            Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                        }
                        iconload.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<UserModel>> call, Throwable t) {
                    Log.d("CommunityMain>>>", "failed1: " + call.toString());
                    Log.d("CommunityMain>>>", "failed2: " + t.toString());
                }
            });
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
                    call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
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
                    call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
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
                    call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
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

    private void setDatabaseHandler() {
        databaseHandler.setDB(CommunityMain.this);
        databaseHandler = new DatabaseHandler(CommunityMain.this);
    }

    private void goToSignInButton() {
        gosignin = findViewById(R.id.community_main_go_to_signin);
        gosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch(new Intent(CommunityMain.this, CommunitySignIn.class));
            }
        });
    }

    private void test() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button test1;
        test1 = findViewById(R.id.community_main_firestore_test1);
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                iconload.setVisibility(View.VISIBLE);
                if (auth.getCurrentUser() != null) {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(CommunityMain.this, gso);
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 111);
                }
            }
        });

        Button test2;
        test2 = findViewById(R.id.community_main_firestore_test2);
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    db.collection("users").document(auth.getCurrentUser().getEmail()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            AuthCredential authCredential = EmailAuthProvider.getCredential("kmj654649@gmail.com", "shangus12!");
                            auth.getCurrentUser().reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CommunityMain.this, "success", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });

        Button test3;
        test3 = findViewById(R.id.community_main_firestore_test3);
        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(CommunityMain.this, gso);
                googleSignInClient.signOut();
                String bo;
                if (auth.getCurrentUser() == null) {
                    bo = "null";
                } else {
                    bo = "have";
                }
                Toast.makeText(CommunityMain.this, "auth is: " + bo, Toast.LENGTH_SHORT).show();
            }
        });

        Button test4;
        test4 = findViewById(R.id.community_main_firestore_test4);
        test4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(CommunityMain.this, gso);
                googleSignInClient.signOut();
            }
        });

        Button test5;
        test5 = findViewById(R.id.community_main_firestore_test5);
        test5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch(new Intent(CommunityMain.this, CommunityProfileCreate.class));
            }
        });

        Button test6;
        test6 = findViewById(R.id.community_main_firestore_test6);
        test6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button test7;
        test7 = findViewById(R.id.community_main_firestore_test7);
        test7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage.getInstance().getReference().child("userimages/kmj654649@gmail.coma").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        if (listResult.getItems().size() != 0) {
                            for(StorageReference storageReference : listResult.getItems()) {
                                Log.d("CommunityMain>>>", "filename: " + storageReference.getName());
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
            }
        });

        Button test8;
        test8 = findViewById(R.id.community_main_firestore_test8);
        test8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document("kmj654649@gmail.coma").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                Log.d("CommunityMain>>>", "1");
                            } else {
                                Log.d("CommunityMain>>>", "2");
                            }
                        }
                    }
                });
            }
        });

        Button test9;
        test9 = findViewById(R.id.community_main_firestore_test9);
        test9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document("kmj654649@gmail.com").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                Log.d("CommunityMain>>>", "1");
                            } else {
                                Log.d("CommunityMain>>>", "2");
                            }
                        }
                    }
                });
            }
        });

        Button test10;
        test10 = findViewById(R.id.community_main_firestore_test10);
        test10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<UserModel>> call;
                call = RetrofitClient.getApiService().getUser(auth.getCurrentUser().getUid());
                call.enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        List<UserModel> model = response.body();
                        Log.d("CommunityMain>>>", "get: " + model.get(0).getNickname());
                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {
                        Log.d("CommunityMain>>>", "failed1: " + call.toString());
                        Log.d("CommunityMain>>>", "failed2: " + t.toString());
                    }
                });
            }
        });

        Button test11;
        test11 = findViewById(R.id.community_main_firestore_test11);
        test11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel();
                userModel.setEmail("kmj654649@gmail.com");
                userModel.setImageurl("");
                userModel.setNickname("nickname2");
                userModel.setUid("testuid2testuid2testuid2");
                RetrofitClient.getApiService().createUser(userModel).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "1");
                        } else {
                            Log.d("CommunityMain>>>", "2: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.toString());
                    }
                });
            }
        });

        Button test12 = findViewById(R.id.community_main_firestore_test12);
        test12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel();
                userModel.setEmail("dippolas@gmail.com");
                userModel.setUid("v5agRuBuyibhqwXHuazrTyIXU3y2");
                userModel.setNickname("changeNick");
                userModel.setImageurl("");
                RetrofitClient.getApiService().updateUser(auth.getCurrentUser().getUid(), userModel).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "1: " + response.message());
                        } else {
                            Log.d("CommunityMain>>>", "2: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.getMessage());
                    }
                });
            }
        });

        Button test13 = findViewById(R.id.community_main_firestore_test13);
        test13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String> call;
                call = RetrofitClient.getApiService().deleteUser("testuid2testuid2testuid2");
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "1: " + response.message());
                        } else {
                            Log.d("CommunityMain>>>", "2: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("CommunityMain>>>", "failed1: " + call.toString());
                        Log.d("CommunityMain>>>", "failed2: " + t.toString());
                    }
                });
            }
        });

        Button test14 = findViewById(R.id.community_main_firestore_test14);
        test14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<UserModel>> call;
                call = RetrofitClient.getApiService().searchNickname("changeNick");
                call.enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "size: " + response.body().size());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.getMessage());
                    }
                });
            }
        });

        Button test15 = findViewById(R.id.community_main_firestore_test15);
        test15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<UserModel>> call;
                call = RetrofitClient.getApiService().searchNickname("changeNicka");
                call.enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "size: " + response.body().size());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.getMessage());
                    }
                });
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            load.setVisibility(View.GONE);
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
                                                    for(StorageReference storageReference : listResult.getItems()) {
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

//    private void checkPremium() {
//        Qonversion.checkPermissions(new QonversionPermissionsCallback() {
//            @Override
//            public void onSuccess(@NonNull Map<String, QPermission> map) {
//                QPermission qPermission = map.get("dippola_relaxtour_premium");
//                if (qPermission != null && qPermission.isActive()) {
//                    Log.d("CommunityMain>>>", "have permission");
//                    checkAuth();
//                } else {
//                    Log.d("CommunityMain>>>", "null permission: ");
//                    finish();
//                    Toast.makeText(CommunityMain.this, "The community is only available to premium users.", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(CommunityMain.this, Premium.class));
//                }
//            }
//
//            @Override
//            public void onError(@NonNull QonversionError qonversionError) {
//                Log.d("CommunityMain>>>", "qper error: " + qonversionError);
//                Toast.makeText(CommunityMain.this, "Load failed with an error.\nError: " + qonversionError, Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }
//
//    private void checkAuth() {
//        if (auth.getCurrentUser() == null) {
//            startActivity(new Intent(CommunityMain.this, CommunitySignIn.class));
//        }
//        load.setVisibility(View.GONE);
//        loadCommunity();
//    }

    private void loadCommunity() {

    }
}
