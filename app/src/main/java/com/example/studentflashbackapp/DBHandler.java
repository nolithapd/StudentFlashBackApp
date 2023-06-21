package com.example.studentflashbackapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "carddb";

    // int for our database version
    private static final int DB_VERSION = 1;

    // variable is for our table name.
    private static final String TABLE_NAME = "card";

    // variable is for our id column.
    private static final String ID_COL1 = "id";

    //variable is for our card topic column
    private static final String CARD_TOPIC= "cardTopic";

    //variable is for our card front column
    private static final String FRONT_COL = "front";

    //variable is for our card back column
    private static final String BACK_COL = "back";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {

        //creating an sqlite query and we are setting our column names along with their data types.
        String query1 = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CARD_TOPIC + " TEXT,"
                + FRONT_COL + " TEXT,"
                + BACK_COL + " TEXT)";

        // method to execute above sql query
        db.execSQL(query1);

    }
   // method is use to updating card to our sqlite database.
    public void updateCard(int id, String topic, String front, String back){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //passing all values along with its key and value pair.
        values.put(CARD_TOPIC, topic);
        values.put(FRONT_COL, front);
        values.put(BACK_COL, back);

        //update method to update our database and passing our values.and we are comparing it with id of card which is stored in original name variable.
        db.update(TABLE_NAME, values, "id=?", new String[] { String.valueOf(id) });
        db.close();

    }

    //method for deleting card
    public void deleteCard(int id) {

        // creating a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        //calling a method to delete our card and we are comparing it with our course name.
        db.delete(TABLE_NAME, "id=?", new String[]{ String.valueOf(id) });

        //closing the database
        db.close();
        }

        //method is use to add new card to our sqlite database.
        public void addNewCard(String cardTopic, String cardFront, String cardBack) {

        //creating a variable for our sqlite database and calling writable method as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        //creating variable for content values.
        ContentValues values = new ContentValues();

        //passing all value along with its key and value pair.
        values.put(CARD_TOPIC, cardTopic);
        values.put(FRONT_COL, cardFront);
        values.put(BACK_COL, cardBack);

        //passing content values to our table.
        db.insert(TABLE_NAME, null, values);

        //closing the database
        db.close();
    }
    //method gets all the cards in the database
    public ArrayList<Card> getAllCards(){
        // creating a variable to write our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // creating a cursor with query to read data from database.
        Cursor cursorCourses
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        //creating a new array list.
        ArrayList<Card> cardArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
               //adding the data from cursor to our array list.
                cardArrayList.add(new Card(
                        cursorCourses.getInt(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        cursorCourses.close();
        //returning our array list.
        return cardArrayList;

    }
    //method checks if object exist
    public boolean hasObject(String topic) {
        // creating a variable to write our database.
        SQLiteDatabase db = getWritableDatabase();

        // Add the String you are searching by here.
        String selectQuerys = "SELECT * FROM "+TABLE_NAME+" WHERE cardTopic = '"+topic+"'";

        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectQuerys, null);

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;
            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
        }

        // at last closing our cursor
        cursor.close();
        //close database
        db.close();
        //returning hasObject
        return hasObject;
    }
    public ArrayList<Card> getSelectedTopic(String topic){
        // creating a variable to write our database.
        SQLiteDatabase db = this.getReadableDatabase();

        //creating a cursor with query to read data from database.
        String selectQuerys = "SELECT * FROM "+TABLE_NAME+" WHERE cardTopic = '"+topic+"'";
        Cursor cursorCourses
                = db.rawQuery(selectQuerys, null);

        //creating a new array list.
        ArrayList<Card> cardArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                //adding the data from cursor to our array list.
                cardArrayList.add(new Card(
                        cursorCourses.getInt(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        cursorCourses.close();
        // and returning our array list.
        return cardArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
