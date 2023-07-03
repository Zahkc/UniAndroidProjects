package com.zb147.quizforkids;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quiz4kidz.db";
    public DatabaseHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS (USERNAME TEXT PRIMARY KEY, EMAIL TEXT, PASSWORD TEXT, POINTS INTEGER)");

        db.execSQL("INSERT INTO USERS (USERNAME, EMAIL, PASSWORD, POINTS) VALUES (\"John\", \"john@email.com\", \"password123\", 0)");
        db.execSQL("INSERT INTO USERS (USERNAME, EMAIL, PASSWORD, POINTS) VALUES (\"Lisa\", \"lisa@email.com\", \"password321\", 0)");

        db.execSQL("CREATE TABLE ANIMAL (QNUM INTEGER PRIMARY KEY, IMAGE TEXT, QUESTION TEXT, ANSWER TEXT)");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (1, \"camel\", \"Enter the name of this animal.\", \"Camel\")");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (2, \"giraffe\", \"Enter the name of this animal.\", \"Giraffe\")");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (3, \"hippo\", \"Enter the name of this animal.\", \"Hippo\")");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (4, \"koala\", \"Enter the name of this animal.\", \"Koala\")");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (5, \"meerkat\", \"Enter the name of this animal.\", \"Meerkat\")");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (6, \"panda\", \"Enter the name of this animal.\", \"Panda\")");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (7, \"pig\", \"Enter the name of this animal.\", \"Pig\")");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (8, \"racoon\", \"Enter the name of this animal.\", \"Racoon\")");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (9, \"wombat\", \"Enter the name of this animal.\", \"Wombat\")");
        db.execSQL("INSERT INTO ANIMAL (QNUM, IMAGE, QUESTION, ANSWER) VALUES (10, \"sloth\", \"Enter the name of this animal.\", \"Sloth\")");
        db.execSQL("CREATE TABLE CARTOON (QNUM INTEGER PRIMARY KEY, QUESTION TEXT, ANSWER INTEGER)");

        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (1, \"In Popeye the sailor man, who is the sailor?\n A. Popeye \n B. Edgar \n C. George\", 1)");
        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (2, \"In Spongebob Squarepants, who is the Spongebob's Best Friend?\n A. Patrick \n B. Gary \n C. George\", 1)");
        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (3, \"In MTV's Daria, what is Daria's jacket colour?\n A. Green \n B. Pink \n C. George\", 1)");
        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (4, \"In The Flintstones, what is Barneys last name?\n A. Rubble \n B. Gravle \n C. George\", 1)");
        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (5, \"In Johnny Bravo, who is Johnny's type?\n A. Breathing \n B. Brunettes \n C. George\", 1)");
        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (6, \"From Dexters Lab, how tall is Dexter?\n A. ~1 Foot \n B. 8 Foot \n C. George's Height\", 1)");
        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (7, \"In Yogi Bear, what language do the bears speak?\n A. English \n B. Grizzaly \n C. Georgian\", 1)");
        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (8, \"In Winnie-The-Pooh, what garment dose Winnie wear?\n A. Shirt \n B. Pants \n C. George\", 1)");
        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (9, \"In the many Batman cartoons, who is Batmans Sidekick?\n A. Robin \n B. Cat-Woman \n C. George\", 1)");
        db.execSQL("INSERT INTO CARTOON (QNUM, QUESTION, ANSWER) VALUES (10,\"In Avatar The Last Airbender, What is happening in Ba Sing Se?\n A. No War \n B. War \n C. George's Rage\", 1)");


        db.execSQL("CREATE TABLE SCORE (USER TEXT, DATEACHIVED TIMESTAMP, POINTS INTEGER, QUIZ TEXT)");

        db.execSQL("INSERT INTO SCORE (USER, DATEACHIVED, POINTS, QUIZ) VALUES ('John', CURRENT_TIMESTAMP, 10, 'CARTOON')");
        db.execSQL("INSERT INTO SCORE (USER, DATEACHIVED, POINTS, QUIZ) VALUES ('John', CURRENT_TIMESTAMP, 5, 'CARTOON')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS ANIMAL");
        db.execSQL("DROP TABLE IF EXISTS CARTOON");
        db.execSQL("DROP TABLE IF EXISTS SCORE");
        onCreate(db);
    }

    public boolean login(String user, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(1) FROM USERS WHERE USERNAME = '" + user + "' AND PASSWORD = '" + pass +"'", null);
        res.moveToFirst();
        if (res.getInt(0) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean login(String user, String pass, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("INSERT INTO USERS (USERNAME, EMAIL, PASSWORD, POINTS) VALUES (\"" + user + "\", \"" + email + "\", \"" + pass + "\", 0)");

        } catch (Exception e) {
            return false;
        }
        return(login(user, pass));
    }

    public int getUserPoints(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT POINTS FROM USERS WHERE USERNAME = '" + user + "'", null);
        res.moveToFirst();
        return (res.getInt(0));
    }

    public void updateUserPoints(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT POINTS FROM SCORE WHERE USER = '" + user + "'", null);
        int total = 0;
        while(res.moveToNext()){
            total += res.getInt(0);
        }
        db.execSQL("UPDATE USERS SET POINTS = " + total + " WHERE USERNAME = '" + user + "'");
    }

    public String getQuestion(boolean quiz, int num){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (quiz){
            res = db.rawQuery("SELECT QUESTION FROM CARTOON WHERE QNUM = " + num, null);
        } else {
            res = db.rawQuery("SELECT QUESTION FROM ANIMAL WHERE QNUM = " + num, null);
        }
        res.moveToFirst();
        return (res.getString(0));
    }
    public String getAnswer(boolean quiz, int num){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (quiz){
            res = db.rawQuery("SELECT ANSWER FROM CARTOON WHERE QNUM = " + num, null);
        } else {
            res = db.rawQuery("SELECT ANSWER FROM ANIMAL WHERE QNUM = " + num, null);
        }
        res.moveToFirst();
        return (res.getString(0));
    }
    public String getImage(int num){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT IMAGE FROM ANIMAL WHERE QNUM = " + num, null);
        res.moveToFirst();
        return (res.getString(0));
    }

    public void addRecord(String user, int points, String quizType){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO SCORE (USER, DATEACHIVED, POINTS, QUIZ) VALUES ('"+user+"', CURRENT_TIMESTAMP, "+points+", '"+quizType+"')");
    }

    public String[] getRecords(String user, boolean sort){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (sort){
            res = db.rawQuery("SELECT QUIZ, DATEACHIVED, POINTS FROM SCORE WHERE USER = '" + user + "' ORDER BY DATEACHIVED DESC", null);
        } else {
            res = db.rawQuery("SELECT QUIZ, DATEACHIVED, POINTS FROM SCORE WHERE USER = '" + user + "' ORDER BY QUIZ DESC", null);
        }

        String records[] = new String[res.getCount()];
        int x = 0;
        while (res.moveToNext()){
            records[x] = '"' + res.getString(0) + '"' + " area - attempt completed on " + res.getString(1) + " - points earned " + res.getInt(2);
            x++;
        }
        return records;
    }

}
