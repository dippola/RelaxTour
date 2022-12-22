package com.dippola.relaxtour.community.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;
import com.dippola.relaxtour.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCommunityDialog extends AppCompatActivity {

    private TextView title, body;
    private Button ok, cancel;
    private LinearLayout load;

    private String from;
    private int id;
    private int commentid;
    private int comment_index;

    private boolean isDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_delete_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        from = getIntent().getStringExtra("delete");
        id = getIntent().getIntExtra("id", 0);
        if (from.equals("comment")) {
            commentid = getIntent().getIntExtra("commentid", 0);
        }

        setInit();
        onClickOk();
        onClickCancel();
    }

    private void setInit() {
        load = findViewById(R.id.community_delete_dialog_load);
        load.setVisibility(View.INVISIBLE);
        title = findViewById(R.id.community_delete_dialog_title);
        title.setText("Delete " + from);
        body = findViewById(R.id.community_delete_dialog_body);
        ok = findViewById(R.id.community_delete_dialog_ok);
        cancel = findViewById(R.id.community_delete_dialog_cancel);
    }

    private void onClickOk() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFinishOnTouchOutside(false);
                ok.setEnabled(false);
                cancel.setEnabled(false);
                load.setVisibility(View.VISIBLE);
                if (from.equals("post")) {
                    deletePost();
                } else if (from.equals("comment")) {
                    deleteComment();
                }
            }
        });
    }

    private void onClickCancel() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteCommunityDialog.this, CommunityMainDetail.class);
                intent.putExtra("isDelete" , isDelete);
                intent.putExtra("from", from);
                setResult(CommunityMainDetail.FROM_DELETE, intent);
                finish();
            }
        });
    }

    private void deletePost() {
        RetrofitClient.getApiService().deleteMain(id, getString(R.string.appkey)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (!response.body().equals("Failed")) {
                        isDelete = true;
                        Intent intent = new Intent(DeleteCommunityDialog.this, CommunityMainDetail.class);
                        intent.putExtra("isDelete" , isDelete);
                        intent.putExtra("from", from);
                        setResult(CommunityMainDetail.FROM_DELETE, intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void deleteComment() {
        RetrofitClient.getApiService().deleteComment(id, commentid, getString(R.string.appkey)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (!response.body().equals("Failed")) {
                        isDelete = true;
                        Intent intent = new Intent(DeleteCommunityDialog.this, CommunityMainDetail.class);
                        intent.putExtra("isDelete" , isDelete);
                        intent.putExtra("from", from);
                        setResult(CommunityMainDetail.FROM_DELETE, intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (load.getVisibility() != View.VISIBLE) {
            Intent intent = new Intent(DeleteCommunityDialog.this, CommunityMainDetail.class);
            intent.putExtra("isDelete" , isDelete);
            intent.putExtra("from", from);
            setResult(CommunityMainDetail.FROM_DELETE, intent);
            super.onBackPressed();
        }
    }
}
