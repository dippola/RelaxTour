package com.dippola.relaxtour.community.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.CommunityMain;

public class CommunityProfileCreate extends AppCompatActivity {

    private ImageView img;
    private TextView picbtn, count, skip, error;
    private Button deletePic, ok;
    private EditText editNickname;
    private RelativeLayout load;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_profile_create);

        setInit();
        setImage();
        onClickSkip();
    }

    private void setInit() {
        img = findViewById(R.id.community_profile_create_img);
        picbtn = findViewById(R.id.community_profile_create_photobtn);
        count = findViewById(R.id.community_profile_create_count);
        skip = findViewById(R.id.community_profile_create_skip);
        deletePic = findViewById(R.id.community_profile_create_cancel_img);
        ok = findViewById(R.id.community_profile_create_okbtn);
        editNickname = findViewById(R.id.community_profile_create_edit_nickname);
        error = findViewById(R.id.community_profile_create_errortext);
        load = findViewById(R.id.community_profile_create_load);
        load.setVisibility(View.GONE);
    }

    private void setImage() {
        Glide.with(CommunityProfileCreate.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(img);
    }

    private void onClickSkip() {
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityProfileCreate.this, CommunityMain.class);
                intent.putExtra("isCreate", false);
                setResult(CommunityMain.FROM_CREATE_PROFILE, intent);
                finish();
            }
        });
    }
}
