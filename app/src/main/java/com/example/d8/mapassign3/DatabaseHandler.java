package com.example.d8.mapassign3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context){
        super(context, "SportTeams", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS 'teams' (id INTEGER PRIMARY KEY, name VARCHAR, " +
                "sport VARCHAR, city VARCHAR, mvp VARCHAR, logoUri VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'teams'");
        onCreate(db);
    }

    //Build an arraylist of values from a column.
    public List<SportTeam> getAllAsList(){
        List<SportTeam> listTeam = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM 'teams'", null);
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            int id      = c.getColumnIndex("id");
            int name    = c.getColumnIndex("name");
            int sport   = c.getColumnIndex("sport");
            int city   = c.getColumnIndex("city");
            int logo    = c.getColumnIndex("logoUri");
            int mvp   = c.getColumnIndex("mvp");
            do{
                SportTeam tempTeam = new SportTeam(c.getInt(id), c.getString(name),
                        c.getString(sport), c.getString(city), c.getString(mvp), Uri.parse(c.getString(logo)));
                //Log.d("Team details check", tempTeam.toString());
                listTeam.add(tempTeam);
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return listTeam;
    }

    //Return a single sport team object
    public SportTeam getTeam(int id){
        //Log.d("ID passed into getTeam", ""+id);
        SportTeam tempTeam = new SportTeam();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM 'teams' WHERE id = " + id, null);
        if(c != null && c.getCount() > 0){
            Log.d("GetTeam", "Found team with id: " + id);
            c.moveToFirst();
            //int id      = c.getColumnIndex("id");
            int name    = c.getColumnIndex("name");
            int sport   = c.getColumnIndex("sport");
            int city    = c.getColumnIndex("city");
            int logo    = c.getColumnIndex("logoUri");
            //Uri logoUri = Uri.parse(c.getString(logo));
            int mvp   = c.getColumnIndex("mvp");
            tempTeam = new SportTeam(id, c.getString(name),
                    c.getString(sport), c.getString(city), c.getString(mvp), Uri.parse(c.getString(logo)));
            //Log.d("Team details check 1", tempTeam.toString());
        }
        c.close();
        db.close();
        //Log.d("Team details check 2", tempTeam.toString());
        return tempTeam;
    }

    //Add a team with logoUri
    public void addTeam(String name, String sport, String city, String mvp, Uri logoUri){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sport", sport);
        values.put("city", city);
        values.put("mvp", mvp);
        values.put("logoUri", logoUri.toString());
        db.insert("teams", null, values);
        db.close();
    }

    //Edit team details with logoUri
    public void editTeam(int id, String name, String sport, String city, String mvp, Uri logoUri){
        String whereClause = "id = " + id;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sport", sport);
        values.put("city", city);
        values.put("mvp", mvp);
        values.put("logoUri", logoUri.toString());
        db.update("teams", values, whereClause, null);
        db.close();
    }

    //Delete team
    public void deleteTeam(int id){
        String whereClause = "id = " + id;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("teams", whereClause, null);
        db.close();
    }
}
