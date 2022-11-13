package com.dippola.relaxtour.community.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.CommunityMain;
import com.dippola.relaxtour.community.signIn.CommunityProfileCreate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CommunityAuth extends AppCompatActivity {

    public static final int FROM_CHANGE_PROFILE = 100;

    private ImageView img;
    private TextView nickname, email;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private RelativeLayout load;
    private Button back, changeprofile;
    private boolean isChangePic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_auth);

        setInit();
        setProfile();
        onClickChangeProfile();
        setBack();
    }

    private void setInit() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        img = findViewById(R.id.community_auth_img);
        nickname = findViewById(R.id.community_auth_nickname);
        email = findViewById(R.id.community_auth_email);
        back = findViewById(R.id.community_auth_goback);
        changeprofile = findViewById(R.id.community_auth_change_profile);
        load = findViewById(R.id.community_auth_load);
        load.setVisibility(View.VISIBLE);
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

    private void setProfile() {
        db.collection("users").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().get("imageurl") != null) {
                        Glide.with(CommunityAuth.this).load(task.getResult().get("imageurl").toString()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                    } else {
                        Glide.with(CommunityAuth.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                    }
                    if (task.getResult().get("nickname") != null) {
                        nickname.setText(task.getResult().get("nickname").toString());
                    } else {
                        nickname.setText("nickname");
                    }
                    email.setText(auth.getCurrentUser().getEmail());
                    load.setVisibility(View.GONE);
                } else {
                    Toast.makeText(CommunityAuth.this, "Profile load failed due to unstable internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onClickChangeProfile() {
        changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch(new Intent(CommunityAuth.this, CommunityProfileChange.class));
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == FROM_CHANGE_PROFILE) {
                if (result.getData().getBooleanExtra("isChangePic", false)) {
                    isChangePic = true;
                    db.collection("users").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().get("imageurl") != null) {
                                    Glide.with(CommunityAuth.this).load(task.getResult().get("imageurl").toString()).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                } else {
                                    Glide.with(CommunityAuth.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CenterCrop(), new RoundedCorners(80)).into(img);
                                }
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
            }
        }
    });

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CommunityAuth.this, CommunityMain.class);
        intent.putExtra("isChangePic", isChangePic);
        setResult(CommunityMain.FROM_AUTH, intent);
        super.onBackPressed();
    }
}
