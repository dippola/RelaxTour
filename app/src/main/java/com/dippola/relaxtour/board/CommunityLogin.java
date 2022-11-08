package com.dippola.relaxtour.board;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.google.firebase.auth.FirebaseAuth;

public class CommunityLogin extends AppCompatActivity {

    FirebaseAuth auth;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_login);
    }

}
