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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.CommunityMain;
import com.dippola.relaxtour.community.ImageViewer;
import com.dippola.relaxtour.community.signIn.CommunityProfileCreate;
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

public class CommunityAuth extends AppCompatActivity {

    public static final int FROM_CHANGE_PROFILE = 100;
    public static final int FROM_CREATE_PROFILE = 99;
    public static final int FROM_ASK_SIGNOUT = 98;
    public static final int FROM_DELETE_ACCOUNT = 97;
    public static final int FROM_GOOGLE_CLIENT = 96;

    private ImageView img, provicerIcon;
    private TextView nickname, email;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private RelativeLayout load;
    private Button back, editprofile;
    private ProgressBar imgload;
    private ConstraintLayout signout, deleteaccount, findPassword;
    private boolean isChangePic;
    private String provider;
    private String imageurl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_auth);

        setInit();
        onClickEditProfile();
        setBack();
        onClickSignOut();
        onClickDeleteAccount();
        onClickFindPassword();
    }

    private void setInit() {
        db = FirebaseFirestore.getInstance();
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
                load.setVisibility(View.VISIBLE);
                db.collection("users").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(CommunityAuth.this, CommunityAskDeleteAccountDialog.class);
                            intent.putExtra("email", auth.getCurrentUser().getEmail().toString());
                            intent.putExtra("provider", task.getResult().get("provider").toString());
                            launcher.launch(intent);
                            load.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private void setProfile() {
        db.collection("users").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().get("imageurl") != null) {
                        imageurl = task.getResult().get("imageurl").toString();
                        Glide.with(CommunityAuth.this).load(task.getResult().get("imageurl").toString()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                    } else {
                        Glide.with(CommunityAuth.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                    }
                    if (task.getResult().get("nickname") != null) {
                        nickname.setText(task.getResult().get("nickname").toString());
                    } else {
                        nickname.setText("nickname not set");
                    }
                    if (task.getResult().get("provider").toString().equals("google")) {
                        findPassword.setVisibility(View.GONE);
                        findPassword.setEnabled(false);
                        provicerIcon.setBackground(getResources().getDrawable(R.drawable.google_white_icon));
                    } else {
                        findPassword.setVisibility(View.VISIBLE);
                        provicerIcon.setBackground(getResources().getDrawable(R.drawable.community_auth_email_icon));
                    }
                    provider = task.getResult().get("provider").toString();
                    email.setText(auth.getCurrentUser().getEmail());
                    imgload.setVisibility(View.GONE);
                    load.setVisibility(View.GONE);
                } else {
                    Toast.makeText(CommunityAuth.this, "Profile load failed due to unstable internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    db.collection("users").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().get("nickname") != null) {
                                    launcher.launch(new Intent(CommunityAuth.this, CommunityProfileChange.class));
                                    load.setVisibility(View.GONE);
                                }
                            }
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
                    db.collection("users").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().get("imageurl") != null) {
                                    imageurl = task.getResult().get("imageurl").toString();
                                    Glide.with(CommunityAuth.this).load(task.getResult().get("imageurl").toString()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                } else {
                                    imageurl = null;
                                    Glide.with(CommunityAuth.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                }
                                imgload.setVisibility(View.GONE);
                            }
                        }
                    });
                }

                if (result.getData().getBooleanExtra("isChangeNickname", false)) {
                    db.collection("users").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                nickname.setText(task.getResult().get("nickname").toString());
                            }
                        }
                    });
                }
            } else if (result.getResultCode() == FROM_CREATE_PROFILE) {
                if (result.getData().getBooleanExtra("isCreate", false)) {
                    db.collection("users").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                nickname.setText(task.getResult().get("nickname").toString());
                            }
                        }
                    });
                }

                if (result.getData().getBooleanExtra("isCreatePic", false)) {
                    imgload.setVisibility(View.VISIBLE);
                    isChangePic = true;
                    db.collection("users").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().get("imageurl") != null) {
                                    imageurl = task.getResult().get("imageurl").toString();
                                    Glide.with(CommunityAuth.this).load(task.getResult().get("imageurl").toString()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                } else {
                                    Glide.with(CommunityAuth.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                }
                                imgload.setVisibility(View.GONE);
                            }
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
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(auth.getCurrentUser().getEmail()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseStorage.getInstance().getReference().child("userimages/kmj654649@gmail.coma").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                            @Override
                            public void onSuccess(ListResult listResult) {
                                if (listResult.getItems().size() != 0) {
                                    for(StorageReference storageReference : listResult.getItems()) {
                                        storageReference.delete();
                                    }
                                } else {
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
                                Toast.makeText(CommunityAuth.this, "delete success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CommunityAuth.this, CommunityMain.class);
                                intent.putExtra("isDeleteUser", true);
                                setResult(CommunityMain.FROM_AUTH, intent);
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("CommunityMain>>>", "firestore delete user failed");
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
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("users").document(auth.getCurrentUser().getEmail()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseStorage.getInstance().getReference().child("userimages/kmj654649@gmail.coma").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                        @Override
                                        public void onSuccess(ListResult listResult) {
                                            if (listResult.getItems().size() != 0) {
                                                for(StorageReference storageReference : listResult.getItems()) {
                                                    storageReference.delete();
                                                }
                                            } else {
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
                                            Toast.makeText(CommunityAuth.this, "delete success", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(CommunityAuth.this, CommunityMain.class);
                                            intent.putExtra("isDeleteUser", true);
                                            setResult(CommunityMain.FROM_AUTH, intent);
                                            finish();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("CommunityMain>>>", "firestore delete user failed");
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
}
