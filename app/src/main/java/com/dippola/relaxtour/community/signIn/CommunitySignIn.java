package com.dippola.relaxtour.community.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.CommunityMain;
import com.dippola.relaxtour.community.Test;
import com.dippola.relaxtour.community.auth.CommunityAuthResetPasswordDialog;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunitySignIn extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private TextView errorMessage, signUpBtn, forgot;
    private Button signInBtn;
    private RelativeLayout load;

    ConstraintLayout googleBtn;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 9001;
    public static final int ASK_SIGNIN_RESULT_CODE = 100;
    public static final int FROM_SIGN_UP = 101;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_signin);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        //359180136078-c0fbgfjujmaluggsqvud5o9sdnt0ske6.apps.googleusercontent.com
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        auth = FirebaseAuth.getInstance();

        setInit();

        test();
    }

    private void setInit() {
        editEmail = findViewById(R.id.community_signin_edit_email);
        editPassword = findViewById(R.id.community_signin_edit_password);
        errorMessage = findViewById(R.id.community_signin_error_message);
        signUpBtn = findViewById(R.id.community_signin_signup_btn);
        googleBtn = findViewById(R.id.community_signin_googlebtn);
        signInBtn = findViewById(R.id.community_signin_signin_btn);
        forgot = findViewById(R.id.community_signin_forgot_password);
        load = findViewById(R.id.community_signin_load);
        load.setVisibility(View.GONE);
        setOnClickSignInEmail();
        setOnClickGoogleBtn();
        setOnClickSignUp();
        setOnClickForgot();
    }

    private void setOnClickSignInEmail() {
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                errorMessage.setText("");
                editEmail.setBackground(getResources().getDrawable(R.drawable.edittext));
                editPassword.setBackground(getResources().getDrawable(R.drawable.edittext));
                if (editEmail.getText().toString().length() == 0) {
                    errorMessage.setText("Please enter your email");
                    editEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                } else {
                    Pattern pattern = Patterns.EMAIL_ADDRESS;
                    if (pattern.matcher(editEmail.getText().toString()).matches()) {
                        if (editPassword.getText().toString().length() < 8) {
                            errorMessage.setText("Password must be at least 8 digits.");
                            editPassword.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                        } else {
                            load.setVisibility(View.VISIBLE);
                            checkUserAreadyWhenEmail(editEmail.getText().toString());
                        }
                    } else {
                        errorMessage.setText("This is not in email format.");
                        editEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    }
                }

            }
        });
    }

    private void setOnClickGoogleBtn() {
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                load.setVisibility(View.VISIBLE);
                startSignIn();
            }
        });
    }

    private void setOnClickForgot() {
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunitySignIn.this, CommunityAuthResetPasswordDialog.class);
                intent.putExtra("from", "signin");
                launcher.launch(intent);
            }
        });
    }

    private void setOnClickSignUp() {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                launcher.launch(new Intent(CommunitySignIn.this, CommunitySignUp.class));
            }
        });
    }

    private void startSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("CommunityLogin>>>", "firebaseAuthWithGoogle:" + account.getId());
                checkUserAreadyWhenGoogle(account.getEmail(), account.getIdToken());
//                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                load.setVisibility(View.GONE);
                // Google Sign In failed, update UI appropriately
                Log.w("CommunityLogin>>>", "Google sign in failed", e);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (load.getVisibility() == View.GONE) {
            super.onBackPressed();
        }
    }

    private void checkUserAreadyWhenEmail(String email) {
        Call<List<UserModel>> call;
        call = RetrofitClient.getApiService().searchEmail(email);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {
                        if (!response.body().get(0).getProvider().equals("Email/Password")) {
                            Intent intent = new Intent(CommunitySignIn.this, CommunitySignInAnotherProviderDialog.class);
                            intent.putExtra("showProvider", "Google");
                            startActivity(intent);
                            load.setVisibility(View.GONE);
                        } else {
                            getToken(response.body().get(0).getId(), response.body().get(0).getEmail(), response.body().get(0).getUid(), response.body().get(0).getNickname(), response.body().get(0).getImageurl(), response.body().get(0).getProvider(), response.body().get(0).getNotification());
                            auth.signInWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        goToMain();
                                    } else {
                                        Toast.makeText(CommunitySignIn.this, "failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                                        load.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    } else {
                        load.setVisibility(View.GONE);
                        Intent intent = new Intent(CommunitySignIn.this, CommunityAskSignUpDialog.class);
                        launcher.launch(intent);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(CommunitySignIn.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                load.setVisibility(View.GONE);
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == ASK_SIGNIN_RESULT_CODE) {
                if (result.getData().getStringExtra("click").equals("ok")) {
                    launcher.launch(new Intent(CommunitySignIn.this, CommunitySignUp.class));
                }
            } else if (result.getResultCode() == FROM_SIGN_UP) {
                if (result.getData().getBooleanExtra("isSignUp", false)) {
                    load.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(CommunitySignIn.this, CommunityMain.class);
                    intent.putExtra("need_create_profile", true);
                    setResult(CommunityMain.FROM_SIGNIN, intent);
                    finish();
                }
            }
        }
    });

    private void checkUserAreadyWhenGoogle(String email, String idToken) {
        Call<List<UserModel>> call;
        call = RetrofitClient.getApiService().searchEmail(email);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    Log.d("CommunitySignIn>>>", "1: " + email);
                    if (response.body().size() != 0) {
                        Log.d("CommunitySignIn>>>", "2");
                        if (!response.body().get(0).getProvider().equals("Google")) {
                            Intent intent = new Intent(CommunitySignIn.this, CommunitySignInAnotherProviderDialog.class);
                            intent.putExtra("showProvider", "Email/Password");
                            startActivity(intent);
                            load.setVisibility(View.GONE);
                        } else {
                            getToken(response.body().get(0).getId(), response.body().get(0).getEmail(), response.body().get(0).getUid(), response.body().get(0).getNickname(), response.body().get(0).getImageurl(), response.body().get(0).getProvider(), response.body().get(0).getNotification());
                            firebaseAuthWithGoogle(idToken);
                        }
                    } else {
                        Log.d("CommunitySignIn>>>", "3: " + response.message());
                        load.setVisibility(View.GONE);
                        Intent intent = new Intent(CommunitySignIn.this, CommunityAskSignUpDialog.class);
                        launcher.launch(intent);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(CommunitySignIn.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                load.setVisibility(View.GONE);
            }
        });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information=
                            goToMain();
//                            updateUI(user);
                        } else {
                            load.setVisibility(View.GONE);
                            Toast.makeText(CommunitySignIn.this, "failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
                            Log.w("CommunityLogin>>>", "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        editEmail.setText("");
        editPassword.setText("");
    }

    private void getToken(int id, String email, String uid, String nickname, String imageurl, String provider, boolean notification) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    updateDatabase(id, email, uid, nickname, task.getResult(), imageurl, provider, notification);
                }
            }
        });
    }

    private void updateDatabase(int id, String email, String uid, String nickname, String token, String imageurl, String provider, boolean notification) {
        DatabaseHandler databaseHandler = new DatabaseHandler(CommunitySignIn.this);
        databaseHandler.makeDbUserWhenSignIn(id, email, uid, nickname, token, imageurl, provider, notification);
    }

    private void goToMain() {
        Toast.makeText(CommunitySignIn.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CommunitySignIn.this, CommunityMain.class);
        intent.putExtra("isSignIn", true);
        setResult(CommunityMain.FROM_SIGNIN, intent);
        finish();
    }

    private void hideKeyboard(View v) {
        InputMethodManager manager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void test() {
        ImageView test = findViewById(R.id.community_signin_titleimg);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunitySignIn.this, Test.class));
            }
        });
    }

}
