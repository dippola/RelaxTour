package com.dippola.relaxtour.community.translate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.auth.CommunityAuth;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SelectLanguage extends AppCompatActivity {

    Button back;
    RecyclerView recyclerView;
    String[] showLanguageList;
    String[] languageList;
    SelectLanguageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView save;
    String beforeLanguage;
    int beforeLanguageIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_language);

        setInit();
    }

    private void setInit() {
        showLanguageList = getResources().getStringArray(R.array.showlanguage);
        languageList = getResources().getStringArray(R.array.languagelist);
        back = findViewById(R.id.select_language_back);
        recyclerView = findViewById(R.id.select_language_recyclerview);
        save = findViewById(R.id.select_language_save);

        SharedPreferences sharedPreferences;
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    "languageTable",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        String nowLang = sharedPreferences.getString("nowLanguage", "device_language");
        String[] languageList = getResources().getStringArray(R.array.languagelist);
        for (int i = 0; i < languageList.length; i++) {
            if (nowLang.equals(languageList[i])) {
                beforeLanguageIndex = i;
                break;
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setRecyclerview();
        setSaveButton();
    }

    private void setRecyclerview() {
        adapter = new SelectLanguageAdapter(SelectLanguage.this, showLanguageList, languageList, beforeLanguage, beforeLanguageIndex);
        layoutManager = new LinearLayoutManager(SelectLanguage.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setSaveButton() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences;
                try {
                    sharedPreferences = EncryptedSharedPreferences.create(
                            "languageTable",
                            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                            getApplicationContext(),
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );
                } catch (GeneralSecurityException | IOException e) {
                    throw new RuntimeException(e);
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nowLanguage", languageList[SelectLanguageAdapter.selectPosition]);
                editor.apply();
                Intent intent = new Intent(SelectLanguage.this, CommunityAuth.class);
                intent.putExtra("newLanguage", showLanguageList[SelectLanguageAdapter.selectPosition]);
                setResult(CommunityAuth.FROM_SELECT_LANGUAGE, intent);
                finish();
            }
        });
    }
}
