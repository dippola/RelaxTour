package com.dippola.relaxtour.board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.board.signIn.CommunitySignInMain;
import com.dippola.relaxtour.dialog.Premium;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionPermissionsCallback;
import com.qonversion.android.sdk.dto.QPermission;

import java.util.Map;

public class CommunityMain extends AppCompatActivity {

    private RelativeLayout load;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main);

        load = findViewById(R.id.community_main_load);
        load.setVisibility(View.VISIBLE);

        auth = FirebaseAuth.getInstance();

//        checkPremium();
        checkAuth();
        test();
    }


    private void test() {
        Button btn;
        btn = findViewById(R.id.community_main_testbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(CommunityMain.this, gso);
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 9001);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9001) {
            load.setVisibility(View.GONE);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("CommunityLogin>>>", "firebaseAuthWithGoogle:" + account.getId());
                AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                auth.getCurrentUser().reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(CommunityMain.this, "delete success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("CommunityLogin>>>", "Google sign in failed", e);
            }
        }
    }

    private void checkPremium() {
        Qonversion.checkPermissions(new QonversionPermissionsCallback() {
            @Override
            public void onSuccess(@NonNull Map<String, QPermission> map) {
                QPermission qPermission = map.get("dippola_relaxtour_premium");
                if (qPermission != null && qPermission.isActive()) {
                    Log.d("CommunityMain>>>", "have permission");
                    checkAuth();
                } else {
                    Log.d("CommunityMain>>>", "null permission: ");
                    finish();
                    Toast.makeText(CommunityMain.this, "The community is only available to premium users.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CommunityMain.this, Premium.class));
                }
            }

            @Override
            public void onError(@NonNull QonversionError qonversionError) {
                Log.d("CommunityMain>>>", "qper error: " + qonversionError);
                Toast.makeText(CommunityMain.this, "Load failed with an error.\nError: " + qonversionError, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void checkAuth() {
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(CommunityMain.this, CommunitySignInMain.class));
        }
        load.setVisibility(View.GONE);
        loadCommunity();
    }

    private void loadCommunity() {

    }
}
