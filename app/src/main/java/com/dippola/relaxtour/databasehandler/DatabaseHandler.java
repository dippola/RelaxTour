package com.dippola.relaxtour.databasehandler;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.community.main.ForHitsModel;
import com.dippola.relaxtour.community.main.notification.NotificationItem;
import com.dippola.relaxtour.dialog.AddFavDialog;
import com.dippola.relaxtour.dialog.DeleteFavTitleDialog;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    //upgrade
    Context context;
    Activity activity;

    private static final int DATABASE_VERSION = 3;
    //1.0.44 = 1

    private static final String DATABASE_NAME = "list.sqlite";
    private static final String DBLOCATION = "/data/data/com.dippola.relaxtour/databases/";

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

    public DatabaseHandler(Context context, Activity activity) {//in splash
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.activity = activity;
        checkUpgrade();
    }

    public void checkUpgrade() {
        sqLiteDatabase = this.getWritableDatabase();
        if (sqLiteDatabase.getVersion() < DATABASE_VERSION) {
            onUpgrade(sqLiteDatabase, sqLiteDatabase.getVersion(), DATABASE_VERSION);
        }
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

        //version
        if (v1 < 2) {
            upgrade1to2(db, newDb);
        } if (v1 < 3) {
            upgrade2to3(db, newDb);
        }
        //version

        if (db.inTransaction()) {
            try {
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
        db.execSQL("vacuum");
        db.beginTransaction();
        newDb.close();
        //delete copy database file
        File forDeleteNewDb = new File(DBLOCATION + "newdb.sqlite");
        if (forDeleteNewDb.exists()) {
            forDeleteNewDb.delete();
        }
        File forDeleteNewDbJournal = new File(DBLOCATION + "newdb.sqlite-journal");
        if (forDeleteNewDbJournal.exists()) {
            forDeleteNewDbJournal.delete();
        }
    }

    public void upgrade1to2(SQLiteDatabase db, SQLiteDatabase newDb) {
        db.execSQL(TRACK_TEAM);
        db.execSQL("drop table rain");
        db.execSQL("drop table water");
        db.execSQL("drop table wind");
        db.execSQL("drop table nature");
        db.execSQL("drop table chakra");
        db.execSQL("drop table mantra");
        db.execSQL("drop table hz");
        db.execSQL("drop table favlist");
        db.execSQL("create table if not exists favlist(page INTEGER, position INTEGER, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, favtitlename TEXT, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER, tid TEXT)");
        db.execSQL("delete from favtitle");
        db.execSQL("drop table playing");
        db.execSQL("create table if not exists playing(page INTEGER, position INTEGER, imgdefault BLOB, img BLOB, darkdefault BLOB, dark BLOB, seek INTEGER, isplay INTEGER, time INTEGER, name TEXT, ispro INTEGER, needdownload INTEGER, tid TEXT);");

        List<PageItem> nlist = getNewTackListForUpgrade(newDb);
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
    }

    public void upgrade2to3(SQLiteDatabase db, SQLiteDatabase newDb) {
        List<PageItem> newTrackList = getNewTackListForUpgrade(newDb);
        List<PageItem> oldTrackList = getNewTackListForUpgrade(db);
        db.execSQL("delete from track");
        for (int i = 0; i < newTrackList.size(); i++) {
            ContentValues c = new ContentValues();
            c.put("page", newTrackList.get(i).getPage());
            c.put("position", newTrackList.get(i).getPosition());
            c.put("imgdefault", newTrackList.get(i).getImgdefault());
            c.put("img", newTrackList.get(i).getImg());
            c.put("darkdefault", newTrackList.get(i).getDarkdefault());
            c.put("dark", newTrackList.get(i).getDark());
            c.put("seek", newTrackList.get(i).getSeek());
            c.put("isplay", newTrackList.get(i).getIsplay());
            c.put("time", newTrackList.get(i).getTime());
            c.put("name", newTrackList.get(i).getName());
            c.put("ispro", newTrackList.get(i).getIspro());
            c.put("needdownload", newTrackList.get(i).getNeeddownload());
            c.put("tid", newTrackList.get(i).getTid());
            db.insert("track", null, c);
        }
        for (PageItem oldTrack : oldTrackList) {
            db.execSQL("update track set seek = " + oldTrack.getSeek() + ", isPlay = " + oldTrack.getIsplay() + " where tid = '" + oldTrack.getTid() + "'");
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
                int seek = Integer.parseInt(list[i].split("-")[1]);
                Log.d("DatabaseHandler>>>", "tid: " + tid);
                Cursor cursor = sqLiteDatabase.rawQuery("select * from track where tid = '" + tid + "'", null);
                cursor.moveToFirst();
                favListItem = new FavListItem(cursor.getInt(0), cursor.getInt(1), cursor.getBlob(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), seek, cursor.getInt(7), list[0], cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getInt(11), cursor.getString(12));
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
        sqLiteDatabase.execSQL("vacuum");

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