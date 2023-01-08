package com.dippola.relaxtour.community.bottomsheet_intent;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

public class ReportDialog extends AppCompatActivity {

    private Button ok, cancel;
    private TextView title, body;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_report_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        from = getIntent().getStringExtra("from");

        title = findViewById(R.id.community_report_success_idalog_title);
        body = findViewById(R.id.community_report_success_idalog_body);
        ok = findViewById(R.id.community_report_success_dialog_ok);
        cancel = findViewById(R.id.community_report_success_dialog_cancel);

        if (from.equals("ask")) {
            title.setText("Send Report");
            body.setText("Are you sure you want to\nreport it as you filled it out?");
        } else {
            cancel.setVisibility(View.VISIBLE);
            title.setText("Report completed");
            body.setText("Thank you for your cooperation for\na healthy community.\n\nThe report will be reviewed by the management team and taken action.");
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equals("ask")) {
                    Intent intent = new Intent(ReportDialog.this, Report.class);
                    intent.putExtra("isReport", true);
                    setResult(Report.FROM_REPORT_DIALOG_ASK, intent);
                    finish();
                } else {
                    setResult(Report.FROM_REPORT_DIALOG_SUCCESS);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equals("ask")) {
                    Intent intent = new Intent(ReportDialog.this, Report.class);
                    intent.putExtra("isReport", false);
                    setResult(Report.FROM_REPORT_DIALOG_ASK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (from.equals("ask")) {
            Intent intent = new Intent(ReportDialog.this, Report.class);
            intent.putExtra("isReport", false);
            setResult(Report.FROM_REPORT_DIALOG_ASK, intent);
            finish();
        } else {
            setResult(Report.FROM_REPORT_DIALOG_SUCCESS);
            finish();
        }
    }
}
