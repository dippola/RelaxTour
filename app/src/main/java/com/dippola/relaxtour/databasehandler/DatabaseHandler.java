package com.dippola.relaxtour.databasehandler;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.dialog.AddTitleDialog;
import com.dippola.relaxtour.dialog.DeleteFavTitleDialog;
import com.dippola.relaxtour.dialog.EditFavTitleDialog;
import com.dippola.relaxtour.pages.FavPage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.item.FavListItem;
import com.dippola.relaxtour.pages.item.FavTitleItem;
import com.dippola.relaxtour.pages.item.PageItem;

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
    private static final String WIND_TABLE_NAME = "wind";
    private static final String CHAKRA_TABLE_NAME = "chakra";
    private static final String HZ_TABLE_NAME = "hz";

    public static final String COLUMN_PAGE = "page";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_PNP = "pnp";
    public static final String COLUMN_IMGDEFAULT = "imgdefault";
    public static final String COLUMN_IMAGE = "img";
    public static final String COLUMN_SEEK = "seek";
    public static final String COLUMN_ISPLAY = "isplay";

    //fav
    private static final String FAV_TITLE_TABLE_NAME = "favtitle";
    public static final String COLUMN_FAV_TITLE = "title";
    public static final String COLUMN_FAV_ISPLAY = "isopen";
    private static final String FAV_LIST_TABLE_NAME = "favlist";
    public static final String COLUMN_FAVTITLENAME = "favtitlename";

    private static final String PLAYING_TEAM = "create table if not exists " + PLAYING_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER" + ");";
    private static final String RAIN_TEAM = "create table if not exists " + RAIN_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER" + ");";
    private static final String WIND_TEAM = "create table if not exists " + WIND_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER" + ");";
    private static final String CHAKRA_TEAM = "create table if not exists " + CHAKRA_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER" + ");";
    private static final String HZ_TEAM = "create table if not exists " + HZ_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER" + ");";

    private static final String FAV_TITLE_TEAM = "create table if not exists " + FAV_TITLE_TABLE_NAME + "(" + COLUMN_FAV_TITLE + " TEXT," + COLUMN_FAV_ISPLAY + " INTEGER" + ");";
    private static final String FAV_LIST_TEAM = "create table if not exists " + WIND_TABLE_NAME + "(" + COLUMN_PAGE + " INTEGER," + COLUMN_POSITION + " INTEGER," + COLUMN_PNP + " TEXT, " + COLUMN_IMGDEFAULT + " BLOB," + COLUMN_IMAGE + " BLOB," + COLUMN_SEEK + " INTEGER," + COLUMN_ISPLAY + " INTEGER," +  COLUMN_FAVTITLENAME + " INTEGER" + ");";

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
        sqLiteDatabase.execSQL(PLAYING_TEAM);
        sqLiteDatabase.execSQL(RAIN_TEAM);
        sqLiteDatabase.execSQL(WIND_TEAM);
        sqLiteDatabase.execSQL(CHAKRA_TEAM);
        sqLiteDatabase.execSQL(HZ_TEAM);
        sqLiteDatabase.execSQL(FAV_TITLE_TEAM);
        sqLiteDatabase.execSQL(FAV_LIST_TEAM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + PLAYING_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + RAIN_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + WIND_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + FAV_TITLE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + FAV_LIST_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + CHAKRA_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + HZ_TABLE_NAME);
        sqLiteDatabase.execSQL(PLAYING_TEAM);
        sqLiteDatabase.execSQL(RAIN_TEAM);
        sqLiteDatabase.execSQL(WIND_TEAM);
        sqLiteDatabase.execSQL(CHAKRA_TEAM);
        sqLiteDatabase.execSQL(HZ_TEAM);
        sqLiteDatabase.execSQL(FAV_TITLE_TEAM);
        sqLiteDatabase.execSQL(FAV_LIST_TEAM);
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

    public ArrayList<PageItem> getBottomSheetPlayingList() {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();

        openDatabase();
        String sql = "SELECT * FROM playing";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getInt(5), cursor.getInt(6));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return pageItems;
    }

    public ArrayList<PageItem> getRainList() {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();

        openDatabase();
        String sql = "SELECT * FROM rain";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getInt(5), cursor.getInt(6));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return pageItems;
    }

    public ArrayList<PageItem> getWindList() {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();

        openDatabase();
        String sql = "SELECT * FROM wind";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getInt(5), cursor.getInt(6));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return pageItems;
    }

    public ArrayList<PageItem> getChakraList() {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();

        openDatabase();
        String sql = "SELECT * FROM chakra";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getInt(5), cursor.getInt(6));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return pageItems;
    }

    public ArrayList<PageItem> getHzList() {
        PageItem pageItem = null;
        ArrayList<PageItem> pageItems = new ArrayList<>();

        openDatabase();
        String sql = "SELECT * FROM hz";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getInt(5), cursor.getInt(6));
            pageItems.add(pageItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        return pageItems;
    }

    public void deletePlayingList(int page, int position) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from playing where page = " + page);
        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 1 where position = " + position);
    }

    String getPageName(int page) {
        if (page == 1) {
            return "rain";
        } else if (page == 2) {
            return "wind";
        } else if (page == 3) {
            return "chakra";
        } else if (page == 4) {
            return "hz";
        } else {
            return "null";
        }
    }

    public void setPlay1(int page, int position) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update " + getPageName(page) + " set isplay = 1");
        sqLiteDatabase.execSQL("delete from playing where page = " + page);
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
        sqLiteDatabase.execSQL("update favlist set seek = " + progress + " where pnp = " + "'" + pnp + "'");
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
            Toast.makeText(context, "같은이름", Toast.LENGTH_SHORT).show();
        } else {
            checkSameFavList(context, title);
//            addFavTitleList(title);
        }
    }

    public void checkSameFavList(Context context, String title) {
        Log.d(">>>check", "4");
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


                if (favtitles.size() != 0) {//여기서 error
                    if (haveSame(nowPnps, favtitles)) {
                        Toast.makeText(context, "same already", Toast.LENGTH_SHORT).show();
                    } else {
                        addFavTitleList(title);
                    }
                } else {
                    addFavTitleList(title);
                }
            }
        }
    }

    public void addFavTitleList(String title1) {
        FavTitleItem favTitleItem = null;
        sqLiteDatabase = this.getWritableDatabase();

        if (MainActivity.bottomSheetPlayList.size() != 0) {
            String pnp = MainActivity.bottomSheetPlayList.get(0).getPnp();
            sqLiteDatabase.execSQL("insert into favtitle values (" + "'" + title1 + "'" + "," + 1 + ")");
            favTitleItem = new FavTitleItem(title1, 1);
            FavPage.favTitleItemArrayList.add(favTitleItem);
            FavPage.adapter.notifyItemInserted(FavPage.favTitleItemArrayList.size() - 1);
            FavPage.adapter.notifyDataSetChanged();
            if (AddTitleDialog.alertDialog.isShowing()) {
                AddTitleDialog.alertDialog.dismiss();
            }
            addFavList(title1);
        }
    }

    public void addFavList(String title) {
        sqLiteDatabase = this.getWritableDatabase();

        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("page", MainActivity.bottomSheetPlayList.get(i).getPage());
            contentValues.put("position", MainActivity.bottomSheetPlayList.get(i).getPosition());
            contentValues.put("pnp", MainActivity.bottomSheetPlayList.get(i).getPnp());
            contentValues.put("imagedefault", MainActivity.bottomSheetPlayList.get(i).getImgdefault());
            contentValues.put("img", MainActivity.bottomSheetPlayList.get(i).getImg());
            contentValues.put("seek", MainActivity.bottomSheetPlayList.get(i).getSeek());
            contentValues.put("isplay", MainActivity.bottomSheetPlayList.get(i).getIsplay());
            contentValues.put("favtitlename", title);
            sqLiteDatabase.insert("favlist", null, contentValues);
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
                //move to next 없어서 error난걸수도있음
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
            favTitleItem = new FavTitleItem(cursor.getString(0), cursor.getInt(1));
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
            favListItem = new FavListItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7));
            favListItems.add(favListItem);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabse();
        Log.d(">>>favlist", "size: " + favListItems.size());
        return favListItems;
    }

    public void removeFavList(String title) {
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select _rowid_ from favtitle where title = " + title, null);
        cursor.moveToFirst();
        int rowid = cursor.getInt(0);
        sqLiteDatabase.execSQL("delete from favtitle where _rowid_ = " + rowid);
        sqLiteDatabase.execSQL("delete from favlist where favtitlename = " + rowid);
        FavPage.favTitleItemArrayList.remove(rowid - 1);
        FavPage.adapter.notifyItemRemoved(rowid - 1);
        FavPage.adapter.notifyDataSetChanged();
        sqLiteDatabase.execSQL("vacuum");
        cursor.close();
        closeDatabse();
        if (DeleteFavTitleDialog.alertDialog.isShowing()) {
            DeleteFavTitleDialog.alertDialog.dismiss();
        }
    }

    public void changeIsOpen(int isopen, String title) {
        sqLiteDatabase = this.getWritableDatabase();
        if (isopen == 1) {
            sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
            sqLiteDatabase.execSQL("update favtitle set isopen = 2 where title = " + "'" + title + "'");
        } else if (isopen == 2) {
            sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
        }
    }

    public void deleteAllPlayinglist(ArrayList<Integer> pagelist, ArrayList<Integer> positionlist, String title) {
        Log.d(">>>DatabaseHandler", "check, title: + " + title);
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from playing");
        sqLiteDatabase.execSQL("update rain set isplay = 1 where isplay = 2");
        sqLiteDatabase.execSQL("update wind set isplay = 1 where isplay = 2");
        for (int i = 0; i < pagelist.size(); i++) {
            if (pagelist.get(i) == 1) {
                sqLiteDatabase.execSQL("update rain set isplay = 2 where position = " + positionlist.get(i));
                RainPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                RainPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                RainPage.adapter.notifyDataSetChanged();
            } else if (pagelist.get(i) == 2) {
                sqLiteDatabase.execSQL("update wind set isplay = 2 where position = " + positionlist.get(i));
                WindPage.arrayList.get(positionlist.get(i) - 1).setIsplay(1);
                WindPage.adapter.notifyItemChanged(positionlist.get(i) - 1);
                WindPage.adapter.notifyDataSetChanged();
            }
        }
        sqLiteDatabase.execSQL("vacuum");

        addFavListInPlayinglist(title);
    }

    public void addFavListInPlayinglist(String title) {
        Log.d(">>>DatabaseHandler", "check, title: " + title);
        sqLiteDatabase = this.getWritableDatabase();
        PageItem pageItem = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from favlist where favtitlename = " + "'" + title + "'", null);
        cursor.moveToFirst();
        int count = 0;
        while (!cursor.isAfterLast()) {
            count += 1;
            pageItem = new PageItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getBlob(3), cursor.getBlob(4), cursor.getInt(5), 2);
            MainActivity.bottomSheetPlayList.add(pageItem);
            ContentValues contentValues = new ContentValues();
            contentValues.put("page", cursor.getInt(0));
            contentValues.put("position", cursor.getInt(1));
            contentValues.put("pnp", cursor.getString(2));
            contentValues.put("imgdefault", cursor.getBlob(3));
            contentValues.put("img", cursor.getBlob(4));
            contentValues.put("seek", cursor.getInt(5));
            contentValues.put("isplay", 2);
            sqLiteDatabase.insert("playing", null, contentValues);
            cursor.moveToNext();
        }
        MainActivity.bottomSheetAdapter.notifyItemRangeInserted(0, count);
        MainActivity.bottomSheetAdapter.notifyDataSetChanged();
        cursor.close();
        closeDatabse();
    }

    public void whenAppKillTask() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update favtitle set isopen = 1 where isopen = 2");
    }
}
