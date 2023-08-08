package com.dippola.relaxtour.databasehandler;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.community.main.ForHitsModel;
import com.dippola.relaxtour.community.main.detail.AddFavAskDialog;
import com.dippola.relaxtour.community.main.notification.NotificationItem;
import com.dippola.relaxtour.dialog.AddFavDialog;
import com.dippola.relaxtour.dialog.DeleteFavTitleDialog;
import com.dippola.relaxtour.onboarding.OnBoarding;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.FavPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.MantraPage;
import com.dippola.relaxtour.pages.NaturePage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WaterPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.item.FavListItem;
import com.dippola.relaxtour.pages.item.FavTitleItem;
import com.dippola.relaxtour.pages.item.PageItem;
import com.dippola.relaxtour.dialog.credit_dialog.CreditItem;
import com.dippola.relaxtour.retrofit.model.UserModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    //upgrade
    Context context;
    Activity activity;
    TextView text, percent;
    ProgressBar progressBar, circle;
    ConstraintLayout box;

    private static final int DATABASE_VERSION = 2;
    //1.0.44 = 1

    private static final String DATABASE_NAME = "list.sqlite";
    private static final String DBLOCATION = "/data/data/com.dippola.relaxtour/databases/";
    public static boolean isHaveNewVersionDB = false;

    //pageicon
    private final String PAGE_ICON_TEAM = "create table if not exists pageicon(download BLOB, pro BLOB);";

    //credit
    private final String CREDIT_TEAM = "create table if not exists credit(track TEXT, url TEXT)";

    private final String PLAYING_TEAM = "create table if not exists playing(page INTEGER, position INTEGER, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER, tid TEXT);";
    private final String TRACK_TEAM = "create table if not exists track(page INTEGER, position INTEGER, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER, tid TEXT);";

    private final String FAV_TITLE_TEAM = "create table if not exists favtitle(title TEXT, isopen INTEGER, isedit INTEGER);";
//    private final String FAV_LIST_TEAM = "create table if not exists " + WIND_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER," + COLUMN_FAVTITLENAME + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";
    private final String FAV_LIST_TEAM = "create table if not exists favlist(page INTEGER, position INTEGER, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, favtitlename TEXT, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER, tid TEXT);";

    private final String ISPRO_TEAM = "create table if not exists ispro (ispro INTEGER);";

    //notification table set
    private final String NOTIFICATION_TEAM = "create table if not exists notification(agree INTEGER);";

    //user
    private final String USER_TEAM = "create table if not exists user(id INTEGER, email TEXT, uid TEXT, nickname TEXT, imageurl TEXT, provider TEXT, token TEXT, notification TEXT);";

    //for community hits
    private final String FOR_HITS = "create table if not exists forhits(postid INTEGER, date TEXT);";

    //c notification
    private final String CNOTIFI_TEAM = "create table if not exists cnotification(title TEXT, body TEXT, date TEXT, postid INTEGER);";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public DatabaseHandler(Context context, Activity activity) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.activity = activity;
    }

    public static void setDB(Context context) {
        File folder = new File(DBLOCATION);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        AssetManager assetManager = context.getResources().getAssets();
        File outfile = new File(DBLOCATION + DATABASE_NAME);
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
            filesize = is.available();
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } else {
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ISPRO_TEAM);
        sqLiteDatabase.execSQL(NOTIFICATION_TEAM);
        sqLiteDatabase.execSQL(PAGE_ICON_TEAM);
        sqLiteDatabase.execSQL(PLAYING_TEAM);
        sqLiteDatabase.execSQL(TRACK_TEAM);
        sqLiteDatabase.execSQL(FAV_TITLE_TEAM);
        sqLiteDatabase.execSQL(FAV_LIST_TEAM);
        sqLiteDatabase.execSQL(CREDIT_TEAM);
        sqLiteDatabase.execSQL(USER_TEAM);
        sqLiteDatabase.execSQL(FOR_HITS);
        sqLiteDatabase.execSQL(CNOTIFI_TEAM);
    }

    @Override
    public synchronized void onUpgrade(SQLiteDatabase db, int i, int i1) {
        isHaveNewVersionDB = true;

        AssetManager assetManager = context.getAssets();
        try {
            InputStream newDb = assetManager.open("list.sqlite");
            FileOutputStream newDbCopy = new FileOutputStream(DBLOCATION + "newdb.sqlite");
            byte[] b = new byte[1024];
            int length;
            while ((length = newDb.read(b, 0, 1024)) > 0) {
                newDbCopy.write(b, 0, length);
            }
            newDb.close();
            newDbCopy.close();
            restoreDatabase(db, i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void restoreDatabase(SQLiteDatabase db, int v1) {
        Log.d("DB>>>", "test1");
        SQLiteDatabase newDb = SQLiteDatabase.openDatabase(DBLOCATION + "newdb.sqlite", null, SQLiteDatabase.OPEN_READWRITE);

        //pageicon
        db.execSQL("delete from pageicon");
        ContentValues pageiconValue = new ContentValues();
        pageiconValue.put("download", getPageiconForUpgrade(newDb, "download"));
        pageiconValue.put("pro", getPageiconForUpgrade(newDb, "pro"));
        pageiconValue.put("circlepro", getPageiconForUpgrade(newDb, "circlepro"));
        pageiconValue.put("circledownload", getPageiconForUpgrade(newDb, "circledownload"));
        db.insert("pageicon", null, pageiconValue);

        //credit
        List<CreditItem> creditList = getCreditListForUpgrade(newDb);
        db.execSQL("delete from credit");
        for (int i = 0; i < creditList.size(); i++) {
            ContentValues creditValue = new ContentValues();
            creditValue.put("track", creditList.get(i).getTrack());
            creditValue.put("url", creditList.get(i).getUrl());
            db.insert("credit", null, creditValue);
        }

        Log.d("DB>>>", "test2");
        //version
        if (v1 < 2) {
            Log.d("DB>>>", "test3");
            upgrade1to2(db, newDb, v1);
        } else {
            Log.d("DB>>>", "test4");
            upgrademore2(db, newDb, v1);
        }
        //version

        Log.d("DB>>>", "test5");
        if (db.inTransaction()) {
            Log.d("DB>>>", "test6");
            try {
                Log.d("DB>>>", "test7");
                db.setTransactionSuccessful();
            } finally {
                Log.d("DB>>>", "test8");
                db.endTransaction();
            }
        }
        Log.d("DB>>>", "test9");
        db.execSQL("vacuum");
        db.beginTransaction();
        newDb.close();
        //delete copy database file
        File forDeleteNewDb = new File(DBLOCATION + "newdb.sqlite");
        if (forDeleteNewDb.exists()) {
            Log.d("DB>>>", "test10");
            forDeleteNewDb.delete();
        }
        Log.d("DB>>>", "test11");
        File forDeleteNewDbJournal = new File(DBLOCATION + "newdb.sqlite-journal");
        if (forDeleteNewDbJournal.exists()) {
            Log.d("DB>>>", "test12");
            forDeleteNewDbJournal.delete();
        }
        Log.d("DB>>>", "test13");
        goToNext();
    }

    public void upgrade1to2(SQLiteDatabase db, SQLiteDatabase newDb, int v1) {
        db.execSQL(TRACK_TEAM);
        List<PageItem> nlist = getNewTackListForUpgrade(newDb);
        List<PageItem> olist = getPageListForUpgradeVersion1(db, v1);

        //user
        Cursor cursor = db.rawQuery("select * from user", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
        } else {
            ContentValues userValue = new ContentValues();
            userValue.put("id", 0);
            userValue.put("email", "");
            userValue.put("uid", "");
            userValue.put("nickname", "");
            userValue.put("imageurl", "");
            userValue.put("provider", "");
            userValue.put("token", "");
            userValue.put("notification", "false");
            db.insert("user", null, userValue);
        }

        //pages
        db.execSQL("drop table rain");
        db.execSQL("drop table water");
        db.execSQL("drop table wind");
        db.execSQL("drop table nature");
        db.execSQL("drop table chakra");
        db.execSQL("drop table mantra");
        db.execSQL("drop table hz");
        db.execSQL("alter table favlist add column tid text");
        for (int i = 0; i < nlist.size(); i++) {
            ContentValues c = new ContentValues();
            c.put("page", nlist.get(i).getPage());
            c.put("position", nlist.get(i).getPosition());
            c.put("imgdefault", nlist.get(i).getImgdefault());
            c.put("img", nlist.get(i).getImg());
            c.put("darkdefault", nlist.get(i).getDarkdefault());
            c.put("dark", nlist.get(i).getDark());
            c.put("seek", nlist.get(i).getSeek());
            c.put("isplay", nlist.get(i).getIsplay());
            c.put("time", nlist.get(i).getTime());
            c.put("name", nlist.get(i).getName());
            c.put("ispro", nlist.get(i).getIspro());
            c.put("needdownload", nlist.get(i).getNeeddownload());
            c.put("tid", nlist.get(i).getTid());
            db.insert("track", null, c);
        }
        for (int i = 0; i < olist.size(); i++) {
            db.execSQL("update track set seek + " + olist.get(i).getSeek() + " where name = '" + olist.get(i).getName() + "'");
        }
        db.execSQL("update track set isplay = 1");

        //playlist
        db.execSQL("delete from playing");
        db.execSQL("create table if not exists playingbackup(page INTEGER, position INTEGER, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER, tid TEXT);");
//        for (int i = 0; i < olist.size(); i++) {
//            db.execSQL("update track set seek = " + olist.get(i).getSeek() + " where name = '" + olist.get(i).getName() + "'");
//            db.execSQL("update track set isplay = " + olist.get(i).getIsplay() + " where name = '" + olist.get(i).getName() + "'");
//            if (olist.get(i).getIsplay() == 2) {
//                ContentValues addPlaying = new ContentValues();
//                addPlaying.put("page", olist.get(i).getPage());
//                addPlaying.put("position", olist.get(i).getPosition());
//                addPlaying.put("imgdefault", olist.get(i).getImgdefault());
//                addPlaying.put("img", olist.get(i).getImg());
//                addPlaying.put("darkdefault", olist.get(i).getDarkdefault());
//                addPlaying.put("dark", olist.get(i).getDark());
//                addPlaying.put("seek", olist.get(i).getSeek());
//                addPlaying.put("isplay", olist.get(i).getIsplay());
//                addPlaying.put("time", olist.get(i).getTime());
//                addPlaying.put("name", olist.get(i).getName());
//                addPlaying.put("ispro", olist.get(i).getIspro());
//                addPlaying.put("needdownload", olist.get(i).getNeeddownload());
//                Cursor cursor1 = newDb.rawQuery("select tid from track where name = '" + olist.get(i).getName() + "'", null);
//                cursor1.moveToFirst();
//                addPlaying.put("tid", cursor1.getString(0));
//                cursor1.close();
//                db.insert("playingbackup", null, addPlaying);
//            }
//        }
        db.execSQL("drop table playing");
        db.execSQL("alter table playingbackup rename to playing");

        //favlist
        List<FavListItem> favList = getFavListItemForUpgrade(db);
        db.execSQL("create table if not exists favlistbackup(page INTEGER, position INTEGER, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, favtitlename TEXT, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER, tid TEXT);");
        for (int i = 0; i < favList.size(); i++) {
            ContentValues c = new ContentValues();
            c.put("page", favList.get(i).getPage());
            c.put("position", favList.get(i).getPosition());
            c.put("imgdefault", favList.get(i).getImgdefault());
            c.put("img", favList.get(i).getImg());
            c.put("darkdefault", favList.get(i).getDarkdefault());
            c.put("dark", favList.get(i).getDark());
            c.put("seek", favList.get(i).getSeek());
            c.put("isplay", favList.get(i).getIsplay());
            c.put("favtitlename", favList.get(i).getFavtitlename());
            c.put("time", favList.get(i).getTime());
            c.put("name", favList.get(i).getName());
            c.put("ispro", favList.get(i).getIspro());
            c.put("needdownload", favList.get(i).getNeeddownload());
            Cursor cursor1 = newDb.rawQuery("select tid from track where name = '" + favList.get(i).getName() + "'", null);
            cursor1.moveToFirst();
            c.put("tid", cursor1.getString(0));
            cursor1.close();
            db.insert("favlistbackup", null, c);
        }
        db.execSQL("drop table favlist");
        db.execSQL("alter table favlistbackup rename to favlist");
    }

    public void upgrademore2(SQLiteDatabase db, SQLiteDatabase newDb, int v1) {

    }

    public void goToNext() {
        SharedPreferences preferences;
        try {
            preferences = EncryptedSharedPreferences.create(
                    "checkFirst",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    context.getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        if (!preferences.getBoolean("checkFirst", false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("checkFirst", true);
            editor.apply();
            Intent intent = new Intent(context, OnBoarding.class);
            intent.putExtra("fromSplash", true);
            context.startActivity(intent);
            activity.finish();
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("fromSplash", false);
            Log.d("DB>>>", "14");
            context.startActivity(intent);
            activity.finish();
        }
    }

    public void openDatabase() {
        String dbPath = DBLOCATION + DATABASE_NAME;
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            return;
        }
        sqLiteDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabse() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
    }

    public int getNotificationAgree() {
        int agree;
        openDatabase();
        String sql = "SELECT * FROM notification";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        agree = cursor.getInt(0);
        cursor.close();
        closeDatabse();
        return agree;
    }

    public void changeNotificationAgree(int before) {
        sqLiteDatabase = this.getWritableDatabase();
        if (before == 1) {
            sqLiteDatabase.execSQL("update notification set agree = 0");
        } else {
            sqLiteDatabase.execSQL("update notification set agree = 1");
        }
    }

    public ArrayList<PageItem> getBottomSheetPlayingList() {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();

        openDatabase();
        String sql = "SELECT * FROM playing";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getInt(11), cursor.getString(12));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return pageItems;
    }

    public ArrayList<PageItem> getBottomSheetPlayingListForUpgrade(SQLiteDatabase db) {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();

        String sql = "SELECT * FROM playing";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getInt(11), cursor.getString(12));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        return pageItems;
    }

    public ArrayList<PageItem> getPageList(int page) {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();

        openDatabase();
        String sql = "SELECT * FROM track where page = " + page;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getInt(11), cursor.getString(12));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return pageItems;
    }

    public ArrayList<PageItem> getPageListForUpgradeVersion1(SQLiteDatabase db, int oldVersion) {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            String sql = "SELECT page, position, imgdefault, img, dark, darkdefault, seek, isplay, time, name, ispro, needdownload FROM " + getPageName(i);
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getInt(11), "");
                pageItems.add(pageItem);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return pageItems;
    }

    public ArrayList<PageItem> getNewTackListForUpgrade(SQLiteDatabase db) {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();

        String sql = "SELECT * FROM track";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getInt(11), cursor.getString(12));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        return pageItems;
    }

    public ArrayList<CreditItem> getCreditList() {
        CreditItem creditItem = null;
        ArrayList<CreditItem> creditItems = new ArrayList<>();
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from credit", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            creditItem = new CreditItem(cursor.getString(0), cursor.getString(1));
            creditItems.add(creditItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return creditItems;
    }

    public ArrayList<CreditItem> getCreditListForUpgrade(SQLiteDatabase db) {
        CreditItem creditItem = null;
        ArrayList<CreditItem> creditItems = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from credit", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            creditItem = new CreditItem(cursor.getString(0), cursor.getString(1));
            creditItems.add(creditItem);
            cursor.moveToNext();
        }
        cursor.close();
        return creditItems;
    }

    public void deletePlayingList(String tid) {//무료회원이 pro인 트랙 있으면 지우기
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select page from track where tid = '" + tid + "'", null);
        cursor.moveToFirst();
        int page = cursor.getInt(0);
        cursor.close();
        if (page != 4) {
            sqLiteDatabase.execSQL("delete from playing where page = " + page);
        } else {
            sqLiteDatabase.execSQL("delete from playing where tid = '" + tid + "'");
        }
        sqLiteDatabase.execSQL("update track set isplay = 1 where tid = '" + tid + "'");
    }

    String getPageName(int page) {
        if (page == 1) {
            return "rain";
        } else if (page == 2) {
            return "water";
        } else if (page == 3) {
            return "wind";
        } else if (page == 4) {
            return "nature";
        } else if (page == 5) {
            return "chakra";
        } else if (page == 6) {
            return "mantra";
        } else if (page == 7) {
            return "hz";
        } else {
            return "null";
        }
    }

    public void setPlay1(int page, String tid) {//같은 page에 다른 재생중인거 있을때 지우고 새로운 재생할거 트랙 재생하기
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update track set isplay = 1 where page = " + page);//해당페이지 재생중인거 isplay 1로 변경
        sqLiteDatabase.execSQL("delete from playing where page = " + page);//bottom list에서 해당 페이지 있는거 지우기
        sqLiteDatabase.execSQL("update track set isplay = 2 where tid = '" + tid + "'");//새로 재생할거 isplay2로 바꾸기
        sqLiteDatabase.execSQL("insert into playing select * from track where tid = '" + tid + "'");//새로 재생할거 bottom list에 insert
    }

    public void setIsPlay1(String tid) {
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select page, position from track where tid = '" + tid + "'", null);
        cursor.moveToFirst();
        int page = cursor.getInt(0);
        int position = cursor.getInt(1);
        cursor.close();
        sqLiteDatabase.execSQL("update track set isplay = 1 where tid = " + tid);
        if (page == 1 && RainPage.adapter != null) {
            RainPage.arrayList.get(position - 1).setIsplay(1);
            RainPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 2 && WaterPage.adapter != null) {
            WaterPage.arrayList.get(position - 1).setIsplay(1);
            WaterPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 3 && WindPage.adapter != null) {
            WindPage.arrayList.get(position - 1).setIsplay(1);
            WindPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 4 && NaturePage.adapter != null) {
            NaturePage.arrayList.get(position - 1).setIsplay(1);
            NaturePage.adapter.notifyItemChanged(position - 1);
        } else if (page == 5 && ChakraPage.adapter != null) {
            ChakraPage.arrayList.get(position - 1).setIsplay(1);
            ChakraPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 6 && MantraPage.adapter != null) {
            MantraPage.arrayList.get(position - 1).setIsplay(1);
            MantraPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 7 && HzPage.adapter != null) {
            HzPage.arrayList.get(position - 1).setIsplay(1);
            HzPage.adapter.notifyItemChanged(position - 1);
        }
    }

    public void setIsPlayPage4(String tid) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update track set isplay = 2 where tid = '" + tid + "'");
        sqLiteDatabase.execSQL("insert into playing select * from track where tid = '" + tid + "'");
    }

    public void changePageSeek(int progress, String tid) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update playing set seek = " + progress + " where tid = " + "'" + tid + "'");
        sqLiteDatabase.execSQL("update track set seek = " + progress + " where tid = '" + tid + "'");
    }

    public void checkTitleAlready(Context context, String title) {
        List<String> titles = new ArrayList<>();
        sqLiteDatabase = this.getWritableDatabase();
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select title from favtitle", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            titles.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        if (titles.contains(title)) {
            Toast.makeText(context, "'" + title + "' playlist with the same name already exists", Toast.LENGTH_SHORT).show();
        } else {
            addFavTitleList(context, title);
        }
    }

    public boolean isContainsTitleAlreadyWhenEditTitle(String beforeTitle, String newTitle) {
        if (beforeTitle.equals(newTitle)) {
            return false;
        } else {
            List<String> titles = new ArrayList<>();
            sqLiteDatabase = this.getWritableDatabase();
            openDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select title from favtitle", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (cursor.getString(0).equals(newTitle)) {
                    titles.add(cursor.getString(0));
                }
                cursor.moveToNext();
            }
            cursor.close();
            closeDatabse();
            if (titles.contains(newTitle)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void addFavTitleList(Context context, String title1) {
        FavTitleItem favTitleItem = null;
        sqLiteDatabase = this.getWritableDatabase();

        if (MainActivity.bottomSheetPlayList.size() != 0) {
            String s = "'" + title1 + "'" + ", 1, 1";
//            sqLiteDatabase.execSQL("insert into favtitle values (" + "'" + title1 + "'" + "," + 1 + "," + 1 + ")");
            sqLiteDatabase.execSQL("insert into favtitle values (" + s + ")");
            favTitleItem = new FavTitleItem(title1, 1, 1);
            FavPage.favTitleItemArrayList.add(favTitleItem);
            FavPage.adapter.notifyItemInserted(FavPage.favTitleItemArrayList.size() - 1);
            FavPage.adapter.notifyDataSetChanged();
            if (AddFavDialog.alertDialog.isShowing()) {
                AddFavDialog.alertDialog.dismiss();
            }
            addFavList(context, title1);
        }
    }

    public void addFavList(Context context, String title) {
        sqLiteDatabase = this.getWritableDatabase();

        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("page", MainActivity.bottomSheetPlayList.get(i).getPage());
            contentValues.put("position", MainActivity.bottomSheetPlayList.get(i).getPosition());
            contentValues.put("imgdefault", MainActivity.bottomSheetPlayList.get(i).getImgdefault());
            contentValues.put("img", MainActivity.bottomSheetPlayList.get(i).getImg());
            contentValues.put("darkdefault", MainActivity.bottomSheetPlayList.get(i).getDarkdefault());
            contentValues.put("dark", MainActivity.bottomSheetPlayList.get(i).getDark());
            contentValues.put("seek", MainActivity.bottomSheetPlayList.get(i).getSeek());
            contentValues.put("isplay", MainActivity.bottomSheetPlayList.get(i).getIsplay());
            contentValues.put("favtitlename", title);
            contentValues.put("time", MainActivity.bottomSheetPlayList.get(i).getTime());
            contentValues.put("name", MainActivity.bottomSheetPlayList.get(i).getName());
            contentValues.put("ispro", MainActivity.bottomSheetPlayList.get(i).getIspro());
            contentValues.put("needdownload", MainActivity.bottomSheetPlayList.get(i).getNeeddownload());
            contentValues.put("tid", MainActivity.bottomSheetPlayList.get(i).getTid());
            sqLiteDatabase.insert("favlist", null, contentValues);
        }
        Toast.makeText(context, "success add fav list!", Toast.LENGTH_SHORT).show();
        if (FavPage.favTitleItemArrayList.size() != 0) {
            FavPage.message.setVisibility(View.GONE);
        }
    }

    boolean haveSame(List<String> nowPnps, List<String> favtitles) {
        boolean isSame = false;
        sqLiteDatabase = this.getWritableDatabase();
        List<String> checkWithThisList = new ArrayList<>();
        for (int i = 0; i < favtitles.size(); i++) {
            Cursor cursor = sqLiteDatabase.rawQuery("select pnp from favlist where favtitlename = " + "'" + favtitles.get(i) + "'", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                checkWithThisList.add(cursor.getString(0));
                cursor.moveToNext();
            }
            if (listEqualsIgnoreOrder(checkWithThisList, nowPnps)) {
                isSame = true;
                break;
            } else {
                isSame = false;
            }
        }
        if (isSame) {
            return true;
        } else {
            return false;
        }
    }

    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {//순서 무시 똑같으면 true
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public ArrayList<FavTitleItem> getFavTitleList() {
        FavTitleItem favTitleItem = null;
        ArrayList<FavTitleItem> favTitleItems = new ArrayList<>();

        openDatabase();
        String sql = "SELECT * FROM favtitle";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            favTitleItem = new FavTitleItem(cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
            if (favTitleItem.getTitle() != null) {
                favTitleItems.add(favTitleItem);
            }
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return favTitleItems;
    }
    public ArrayList<FavTitleItem> getFavTitleListForUpgrade(SQLiteDatabase db) {
        FavTitleItem favTitleItem = null;
        ArrayList<FavTitleItem> favTitleItems = new ArrayList<>();

        String sql = "SELECT * FROM favtitle";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            favTitleItem = new FavTitleItem(cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
            if (favTitleItem.getTitle() != null) {
                favTitleItems.add(favTitleItem);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return favTitleItems;
    }

    public ArrayList<FavListItem> getFavListItem(String title) {
        FavListItem favListItem = null;
        ArrayList<FavListItem> favListItems = new ArrayList<>();
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from favlist where favtitlename = " + "'" + title + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            favListItem = new FavListItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getString(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12), cursor.getString(13));
            favListItems.add(favListItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return favListItems;
    }

    public ArrayList<FavListItem> getFavListItemForUpgrade(SQLiteDatabase db) {
        FavListItem favListItem = null;
        ArrayList<FavListItem> favListItems = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from favlist", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            favListItem = new FavListItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getString(11), cursor.getInt(11), cursor.getInt(12), "");
            favListItems.add(favListItem);
            cursor.moveToNext();
        }
        cursor.close();
        return favListItems;
    }

    public ArrayList<FavListItem> getListInCommunityShare(String fromserver) {
        FavListItem favListItem = null;
        ArrayList<FavListItem> favListItems = new ArrayList<>();
        openDatabase();

        String[] list = fromserver.split("●");
        for (int i = 0; i < list.length; i++) {
            if (i != 0) {
                String tid = list[i].split("-")[0];
                Cursor cursor = sqLiteDatabase.rawQuery("select * from track where tid = '" + tid + "'", null);
                cursor.moveToFirst();
                favListItem = new FavListItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getString(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12), cursor.getString(13));
                favListItems.add(favListItem);
                cursor.close();
            }
        }
        closeDatabse();
        return favListItems;
    }

    public List<PageItem> getAllTrackTidList() {
        PageItem pageItem = null;
        List<PageItem> result = new ArrayList<>();
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from track", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getInt(11), cursor.getString(12));
            result.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    public void removeFavList(String title) {
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select _rowid_ from favtitle where title = " + "'" + title + "'", null);
        cursor.moveToFirst();
        int rowid = cursor.getInt(0);
        sqLiteDatabase.execSQL("delete from favtitle where _rowid_ = " + rowid);
        sqLiteDatabase.execSQL("delete from favlist where favtitlename = " + "'" + title + "'");
        FavPage.favTitleItemArrayList.remove(rowid - 1);
        FavPage.adapter.notifyItemRemoved(rowid - 1);
        FavPage.adapter.notifyDataSetChanged();
        sqLiteDatabase.execSQL("vacuum");
        cursor.close();
        closeDatabse();
        if (DeleteFavTitleDialog.alertDialog.isShowing()) {
            DeleteFavTitleDialog.alertDialog.dismiss();
        }
        if (FavPage.favTitleItemArrayList.size() == 0) {
            FavPage.message.setVisibility(View.VISIBLE);
        }
    }

    public void changeFavListIsOpen(int isopen, String title) {
        sqLiteDatabase = this.getWritableDatabase();
        if (isopen == 1) {
            sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
            sqLiteDatabase.execSQL("update favtitle set isopen = 2 where title = " + "'" + title + "'");
        } else if (isopen == 2) {
            sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
        }
    }

    public void changeIsOpenWhenFavPageOnPause() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update favtitle set isopen = 1");
        sqLiteDatabase.execSQL("update favtitle set isedit = 1");
    }

    public void changeFavListToEdit(String beforeTitle, String afterTitle, ArrayList<FavListItem> arrayList) {
        sqLiteDatabase = this.getWritableDatabase();
        if (!beforeTitle.equals(afterTitle)) {
            sqLiteDatabase.execSQL("update favtitle set title = " + "'" + afterTitle + "'" + " where title = " + "'" + beforeTitle + "'");
        }
        sqLiteDatabase.execSQL("delete from favlist where favtitlename = " + "'" + beforeTitle + "'");
        for (int i = 0; i < arrayList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("page", arrayList.get(i).getPage());
            contentValues.put("position", arrayList.get(i).getPosition());
            contentValues.put("imagedefault", arrayList.get(i).getImgdefault());
            contentValues.put("img", arrayList.get(i).getImg());
            contentValues.put("darkdefault", arrayList.get(i).getDarkdefault());
            contentValues.put("dark", arrayList.get(i).getDark());
            contentValues.put("seek", arrayList.get(i).getSeek());
            contentValues.put("isplay", arrayList.get(i).getIsplay());
            contentValues.put("favtitlename", arrayList.get(i).getFavtitlename());
            contentValues.put("time", arrayList.get(i).getTime());
            contentValues.put("name", arrayList.get(i).getName());
            contentValues.put("ispro", arrayList.get(i).getIspro());
            contentValues.put("needdownload", arrayList.get(i).getNeeddownload());
            sqLiteDatabase.insert("favlist", null, contentValues);
        }
    }

    public void deleteAllPlayinglist(ArrayList<Integer> pagelist, ArrayList<Integer> positionlist, String title) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from playing");
        for (int i = 0; i < pagelist.size(); i++) {
            if (pagelist.get(i) == 1 && RainPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update track set isplay = 1 where isplay = 2 and page = 1");
                RainPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                RainPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                RainPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 2 && WaterPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update track set isplay = 1 where isplay = 2 and page = 2");
                WaterPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                WaterPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                WaterPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 3 && WindPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update track set isplay = 1 where isplay = 2 and page = 3");
                WindPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                WindPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                WindPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 4 && NaturePage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update track set isplay = 1 where isplay = 2 and page = 4");
                NaturePage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                NaturePage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                NaturePage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 5 && ChakraPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update track set isplay = 1 where isplay = 2 and page = 5");
                ChakraPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                ChakraPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                ChakraPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 6 && MantraPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update track set isplay = 1 where isplay = 2 and page = 6");
                MantraPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                MantraPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                MantraPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 7 && HzPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update track set isplay = 1 where isplay = 2 and page = 7");
                HzPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                HzPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                HzPage.adapter.notifyDataSetChanged();
            }
        }
        Log.d("DatabaseHandler>>>", "1");
        sqLiteDatabase.execSQL("vacuum");

        Log.d("DatabaseHandler>>>", "0");
        addFavListInPlayinglist(title);
    }

    public void deleteAllPlayinglistWhenDeleteInBottomSheet() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from playing");
        sqLiteDatabase.execSQL("update track set isplay = 1 where isplay = 2");
    }

    public void addFavListInPlayinglist(String title) {
        sqLiteDatabase = this.getWritableDatabase();
        PageItem pageItem = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from favlist where favtitlename = " + "'" + title + "'", null);
        cursor.moveToFirst();
        int count = 0;
        while (!cursor.isAfterLast()) {
            count += 1;
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12), cursor.getString(13));
            MainActivity.bottomSheetPlayList.add(pageItem);
            ContentValues contentValues = new ContentValues();
            contentValues.put("page", cursor.getInt(0));
            contentValues.put("position", cursor.getInt(1));
//            contentValues.put("pnp", cursor.getString(2));
            contentValues.put("imgdefault", cursor.getBlob(2));
            contentValues.put("img", cursor.getBlob(3));
            contentValues.put("darkdefault", cursor.getBlob(4));
            contentValues.put("dark", cursor.getBlob(5));
            contentValues.put("seek", cursor.getInt(6));
            contentValues.put("isplay", 7);
            contentValues.put("time", cursor.getInt(9));
            contentValues.put("name", cursor.getString(10));
            contentValues.put("ispro", cursor.getInt(11));
            contentValues.put("needdownload", cursor.getInt(12));
            contentValues.put("tid", cursor.getString(13));
            setIsPlay2WhenPlayInFavTitle(cursor.getString(13));
            sqLiteDatabase.insert("playing", null, contentValues);
            cursor.moveToNext();
        }
        MainActivity.bottomSheetAdapter.notifyItemRangeInserted(0, count);
        MainActivity.bottomSheetAdapter.notifyDataSetChanged();
        cursor.close();
        closeDatabse();
    }

    private void setIsPlay2WhenPlayInFavTitle(String tid) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update track set isplay = 2 where tid = '" + tid + "'");
    }

    public void whenAppKillTask() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
        sqLiteDatabase.execSQL("update favtitle set isedit = 1 where isedit = 2");
        close();
    }

    public Bitmap getPageicon(String what) {
        if (what.equals("download")) {
            openDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select download from pageicon where _rowid_ = 1", null);
            cursor.moveToFirst();
            byte[] icon = cursor.getBlob(0);
            cursor.close();
            closeDatabse();
            return BitmapFactory.decodeByteArray(icon, 0, icon.length);
        } else if (what.equals("pro")) {
            openDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select pro from pageicon where _rowid_ = 1", null);
            cursor.moveToFirst();
            byte[] icon = cursor.getBlob(0);
            cursor.close();
            closeDatabse();
            return BitmapFactory.decodeByteArray(icon, 0, icon.length);
        } else if (what.equals("circlepro")) {
            openDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select circlepro from pageicon where _rowid_ = 1", null);
            cursor.moveToFirst();
            byte[] icon = cursor.getBlob(0);
            cursor.close();
            closeDatabse();
            return BitmapFactory.decodeByteArray(icon, 0, icon.length);
        } else {
            openDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select circledownload from pageicon where _rowid_ = 1", null);
            cursor.moveToFirst();
            byte[] icon = cursor.getBlob(0);
            cursor.close();
            closeDatabse();
            return BitmapFactory.decodeByteArray(icon, 0, icon.length);
        }
    }

    public byte[] getPageiconForUpgrade(SQLiteDatabase db, String what) {
        if (what.equals("download")) {
            Cursor cursor = db.rawQuery("select download from pageicon where _rowid_ = 1", null);
            cursor.moveToFirst();
            byte[] icon = cursor.getBlob(0);
            cursor.close();
            return icon;
        } else if (what.equals("pro")) {
            Cursor cursor = db.rawQuery("select pro from pageicon where _rowid_ = 1", null);
            cursor.moveToFirst();
            byte[] icon = cursor.getBlob(0);
            cursor.close();
            return icon;
        } else if (what.equals("circlepro")) {
            Cursor cursor = db.rawQuery("select circlepro from pageicon where _rowid_ = 1", null);
            cursor.moveToFirst();
            byte[] icon = cursor.getBlob(0);
            cursor.close();
            return icon;
        } else {
            Cursor cursor = db.rawQuery("select circledownload from pageicon where _rowid_ = 1", null);
            cursor.moveToFirst();
            byte[] icon = cursor.getBlob(0);
            cursor.close();
            return icon;
        }
    }

    public int getIsProUser() {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ispro FROM ispro", null);
        cursor.moveToFirst();
        int ispro = cursor.getInt(0);
        cursor.close();
        closeDatabse();
        return ispro;
    }

    public int getPageUseTid(String tid) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select page from track where tid = '" + tid + "'", null);
        cursor.moveToFirst();
        int page = cursor.getInt(0);
        cursor.close();
        closeDatabse();
        return page;
    }

    public void changeIsProUserFromSplash(int ispro) {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = this.getWritableDatabase();
        }
        sqLiteDatabase.execSQL("update ispro set ispro = " + ispro);
    }

    public void changeIsProUserFromPremium(int ispro) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update ispro set ispro = " + ispro);
        closeDatabse();
    }

    public PageItem getPageItemInStorageManage(String tid) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from track where tid = '" + tid + "'", null);
        cursor.moveToFirst();
        PageItem pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getInt(11), cursor.getString(12));
        cursor.close();
        closeDatabse();
        return pageItem;
    }

    public String getTrackName(String tid) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select name from track where tid = '" + tid + "'", null);
        cursor.moveToFirst();
        String name = cursor.getString(0);
        cursor.close();
        closeDatabse();
        return name;
    }

    public void changePageSeekWhenPlayInFavTitle(String tid, int volumn) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update track set seek = " + volumn + " where tid = '" + tid + "'");
    }

    public void changeFavTitleIsEdit(String title, int isedit) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update favtitle set isedit = " + isedit + " where title = " + "'" + title + "'");
    }

    public int getFavTitleIsEdit(String title) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select isedit from favtitle where title = " + "'" + title + "'", null);
        cursor.moveToFirst();
        int isedit = cursor.getInt(0);
        cursor.close();
        closeDatabse();
        return isedit;
    }

    public UserModel getUserModel() {
        UserModel userModel = new UserModel();
        openDatabase();
        String sql = "SELECT * FROM user";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            userModel.setId(cursor.getInt(0));
            userModel.setEmail(cursor.getString(1));
            userModel.setUid(cursor.getString(2));
            userModel.setNickname(cursor.getString(3));
            userModel.setImageurl(cursor.getString(4));
            userModel.setProvider(cursor.getString(5));
            userModel.setToken(cursor.getString(6));
            userModel.setNotification(changeStringToBoolean(cursor.getString(7)));
            cursor.close();
            closeDatabse();
            return userModel;
        } else {
            return new UserModel(0, "", "", "", "", "", "", false);
        }
    }

    public void makeDbUserWhenSignIn(int id, String email, String uid, String nickname, String token, String imageurl, String provider, boolean notification) {
        sqLiteDatabase = this.getWritableDatabase();
        deleteUserProfile();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("email", email);
        contentValues.put("uid", uid);
        contentValues.put("provider", provider);
        contentValues.put("nickname", nickname);
        contentValues.put("token", token);
        contentValues.put("imageurl", imageurl);
        contentValues.put("notification", changeBooleanToString(notification));
        sqLiteDatabase.insert("user", null, contentValues);
        closeDatabse();
    }

    public void createUserProfile(int id, String email, String uid, String provider) {
        sqLiteDatabase = this.getWritableDatabase();
        deleteUserProfile();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("email", email);
        contentValues.put("uid", uid);
        contentValues.put("provider", provider);
        contentValues.put("notification", "true");
        sqLiteDatabase.insert("user", null, contentValues);
        closeDatabse();
    }

    public void updateUserProfile(String nickname, String imageurl, String uid) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update user set nickname = " + "'" + nickname + "'" + ", imageurl = " + "'" + imageurl + "'" + " where uid = " + "'" + uid + "'");
        closeDatabse();
    }

    public void updateNotification(Boolean b, String uid) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update user set notification =" + "'" + changeBooleanToString(b) + "'" + " where uid = " + "'" + uid + "'");
    }

    public void deleteUserProfile() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from user");
    }

    public String changeBooleanToString(boolean b) {
        if (b) {
            return "true";
        } else {
            return "false";
        }
    }

    public Boolean changeStringToBoolean(String s) {
        if (s.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public byte[] getTrackImageLight(String tid) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT img from track where tid = '" + tid + "'", null);
        cursor.moveToFirst();
        byte[] result = cursor.getBlob(0);
        cursor.close();
        closeDatabse();
        return result;
    }

    public byte[] getTrackImageDark(String tid) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT dark from track where tid = '" + tid + "'", null);
        cursor.moveToFirst();
        byte[] result = cursor.getBlob(0);
        cursor.close();
        closeDatabse();
        return result;
    }

    public List<ForHitsModel> getViewPostList() {
        ForHitsModel model = null;
        List<ForHitsModel> hitsList = new ArrayList<>();
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select postid from viewpost", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            model = new ForHitsModel(cursor.getInt(0), cursor.getString(1));
            hitsList.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return hitsList;
    }

    public String getPostDate(int postid) {
        String result = "";
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select date from forhits where postid = " + postid, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = cursor.getString(0);
            cursor.close();
        }
        closeDatabse();
        return result;
    }

    public void insertForHits(int postid, String date) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("postid", postid);
        contentValues.put("date", date);
        sqLiteDatabase.insert("forhits", null, contentValues);
    }

    public void updateForHits(int postid, String date) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update forhits set date = " + "'" + date + "'" + " where postid = " + postid);
    }

    public List<NotificationItem> getNotificationList(int start, int end) {
        NotificationItem item = null;
        List<NotificationItem> list = new ArrayList<>();
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from cnotification order by date desc", null);
        cursor.moveToPosition(start);
        while (cursor.getPosition() != end) {
            if (cursor.isAfterLast()) {
                break;
            }
            item = new NotificationItem(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return list;
    }

    public void insertCNotification(String title, String body, String date) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("body", body);
        contentValues.put("date", date);
        sqLiteDatabase.insert("cnotification", null, contentValues);
    }

    public void deleteCNotification(String body, String date) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from cnotification where body = " + "'" + body + "'" + " and date = " + "'" + date + "'");
    }

    public void deleteNotificationAll() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from cnotification");
    }

    public void AddFavTitleFromPost(String title, String list, Activity dialog, Context ctx) {
        sqLiteDatabase = this.getWritableDatabase();
        List<String> myTitleList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select title from favtitle", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            myTitleList.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();

        if (myTitleList.contains(title)) {
            Toast.makeText(context, "There is already a playlist with the same name. Please save it with a different name.", Toast.LENGTH_SHORT).show();
        } else {
            String s = "'" + title + "'" + ", 1, 1";
            sqLiteDatabase.execSQL("insert into favtitle values (" + s + ")");
            String[] li = list.split("●");
            List<PageItem> lists = getAllTrackTidList();
            for (int i = 0; i < li.length; i++) {
                for (int ii = 0; ii < lists.size(); ii++) {
                    if (li[i].split("-")[0].equals(lists.get(ii).getTid())) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("page", lists.get(ii).getPage());
                        contentValues.put("position", lists.get(ii).getPosition());
                        contentValues.put("imgdefault", lists.get(ii).getImgdefault());
                        contentValues.put("img", lists.get(ii).getImg());
                        contentValues.put("darkdefault", lists.get(ii).getDarkdefault());
                        contentValues.put("dark", lists.get(ii).getDark());
                        contentValues.put("seek", li[i].split("-")[1]);
                        contentValues.put("isplay", lists.get(ii).getIsplay());
                        contentValues.put("favtitlename", title);
                        contentValues.put("time", lists.get(ii).getTime());
                        contentValues.put("name", lists.get(ii).getName());
                        contentValues.put("ispro", lists.get(ii).getIspro());
                        contentValues.put("needdownload", lists.get(ii).getNeeddownload());
                        contentValues.put("tid", lists.get(ii).getTid());
                        sqLiteDatabase.insert("favlist", null, contentValues);
                        break;
                    }
                }
            }
            if (FavPage.adapter != null) {
                FavPage.adapter.notifyDataSetChanged();
            }
            Toast.makeText(ctx, "Added to the list", Toast.LENGTH_SHORT).show();
            dialog.finish();
        }
        closeDatabse();
    }
}

//package com.dippola.relaxtour.databasehandler;
//
//import android.app.Activity;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.res.AssetManager;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Log;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.security.crypto.EncryptedSharedPreferences;
//import androidx.security.crypto.MasterKeys;
//
//import com.dippola.relaxtour.MainActivity;
//import com.dippola.relaxtour.community.main.ForHitsModel;
//import com.dippola.relaxtour.community.main.detail.AddFavAskDialog;
//import com.dippola.relaxtour.community.main.notification.NotificationItem;
//import com.dippola.relaxtour.dialog.AddFavDialog;
//import com.dippola.relaxtour.dialog.DeleteFavTitleDialog;
//import com.dippola.relaxtour.onboarding.OnBoarding;
//import com.dippola.relaxtour.pages.ChakraPage;
//import com.dippola.relaxtour.pages.FavPage;
//import com.dippola.relaxtour.pages.HzPage;
//import com.dippola.relaxtour.pages.MantraPage;
//import com.dippola.relaxtour.pages.NaturePage;
//import com.dippola.relaxtour.pages.RainPage;
//import com.dippola.relaxtour.pages.WaterPage;
//import com.dippola.relaxtour.pages.WindPage;
//import com.dippola.relaxtour.pages.item.FavListItem;
//import com.dippola.relaxtour.pages.item.FavTitleItem;
//import com.dippola.relaxtour.pages.item.PageItem;
//import com.dippola.relaxtour.dialog.credit_dialog.CreditItem;
//import com.dippola.relaxtour.retrofit.model.UserModel;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//public class DatabaseHandler extends SQLiteOpenHelper {
//
//    private SQLiteDatabase sqLiteDatabase;
//
//    //upgrade
//    Context context;
//    Activity activity;
//    TextView text, percent;
//    ProgressBar progressBar, circle;
//    ConstraintLayout box;
//
//    private static final int DATABASE_VERSION = 1;
//    //1.0.44 = 1
//
//    private static final String DATABASE_NAME = "list.sqlite";
//    private static final String DBLOCATION = "/data/data/com.dippola.relaxtour/databases/";
//    public static boolean isHaveNewVersionDB = false;
//
//    //pageicon
//    private final String PAGE_ICON_TEAM = "create table if not exists pageicon(download BLOB, pro BLOB);";
//
//    //credit
//    private final String CREDIT_TEAM = "create table if not exists credit(track TEXT, url TEXT)";
//
//    private final String PLAYING_TEAM = "create table if not exists playing(page INTEGER, position INTEGER, pnp TEXT, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER);";
//    private final String RAIN_TEAM = "create table if not exists rain(page INTEGER, position INTEGER, pnp TEXT, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER);";
//    private final String WATER_TEAM = "create table if not exists water(page INTEGER, position INTEGER, pnp TEXT, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER);";
//    private final String WIND_TEAM = "create table if not exists wind(page INTEGER, position INTEGER, pnp TEXT, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER);";
//    private final String NATURE_TEAM = "create table if not exists nature(page INTEGER, position INTEGER, pnp TEXT, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER);";
//    private final String CHAKRA_TEAM = "create table if not exists chakra(page INTEGER, position INTEGER, pnp TEXT, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER);";
//    private final String MANTRA_TEAM = "create table if not exists mantra(page INTEGER, position INTEGER, pnp TEXT, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER);";
//    private final String HZ_TEAM = "create table if not exists hz(page INTEGER, position INTEGER, pnp TEXT, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER);";
//
//    private final String FAV_TITLE_TEAM = "create table if not exists favtitle(title TEXT, isopen INTEGER, isedit INTEGER);";
//    //    private final String FAV_LIST_TEAM = "create table if not exists " + WIND_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER," + COLUMN_FAVTITLENAME + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";
//    private final String FAV_LIST_TEAM = "create table if not exists favlist(page INTEGER, position INTEGER, pnp TEXT, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, favtitlename TEXT, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER);";
//
//    private final String ISPRO_TEAM = "create table if not exists ispro (ispro INTEGER);";
//
//    //notification table set
//    private final String NOTIFICATION_TEAM = "create table if not exists notification(agree INTEGER);";
//
//    //user
//    private final String USER_TEAM = "create table if not exists user(id INTEGER, email TEXT, uid TEXT, nickname TEXT, imageurl TEXT, provider TEXT, token TEXT, notification TEXT);";
//
//    //for community hits
//    private final String FOR_HITS = "create table if not exists forhits(postid INTEGER, date TEXT);";
//
//    //c notification
//    private final String CNOTIFI_TEAM = "create table if not exists cnotification(title TEXT, body TEXT, date TEXT, postid INTEGER);";
//
//    public DatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
//    }
//
//    public DatabaseHandler(Context context, Activity activity) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
//        this.activity = activity;
//    }
//
//    public static void setDB(Context context) {
//        File folder = new File(DBLOCATION);
//        if (!folder.exists()) {
//            folder.mkdirs();
//        }
//        AssetManager assetManager = context.getResources().getAssets();
//        File outfile = new File(DBLOCATION + DATABASE_NAME);
//        InputStream is = null;
//        FileOutputStream fo = null;
//        long filesize = 0;
//        try {
//            is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
//            filesize = is.available();
//            if (outfile.length() <= 0) {
//                byte[] tempdata = new byte[(int) filesize];
//                is.read(tempdata);
//                is.close();
//                outfile.createNewFile();
//                fo = new FileOutputStream(outfile);
//                fo.write(tempdata);
//                fo.close();
//            } else {
//            }
//        } catch (IOException e) {
//        }
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(ISPRO_TEAM);
//        sqLiteDatabase.execSQL(NOTIFICATION_TEAM);
//        sqLiteDatabase.execSQL(PAGE_ICON_TEAM);
//        sqLiteDatabase.execSQL(PLAYING_TEAM);
//        sqLiteDatabase.execSQL(RAIN_TEAM);
//        sqLiteDatabase.execSQL(WATER_TEAM);
//        sqLiteDatabase.execSQL(WIND_TEAM);
//        sqLiteDatabase.execSQL(NATURE_TEAM);
//        sqLiteDatabase.execSQL(CHAKRA_TEAM);
//        sqLiteDatabase.execSQL(MANTRA_TEAM);
//        sqLiteDatabase.execSQL(HZ_TEAM);
//        sqLiteDatabase.execSQL(FAV_TITLE_TEAM);
//        sqLiteDatabase.execSQL(FAV_LIST_TEAM);
//        sqLiteDatabase.execSQL(CREDIT_TEAM);
//        sqLiteDatabase.execSQL(USER_TEAM);
//        sqLiteDatabase.execSQL(FOR_HITS);
//        sqLiteDatabase.execSQL(CNOTIFI_TEAM);
//    }
//
//    @Override
//    public synchronized void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        isHaveNewVersionDB = true;
//        List<List<PageItem>> lists = new ArrayList<>();
//        for (int i3 = 1; i3 < 8; i3++) {
//            lists.add(getPageListForUpgradeVersion1(db, i3, i));
//        }//back up
//
//        AssetManager assetManager = context.getAssets();
//        try {
//            InputStream newDb = assetManager.open("list.sqlite");
//            FileOutputStream newDbCopy = new FileOutputStream(DBLOCATION + "newdb.sqlite");
//            byte[] b = new byte[1024];
//            int length;
//            while ((length = newDb.read(b, 0, 1024)) > 0) {
//                newDbCopy.write(b, 0, length);
//            }
//            newDb.close();
//            newDbCopy.close();
////            restoreDatabase(db, lists, i);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
////    public void restoreDatabase(SQLiteDatabase db, List<List<PageItem>> lists, int v1) {
////        SQLiteDatabase newDb = SQLiteDatabase.openDatabase(DBLOCATION + "newdb.sqlite", null, SQLiteDatabase.OPEN_READWRITE);
////        List<List<PageItem>> nlists = new ArrayList<>();
////        for (int i3 = 1; i3 < 8; i3++) {
////            nlists.add(getPageListForUpgrade(newDb, i3));
////        }
////
////        //pageicon
////        db.execSQL("delete from pageicon");
////        ContentValues pageiconValue = new ContentValues();
////        pageiconValue.put("download", getPageiconForUpgrade(newDb, "download"));
////        pageiconValue.put("pro", getPageiconForUpgrade(newDb, "pro"));
////        pageiconValue.put("circlepro", getPageiconForUpgrade(newDb, "circlepro"));
////        pageiconValue.put("circledownload", getPageiconForUpgrade(newDb, "circledownload"));
////        db.insert("pageicon", null, pageiconValue);
////
////        //credit
////        List<CreditItem> creditList = getCreditListForUpgrade(newDb);
////        db.execSQL("delete from credit");
////        for (int i = 0; i < creditList.size(); i++) {
////            ContentValues creditValue = new ContentValues();
////            creditValue.put("track", creditList.get(i).getTrack());
////            creditValue.put("url", creditList.get(i).getUrl());
////            db.insert("credit", null, creditValue);
////        }
////
////        //user
////        if (v1 < 2) {
////            ContentValues userValue = new ContentValues();
////            userValue.put("id", 0);
////            userValue.put("email", "");
////            userValue.put("uid", "");
////            userValue.put("nickname", "");
////            userValue.put("imageurl", "");
////            userValue.put("provider", "");
////            userValue.put("token", "");
////            userValue.put("notification", "false");
////            db.insert("user", null, userValue);
////        }
////
////        //pages
////        db.execSQL("delete from rain");
////        db.execSQL("delete from water");
////        db.execSQL("delete from wind");
////        db.execSQL("delete from nature");
////        db.execSQL("delete from chakra");
////        db.execSQL("delete from mantra");
////        db.execSQL("delete from hz");
////        if (v1 < 2) {
////            db.execSQL("alter table rain add column tid text");
////            db.execSQL("alter table water add column tid text");
////            db.execSQL("alter table wind add column tid text");
////            db.execSQL("alter table nature add column tid text");
////            db.execSQL("alter table chakra add column tid text");
////            db.execSQL("alter table mantra add column tid text");
////            db.execSQL("alter table hz add column tid text");
////            db.execSQL("alter table favlist add column tid text");
////            db.execSQL("alter table playing add column tid text");
////        }
////        for (int i = 0; i < nlists.size(); i++) {
////            List<String> tidList = new ArrayList<>();
////            for (int ii = 0; ii < nlists.get(i).size(); ii++) {
////                PageItem pi = nlists.get(i).get(ii);
////                tidList.add(pi.getTid());
////                ContentValues pageValues = new ContentValues();
////                pageValues.put("page", pi.getPage());
////                pageValues.put("position", pi.getPosition());
////                pageValues.put("pnp", pi.getPnp());
////                pageValues.put("imgdefault", pi.getImgdefault());
////                pageValues.put("img", pi.getImg());
////                pageValues.put("darkdefault", pi.getDarkdefault());
////                pageValues.put("dark", pi.getDark());
////                pageValues.put("seek", pi.getSeek());
////                pageValues.put("isplay", pi.getIsplay());
////                pageValues.put("time", pi.getTime());
////                pageValues.put("name", pi.getName());
////                pageValues.put("ispro", pi.getIspro());
////                pageValues.put("needdownload", pi.getNeeddownload());
////                pageValues.put("tid", pi.getTid());
////                db.insert(getPageName(pi.getPage()), null, pageValues);
////                //play list
////                //fav list
////                if (v1 < 2) {
////                    db.execSQL("update playing set ispro = " + pi.getIspro() + " where name = '" + pi.getName() + "' and exists(select name from playing where name = '" + pi.getName() + "')");
////                    db.execSQL("update playing set tid = '" + pi.getTid() + "' where name = '" + pi.getName() + "' and exists(select name from playing where name = '" + pi.getName() + "')");
////                    db.execSQL("update favlist set ispro = " + pi.getIspro() + " where name = '" + pi.getName() + "' and exists(select name from favlist where name = '" + pi.getName() + "')");
////                    db.execSQL("update favlist set tid = '" + pi.getTid() + "' where name = '" + pi.getName() + "' and exists(select name from favlist where name = '" + pi.getName() + "')");
////                } else {
////                    db.execSQL("update playing set ispro = " + pi.getIspro() + " where tid = '" + pi.getTid() + "' and exists(select tid from playing where tid = '" + pi.getTid() + "')");
////                    db.execSQL("update playing set name = '" + pi.getName() + "' where tid = '" + pi.getTid() + "' and exists(select tid from playing where tid = '" + pi.getTid() + "')");
////                    db.execSQL("update favlist set ispro = " + pi.getIspro() + " where tid = '" + pi.getTid() + "' and exists(select tid from favlist where tid = '" + pi.getTid() + "')");
////                    db.execSQL("update favlist set name = '" + pi.getName() + "' where tid = '" + pi.getTid() + "' and exists(select tid from playing where tid = '" + pi.getTid() + "')");
////                }
////            }
////            //old db restore in page
////            for (int ii = 0; ii < lists.get(i).size(); ii++) {
////                if (v1 < 2) {
////                    db.execSQL("update " + getPageName(lists.get(i).get(ii).getPage()) + " set seek = " + lists.get(i).get(ii).getSeek() + " where name = '" + lists.get(i).get(ii).getName() + "' and exists(select name from " + getPageName(lists.get(i).get(ii).getPage()) + " where name = '" + lists.get(i).get(ii).getName() + "')");
////                    db.execSQL("update " + getPageName(lists.get(i).get(ii).getPage()) + " set isplay = " + lists.get(i).get(ii).getIsplay() + " where name = '" + lists.get(i).get(ii).getName() + "' and exists(select name from " + getPageName(lists.get(i).get(ii).getPage()) + " where name = '" + lists.get(i).get(ii).getName() + "')");
////                } else {
////                    if (tidList.contains(lists.get(i).get(ii).getTid())) {
////                        db.execSQL("update " + getPageName(lists.get(i).get(ii).getPage()) + " set seek = " + lists.get(i).get(ii).getSeek() + " where tid = '" + lists.get(i).get(ii).getTid() + "'");
////                        db.execSQL("update " + getPageName(lists.get(i).get(ii).getPage()) + " set isplay = " + lists.get(i).get(ii).getIsplay() + " where tid = '" + lists.get(i).get(ii).getTid() + "'");
////                    }
////                }
////            }
////        }
////
////        if (db.inTransaction()) {
////            try {
////                db.setTransactionSuccessful();
////            } finally {
////                db.endTransaction();
////            }
////        }
////        db.execSQL("vacuum");
////        db.beginTransaction();
////
////        newDb.close();
////
////        //delete copy database file
////        File forDeleteNewDb = new File(DBLOCATION + "newdb.sqlite");
////        if (forDeleteNewDb.exists()) {
////            forDeleteNewDb.delete();
////        }
////        File forDeleteNewDbJournal = new File(DBLOCATION + "newdb.sqlite-journal");
////        if (forDeleteNewDbJournal.exists()) {
////            forDeleteNewDbJournal.delete();
////        }
////
////        goToNext();
////    }
//
//    public void goToNext() {
//        SharedPreferences preferences;
//        try {
//            preferences = EncryptedSharedPreferences.create(
//                    "checkFirst",
//                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
//                    context.getApplicationContext(),
//                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//            );
//        } catch (GeneralSecurityException | IOException e) {
//            throw new RuntimeException(e);
//        }
//        if (!preferences.getBoolean("checkFirst", false)) {
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("checkFirst", true);
//            editor.apply();
//            Intent intent = new Intent(context, OnBoarding.class);
//            intent.putExtra("fromSplash", true);
//            context.startActivity(intent);
//            activity.finish();
//        } else {
//            Intent intent = new Intent(context, MainActivity.class);
//            intent.putExtra("fromSplash", false);
//            context.startActivity(intent);
//            activity.finish();
//        }
//    }
//
//    public void openDatabase() {
//        String dbPath = DBLOCATION + DATABASE_NAME;
//        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
//            return;
//        }
//        sqLiteDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
//    }
//
//    public void closeDatabse() {
//        if (sqLiteDatabase != null) {
//            sqLiteDatabase.close();
//        }
//    }
//
//    public int getNotificationAgree() {
//        int agree;
//        openDatabase();
//        String sql = "SELECT * FROM notification";
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
//        cursor.moveToFirst();
//        agree = cursor.getInt(0);
//        cursor.close();
//        closeDatabse();
//        return agree;
//    }
//
//    public void changeNotificationAgree(int before) {
//        sqLiteDatabase = this.getWritableDatabase();
//        if (before == 1) {
//            sqLiteDatabase.execSQL("update notification set agree = 0");
//        } else {
//            sqLiteDatabase.execSQL("update notification set agree = 1");
//        }
//    }
//
//    public ArrayList<PageItem> getBottomSheetPlayingList() {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM playing";
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
//            pageItems.add(pageItem);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        closeDatabse();
//        return pageItems;
//    }
//
//    public ArrayList<PageItem> getBottomSheetPlayingListForUpgrade(SQLiteDatabase db) {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        String sql = "SELECT * FROM playing";
//        Cursor cursor = db.rawQuery(sql, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
//            pageItems.add(pageItem);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return pageItems;
//    }
//
//    public ArrayList<PageItem> getPageList(int page) {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM " + getPageName(page);
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
//            pageItems.add(pageItem);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        closeDatabse();
//        return pageItems;
//    }
//
//    public ArrayList<PageItem> getPageListForUpgradeVersion1(SQLiteDatabase db, int page, int oldVersion) {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        String sql = "SELECT * FROM " + getPageName(page);
//        Cursor cursor = db.rawQuery(sql, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            if (oldVersion < 2) {
//                pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
//            } else {
//                pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
//            }
//            pageItems.add(pageItem);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return pageItems;
//    }
//
//    public ArrayList<PageItem> getPageListForUpgrade(SQLiteDatabase db, int page) {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        String sql = "SELECT * FROM " + getPageName(page);
//        Cursor cursor = db.rawQuery(sql, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
//            pageItems.add(pageItem);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return pageItems;
//    }
//
//    public ArrayList<CreditItem> getCreditList() {
//        CreditItem creditItem = null;
//        ArrayList<CreditItem> creditItems = new ArrayList<>();
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from credit", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            creditItem = new CreditItem(cursor.getString(0), cursor.getString(1));
//            creditItems.add(creditItem);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        closeDatabse();
//        return creditItems;
//    }
//
//    public ArrayList<CreditItem> getCreditListForUpgrade(SQLiteDatabase db) {
//        CreditItem creditItem = null;
//        ArrayList<CreditItem> creditItems = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from credit", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            creditItem = new CreditItem(cursor.getString(0), cursor.getString(1));
//            creditItems.add(creditItem);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return creditItems;
//    }
//
//    public void deletePlayingList(int page, int position) {
//        sqLiteDatabase = this.getWritableDatabase();
//        if (page != 4) {
//            sqLiteDatabase.execSQL("delete from playing where page = " + page);
//        } else {
//            String pnp = page + "-" + position;
//            sqLiteDatabase.execSQL("delete from playing where pnp = '" + pnp + "'");
//        }
//        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 1 where position = " + position);
//    }
//
//    String getPageName(int page) {
//        if (page == 1) {
//            return "rain";
//        } else if (page == 2) {
//            return "water";
//        } else if (page == 3) {
//            return "wind";
//        } else if (page == 4) {
//            return "nature";
//        } else if (page == 5) {
//            return "chakra";
//        } else if (page == 6) {
//            return "mantra";
//        } else if (page == 7) {
//            return "hz";
//        } else {
//            return "null";
//        }
//    }
//
//    public void setPlay1(int page, int position) {//같은 page에 다른 재생중인거 있을때 지우고 새로운 재생할거 트랙 재생하기
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 1");//해당페이지 전체 isplay 1로 변경
//        sqLiteDatabase.execSQL("delete from playing where page = " + page);//bottom list에서 해당 페이지 있는거 지우기
//        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 2 where position = " + position);//새로 재생할거 isplay2로 바꾸기
//        sqLiteDatabase.execSQL("insert into playing select * from " + getPageName(page) + " where position = " + position);//새로 재생할거 bottom list에 insert
//    }
//
//    public void setIsPlay1(int page, int position) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 1 where position = " + position);
//        if (page == 1 && RainPage.adapter != null) {
//            RainPage.arrayList.get(position - 1).setIsplay(1);
//            RainPage.adapter.notifyItemChanged(position - 1);
//        } else if (page == 2 && WaterPage.adapter != null) {
//            WaterPage.arrayList.get(position - 1).setIsplay(1);
//            WaterPage.adapter.notifyItemChanged(position - 1);
//        } else if (page == 3 && WindPage.adapter != null) {
//            WindPage.arrayList.get(position - 1).setIsplay(1);
//            WindPage.adapter.notifyItemChanged(position - 1);
//        } else if (page == 4 && NaturePage.adapter != null) {
//            NaturePage.arrayList.get(position - 1).setIsplay(1);
//            NaturePage.adapter.notifyItemChanged(position - 1);
//        } else if (page == 5 && ChakraPage.adapter != null) {
//            ChakraPage.arrayList.get(position - 1).setIsplay(1);
//            ChakraPage.adapter.notifyItemChanged(position - 1);
//        } else if (page == 6 && MantraPage.adapter != null) {
//            MantraPage.arrayList.get(position - 1).setIsplay(1);
//            MantraPage.adapter.notifyItemChanged(position - 1);
//        } else if (page == 7 && HzPage.adapter != null) {
//            HzPage.arrayList.get(position - 1).setIsplay(1);
//            HzPage.adapter.notifyItemChanged(position - 1);
//        }
//    }
//
//    public void setIsPlayPage4(int page, int position) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 2 where position = " + position);
//        sqLiteDatabase.execSQL("insert into playing select * from " + getPageName(page) + " where position = " + position);
//    }
//
//    public void deleteAllPlayingListTest() {
//        sqLiteDatabase = this.getWritableDatabase();
////        sqLiteDatabase.execSQL("delete from playing");
////        sqLiteDatabase.execSQL("update rain set isplay = 1");
////        sqLiteDatabase.execSQL("update wind set isplay = 1");
////        sqLiteDatabase.execSQL("update favtitle set title = null");
//        sqLiteDatabase.execSQL("delete from favlist");
//        sqLiteDatabase.execSQL("delete from favtitle");
//    }
//
//    public void changePageSeek(int page, int progress, int position, String pnp) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update playing set seek = " + progress + " where pnp = " + "'" + pnp + "'");
//        sqLiteDatabase.execSQL("update " + getPageName(page) + " set seek = " + progress + " where position = " + position);
////        sqLiteDatabase.execSQL("update favlist set seek = " + progress + " where pnp = " + "'" + pnp + "'");
//    }
//
////    public void changeSeekInFavList(int progress, String favtitlename, String pnp) {
////        sqLiteDatabase = this.getWritableDatabase();
////        sqLiteDatabase.execSQL("update favlist set seek = " + progress + " where favtitlename = " + "'" + favtitlename + "'" + " and pnp = " + "'" + pnp + "'");
////    }
//
//    public void checkTitleAlready(Context context, String title) {
//        List<String> titles = new ArrayList<>();
//        sqLiteDatabase = this.getWritableDatabase();
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select title from favtitle", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            titles.add(cursor.getString(0));
//            cursor.moveToNext();
//        }
//        cursor.close();
//        closeDatabse();
//        if (titles.contains(title)) {
//            Toast.makeText(context, "'" + title + "' playlist with the same name already exists", Toast.LENGTH_SHORT).show();
//        } else {
////            checkSameFavList(context, title);
//            addFavTitleList(context, title);
//        }
//    }
//
//    public boolean isContainsTitleAlreadyWhenEditTitle(String beforeTitle, String newTitle) {
//        if (beforeTitle.equals(newTitle)) {
//            return false;
//        } else {
//            List<String> titles = new ArrayList<>();
//            sqLiteDatabase = this.getWritableDatabase();
//            openDatabase();
//            Cursor cursor = sqLiteDatabase.rawQuery("select title from favtitle", null);
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                if (cursor.getString(0).equals(newTitle)) {
//                    titles.add(cursor.getString(0));
//                }
//                cursor.moveToNext();
//            }
//            cursor.close();
//            closeDatabse();
//            if (titles.contains(newTitle)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//
////    public void checkSameFavList(Context context, String title) {
////        List<String> nowPnps = new ArrayList<>();//현제 playinglist의 pnp list
////        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
////            nowPnps.add(MainActivity.bottomSheetPlayList.get(i).getPnp());
////            if (i == MainActivity.bottomSheetPlayList.size() - 1) {
////                sqLiteDatabase = this.getWritableDatabase();
////                List<String> favtitles = new ArrayList<>();//현제 favtitle들의 title list
////                Cursor cursor = sqLiteDatabase.rawQuery("select title from favtitle", null);
////                cursor.moveToFirst();
////                while (!cursor.isAfterLast()) {
////                    favtitles.add(cursor.getString(0));
////                    cursor.moveToNext();
////                }
////
////
////                if (favtitles.size() != 0) {
////                    if (haveSame(nowPnps, favtitles)) {
////                        Toast.makeText(context, "same list have already", Toast.LENGTH_SHORT).show();
////                    } else {
////                        addFavTitleList(context, title);
////                    }
////                } else {
////                    addFavTitleList(context, title);
////                }
////            }
////        }
////    }
//
//    public void addFavTitleList(Context context, String title1) {
//        FavTitleItem favTitleItem = null;
//        sqLiteDatabase = this.getWritableDatabase();
//
//        if (MainActivity.bottomSheetPlayList.size() != 0) {
//            String s = "'" + title1 + "'" + ", 1, 1";
////            sqLiteDatabase.execSQL("insert into favtitle values (" + "'" + title1 + "'" + "," + 1 + "," + 1 + ")");
//            sqLiteDatabase.execSQL("insert into favtitle values (" + s + ")");
//            favTitleItem = new FavTitleItem(title1, 1, 1);
//            FavPage.favTitleItemArrayList.add(favTitleItem);
//            FavPage.adapter.notifyItemInserted(FavPage.favTitleItemArrayList.size() - 1);
//            FavPage.adapter.notifyDataSetChanged();
//            if (AddFavDialog.alertDialog.isShowing()) {
//                AddFavDialog.alertDialog.dismiss();
//            }
//            addFavList(context, title1);
//        }
//    }
//
//    public void addFavList(Context context, String title) {
//        sqLiteDatabase = this.getWritableDatabase();
//
//        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("page", MainActivity.bottomSheetPlayList.get(i).getPage());
//            contentValues.put("position", MainActivity.bottomSheetPlayList.get(i).getPosition());
//            contentValues.put("pnp", MainActivity.bottomSheetPlayList.get(i).getPnp());
//            contentValues.put("imagedefault", MainActivity.bottomSheetPlayList.get(i).getImgdefault());
//            contentValues.put("img", MainActivity.bottomSheetPlayList.get(i).getImg());
//            contentValues.put("darkdefault", MainActivity.bottomSheetPlayList.get(i).getDarkdefault());
//            contentValues.put("dark", MainActivity.bottomSheetPlayList.get(i).getDark());
//            contentValues.put("seek", MainActivity.bottomSheetPlayList.get(i).getSeek());
//            contentValues.put("isplay", MainActivity.bottomSheetPlayList.get(i).getIsplay());
//            contentValues.put("favtitlename", title);
//            contentValues.put("time", MainActivity.bottomSheetPlayList.get(i).getTime());
//            contentValues.put("name", MainActivity.bottomSheetPlayList.get(i).getName());
//            contentValues.put("ispro", MainActivity.bottomSheetPlayList.get(i).getIspro());
//            contentValues.put("needdownload", MainActivity.bottomSheetPlayList.get(i).getNeeddownload());
//            sqLiteDatabase.insert("favlist", null, contentValues);
//        }
//        Toast.makeText(context, "success add fav list!", Toast.LENGTH_SHORT).show();
//        if (FavPage.favTitleItemArrayList.size() != 0) {
//            FavPage.message.setVisibility(View.GONE);
//        }
//    }
//
//    boolean haveSame(List<String> nowPnps, List<String> favtitles) {
//        boolean isSame = false;
//        sqLiteDatabase = this.getWritableDatabase();
//        List<String> checkWithThisList = new ArrayList<>();
//        for (int i = 0; i < favtitles.size(); i++) {
//            Cursor cursor = sqLiteDatabase.rawQuery("select pnp from favlist where favtitlename = " + "'" + favtitles.get(i) + "'", null);
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                checkWithThisList.add(cursor.getString(0));
//                cursor.moveToNext();
//            }
//            if (listEqualsIgnoreOrder(checkWithThisList, nowPnps)) {
//                isSame = true;
//                break;
//            } else {
//                isSame = false;
//            }
//        }
//        if (isSame) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {//순서 무시 똑같으면 true
//        return new HashSet<>(list1).equals(new HashSet<>(list2));
//    }
//
//    public ArrayList<FavTitleItem> getFavTitleList() {
//        FavTitleItem favTitleItem = null;
//        ArrayList<FavTitleItem> favTitleItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM favtitle";
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            favTitleItem = new FavTitleItem(cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
//            if (favTitleItem.getTitle() != null) {
//                favTitleItems.add(favTitleItem);
//            }
//            cursor.moveToNext();
//        }
//        cursor.close();
//        closeDatabse();
//        return favTitleItems;
//    }
//    public ArrayList<FavTitleItem> getFavTitleListForUpgrade(SQLiteDatabase db) {
//        FavTitleItem favTitleItem = null;
//        ArrayList<FavTitleItem> favTitleItems = new ArrayList<>();
//
//        String sql = "SELECT * FROM favtitle";
//        Cursor cursor = db.rawQuery(sql, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            favTitleItem = new FavTitleItem(cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
//            if (favTitleItem.getTitle() != null) {
//                favTitleItems.add(favTitleItem);
//            }
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return favTitleItems;
//    }
//
//    public ArrayList<FavListItem> getFavListItem(String title) {
//        FavListItem favListItem = null;
//        ArrayList<FavListItem> favListItems = new ArrayList<>();
//        sqLiteDatabase = this.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from favlist where favtitlename = " + "'" + title + "'", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            favListItem = new FavListItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getString(11), cursor.getInt(12), cursor.getInt(13));
//            favListItems.add(favListItem);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        closeDatabse();
//        return favListItems;
//    }
//
//    public ArrayList<FavListItem> getFavListItemForUpgrade(String title, SQLiteDatabase db) {
//        FavListItem favListItem = null;
//        ArrayList<FavListItem> favListItems = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from favlist where favtitlename = " + "'" + title + "'", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            favListItem = new FavListItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getString(11), cursor.getInt(12), cursor.getInt(13));
//            favListItems.add(favListItem);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return favListItems;
//    }
//
//    public ArrayList<FavListItem> getListInCommunityShare(String fromserver) {
//        FavListItem favListItem = null;
//        ArrayList<FavListItem> favListItems = new ArrayList<>();
//        openDatabase();
//
//        String[] list = fromserver.split("●");
//        String title = list[0];
//        for (int i = 0; i < list.length; i++) {
//            if (i != 0) {
//                String pageName = getPageName(Integer.parseInt(list[i].split("-")[0]));
//                int position = Integer.parseInt(list[i].split("-")[1]);
//                int seek = Integer.parseInt(list[i].split("-")[2]);
//                Cursor cursor = sqLiteDatabase.rawQuery("select * from '" + pageName + "'" + " where position = " + position, null);
//                cursor.moveToFirst();
//                favListItem = new FavListItem(
//                        cursor.getInt(0),
//                        cursor.getInt(1),
//                        cursor.getString(2),
//                        cursor.getBlob(3),
//                        cursor.getBlob(4),
//                        cursor.getBlob(5),
//                        cursor.getBlob(6),
//                        seek,
//                        cursor.getInt(8),
//                        title,
//                        cursor.getInt(9),
//                        cursor.getString(10),
//                        cursor.getInt(11),
//                        cursor.getInt(12)
//                );
//                favListItems.add(favListItem);
//                cursor.close();
//            }
//        }
//        closeDatabse();
//        return favListItems;
//    }
//
//    public List<PageItem> getAllTrackTidList() {
//        PageItem pageItem = null;
//        List<PageItem> result = new ArrayList<>();
//        openDatabase();
//        for (int i = 1; i <= 7; i++) {
//            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + getPageName(i), null);
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
//                result.add(pageItem);
//                cursor.moveToNext();
//            }
//            cursor.close();
//        }
//        return result;
//    }
//
//    public void removeFavList(String title) {
//        sqLiteDatabase = this.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select _rowid_ from favtitle where title = " + "'" + title + "'", null);
//        cursor.moveToFirst();
//        int rowid = cursor.getInt(0);
//        sqLiteDatabase.execSQL("delete from favtitle where _rowid_ = " + rowid);
//        sqLiteDatabase.execSQL("delete from favlist where favtitlename = " + "'" + title + "'");
//        FavPage.favTitleItemArrayList.remove(rowid - 1);
//        FavPage.adapter.notifyItemRemoved(rowid - 1);
//        FavPage.adapter.notifyDataSetChanged();
//        sqLiteDatabase.execSQL("vacuum");
//        cursor.close();
//        closeDatabse();
//        if (DeleteFavTitleDialog.alertDialog.isShowing()) {
//            DeleteFavTitleDialog.alertDialog.dismiss();
//        }
//        if (FavPage.favTitleItemArrayList.size() == 0) {
//            FavPage.message.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void changeFavListIsOpen(int isopen, String title) {
//        sqLiteDatabase = this.getWritableDatabase();
//        if (isopen == 1) {
//            sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
//            sqLiteDatabase.execSQL("update favtitle set isopen = 2 where title = " + "'" + title + "'");
//        } else if (isopen == 2) {
//            sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
//        }
//    }
//
//    public void changeIsOpenWhenFavPageOnPause() {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update favtitle set isopen = 1");
//        sqLiteDatabase.execSQL("update favtitle set isedit = 1");
//    }
//
//    public void changeFavListToEdit(String beforeTitle, String afterTitle, ArrayList<FavListItem> arrayList) {
//        sqLiteDatabase = this.getWritableDatabase();
//        if (!beforeTitle.equals(afterTitle)) {
//            sqLiteDatabase.execSQL("update favtitle set title = " + "'" + afterTitle + "'" + " where title = " + "'" + beforeTitle + "'");
//        }
//        sqLiteDatabase.execSQL("delete from favlist where favtitlename = " + "'" + beforeTitle + "'");
//        for (int i = 0; i < arrayList.size(); i++) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("page", arrayList.get(i).getPage());
//            contentValues.put("position", arrayList.get(i).getPosition());
//            contentValues.put("pnp", arrayList.get(i).getPnp());
//            contentValues.put("imagedefault", arrayList.get(i).getImgdefault());
//            contentValues.put("img", arrayList.get(i).getImg());
//            contentValues.put("darkdefault", arrayList.get(i).getDarkdefault());
//            contentValues.put("dark", arrayList.get(i).getDark());
//            contentValues.put("seek", arrayList.get(i).getSeek());
//            contentValues.put("isplay", arrayList.get(i).getIsplay());
//            contentValues.put("favtitlename", arrayList.get(i).getFavtitlename());
//            contentValues.put("time", arrayList.get(i).getTime());
//            contentValues.put("name", arrayList.get(i).getName());
//            contentValues.put("ispro", arrayList.get(i).getIspro());
//            contentValues.put("needdownload", arrayList.get(i).getNeeddownload());
//            sqLiteDatabase.insert("favlist", null, contentValues);
//        }
//    }
//
//    public void deleteAllPlayinglist(ArrayList<Integer> pagelist, ArrayList<Integer> positionlist, String title) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("delete from playing");
////        if (pagelist.contains(1)) {
////            sqLiteDatabase.execSQL("update rain set isplay = 1 where isplay = 2");
////        } else if (pagelist.contains(2)) {
////            sqLiteDatabase.execSQL("update water set isplay = 1 where isplay = 2");
////        } else if (pagelist.contains(3)) {
////            sqLiteDatabase.execSQL("update wind set isplay = 1 where isplay = 2");
////        } else if (pagelist.contains(4)) {
////            sqLiteDatabase.execSQL("update nature set isplay = 1 where isplay = 2");
////        } else if (pagelist.contains(5)) {
////            sqLiteDatabase.execSQL("update chakra set isplay = 1 where isplay = 2");
////        } else if (pagelist.contains(6)) {
////            sqLiteDatabase.execSQL("update mantra set isplay = 1 where isplay = 2");
////        } else if (pagelist.contains(7)) {
////            sqLiteDatabase.execSQL("update hz set isplay = 1 where isplay = 2");
////        }
//        for (int i = 0; i < pagelist.size(); i++) {
//            if (pagelist.get(i) == 1 && RainPage.arrayList.size() != 0) {
//                sqLiteDatabase.execSQL("update rain set isplay = 1 where isplay = 2");
//                RainPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
//                RainPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
//                RainPage.adapter.notifyDataSetChanged();
//            } else if (pagelist.get(i) == 2 && WaterPage.arrayList.size() != 0) {
//                sqLiteDatabase.execSQL("update water set isplay = 1 where isplay = 2");
//                WaterPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
//                WaterPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
//                WaterPage.adapter.notifyDataSetChanged();
//            } else if (pagelist.get(i) == 3 && WindPage.arrayList.size() != 0) {
//                sqLiteDatabase.execSQL("update wind set isplay = 1 where isplay = 2");
//                WindPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
//                WindPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
//                WindPage.adapter.notifyDataSetChanged();
//            } else if (pagelist.get(i) == 4 && NaturePage.arrayList.size() != 0) {
//                sqLiteDatabase.execSQL("update nature set isplay = 1 where isplay = 2");
//                NaturePage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
//                NaturePage.adapter.notifyItemChanged(positionlist.get(i) - 1);
//                NaturePage.adapter.notifyDataSetChanged();
//            } else if (pagelist.get(i) == 5 && ChakraPage.arrayList.size() != 0) {
//                sqLiteDatabase.execSQL("update chakra set isplay = 1 where isplay = 2");
//                ChakraPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
//                ChakraPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
//                ChakraPage.adapter.notifyDataSetChanged();
//            } else if (pagelist.get(i) == 6 && MantraPage.arrayList.size() != 0) {
//                sqLiteDatabase.execSQL("update mantra set isplay = 1 where isplay = 2");
//                MantraPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
//                MantraPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
//                MantraPage.adapter.notifyDataSetChanged();
//            } else if (pagelist.get(i) == 7 && HzPage.arrayList.size() != 0) {
//                sqLiteDatabase.execSQL("update hz set isplay = 1 where isplay = 2");
//                HzPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
//                HzPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
//                HzPage.adapter.notifyDataSetChanged();
//            }
//        }
//        Log.d("DatabaseHandler>>>", "1");
//        sqLiteDatabase.execSQL("vacuum");
//
//        Log.d("DatabaseHandler>>>", "0");
//        addFavListInPlayinglist(title);
//    }
//
//    public void deleteAllPlayinglistWhenDeleteInBottomSheet() {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("delete from playing");
//        sqLiteDatabase.execSQL("update rain set isplay = 1 where isplay = 2");
//        sqLiteDatabase.execSQL("update water set isplay = 1 where isplay = 2");
//        sqLiteDatabase.execSQL("update wind set isplay = 1 where isplay = 2");
//        sqLiteDatabase.execSQL("update nature set isplay = 1 where isplay = 2");
//        sqLiteDatabase.execSQL("update chakra set isplay = 1 where isplay = 2");
//        sqLiteDatabase.execSQL("update mantra set isplay = 1 where isplay = 2");
//        sqLiteDatabase.execSQL("update hz set isplay = 1 where isplay = 2");
//    }
//
//    public void addFavListInPlayinglist(String title) {
//        sqLiteDatabase = this.getWritableDatabase();
//        PageItem pageItem = null;
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from favlist where favtitlename = " + "'" + title + "'", null);
//        cursor.moveToFirst();
//        int count = 0;
//        while (!cursor.isAfterLast()) {
//            count += 1;
//            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(10), cursor.getString(11), cursor.getInt(12), cursor.getInt(13));
//            MainActivity.bottomSheetPlayList.add(pageItem);
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("page", cursor.getInt(0));
//            contentValues.put("position", cursor.getInt(1));
//            contentValues.put("pnp", cursor.getString(2));
//            contentValues.put("imgdefault", cursor.getBlob(3));
//            contentValues.put("img", cursor.getBlob(4));
//            contentValues.put("darkdefault", cursor.getBlob(5));
//            contentValues.put("dark", cursor.getBlob(6));
//            contentValues.put("seek", cursor.getInt(7));
//            contentValues.put("isplay", 2);
//            contentValues.put("time", cursor.getInt(10));
//            contentValues.put("name", cursor.getString(11));
//            contentValues.put("ispro", cursor.getInt(12));
//            contentValues.put("needdownload", cursor.getInt(13));
//            setIsPlay2WhenPlayInFavTitle(cursor.getInt(0), cursor.getInt(1));
//            sqLiteDatabase.insert("playing", null, contentValues);
//            cursor.moveToNext();
//        }
//        MainActivity.bottomSheetAdapter.notifyItemRangeInserted(0, count);
//        MainActivity.bottomSheetAdapter.notifyDataSetChanged();
//        cursor.close();
//        closeDatabse();
//    }
//
//    private void setIsPlay2WhenPlayInFavTitle(int page, int position) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 2 where position = " + position);
//    }
//
//    public void whenAppKillTask() {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
//        sqLiteDatabase.execSQL("update favtitle set isedit = 1 where isedit = 2");
//        close();
//    }
//
//    public Bitmap getPageicon(String what) {
//        if (what.equals("download")) {
//            openDatabase();
//            Cursor cursor = sqLiteDatabase.rawQuery("select download from pageicon where _rowid_ = 1", null);
//            cursor.moveToFirst();
//            byte[] icon = cursor.getBlob(0);
//            cursor.close();
//            closeDatabse();
//            return BitmapFactory.decodeByteArray(icon, 0, icon.length);
//        } else if (what.equals("pro")) {
//            openDatabase();
//            Cursor cursor = sqLiteDatabase.rawQuery("select pro from pageicon where _rowid_ = 1", null);
//            cursor.moveToFirst();
//            byte[] icon = cursor.getBlob(0);
//            cursor.close();
//            closeDatabse();
//            return BitmapFactory.decodeByteArray(icon, 0, icon.length);
//        } else if (what.equals("circlepro")) {
//            openDatabase();
//            Cursor cursor = sqLiteDatabase.rawQuery("select circlepro from pageicon where _rowid_ = 1", null);
//            cursor.moveToFirst();
//            byte[] icon = cursor.getBlob(0);
//            cursor.close();
//            closeDatabse();
//            return BitmapFactory.decodeByteArray(icon, 0, icon.length);
//        } else {
//            openDatabase();
//            Cursor cursor = sqLiteDatabase.rawQuery("select circledownload from pageicon where _rowid_ = 1", null);
//            cursor.moveToFirst();
//            byte[] icon = cursor.getBlob(0);
//            cursor.close();
//            closeDatabse();
//            return BitmapFactory.decodeByteArray(icon, 0, icon.length);
//        }
//    }
//
//    public byte[] getPageiconForUpgrade(SQLiteDatabase db, String what) {
//        if (what.equals("download")) {
//            Cursor cursor = db.rawQuery("select download from pageicon where _rowid_ = 1", null);
//            cursor.moveToFirst();
//            byte[] icon = cursor.getBlob(0);
//            cursor.close();
//            return icon;
//        } else if (what.equals("pro")) {
//            Cursor cursor = db.rawQuery("select pro from pageicon where _rowid_ = 1", null);
//            cursor.moveToFirst();
//            byte[] icon = cursor.getBlob(0);
//            cursor.close();
//            return icon;
//        } else if (what.equals("circlepro")) {
//            Cursor cursor = db.rawQuery("select circlepro from pageicon where _rowid_ = 1", null);
//            cursor.moveToFirst();
//            byte[] icon = cursor.getBlob(0);
//            cursor.close();
//            return icon;
//        } else {
//            Cursor cursor = db.rawQuery("select circledownload from pageicon where _rowid_ = 1", null);
//            cursor.moveToFirst();
//            byte[] icon = cursor.getBlob(0);
//            cursor.close();
//            return icon;
//        }
//    }
//
//    public int getIsProUser() {
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ispro FROM ispro", null);
//        cursor.moveToFirst();
//        int ispro = cursor.getInt(0);
//        cursor.close();
//        closeDatabse();
//        return ispro;
//    }
//
//    public void changeIsProUserFromSplash(int ispro) {
//        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
//            sqLiteDatabase = this.getWritableDatabase();
//        }
//        sqLiteDatabase.execSQL("update ispro set ispro = " + ispro);
//    }
//
//    public void changeIsProUserFromPremium(int ispro) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update ispro set ispro = " + ispro);
//        closeDatabse();
//    }
//
//    public PageItem getPageItemInStorageManage(int page, int position) {
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + getPageName(page) + " where position = " + position, null);
//        cursor.moveToFirst();
//        PageItem pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
//        cursor.close();
//        closeDatabse();
//        return pageItem;
//    }
//
//    public String getTrackName(int page, int position) {
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select name from " + getPageName(page), null);
//        cursor.moveToFirst();
//        String name = cursor.getString(0);
//        cursor.close();
//        closeDatabse();
//        return name;
//    }
//
//    public void changePageSeekWhenPlayInFavTitle(int page, int position, int volumn) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update " + getPageName(page) + " set seek = " + volumn + " where position = " + position);
//    }
//
//    public void changeFavTitleIsEdit(String title, int isedit) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update favtitle set isedit = " + isedit + " where title = " + "'" + title + "'");
//    }
//
//    public int getFavTitleIsEdit(String title) {
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select isedit from favtitle where title = " + "'" + title + "'", null);
//        cursor.moveToFirst();
//        int isedit = cursor.getInt(0);
//        cursor.close();
//        closeDatabse();
//        return isedit;
//    }
//
//    public UserModel getUserModel() {
//        UserModel userModel = new UserModel();
//        openDatabase();
//        String sql = "SELECT * FROM user";
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            userModel.setId(cursor.getInt(0));
//            userModel.setEmail(cursor.getString(1));
//            userModel.setUid(cursor.getString(2));
//            userModel.setNickname(cursor.getString(3));
//            userModel.setImageurl(cursor.getString(4));
//            userModel.setProvider(cursor.getString(5));
//            userModel.setToken(cursor.getString(6));
//            userModel.setNotification(changeStringToBoolean(cursor.getString(7)));
//            cursor.close();
//            closeDatabse();
//            return userModel;
//        } else {
//            return new UserModel(0, "", "", "", "", "", "", false);
//        }
//    }
//
//    public void makeDbUserWhenSignIn(int id, String email, String uid, String nickname, String token, String imageurl, String provider, boolean notification) {
//        sqLiteDatabase = this.getWritableDatabase();
//        deleteUserProfile();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("id", id);
//        contentValues.put("email", email);
//        contentValues.put("uid", uid);
//        contentValues.put("provider", provider);
//        contentValues.put("nickname", nickname);
//        contentValues.put("token", token);
//        contentValues.put("imageurl", imageurl);
//        contentValues.put("notification", changeBooleanToString(notification));
//        sqLiteDatabase.insert("user", null, contentValues);
//        closeDatabse();
//    }
//
//    public void createUserProfile(int id, String email, String uid, String provider) {
//        sqLiteDatabase = this.getWritableDatabase();
//        deleteUserProfile();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("id", id);
//        contentValues.put("email", email);
//        contentValues.put("uid", uid);
//        contentValues.put("provider", provider);
//        contentValues.put("notification", "true");
//        sqLiteDatabase.insert("user", null, contentValues);
//        closeDatabse();
//    }
//
//    public void updateUserProfile(String nickname, String imageurl, String uid) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update user set nickname = " + "'" + nickname + "'" + ", imageurl = " + "'" + imageurl + "'" + " where uid = " + "'" + uid + "'");
//        closeDatabse();
//    }
//
//    public void updateNotification(Boolean b, String uid) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update user set notification =" + "'" + changeBooleanToString(b) + "'" + " where uid = " + "'" + uid + "'");
//    }
//
//    public void deleteUserProfile() {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("delete from user");
//    }
//
//    public String changeBooleanToString(boolean b) {
//        if (b) {
//            return "true";
//        } else {
//            return "false";
//        }
//    }
//
//    public Boolean changeStringToBoolean(String s) {
//        if (s.equals("true")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public byte[] getTrackImageLight(int page, int position) {
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT img from " + "'" + getPageName(page) + "'" + " where position = " + position, null);
//        cursor.moveToFirst();
//        byte[] result = cursor.getBlob(0);
//        cursor.close();
//        closeDatabse();
//        return result;
//    }
//
//    public byte[] getTrackImageDark(int page, int position) {
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT dark from " + "'" + getPageName(page) + "'" + " where position = " + position, null);
//        cursor.moveToFirst();
//        byte[] result = cursor.getBlob(0);
//        cursor.close();
//        closeDatabse();
//        return result;
//    }
//
//    public List<ForHitsModel> getViewPostList() {
//        ForHitsModel model = null;
//        List<ForHitsModel> hitsList = new ArrayList<>();
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select postid from viewpost", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            model = new ForHitsModel(cursor.getInt(0), cursor.getString(1));
//            hitsList.add(model);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        closeDatabse();
//        return hitsList;
//    }
//
//    public String getPostDate(int postid) {
//        String result = "";
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select date from forhits where postid = " + postid, null);
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            result = cursor.getString(0);
//            cursor.close();
//        }
//        closeDatabse();
//        return result;
//    }
//
//    public void insertForHits(int postid, String date) {
//        sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("postid", postid);
//        contentValues.put("date", date);
//        sqLiteDatabase.insert("forhits", null, contentValues);
//    }
//
//    public void updateForHits(int postid, String date) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update forhits set date = " + "'" + date + "'" + " where postid = " + postid);
//    }
//
//    public List<NotificationItem> getNotificationList(int start, int end) {
//        NotificationItem item = null;
//        List<NotificationItem> list = new ArrayList<>();
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from cnotification order by date desc", null);
//        cursor.moveToPosition(start);
//        while (cursor.getPosition() != end) {
//            if (cursor.isAfterLast()) {
//                break;
//            }
//            item = new NotificationItem(cursor.getString(0), cursor.getString(1), cursor.getString(2));
//            list.add(item);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        closeDatabse();
//        return list;
//    }
//
//    public void insertCNotification(String title, String body, String date) {
//        sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("title", title);
//        contentValues.put("body", body);
//        contentValues.put("date", date);
//        sqLiteDatabase.insert("cnotification", null, contentValues);
//    }
//
//    public void deleteCNotification(String body, String date) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("delete from cnotification where body = " + "'" + body + "'" + " and date = " + "'" + date + "'");
//    }
//
//    public void deleteNotificationAll() {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("delete from cnotification");
//    }
//
//    public void AddFavTitleFromPost(String title, String list, Activity dialog, Context ctx) {
////        sqLiteDatabase = this.getWritableDatabase();
////        List<String> myTitleList = new ArrayList<>();
////        Cursor cursor = sqLiteDatabase.rawQuery("select title from favtitle", null);
////        cursor.moveToFirst();
////        while (!cursor.isAfterLast()) {
////            myTitleList.add(cursor.getString(0));
////            cursor.moveToNext();
////        }
////        cursor.close();
////
////        if (myTitleList.contains(title)) {
////            Toast.makeText(context, "There is already a playlist with the same name. Please save it with a different name.", Toast.LENGTH_SHORT).show();
////        } else {
////            String s = "'" + title + "'" + ", 1, 1";
////            sqLiteDatabase.execSQL("insert into favtitle values (" + s + ")");
////            String[] li = list.split("●");
////            List<PageItem> lists = getAllTrackTidList();
////            for (int i = 0; i < li.length; i++) {
////                for (int ii = 0; ii < lists.size(); ii++) {
////                    if (li[i].split("-")[0].equals(lists.get(ii).getTid())) {
////                        ContentValues contentValues = new ContentValues();
////                        contentValues.put("page", lists.get(ii).getPage());
////                        contentValues.put("position", lists.get(ii).getPosition());
////                        contentValues.put("pnp", lists.get(ii).getPnp());
////                        contentValues.put("imagedefault", lists.get(ii).getImgdefault());
////                        contentValues.put("img", lists.get(ii).getImg());
////                        contentValues.put("darkdefault", lists.get(ii).getDarkdefault());
////                        contentValues.put("dark", lists.get(ii).getDark());
////                        contentValues.put("seek", li[i].split("-")[1]);
////                        contentValues.put("isplay", lists.get(ii).getIsplay());
////                        contentValues.put("favtitlename", title);
////                        contentValues.put("time", lists.get(ii).getTime());
////                        contentValues.put("name", lists.get(ii).getName());
////                        contentValues.put("ispro", lists.get(ii).getIspro());
////                        contentValues.put("needdownload", lists.get(ii).getNeeddownload());
////                        sqLiteDatabase.insert("favlist", null, contentValues);
////                        break;
////                    }
////                }
////            }
////            if (FavPage.adapter != null) {
////                FavPage.adapter.notifyDataSetChanged();
////            }
////            Toast.makeText(ctx, "Added to the list", Toast.LENGTH_SHORT).show();
////            dialog.finish();
////        }
////        closeDatabse();
//    }
//}