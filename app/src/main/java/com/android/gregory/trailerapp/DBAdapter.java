package com.android.gregory.trailerapp;



import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Gregory on 4/26/2015.
 */
public class DBAdapter extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "MoviesDB";

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table



        String CREATE_BOOK_TABLE = "CREATE TABLE movies ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "description TEXT,"+
                "thumbLocation TEXT,"+
                "trailerLocation TEXT," +
                "rating TEXT)";

        // create books table

        db.execSQL(CREATE_BOOK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS movies");


        // create fresh books table
        this.onCreate(db);
    }


    // Books table name
    private static final String TABLE_MOVIES = "movies";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_THUMBLOCATION = "thumbLocation";
    private static final String KEY_TRAILERLOCATION = "trailerLocation";
    private static final String KEY_RATING = "rating";
    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_DESCRIPTION,KEY_THUMBLOCATION,
            KEY_TRAILERLOCATION, KEY_RATING};


    public Movies getMovies(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_MOVIES, // a. table
                        COLUMNS, // b. column names
                        "id =?" , // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Movies movie = new Movies();
        movie.setId(Integer.parseInt(cursor.getString(0)));
        movie.setName((cursor.getString(1)));
        movie.setDescription((cursor.getString(2)));
        movie.setThumbLocation(cursor.getString(3));
        movie.setTrailerLocation(cursor.getString(4));
        movie.setRating(cursor.getString(5));



        //log
        Log.d("getMovie("+id+")", movie.toString());

        // 5. return book
        return movie;
    }



    public void addMovie(Movies movie){
        //for logging
        Log.d("addMovie", movie.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, movie.getName()); // get title
        values.put(KEY_DESCRIPTION, movie.getDescription()); // get author
        values.put(KEY_THUMBLOCATION, movie.getThumbLocation()); // get author
        values.put(KEY_TRAILERLOCATION, movie.getTrailerLocation()); // get author
        values.put(KEY_RATING, movie.getRating());
        // 3. insert
        db.insert(TABLE_MOVIES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();


    }


    public List<Movies> getAllMovies() {

        List<Movies> movieList = new ArrayList<Movies>();


        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MOVIES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Movies movie = null;
        if (cursor.moveToFirst()) {
            do {
                movie = new Movies();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setName(cursor.getString(1));
                movie.setDescription(cursor.getString(2));
                movie.setThumbLocation(cursor.getString(3));
                movie.setTrailerLocation(cursor.getString(4));
                movie.setRating(cursor.getString(5));

                // Add book to books
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        Log.d("getAllMovies()", movie.toString());

        // return the movie list
        return movieList;
    }//end movielist



    public int updateMovie(Movies movie, String Id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", movie.getName());
        values.put("description", movie.getDescription());
        values.put("thumbLocation", movie.getThumbLocation());
        values.put("trailerLocation", movie.getTrailerLocation());
        values.put("rating", movie.getRating());

        // 3. updating row
        int i = db.update(TABLE_MOVIES, //table
                values, // column/value
                KEY_ID+" = +?", // selections
                new String[] { Id }); //selection args
        //new String[] { String.valueOf(movie.getId()) }); //selection args
        // 4. close
        db.close();

        return i;

    }//end update

    public void deleteMovie(String Id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        if(Id.equals(""))
        {

            db.delete(TABLE_MOVIES, //table name
                    null,  // selections
                    null); //selections args
        }
        else
        {
            // 2. delete
            db.delete(TABLE_MOVIES, //table name
                    KEY_ID+" = ?",  // selections
                    new String[] { Id }); //selections args
        }
        // 3. close
        db.close();

        //log
        Log.d("deleteBook", Id);

    }

}
