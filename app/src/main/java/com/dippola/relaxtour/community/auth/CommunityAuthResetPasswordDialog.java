package com.dippola.relaxtour.community.auth;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.signIn.CommunitySignIn;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityAuthResetPasswordDialog extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView title, myEmail, text1, text2, text3, error;
    private EditText editEmail;
    private LinearLayout haveAuth, noAuth;
    private Button ok, cancel, finish;
    private ProgressBar load;
    private LinearLayout box1, box2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_auth_reset_password);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        auth = FirebaseAuth.getInstance();
        setInit();

        if (getIntent().getStringExtra("from").equals("auth")) {
            haveAuth.setVisibility(View.VISIBLE);
            noAuth.setVisibility(View.GONE);
            title.setText("Reset Password");
            myEmail.setText(getIntent().getStringExtra("email"));
            text1.setText("We are sending you a password reset link\nto your email.");
            text2.setText("You can change your password\nby clicking the link.");
            text3.setText("Do you want to reset your password?");
        } else {
            noAuth.setVisibility(View.VISIBLE);
            haveAuth.setVisibility(View.GONE);
            title.setText("Find Password");
            text1.setText("We are sending you a password reset link\nto your email.");
            text2.setText("You can reset your password\nby clicking the link.");
            text3.setText("Do you want to find the password?");
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                if (getIntent().getStringExtra("from").equals("auth")) {
                    loadVissibility();
                    auth.sendPasswordResetEmail(auth.getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CommunityAuthResetPasswordDialog.this, "An email has been sent.", Toast.LENGTH_LONG).show();
                                loadGone();
                                box1.setVisibility(View.GONE);
                                box2.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(CommunityAuthResetPasswordDialog.this, "Email transmission failed for unknown reasons.", Toast.LENGTH_SHORT).show();
                                loadGone();
                            }
                        }
                    });
                } else {
                    Pattern pattern = Patterns.EMAIL_ADDRESS;
                    error.setText("");
                    editEmail.setBackground(getResources().getDrawable(R.drawable.edittext));
                    if (editEmail.getText().toString().length() == 0) {
                        error.setText("Please enter your email.");
                        editEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    } else if (pattern.matcher(editEmail.getText().toString()).matches()) {
                        loadVissibility();
                        Call<List<UserModel>> call;
                        call = RetrofitClient.getApiService().getUser(new DatabaseHandler(CommunityAuthResetPasswordDialog.this).getUserModel().getId(), getString(R.string.appkey));
                        call.enqueue(new Callback<List<UserModel>>() {
                            @Override
                            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        auth.sendPasswordResetEmail(editEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(CommunityAuthResetPasswordDialog.this, "An email has been sent.", Toast.LENGTH_LONG).show();
                                                    loadGone();
                                                    box1.setVisibility(View.GONE);
                                                    box2.setVisibility(View.VISIBLE);
                                                } else {
                                                    Toast.makeText(CommunityAuthResetPasswordDialog.this, "Email transmission failed for unknown reasons.", Toast.LENGTH_SHORT).show();
                                                    loadGone();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    error.setText("This email is not registered for the Relax Tour.");
                                    editEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                                }
                            }

                            @Override
                            public void onFailure(Call<List<UserModel>> call, Throwable t) {

                            }
                        });
                    } else {
                        error.setText("This is not in email format.");
                        editEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setInit() {
        title = findViewById(R.id.community_auth_reset_password_title);
        myEmail = findViewById(R.id.community_auth_reset_password_email);
        editEmail = findViewById(R.id.community_auth_reset_password_edit_email);
        haveAuth = findViewById(R.id.community_auth_reset_password_haveauth);
        noAuth = findViewById(R.id.community_auth_reset_password_nohaveauth);
        text1 = findViewById(R.id.community_auth_reset_password_text1);
        text2 = findViewById(R.id.community_auth_reset_password_text2);
        text3 = findViewById(R.id.community_auth_reset_password_text3);
        ok = findViewById(R.id.community_auth_reset_password_ok);
        cancel = findViewById(R.id.community_auth_reset_password_cancel);
        error = findViewById(R.id.community_auth_reset_password_error);
        load = findViewById(R.id.community_auth_reset_password_load);
        box1 = findViewById(R.id.community_auth_reset_password_title_layout);
        box2 = findViewById(R.id.community_auth_reset_password_success_layout);
        box2.setVisibility(View.GONE);
        finish = findViewById(R.id.community_auth_reset_password_finish);
        loadGone();
    }

    private void loadGone() {
        load.setVisibility(View.GONE);
        ok.setEnabled(true);
        cancel.setEnabled(true);
        setFinishOnTouchOutside(true);
    }

    private void loadVissibility() {
        load.setVisibility(View.VISIBLE);
        ok.setEnabled(false);
        cancel.setEnabled(false);
        setFinishOnTouchOutside(false);
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
