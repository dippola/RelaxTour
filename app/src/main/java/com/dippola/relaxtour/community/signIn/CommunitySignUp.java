package com.dippola.relaxtour.community.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunitySignUp extends AppCompatActivity {

    private EditText editEmail, editPassword1, editPassword2;
    private TextView errorMessage, goToSignin;
    private Button signUpEmail;
    private ConstraintLayout signUpGoogle;
    private RelativeLayout load;

    private FirebaseAuth auth;

    public static final int RC_SIGN_UP = 1010;
    public static final int FROM_ALREADY_USER_DIALOG = 103;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_signup);

        auth = FirebaseAuth.getInstance();

        setInit();
        onClickSignUpEmail();
        onClickSignUpGoogle();
        onClickGoToSignIn();
    }

    private void setInit() {
        editEmail = findViewById(R.id.community_signup_edit_email);
        editPassword1 = findViewById(R.id.community_signup_edit_password);
        editPassword2 = findViewById(R.id.community_signup_edit_password2);
        errorMessage = findViewById(R.id.community_signup_error_message);
        goToSignin = findViewById(R.id.community_signup_signin_btn);
        signUpEmail = findViewById(R.id.community_signup_signup_btn);
        signUpGoogle = findViewById(R.id.community_signup_googlebtn);
        load = findViewById(R.id.community_signup_load);
        load.setVisibility(View.GONE);
    }

    private void onClickSignUpEmail() {
        signUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                errorMessage.setText("");
                editEmail.setBackground(getResources().getDrawable(R.drawable.edittext));
                editPassword1.setBackground(getResources().getDrawable(R.drawable.edittext));
                editPassword2.setBackground(getResources().getDrawable(R.drawable.edittext));
                if (editEmail.getText().toString().length() == 0) {
                    errorMessage.setText("Please enter your email");
                    editEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                } else {
                    Pattern pattern = Patterns.EMAIL_ADDRESS;
                    if (pattern.matcher(editEmail.getText().toString()).matches()) {
                        if (editPassword1.getText().toString().length() < 8) {
                            errorMessage.setText("Password must be at least 8 digits.");
                            editPassword1.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                        } else {
                            if (editPassword1.getText().toString().length() > 14) {
                                errorMessage.setText("Password Must be less than 14 digits.");
                                editPassword1.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                            } else {
                                if (!editPassword1.getText().toString().equals(editPassword2.getText().toString())) {
                                    errorMessage.setText("Password does not match.");
                                    editPassword2.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                                } else {
                                    load.setVisibility(View.VISIBLE);
                                    checkUserAreadyWhenEmail(editEmail.getText().toString());
                                }
                            }
                        }
                    } else {
                        errorMessage.setText("This is not in email format.");
                        editEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    }
                }
            }
        });
    }

    private void checkUserAreadyWhenEmail(String email) {
        Call<List<UserModel>> call;
        call = RetrofitClient.getApiService().searchEmail(email);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {
                        load.setVisibility(View.GONE);
                        Intent intent = new Intent(CommunitySignUp.this, CommunityAlreadyUserDialog.class);
                        intent.putExtra("provider", response.body().get(0).getProvider());
                        launcher.launch(intent);
                    } else {
                        auth.createUserWithEmailAndPassword(editEmail.getText().toString(), editPassword2.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    saveUserInServer("Email/Password");
                                } else {
                                    Toast.makeText(CommunitySignUp.this, "failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                                    load.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(CommunitySignUp.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                load.setVisibility(View.GONE);
                Log.d("CommunityMain>>>", "3: " + t.getMessage());
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == FROM_ALREADY_USER_DIALOG) {
                if (result.getData().getStringExtra("click").equals("ok")) {
                    finish();
                }
            }
        }
    });

    private void onClickSignUpGoogle() {
        signUpGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                load.setVisibility(View.VISIBLE);
                if (auth.getCurrentUser() == null) {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(CommunitySignUp.this, gso);
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_UP);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_UP) {
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

    private void checkUserAreadyWhenGoogle(String email, String idToken) {
        Call<List<UserModel>> call;
        call = RetrofitClient.getApiService().searchEmail(email);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {
                        load.setVisibility(View.GONE);
                        Intent intent = new Intent(CommunitySignUp.this, CommunityAlreadyUserDialog.class);
                        intent.putExtra("provider", response.body().get(0).getProvider());
                        launcher.launch(intent);
                    } else {
                        firebaseAuthWithGoogle(idToken);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(CommunitySignUp.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                load.setVisibility(View.GONE);
                Log.d("CommunityMain>>>", "3: " + t.getMessage());
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
                            // Sign in success, update UI with the signed-in user's information
                            saveUserInServer("Google");
//                            updateUI(user);
                        } else {
                            load.setVisibility(View.GONE);
                            Toast.makeText(CommunitySignUp.this, "failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
                            Log.w("CommunityLogin>>>", "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }

    private void saveUserInServer(String provider) {
        DatabaseHandler databaseHandler = new DatabaseHandler(CommunitySignUp.this);
        databaseHandler.deleteUserProfile();
        databaseHandler.createUserProfile(auth.getCurrentUser().getEmail(), auth.getCurrentUser().getUid(), provider);
        UserModel userModel = new UserModel();
        userModel.setUid(auth.getCurrentUser().getUid());
        userModel.setEmail(auth.getCurrentUser().getEmail());
        userModel.setNickname("");
        userModel.setImageurl("");
        userModel.setProvider(provider);
        RetrofitClient.getApiService().createUser(userModel).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    goBackToSignIn(true);
                } else {
                    Toast.makeText(CommunitySignUp.this, "Error: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    load.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(CommunitySignUp.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                load.setVisibility(View.GONE);
            }
        });
    }

    private void onClickGoToSignIn() {
        goToSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                goBackToSignIn(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (load.getVisibility() == View.GONE) {
            goBackToSignIn(false);
        }
    }

    private void goBackToSignIn(boolean isSignUp) {
        Intent intent = new Intent(CommunitySignUp.this, CommunitySignIn.class);
        intent.putExtra("isSignUp", isSignUp);
        setResult(CommunitySignIn.FROM_SIGN_UP, intent);
        finish();
    }

    private void hideKeyboard(View v) {
        InputMethodManager manager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}



////8 or more digits mixed with English and numbers