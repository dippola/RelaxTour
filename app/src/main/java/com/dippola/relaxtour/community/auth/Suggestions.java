package com.dippola.relaxtour.community.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Suggestions extends AppCompatActivity {

    public static final int FROM_SUGGESTIONS = 100;

    EditText editText;
    Button send;
    RelativeLayout load;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_auth_suggestions);

        setInit();
        setOnClickSend();
    }

    private void setInit() {
        editText = findViewById(R.id.community_auth_suggestions_edittext);
        send = findViewById(R.id.community_auth_suggestions_button);
        load = findViewById(R.id.community_auth_suggestions_load);
        load.setVisibility(View.GONE);
    }

    private void setOnClickSend() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load.setVisibility(View.VISIBLE);
                saveSuggestionsFirestore();
            }
        });
    }

    private void saveSuggestionsFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("suggestions", editText.getText().toString());
        map.put("userid", new DatabaseHandler(Suggestions.this).getUserModel().getId());
        db.collection("suggestions").document(getUTCTime()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(Suggestions.this, SuggestionsSuccessDialog.class);
                    launcher.launch(intent);
                }
            }
        });
    }

    private String getUTCTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(new Date());
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == FROM_SUGGESTIONS) {
                finish();
            }
        }
    });
}
