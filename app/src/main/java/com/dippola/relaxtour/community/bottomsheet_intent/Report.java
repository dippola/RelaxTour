package com.dippola.relaxtour.community.bottomsheet_intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Report extends AppCompatActivity {

    public static final int FROM_REPORT_DIALOG_SUCCESS = 400;
    public static final int FROM_REPORT_DIALOG_ASK = 401;

    private Button back, ok, cancel;
    private RadioGroup radioGroup;
    private RadioButton r1, r2, r3, r4, r5, r6, r7, r8;
    private int selectIndex;
    private EditText editText;
    private RelativeLayout load;

    private int postId, commentId, decUser;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_report);

        selectIndex = -1;
        from = getIntent().getStringExtra("from");
        postId = getIntent().getIntExtra("postid", 0);
        decUser = getIntent().getIntExtra("decuser", 0);
        commentId = getIntent().getIntExtra("commentid", 0);

        setInit();
        setRadioGroup();
        onClick();
    }

    private void setInit() {
        back = findViewById(R.id.community_report_back);
        ok = findViewById(R.id.community_report_ok);
        cancel = findViewById(R.id.community_report_cancel);
        radioGroup = findViewById(R.id.community_report_radiogroup);
        r1 = findViewById(R.id.community_report_radio1);
        r2 = findViewById(R.id.community_report_radio2);
        r3 = findViewById(R.id.community_report_radio3);
        r4 = findViewById(R.id.community_report_radio4);
        r5 = findViewById(R.id.community_report_radio5);
        r6 = findViewById(R.id.community_report_radio6);
        r7 = findViewById(R.id.community_report_radio7);
        r8 = findViewById(R.id.community_report_radio8);
        editText = findViewById(R.id.community_report_edittext);
        load = findViewById(R.id.community_report_load);
        load.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                selectIndex = radioGroup.indexOfChild(radioButton);
            }
        });
    }

    private void onClick() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectIndex == -1) {
                    Toast.makeText(Report.this, "Please select a reason.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Report.this, ReportDialog.class);
                    intent.putExtra("from", "ask");
                    launcher.launch(intent);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (load.getVisibility() == View.GONE) {
                    finish();
                }
            }
        });
    }

    private void saveFirebaseFirestore() {
        String nowDate = getUTCTime();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("from", from);
        map.put("postid", postId);
        map.put("decUser", decUser);
        map.put("commentid", commentId);
        map.put("choice", selectIndex);
        map.put("edit", editText.getText().toString());
        map.put("date", nowDate);
        db.collection("report").document(nowDate).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    load.setVisibility(View.GONE);
                    Intent intent = new Intent(Report.this, ReportDialog.class);
                    intent.putExtra("from", "save");
                    launcher.launch(intent);
                } else {
                    load.setVisibility(View.GONE);
                    Toast.makeText(Report.this, "Error: failed because the internet was unstable.", Toast.LENGTH_SHORT).show();
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
            if (result.getResultCode() == FROM_REPORT_DIALOG_SUCCESS) {
                finish();
            } else if (result.getResultCode() == FROM_REPORT_DIALOG_ASK) {
                if (result.getData().getBooleanExtra("isReport", false)) {
                    load.setVisibility(View.VISIBLE);
                    saveFirebaseFirestore();
                }
            }
        }
    });
}
