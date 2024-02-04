package com.dippola.relaxtour.community.bottomsheet_intent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.PostCommentModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditComment extends AppCompatActivity {

    Button back, ok;
    EditText editText;
    RelativeLayout load;
    int post_id, comment_id;
    String body;

    boolean editting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_edit_comment);

        back = findViewById(R.id.community_edit_comment_back);
        ok = findViewById(R.id.community_edit_comment_ok);
        editText = findViewById(R.id.community_edit_comment_edittext);
        load = findViewById(R.id.community_edit_comment_load);

        post_id = getIntent().getIntExtra("post_id", 0);
        comment_id = getIntent().getIntExtra("comment_id", 0);
        if (post_id == 0 || comment_id == 0) {
            Toast.makeText(EditComment.this, "Error occurred. please try again..", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        body = getIntent().getStringExtra("body");
        editText.setText(body);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editting = true;
//                load.setVisibility(View.VISIBLE);
                editComment();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!editting) {
            super.onBackPressed();
        }
    }

    private void editComment() {
        PostCommentModel postCommentModel = new PostCommentModel();
        postCommentModel.setBody(editText.getText().toString());
        RetrofitClient.getApiService(EditComment.this).updateComment(post_id, comment_id, postCommentModel, getString(R.string.appkey)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditComment.this, "Comment has been edited", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditComment.this, CommunityMainDetail.class);
                    intent.putExtra("editcomment", editText.getText().toString());
                    setResult(CommunityMainDetail.FROM_EDIT_COMMENT, intent);
                    finish();
                } else {
                    load.setVisibility(View.GONE);
                    editting = false;
                    if (response.errorBody() != null) {
                        Toast.makeText(EditComment.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                load.setVisibility(View.GONE);
                editting = false;
                Toast.makeText(EditComment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager manager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
