package com.dippola.relaxtour.community.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.admin.detail.AdminDetail;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin extends AppCompatActivity implements AdminAdapter.ItemClickListener {

    public static final int FROM_ADMIN_DIALOG = 100;

    RecyclerView recyclerView;
    List<ReportModel> list = new ArrayList<>();
    FirebaseFirestore db;
    AdminAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        setInit();
        getData();
    }

    private void setInit() {
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.admin_main_recyclerview);
    }

    private void getData() {
        db.collection("report").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    ReportModel reportModel = snapshot.toObject(ReportModel.class);
                    list.add(reportModel);
                }
                setRecyclerView();
            }
        });
    }

    private void setRecyclerView() {
        adapter = new AdminAdapter(Admin.this, list, Admin.this);
        adapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Admin.this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(Admin.this, AdminDialog.class);
        intent.putExtra("date", list.get(position).getDate());
        intent.putExtra("from", list.get(position).getFrom());
        intent.putExtra("postid", list.get(position).getPostid());
        intent.putExtra("commentid", list.get(position).getCommentid());
        intent.putExtra("choice", list.get(position).getChoice());
        launcher.launch(intent);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == FROM_ADMIN_DIALOG) {
                int i = result.getData().getIntExtra("click", 0);
                if (i != 0) {
                    if (i == 100) {//신고목록에서 지우기
                        String date = result.getData().getStringExtra("date");
                        deleteReport(date);
                    } else if (i == 1) {//글보기
                        Intent intent = new Intent(Admin.this, AdminDetail.class);
                        intent.putExtra("from", result.getData().getStringExtra("from"));
                        intent.putExtra("postid", result.getData().getIntExtra("postid", 0));
                        intent.putExtra("commentid", result.getData().getIntExtra("commentid", 0));
                        startActivity(intent);
                    } else if (i == 2) {//글삭제
                        deletePost(result.getData().getIntExtra("postid", 0), result.getData().getIntExtra("why", 0));
                    } else if (i == 3) {//댓글 삭제

                    } else if (i == 4) {//프로필 수정

                    } else if (i == 5) {//회원삭제

                    }
                }
            }
        }
    });

    private void deleteReport(String date) {
        db.collection("report").document(date).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getDate().equals(date)) {
                        list.remove(i);
                        adapter.notifyItemRemoved(i);
                        break;
                    }
                }
            }
        });
    }

    private void deletePost(int postid, int why) {
        if (postid != 0) {
            RetrofitClient.getApiService(Admin.this).adminPostDelete(postid, getString(R.string.appkey), getWhy(why)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Log.d("Admin>>>", "1");
                    } else {
                        Log.d("Admin>>>", "2");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("Admin>>>", "3: " + t.getMessage());
                }
            });
        }
    }

    private String getWhy(int why) {
        String result = "";
        if (why == 0) {
            result = "Spam promotion or spamming.";
        } else if (why == 1) {
            result = "Pornography, obscene material, porn.";
        } else if (why == 2) {
            result = "Include illegal information.";
        } else if (why == 3) {
            result = "Content harmful to adolescents.";
        } else if (why == 4) {
            result = "Abusive, hateful, discriminatory expressions";
        } else if (why == 5) {
            result = "Exposing personal information";
        } else if (why == 6) {
            result = "Unpleasant content";
        } else if (why == 7) {
            result = "etc.";
        }
        return result;
    }
}
