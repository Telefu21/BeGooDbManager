package com.example.begoodbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter extends SQLiteOpenHelper
{
    static final int    IMG_WIDTH_COL_INDEX = 0;
    static final int    IMG_HEIGHT_COL_INDEX = 1;
    static final int    IMG_BYTES_COL_INDEX = 2;
    static final int    CUSTOM_STR_COL_INDEX = 3;

    private static final int    DB_USER_INFO_USER_NAME_INDEX = 0;
    private static final int    DB_USER_INFO_PASSWORD_INDEX = 1;
    private static final int    DB_USER_INFO_SCORE_INDEX = 2;
    private static final int    DB_USER_INFO_DATA_OFFSET_INDEX = 3;
    private static final int    DB_USER_INFO_LEVEL_INDEX = 4;
    private static final int    DB_USER_INFO_DIFFICULTY_INDEX = 5;
    private static final int    DB_USER_INFO_CATEGORY_INDEX = 6;
	private static final int    DB_USER_INFO_MAX_LEVEL_REP_COUNTER_INDEX = 7;
	private static final int    DB_USER_INFO_NUM_OF_CARD_INDEX = 8;
	private static final int    DB_USER_INFO_MIN_NUM_OF_CARD_BASE_INDEX = 9;

    private String      dbName, tbName;

    public DbAdapter(Context context, String name, CursorFactory factory, int version, String tbName)
    {
        super(context, name, factory, version);

        this.tbName = tbName;
        this.dbName = name;
    }

    public ImgDbRowItems[] getData(int numOfRowsToRead, int dataOffset)
    {
        Cursor cursor;

        if(numOfRowsToRead == 0)
        {
            cursor = getWritableDatabase().rawQuery("SELECT * FROM " + tbName, null);
        }
        else
        {
            cursor = getWritableDatabase().rawQuery("SELECT * FROM " + tbName + " LIMIT " + numOfRowsToRead + " OFFSET " + dataOffset, null);
        }

        ImgDbRowItems[] dbRowItems = new ImgDbRowItems[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++)
        {
            dbRowItems[i] = new ImgDbRowItems();
        }

        if (cursor != null)
        {
            cursor.moveToFirst();
            int index = 0;

            do
            {
                dbRowItems[index].setBmp(BmpProcessor.byteArrayToBmp(cursor.getInt(IMG_WIDTH_COL_INDEX),cursor.getInt(IMG_HEIGHT_COL_INDEX), cursor.getBlob(IMG_BYTES_COL_INDEX)));
                dbRowItems[index].setCustomStr(cursor.getString(CUSTOM_STR_COL_INDEX));
                dbRowItems[index].setWidth(cursor.getInt(IMG_WIDTH_COL_INDEX));
                dbRowItems[index].setHeight(cursor.getInt(IMG_HEIGHT_COL_INDEX));

                index++;
            }
            while (cursor.moveToNext());
        }

        return dbRowItems;
    }

    public UserInfo getData(String userName)
    {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + tbName + " WHERE username=?", new String[] { userName });

        UserInfo userInfo = new UserInfo();

        if (cursor != null)
        {
            cursor.moveToFirst();

            userInfo.setCategory(cursor.getInt(DB_USER_INFO_CATEGORY_INDEX));
            userInfo.setDataOffset(cursor.getInt(DB_USER_INFO_DATA_OFFSET_INDEX));
            userInfo.setDifficulty(cursor.getInt(DB_USER_INFO_DIFFICULTY_INDEX));
            userInfo.setLevel(cursor.getInt(DB_USER_INFO_LEVEL_INDEX));
            userInfo.setPassword(cursor.getString(DB_USER_INFO_PASSWORD_INDEX));
            userInfo.setScore(cursor.getInt(DB_USER_INFO_SCORE_INDEX));
			userInfo.setMaxLevelRepCounter(cursor.getInt(DB_USER_INFO_MAX_LEVEL_REP_COUNTER_INDEX));
			userInfo.setNumOfCards(cursor.getInt(DB_USER_INFO_NUM_OF_CARD_INDEX));
			userInfo.setMinNumOfCardBase(cursor.getInt(DB_USER_INFO_MIN_NUM_OF_CARD_BASE_INDEX));
            userInfo.setUsername(userName);
        }

        return userInfo;
    }

    public void putData(int width, int height, byte[] img, String customStr)
    {
        ContentValues cv = new ContentValues();

        cv.put("image", img);
        cv.put("width", width);
        cv.put("height", height);
        cv.put("customStr", customStr);

        getWritableDatabase().insert(tbName, null, cv);
    }

    public void putData(UserInfo userInfo)
    {
        ContentValues cv = new ContentValues();

        cv.put("username", userInfo.getUsername());
        cv.put("password", userInfo.getPassword());
        cv.put("score", userInfo.getScore());
        cv.put("dataOffset", userInfo.getDataOffset());
        cv.put("level", userInfo.getLevel());
        cv.put("difficulty", userInfo.getDifficulty());
        cv.put("category", userInfo.getCategory());
		cv.put("maxLevelRepCounter", userInfo.getMaxLevelRepCounter());
		cv.put("numOfCards", userInfo.getNumOfCards());
		cv.put("minNumOfCardBase", userInfo.getMinNumOfCardBase());

        getWritableDatabase().insert(tbName, null, cv);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        if(dbName == "UserInfoDb")
        {
            db.execSQL("CREATE TABLE IF NOT EXISTS tbUserInfo (username TEXT PRIMARY KEY, password TEXT, score INTEGER, dataOffset INTEGER, level INTEGER, difficulty INTEGER, category INTEGER, maxLevelRepCounter INTEGER, numOfCards INTEGER, minNumOfCardBase INTEGER);");
        }

        if(dbName == "CardsDb")
        {
            db.execSQL("CREATE TABLE IF NOT EXISTS tbCategory (width INTEGER, height INTEGER, image BLOB, customStr TEXT);");
            db.execSQL("CREATE TABLE IF NOT EXISTS tbPlayCards (width INTEGER, height INTEGER, image BLOB, customStr TEXT);");
            db.execSQL("CREATE TABLE IF NOT EXISTS tbEnglish (width INTEGER, height INTEGER, image BLOB, customStr TEXT);");
            db.execSQL("CREATE TABLE IF NOT EXISTS tbAnatomy (width INTEGER, height INTEGER, image BLOB, customStr TEXT);");
            db.execSQL("CREATE TABLE IF NOT EXISTS tbHistory (width INTEGER, height INTEGER, image BLOB, customStr TEXT);");
            db.execSQL("CREATE TABLE IF NOT EXISTS tbTarot (width INTEGER, height INTEGER, image BLOB, customStr TEXT);");
            db.execSQL("CREATE TABLE IF NOT EXISTS tbFlags (width INTEGER, height INTEGER, image BLOB, customStr TEXT);");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public long getRowCount()
    {
        return DatabaseUtils.queryNumEntries(getWritableDatabase(), tbName);
    }

    public int updateData(String primaryKey, String key, int value)
    {
        ContentValues values = new ContentValues();

        values.put(key, value);

        return getWritableDatabase().update(tbName, values, "username=?", new String[] { primaryKey });
    }

    public int updateData(String primaryKey, String key, String value)
    {
        ContentValues values = new ContentValues();

        values.put(key, value);

        return getWritableDatabase().update(tbName, values, "username=?", new String[] { primaryKey });
    }
}
