package com.dippola.relaxtour.community.main.detail;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;

public class AddFavAskDialog extends AppCompatActivity {

    EditText editText;
    Button button;
    String title, list;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.community_add_fav_ask_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editText = findViewById(R.id.community_add_fav_ask_dialog_edit);
        button = findViewById(R.id.community_add_fav_ask_dialog_button);

        title = getIntent().getStringExtra("title");
        list = getIntent().getStringExtra("list");
        editText.setText(title);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler = new DatabaseHandler(AddFavAskDialog.this);
                databaseHandler.AddFavTitleFromPost(editText.getText().toString(), list, AddFavAskDialog.this, AddFavAskDialog.this);
            }
        });
    }
}
