package com.dippola.relaxtour.community.translate;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;
import com.dippola.relaxtour.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Translate {

    public static void translateTitleAndBody(Context context, List<String> titleAndBody, String to, TextView title, TextView body, TextView transText, ProgressBar transLoad, LinearLayout transBox) {
        RequestModel requestModel = new RequestModel(titleAndBody, to);
        RetrofitClient.getGoogleTranslation(context).getGoogleTrans(requestModel).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    Log.d("Translate>>>", "2");
                    title.setText(response.body().getData().getTranslations().get(0).getTranslatedText());
                    body.setText(response.body().getData().getTranslations().get(1).getTranslatedText());
                    transText.setText("view original");
                    transLoad.setVisibility(View.INVISIBLE);
                    CommunityMainDetail.isTrans = true;
                    transBox.setEnabled(true);
                } else {
                    try {
                        Toast.makeText(context, "Translate failed: " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    CommunityMainDetail.isTrans = false;
                    transLoad.setVisibility(View.INVISIBLE);
                    transBox.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d("Translate>>>", "3: " + t.getMessage());
                Toast.makeText(context, "Translate failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                CommunityMainDetail.isTrans = false;
                transLoad.setVisibility(View.INVISIBLE);
                transBox.setEnabled(true);
            }
        });
    }

    public static void translateComment(Context context, String bodyString, String to, TextView body, ProgressBar transLoad, ImageView transReturn) {
        List<String> sList = new ArrayList<>();
        sList.add(bodyString);
        RequestModel requestModel = new RequestModel(sList, to);
        RetrofitClient.getGoogleTranslation(context).getGoogleTrans(requestModel).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    body.setText(response.body().getData().getTranslations().get(0).getTranslatedText());
                    transLoad.setVisibility(View.GONE);
                    transReturn.setVisibility(View.VISIBLE);
                } else {
                    try {
                        Toast.makeText(context, "Translate failed: " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        transLoad.setVisibility(View.GONE);
                        transReturn.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(context, "Translate failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                transLoad.setVisibility(View.GONE);
                transReturn.setVisibility(View.VISIBLE);
            }
        });
    }

//    String resp = null;
//    String url = null;
//    String langFrom = null;
//    String langTo = null;
//    String word = null;
//
//    public Translate(String langFrom, String langTo, String text){
//        this.langFrom=langFrom;
//        this.langTo=langTo;
//        this.word=text;
//
//        Async async = new Async();
//        async.execute();
//    }
//
//
//    class Async extends AsyncTask<String,String,String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            try {
//                url = "https://translate.googleapis.com/translate_a/single?"+"client=gtx&"+"sl="+
//                        langFrom +"&tl=" + langTo +"&dt=t&q=" + URLEncoder.encode(word, "UTF-8");
//                URL obj = new URL(url);
//                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//                con.setRequestProperty("User-Agent", "Mozilla/5.0");
//                BufferedReader in = new BufferedReader(     new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//                while ((inputLine = in.readLine()) != null)
//                {    response.append(inputLine);   }
//                in.close();
//                resp = response.toString();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            String temp = "";
//
//            if(resp==null){listener.onFailure("Network Error");}else {
//                try {
//                    JSONArray main = new JSONArray(resp);
//                    JSONArray total = (JSONArray) main.get(0);
//                    for (int i = 0; i < total.length(); i++) {
//                        JSONArray currentLine = (JSONArray) total.get(i);
//                        temp = temp + currentLine.get(0).toString();
//                    }
//
//                    if(temp.length()>2)
//                    {
//                        listener.onSuccess(temp);
//                    }else {listener.onFailure("Invalid Input String");}
//                } catch (JSONException e) {
//                    listener.onFailure(e.getLocalizedMessage());
//                    e.printStackTrace();
//                }}
//            super.onPostExecute(s);
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onCancelled(String s) {
//            super.onCancelled(s);
//        }
//    }
//
//    private TranslateListener listener;
//
//    public void setTranslateListener(TranslateListener listener)
//    {
//        this.listener=listener;
//    }
//
//    public interface TranslateListener
//    {
//        public void onSuccess(String translatedText);
//
//        public void onFailure(String ErrorText);
//    }
}
