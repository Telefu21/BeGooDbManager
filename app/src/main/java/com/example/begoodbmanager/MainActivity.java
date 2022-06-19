package com.example.begoodbmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbAdapter categoriesDb = new DbAdapter(getApplicationContext(), "CardsDb", null, 1, "tbCategory");

        initializeCategoriesTable(categoriesDb);

        DbAdapter userInfoDb = new DbAdapter(getApplicationContext(), "UserInfoDb", null, 1, "tbUserInfo");

        initializeUserInfoTable(userInfoDb);

        DbAdapter memCardsDb = new DbAdapter(getApplicationContext(), "CardsDb", null, 1, "tbPlayCards");

        initializeCardsTable(memCardsDb);
    }

    private void initializeCategoriesTable(DbAdapter dba)
    {
        Gson gson = new Gson();

        CategoriesMiscParams params = new CategoriesMiscParams();

        params.setDelayTimeToEndLevelMSec(1000);
        params.setDelayTimeToTurnMSec(400);
        params.setTimerValuePerCardMSec(4000);
		params.setMaxLevelRepetitionCounterMaxValue(7);
        params.setMinDifficultyValue(2);
        params.setMaxDifficultyValue(4);

        params.setTableName("tbPlayCards");
        putData(R.drawable.five, gson.toJson(params), dba);
        params.setTableName("tbEnglish");
        putData(R.drawable.six, gson.toJson(params), dba);
        params.setTableName("tbAnatomy");
        putData(R.drawable.seven, gson.toJson(params), dba);
        params.setTableName("tbHistory");
        putData(R.drawable.eight, gson.toJson(params), dba);
        params.setTableName("tbTarot");
        putData(R.drawable.nine, gson.toJson(params), dba);
        params.setTableName("tbFlags");
        putData(R.drawable.ten, gson.toJson(params), dba);
    }

    private void putData(int id, String customStr, DbAdapter dba)
    {
        Bitmap bmp = BmpProcessor.resourceToBmp(id, this);

        dba.putData(bmp.getWidth(), bmp.getHeight(), BmpProcessor.bmpToByteArray(bmp), customStr);
    }

    private void initializeCardsTable(DbAdapter dba)
    {
        putData(R.drawable.back, "NULL", dba);
        putData(R.drawable.match, "NULL", dba);
		putData(R.drawable.background, "NULL", dba);
        putData(R.drawable.one, "NULL", dba);
        putData(R.drawable.two, "NULL", dba);
        putData(R.drawable.three, "NULL", dba);
        putData(R.drawable.four, "NULL", dba);
        putData(R.drawable.five, "NULL", dba);
        putData(R.drawable.six, "NULL", dba);
        putData(R.drawable.seven, "NULL", dba);
        putData(R.drawable.eight, "NULL", dba);
        putData(R.drawable.nine, "NULL", dba);
        putData(R.drawable.ten, "NULL", dba);
        putData(R.drawable.joker, "NULL", dba);
        putData(R.drawable.kiz, "NULL", dba);
        putData(R.drawable.papaz, "NULL", dba);
        putData(R.drawable.vale, "NULL", dba);
    }

    private void initializeUserInfoTable(DbAdapter dba)
    {
        UserInfo userInfo = new UserInfo();

        userInfo.setUsername("default user");
        userInfo.setScore(0);
        userInfo.setPassword("");
        userInfo.setLevel(0);
        userInfo.setDifficulty(2);
        userInfo.setDataOffset(0);
        userInfo.setCategory(0);
		userInfo.setMaxLevelRepCounter(0);
		userInfo.setNumOfCards(0);
		userInfo.setMinNumOfCardBase(6);

        dba.putData(userInfo);
    }
}