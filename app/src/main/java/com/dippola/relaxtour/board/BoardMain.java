package com.dippola.relaxtour.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

public class BoardMain extends AppCompatActivity {

    RelativeLayout load;
//    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_main);

        this.load = (RelativeLayout) findViewById(R.id.board_main_load);
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        this.user = currentUser;
//        if (currentUser == null) {
//            this.load.setVisibility(View.VISIBLE);
//            startActivity(new Intent(this, BoardLogin.class));
//            return;
//        }
        this.load.setVisibility(View.GONE);

    }
}
