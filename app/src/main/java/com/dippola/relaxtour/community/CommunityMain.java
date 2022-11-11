package com.dippola.relaxtour.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.signIn.CommunityAskSignUpDialog;
import com.dippola.relaxtour.community.signIn.CommunityProfileCreate;
import com.dippola.relaxtour.community.signIn.CommunitySignIn;
import com.dippola.relaxtour.community.signIn.CommunitySignUp;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.Premium;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionPermissionsCallback;
import com.qonversion.android.sdk.dto.QPermission;

import java.util.HashMap;
import java.util.Map;

public class CommunityMain extends AppCompatActivity {

    private RelativeLayout load;
    private FirebaseAuth auth;
    private Button gosignin;

    private DatabaseHandler databaseHandler;

    public static final int NEED_CREATE_PROFILE = 105;
    public static final int FROM_CREATE_PROFILE = 106;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main);

        auth = FirebaseAuth.getInstance();

        setDatabaseHandler();

        load = findViewById(R.id.community_main_load);
        load.setVisibility(View.VISIBLE);

        goToSignInButton();

//        checkPremium();
//        checkAuth();
        test();
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == NEED_CREATE_PROFILE) {
                if (result.getData().getBooleanExtra("need_create_profile", false)) {
                    launcher.launch(new Intent(CommunityMain.this, CommunityProfileCreate.class));
                }
            } else if (result.getResultCode() == FROM_CREATE_PROFILE) {
                if (result.getData().getBooleanExtra("isCreate", false)) {
                    //create profile
                } else {
                    //no create profile
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
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("users").document(auth.getCurrentUser().getEmail()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("CommunityMain>>>", "firestore delete user successed");
                                    auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(CommunityMain.this, "delete success", Toast.LENGTH_SHORT).show();
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
