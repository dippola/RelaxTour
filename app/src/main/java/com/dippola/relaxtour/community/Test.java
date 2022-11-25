package com.dippola.relaxtour.community;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.MainCommentModel;
import com.dippola.relaxtour.retrofit.model.MainCommentUpdateModel;
import com.dippola.relaxtour.retrofit.model.MainModel;
import com.dippola.relaxtour.retrofit.model.MainUpdateModel;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communiry_test);
        test();
        top();
    }

    private void top() {
        Button topl1 = findViewById(R.id.main_topl1);
        topl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getApiService().getUser(new DatabaseHandler(Test.this).getUserModel().getId()).enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        if (response.isSuccessful()) {
                            Log.d("Test>>>", "1: " + response);
                            Log.d("Test>>>", "email: " + response.body().get(0).getEmail());
                        } else {
                            Log.d("Test>>>", "2: " + response);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {
                        Log.d("Test>>>", "3: " + t.getMessage());
                    }
                });
            }
        });
        Button topl2 = findViewById(R.id.main_topl2);
        topl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getApiService().getMainComment(11, 1).enqueue(new Callback<List<MainCommentModel>>() {
                    @Override
                    public void onResponse(Call<List<MainCommentModel>> call, Response<List<MainCommentModel>> response) {
                        if (response.isSuccessful()) {
                            Log.d("Test>>>", "size: " + response.body().size());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MainCommentModel>> call, Throwable t) {

                    }
                });
            }
        });
        Button topl3 = findViewById(R.id.main_topl3);
        topl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getApiService().getMainAllComment(11).enqueue(new Callback<List<MainCommentModel>>() {
                    @Override
                    public void onResponse(Call<List<MainCommentModel>> call, Response<List<MainCommentModel>> response) {
                        if (response.isSuccessful()) {
                            Log.d("Test>>>", "size: " + response.body().size());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MainCommentModel>> call, Throwable t) {

                    }
                });
            }
        });
        Button topl4 = findViewById(R.id.main_topl4);
        topl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button topl5 = findViewById(R.id.main_topl5);
        topl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //2022-11-24T12:06:38.171139Z
                //2022-11-24 12:06:38 이거를
                //2022-11-24 21:06:38 로 바꿔야됨.

//                String fromServer = "2022-11-24 12:06:38";
                String fromServer = "2022-11-24T12:06:38.171139Z";
//                Log.d("Test>>>", "changeTime: " + changeTime(fromServer));
                Log.d("Test>>>", "TimeZone: " + TimeZone.getDefault().getID());
                Log.d("Test>>>", "Locale: " + Locale.getDefault().getCountry());
                Log.d("Test>>>", "Locale2: " + Locale.getDefault().getDisplayCountry());
                Log.d("Test>>>", "Locale2: " + Locale.getDefault().getLanguage());
                Log.d("Test>>>", "Locale2: " + Locale.getDefault().getDisplayLanguage());
                Log.d("Test>>>", "changeTime: " + changeTime1(fromServer));
            }
        });
        Button topl6 = findViewById(R.id.main_topl6);
        topl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (task.isSuccessful()) {
                                Log.d("Test>>>", "token: " + task.getResult());
                            }
                        }
                    });
                }
            }
        });
    }
    private String getDateResult(String dateFromServer) {
        String result = "";
        String nowDate = getTime();
        String nowDateCut = nowDate.split(" ")[0];
        String dateFromServerCut0 = changeTime(dateFromServer).split(" ")[0];
        String dateFromServerCut1 = changeTime(dateFromServer).split(" ")[1];
        if (nowDateCut.equals(dateFromServerCut0)) {
            result = dateFromServerCut1.split(":")[1] + ":" + dateFromServerCut1.split(":")[2];
        } else {
            result = dateFromServerCut0;
        }
        return result;
    }
    private String changeTime1(String dateFromServer) {
        String[] cut = dateFromServer.split("T");
        String[] cut1 = cut[1].split("\\.");
        String result = cut[0] + " " + cut1[0];
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        oldFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormat.parse(result);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newFormat.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormat.format(value);
            Log.d("Test>>>", "try");
        } catch (ParseException e) {
            Log.d("Test>>>", "catch: " + e);
            e.printStackTrace();
        }
        return dueDateAsNormal;
    }
    private String changeTime(String dateFromServer) {
        String changeTime = "";
        String[] cut = dateFromServer.split("T");
        String[] cut1 = cut[1].split("\\.");
        String result = cut[0] + " " + cut1[0];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(result);
            format.setTimeZone(TimeZone.getDefault());
            changeTime = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return changeTime;
    }
    private String getTime() {
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String date = format.format(mDate);
        return date;
    }

    private String getTimeFormat(String time1, String time2) throws ParseException {//my time(after), usertime(before)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM_dd HH:mm:ss", Locale.KOREA);
        Date dtime1 = dateFormat.parse(time1);
        Date dtime2 = dateFormat.parse(time2);
        long diff = dtime1.getTime() - dtime2.getTime();
        long sec = diff / 1000;
        if (sec < 60) {//초
            return sec + "초전";
        } else if (sec < 3600) {//분, 3600초 = 1시간
            long newResult = sec / 60;
            return newResult + "분전";
        } else if (sec < 86400) {//시간, 86400초 = 1일
            long newResult = sec / 3600;
            return newResult + "시간전";
        } else if (sec < 604800) {
            long newResult = sec / 86400;
            return newResult + "일전";
        } else if (sec < 2419200) {
            long newResult = sec / 604800;
            return newResult + "주전";
        } else if (sec < 14515200) {
            long newResult = sec / 2419200;
            return newResult + "개월전";
        } else {
            return "6개월전";
        }
    }

    private void testL() {
        Button l1 = findViewById(R.id.main_l1);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Call<List<UserModel>> call;
//                call = RetrofitClient.getApiService().searchEmail("kmj654649@gmail.com");
//                call.enqueue(new Callback<List<UserModel>>() {
//                    @Override
//                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
//                        if (response.isSuccessful()) {
//                            if (response.body().size() != 0) {
//                                Log.d("CommunityMain>>>", "1: " + response.message());
//                            } else {
//                                Log.d("CommunityMain>>>", "2: " + response.message());
//                            }
//                        } else {
//                            Log.d("CommunityMain>>>", "3: " + response.message());
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<List<UserModel>> call, Throwable t) {
//                        Log.d("CommunityMain>>>", "1: " + t.getMessage());
//                    }
//                });


                new DatabaseHandler(Test.this).createUserProfile(1, "dskfj@ldaskf.com", "lskajdf", "Google");
            }
        });
        Button l2 = findViewById(R.id.main_l2);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button l3 = findViewById(R.id.main_l3);
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel();
                userModel = new DatabaseHandler(Test.this).getUserModel();
                if (userModel.getImageurl() == null) {
                    Log.d("Test>>>", "1");
                }
                if (userModel.getImageurl().length() == 0) {
                    Log.d("Test>>>", "2");
                }
            }
        });
        Button l4 = findViewById(R.id.main_l4);
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Test>>>", "size: " + new DatabaseHandler(Test.this).getUserModel());
            }
        });
    }

    private void test() {
        testL();
        Button c_1 = findViewById(R.id.main_c1);
        c_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<MainModel>> call;
                call = RetrofitClient.getApiService().getMainPage(1);
                call.enqueue(new Callback<List<MainModel>>() {
                    @Override
                    public void onResponse(Call<List<MainModel>> call, Response<List<MainModel>> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "size: " + response.body().size());
                        } else {
                            Log.d("CommunityMain>>>", "1: " + response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<MainModel>> call, Throwable t) {
                        Log.d("CommunityMain>>>", "2: " + t.getMessage());
                    }
                });
            }
        });
        Button c0 = findViewById(R.id.main_c2);
        c0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<MainModel> call;
                call = RetrofitClient.getApiService().getMain(7);
                call.enqueue(new Callback<MainModel>() {
                    @Override
                    public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "1: " + response.body().getTitle());
                        } else {
                            Log.d("CommunityMain>>>", "2: " + response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<MainModel> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.getMessage());
                    }
                });
            }
        });
        Button c1 = findViewById(R.id.main_c3);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler databaseHandler = new DatabaseHandler(Test.this);
                int id = databaseHandler.getUserModel().getId();
                MainModel mainModel = new MainModel();
                mainModel.setTitle("title title dz2");
                mainModel.setBody("body.. ok body\nbody hello2");
                mainModel.setImageurl("");
                mainModel.setList("");
                RetrofitClient.getApiService().createMain(id, mainModel).enqueue(new Callback<MainModel>() {
                    @Override
                    public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "1: " + response.message());
                        } else {
                            Log.d("CommunityMain>>>", "2: " + response.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<MainModel> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.getMessage());
                    }
                });
            }
        });
        Button c2 = findViewById(R.id.main_c4);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainUpdateModel mainModel = new MainUpdateModel();
                mainModel.setTitle("title title dz22");
                mainModel.setBody("body.. ok body\nbody hello22");
                RetrofitClient.getApiService().updateMain(8, mainModel).enqueue(new Callback<MainUpdateModel>() {
                    @Override
                    public void onResponse(Call<MainUpdateModel> call, Response<MainUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "1: " + response.message());
                        } else {
                            Log.d("CommunityMain>>>", "2: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MainUpdateModel> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.getMessage());
                    }
                });
            }
        });
        Button c3 = findViewById(R.id.main_c5);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getApiService().deleteMain(4).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "1: " + response.message());
                        } else {
                            Log.d("CommunityMain>>>", "2: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.getMessage());
                    }
                });
            }
        });
        Button c4 = findViewById(R.id.main_c6);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        test1();
        Button c5 = findViewById(R.id.main_c7);
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            Log.d("CommunityMain>>>", "token: " + task.getResult());
                        }
                    }
                });
            }
        });
    }

    private void test1() {
        Button r1 = findViewById(R.id.main_r1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<MainCommentModel>> call;
                call = RetrofitClient.getApiService().getMainComment(5, 1);
                call.enqueue(new Callback<List<MainCommentModel>>() {
                    @Override
                    public void onResponse(Call<List<MainCommentModel>> call, Response<List<MainCommentModel>> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "1: " + response.body().size());
                        } else {
                            Log.d("CommunityMain>>>", "2: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MainCommentModel>> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.getMessage());
                    }
                });
            }
        });
        Button r2 = findViewById(R.id.main_r2);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler databaseHandler = new DatabaseHandler(Test.this);
                MainCommentModel mainCommentModel = new MainCommentModel();
                mainCommentModel.setBody("comment body\nbody");
                mainCommentModel.setTo("");
                Call<MainCommentModel> call = RetrofitClient.getApiService().createComment(9, 16, mainCommentModel);
                call.enqueue(new Callback<MainCommentModel>() {
                    @Override
                    public void onResponse(Call<MainCommentModel> call, Response<MainCommentModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommunityMain>>>", "1: " + response.message());
                        } else {
                            Log.d("CommunityMain>>>", "2: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MainCommentModel> call, Throwable t) {
                        Log.d("CommunityMain>>>", "3: " + t.getMessage());
                    }
                });
            }
        });
        Button r3 = findViewById(R.id.main_r3);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainCommentUpdateModel mainCommentModel = new MainCommentUpdateModel();
                mainCommentModel.setBody("change body text 22");
                RetrofitClient.getApiService().updateComment(3, mainCommentModel).enqueue(new Callback<MainCommentUpdateModel>() {
                    @Override
                    public void onResponse(Call<MainCommentUpdateModel> call, Response<MainCommentUpdateModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommuntityMain>>>", "1: " + response);
                        } else {
                            Log.d("CommuntityMain>>>", "2: " + response);
                        }
                    }

                    @Override
                    public void onFailure(Call<MainCommentUpdateModel> call, Throwable t) {
                        Log.d("CommuntityMain>>>", "3: " + t);
                    }
                });
            }
        });
        Button r4 = findViewById(R.id.main_r4);
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getApiService().deleteComment(3).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Log.d("CommuntityMain>>>", "1: " + response);
                        } else {
                            Log.d("CommuntityMain>>>", "2: " + response);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("CommuntityMain>>>", "3: " + t);
                    }
                });
            }
        });
    }
}
