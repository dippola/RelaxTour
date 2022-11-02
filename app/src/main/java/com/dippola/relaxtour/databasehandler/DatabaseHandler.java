package com.dippola.relaxtour.databasehandler;

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
import com.dippola.relaxtour.dialog.AddFavDialog;
import com.dippola.relaxtour.dialog.DeleteFavTitleDialog;
import com.dippola.relaxtour.dialog.EditFavTitleDialog;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "list.sqlite";
    public static final String DBLOCATION = "/data/data/com.dippola.relaxtour/databases/";

    private static final String PLAYING_TABLE_NAME = "playing";
    private static final String RAIN_TABLE_NAME = "rain";
    private static final String WATER_TABLE_NAME = "water";
    private static final String WIND_TABLE_NAME = "wind";
    private static final String NATURE_TABLE_NAME = "nature";
    private static final String CHAKRA_TABLE_NAME = "chakra";
    private static final String MANTRA_TABLE_NAME = "mantra";
    private static final String HZ_TABLE_NAME = "hz";

    public static final String COLUMN_PAGE = "page";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_PNP = "pnp";
    public static final String COLUMN_IMGDEFAULT = "imgdefault";
    public static final String COLUMN_IMAGE = "img";
    public static final String COLUMN_DARKDEFAULT = "darkdefault";
    public static final String COLUMN_DARK = "dark";
    public static final String COLUMN_SEEK = "seek";
    public static final String COLUMN_ISPLAY = "isplay";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ISPRO = "ispro";
    public static final String COLUMN_NEED_DOWNLOAD = "needdownload";

    //fav
    private static final String FAV_TITLE_TABLE_NAME = "favtitle";
    public static final String COLUMN_FAV_TITLE = "title";
    public static final String COLUMN_FAV_ISPLAY = "isopen";
    public static final String COLUMN_FAV_ISEDIT = "isedit";
    private static final String FAV_LIST_TABLE_NAME = "favlist";
    public static final String COLUMN_FAVTITLENAME = "favtitlename";

    //pageicon
    public static final String PAGE_ICON_TABLE_NAME = "pageicon";
    public static final String PAGE_ICON_TEAM = "create table if not exists pageicon(download BLOB, pro BLOB);";

    //credit
    public static final String CREDIT_TABLE_NAME = "credit";
    public static final String CREDIT_TEAM = "create table if not exists credit(track TEXT, url TEXT)";


    private static final String PLAYING_TEAM = "create table if not exists " + PLAYING_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";
    private static final String RAIN_TEAM = "create table if not exists " + RAIN_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";
    private static final String RIVER_TEAM = "create table if not exists " + WATER_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";
    private static final String WIND_TEAM = "create table if not exists " + WIND_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";
    private static final String NATURE_TEAM = "create table if not exists " + NATURE_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";
    private static final String CHAKRA_TEAM = "create table if not exists " + CHAKRA_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";
    private static final String MANTRA_TEAM = "create table if not exists " + MANTRA_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";
    private static final String HZ_TEAM = "create table if not exists " + HZ_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";

    private static final String FAV_TITLE_TEAM = "create table if not exists " + FAV_TITLE_TABLE_NAME + "(" + COLUMN_FAV_TITLE + " TEXT," + COLUMN_FAV_ISPLAY + " INTEGER," + COLUMN_FAV_ISEDIT + " INTEGER" + ");";
    private static final String FAV_LIST_TEAM = "create table if not exists " + WIND_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_DARKDEFAULT + " BLOB," + COLUMN_DARK + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER," + COLUMN_FAVTITLENAME + " INTEGER, " + COLUMN_TIME + " INTEGER, " + COLUMN_NAME + " TEXT," + COLUMN_ISPRO + " INTEGER," + COLUMN_NEED_DOWNLOAD + " INTEGER" + ");";

    private static final String ISPRO_TEAM = "create table if not exists ispro (ispro INTEGER);";

    //notification table set
    public static final String NOTIFICATION_TABLE_NAME = "notification";
    public static final String COLUMN_AGREE = "agree";
    private static final String NOTIFICATION_TEAM = "create table if not exists notification(agree INTEGER);";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void setDB(Context context) {
        File folder = new File(DBLOCATION);
        if (folder.exists()) {
        } else {
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
        sqLiteDatabase.execSQL(RAIN_TEAM);
        sqLiteDatabase.execSQL(RIVER_TEAM);
        sqLiteDatabase.execSQL(WIND_TEAM);
        sqLiteDatabase.execSQL(NATURE_TEAM);
        sqLiteDatabase.execSQL(CHAKRA_TEAM);
        sqLiteDatabase.execSQL(MANTRA_TEAM);
        sqLiteDatabase.execSQL(HZ_TEAM);
        sqLiteDatabase.execSQL(FAV_TITLE_TEAM);
        sqLiteDatabase.execSQL(FAV_LIST_TEAM);
        sqLiteDatabase.execSQL(CREDIT_TEAM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE ispro");
        sqLiteDatabase.execSQL("DROP TABLE " + NOTIFICATION_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + PAGE_ICON_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + PLAYING_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + RAIN_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + WATER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + WIND_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + NATURE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + FAV_TITLE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + FAV_LIST_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + CHAKRA_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + MANTRA_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + HZ_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + CREDIT_TABLE_NAME);
        sqLiteDatabase.execSQL(ISPRO_TEAM);
        sqLiteDatabase.execSQL(NOTIFICATION_TEAM);
        sqLiteDatabase.execSQL(PAGE_ICON_TEAM);
        sqLiteDatabase.execSQL(PLAYING_TEAM);
        sqLiteDatabase.execSQL(RAIN_TEAM);
        sqLiteDatabase.execSQL(RIVER_TEAM);
        sqLiteDatabase.execSQL(WIND_TEAM);
        sqLiteDatabase.execSQL(NATURE_TEAM);
        sqLiteDatabase.execSQL(CHAKRA_TEAM);
        sqLiteDatabase.execSQL(MANTRA_TEAM);
        sqLiteDatabase.execSQL(HZ_TEAM);
        sqLiteDatabase.execSQL(FAV_TITLE_TEAM);
        sqLiteDatabase.execSQL(FAV_LIST_TEAM);
        sqLiteDatabase.execSQL(CREDIT_TEAM);
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
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
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
        String sql = "SELECT * FROM " + getPageName(page);
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
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

//    public ArrayList<PageItem> getRainList() {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM rain";
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
//    public ArrayList<PageItem> getWaterList() {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM water";
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
//    public ArrayList<PageItem> getWindList() {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM wind";
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
//    public ArrayList<PageItem> getNatureList() {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM nature";
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
//    public ArrayList<PageItem> getChakraList() {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM chakra";
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
//    public ArrayList<PageItem> getMantraList() {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM mantra";
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
//    public ArrayList<PageItem> getHzList() {
//        PageItem pageItem = null;
//        ArrayList<PageItem> pageItems = new ArrayList<>();
//
//        openDatabase();
//        String sql = "SELECT * FROM hz";
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

    public void deletePlayingList(int page, int position) {
        sqLiteDatabase = this.getWritableDatabase();
        if (page != 4) {
            sqLiteDatabase.execSQL("delete from playing where page = " + page);
        } else {
            String pnp = page + "-" + position;
            sqLiteDatabase.execSQL("delete from playing where pnp = '" + pnp + "'");
        }
        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 1 where position = " + position);
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

    public void setPlay1(int page, int position) {//같은 page에 다른 재생중인거 있을때 지우고 새로운 재생할거 트랙 재생하기
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 1");//해당페이지 전체 isplay 1로 변경
        sqLiteDatabase.execSQL("delete from playing where page = " + page);//bottom list에서 해당 페이지 있는거 지우기
        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 2 where position = " + position);//새로 재생할거 isplay2로 바꾸기
        sqLiteDatabase.execSQL("insert into playing select * from " + getPageName(page) + " where position = " + position);//새로 재생할거 bottom list에 insert
    }

    public void setIsPlayPage4(int page, int position) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 2 where position = " + position);
        sqLiteDatabase.execSQL("insert into playing select * from " + getPageName(page) + " where position = " + position);
    }

    public void deleteAllPlayingListTest() {
        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("delete from playing");
//        sqLiteDatabase.execSQL("update rain set isplay = 1");
//        sqLiteDatabase.execSQL("update wind set isplay = 1");
//        sqLiteDatabase.execSQL("update favtitle set title = null");
        sqLiteDatabase.execSQL("delete from favlist");
        sqLiteDatabase.execSQL("delete from favtitle");
    }

    public void changePageSeek(int page, int progress, int position, String pnp) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update playing set seek = " + progress + " where pnp = " + "'" + pnp + "'");
        sqLiteDatabase.execSQL("update " + getPageName(page) + " set seek = " + progress + " where position = " + position);
//        sqLiteDatabase.execSQL("update favlist set seek = " + progress + " where pnp = " + "'" + pnp + "'");
    }

//    public void changeSeekInFavList(int progress, String favtitlename, String pnp) {
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("update favlist set seek = " + progress + " where favtitlename = " + "'" + favtitlename + "'" + " and pnp = " + "'" + pnp + "'");
//    }

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
            Toast.makeText(context, "same name have already", Toast.LENGTH_SHORT).show();
        } else {
            checkSameFavList(context, title);
        }
    }

    public void checkSameFavList(Context context, String title) {
        List<String> nowPnps = new ArrayList<>();//현제 playinglist의 pnp list
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            nowPnps.add(MainActivity.bottomSheetPlayList.get(i).getPnp());
            if (i == MainActivity.bottomSheetPlayList.size() - 1) {
                sqLiteDatabase = this.getWritableDatabase();
                List<String> favtitles = new ArrayList<>();//현제 favtitle들의 title list
                Cursor cursor = sqLiteDatabase.rawQuery("select title from favtitle", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    favtitles.add(cursor.getString(0));
                    cursor.moveToNext();
                }


                if (favtitles.size() != 0) {
                    if (haveSame(nowPnps, favtitles)) {
                        Toast.makeText(context, "same list have already", Toast.LENGTH_SHORT).show();
                    } else {
                        addFavTitleList(context, title);
                    }
                } else {
                    addFavTitleList(context, title);
                }
            }
        }
    }

    public void addFavTitleList(Context context, String title1) {
        FavTitleItem favTitleItem = null;
        sqLiteDatabase = this.getWritableDatabase();

        if (MainActivity.bottomSheetPlayList.size() != 0) {
            sqLiteDatabase.execSQL("insert into favtitle values (" + "'" + title1 + "'" + "," + 1 + "," + 1 + ")");
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
            contentValues.put("pnp", MainActivity.bottomSheetPlayList.get(i).getPnp());
            contentValues.put("imagedefault", MainActivity.bottomSheetPlayList.get(i).getImgdefault());
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

    public void changeFavTitleName(String oldTitle, String newTitle) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update favtitle set title = " + "'" + newTitle + "' where title = " + "'" + oldTitle + "'");
        sqLiteDatabase.execSQL("update favlist set favtitlename = " + "'" + newTitle + "' where favtitlename = " + "'" + oldTitle + "'");
        for (int i = 0; i < FavPage.favTitleItemArrayList.size(); i++) {
            if (FavPage.favTitleItemArrayList.get(i).getTitle().equals(oldTitle)) {
                FavPage.favTitleItemArrayList.get(i).setTitle(newTitle);
                FavPage.adapter.notifyItemChanged(i);
                FavPage.adapter.notifyDataSetChanged();
                if (EditFavTitleDialog.alertDialog.isShowing()) {
                    EditFavTitleDialog.alertDialog.dismiss();
                }
                break;
            }
        }
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

    public ArrayList<FavListItem> getFavListItem(String title) {
        FavListItem favListItem = null;
        ArrayList<FavListItem> favListItems = new ArrayList<>();
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from favlist where favtitlename = " + "'" + title + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            favListItem = new FavListItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getInt(10), cursor.getString(11), cursor.getInt(12), cursor.getInt(13));
            favListItems.add(favListItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return favListItems;
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
            sqLiteDatabase.execSQL("update favtitle set title = " + afterTitle + " where title = " + beforeTitle);
        }
        sqLiteDatabase.execSQL("delete from favlist where favtitlename = " + beforeTitle);
        for (int i = 0; i < arrayList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("page", arrayList.get(i).getPage());
            contentValues.put("position", arrayList.get(i).getPosition());
            contentValues.put("pnp", arrayList.get(i).getPnp());
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
//        if (pagelist.contains(1)) {
//            sqLiteDatabase.execSQL("update rain set isplay = 1 where isplay = 2");
//        } else if (pagelist.contains(2)) {
//            sqLiteDatabase.execSQL("update water set isplay = 1 where isplay = 2");
//        } else if (pagelist.contains(3)) {
//            sqLiteDatabase.execSQL("update wind set isplay = 1 where isplay = 2");
//        } else if (pagelist.contains(4)) {
//            sqLiteDatabase.execSQL("update nature set isplay = 1 where isplay = 2");
//        } else if (pagelist.contains(5)) {
//            sqLiteDatabase.execSQL("update chakra set isplay = 1 where isplay = 2");
//        } else if (pagelist.contains(6)) {
//            sqLiteDatabase.execSQL("update mantra set isplay = 1 where isplay = 2");
//        } else if (pagelist.contains(7)) {
//            sqLiteDatabase.execSQL("update hz set isplay = 1 where isplay = 2");
//        }
        for (int i = 0; i < pagelist.size(); i++) {
            if (pagelist.get(i) == 1 && RainPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update rain set isplay = 1 where isplay = 2");
                RainPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                RainPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                RainPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 2 && WaterPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update water set isplay = 1 where isplay = 2");
                WaterPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                WaterPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                WaterPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 3 && WindPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update wind set isplay = 1 where isplay = 2");
                WindPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                WindPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                WindPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 4 && NaturePage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update nature set isplay = 1 where isplay = 2");
                NaturePage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                NaturePage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                NaturePage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 5 && ChakraPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update chakra set isplay = 1 where isplay = 2");
                ChakraPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                ChakraPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                ChakraPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 6 && MantraPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update mantra set isplay = 1 where isplay = 2");
                MantraPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                MantraPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                MantraPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 7 && HzPage.arrayList.size() != 0) {
                sqLiteDatabase.execSQL("update hz set isplay = 1 where isplay = 2");
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
        sqLiteDatabase.execSQL("update rain set isplay = 1 where isplay = 2");
        sqLiteDatabase.execSQL("update water set isplay = 1 where isplay = 2");
        sqLiteDatabase.execSQL("update wind set isplay = 1 where isplay = 2");
        sqLiteDatabase.execSQL("update nature set isplay = 1 where isplay = 2");
        sqLiteDatabase.execSQL("update chakra set isplay = 1 where isplay = 2");
        sqLiteDatabase.execSQL("update mantra set isplay = 1 where isplay = 2");
        sqLiteDatabase.execSQL("update hz set isplay = 1 where isplay = 2");
    }

    public void addFavListInPlayinglist(String title) {
        sqLiteDatabase = this.getWritableDatabase();
        PageItem pageItem = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from favlist where favtitlename = " + "'" + title + "'", null);
        cursor.moveToFirst();
        int count = 0;
        while (!cursor.isAfterLast()) {
            count += 1;
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(10), cursor.getString(11), cursor.getInt(12), cursor.getInt(13));
            MainActivity.bottomSheetPlayList.add(pageItem);
            ContentValues contentValues = new ContentValues();
            contentValues.put("page", cursor.getInt(0));
            contentValues.put("position", cursor.getInt(1));
            contentValues.put("pnp", cursor.getString(2));
            contentValues.put("imgdefault", cursor.getBlob(3));
            contentValues.put("img", cursor.getBlob(4));
            contentValues.put("darkdefault", cursor.getBlob(5));
            contentValues.put("dark", cursor.getBlob(6));
            contentValues.put("seek", cursor.getInt(7));
            contentValues.put("isplay", 2);
            contentValues.put("time", cursor.getInt(10));
            contentValues.put("name", cursor.getString(11));
            contentValues.put("ispro", cursor.getInt(12));
            contentValues.put("needdownload", cursor.getInt(13));
            setIsPlay2WhenPlayInFavTitle(cursor.getInt(0), cursor.getInt(1));
            sqLiteDatabase.insert("playing", null, contentValues);
            cursor.moveToNext();
        }
        MainActivity.bottomSheetAdapter.notifyItemRangeInserted(0, count);
        MainActivity.bottomSheetAdapter.notifyDataSetChanged();
        cursor.close();
        closeDatabse();
    }

    private void setIsPlay2WhenPlayInFavTitle(int page, int position) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 2 where position = " + position);
    }

    public void whenAppKillTask() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
        sqLiteDatabase.execSQL("update favtitle set isedit = 1 where isedit = 2");
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

    public int getIsProUser() {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ispro FROM ispro", null);
        cursor.moveToFirst();
        int ispro = cursor.getInt(0);
        cursor.close();
        closeDatabse();
        return ispro;
    }

    public void changeIsProUser(int ispro) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update ispro set ispro = " + ispro);
        closeDatabse();
    }

    public PageItem getPageItemInStorageManage(int page, int position) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + getPageName(page) + " where position = " + position, null);
        cursor.moveToFirst();
        PageItem pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getBlob(5), cursor.getBlob(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getString(10), cursor.getInt(11), cursor.getInt(12));
        cursor.close();
        closeDatabse();
        return pageItem;
    }

    public String getTrackName(int page, int position) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select name from " + getPageName(page), null);
        cursor.moveToFirst();
        String name = cursor.getString(0);
        cursor.close();
        closeDatabse();
        return name;
    }

//    public String getTest() {
//        String pnp;
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("select pnp from rain where _rowid_ = 1", null);
//        cursor.moveToFirst();
//        pnp = cursor.getString(0);
//        cursor.close();
//        closeDatabse();
//        return pnp;
//    }

    //Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);

    public void changePageSeekWhenPlayInFavTitle(int page, int position, int volumn) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update " + getPageName(page) + " set seek = " + volumn + " where position = " + position);
    }

    public void changeFavTitleIsEdit(String title, int isedit) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update favtitle set isedit = " + isedit + " where title = " + title);
    }

//    public int getIsProUser() {
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ispro FROM ispro", null);
//        cursor.moveToFirst();
//        int ispro = cursor.getInt(0);
//        cursor.close();
//        closeDatabse();
//        return ispro;
//    }

    public int getFavTitleIsEdit(String title) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select isedit from favtitle where title = " + title, null);
        cursor.moveToFirst();
        int isedit = cursor.getInt(0);
        cursor.close();
        closeDatabse();
        return isedit;
    }
}
