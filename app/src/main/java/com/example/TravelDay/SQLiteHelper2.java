package com.example.TravelDay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteHelper2 {

    private final  String dbName = "myCalc";
    private static String table2= "CalcTable";
    private final int dbVersion = 1;
    public  int setPOS;
    private SQLiteOpenHelper opener;
    private SQLiteDatabase db;

    private Context context;
    public SQLiteHelper2(Context context){
        this.context = context;
        this.opener = new OpenHelper(context,dbName,null,dbVersion);

    }

    public void setPos(int position){
        this.setPOS = position;
    }
    private class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(@Nullable Context context,@Nullable String name,@Nullable SQLiteDatabase.CursorFactory factory,int verson){
            super(context,name,factory,verson);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String create ="CREATE TABLE "+table2+"("+
                    "seq integer PRIMARY KEY AUTOINCREMENT,"+
                    "mainCalc text ,"+
                    "subtext text )";
            db.execSQL(create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS "+table2);
            onCreate(db);
        }
    }

    public void insertCalc(Calc calc){
        db = opener.getWritableDatabase();
        //INSERT INTO MemoTable Values(null,'maintext','subtext');
        String sql = "INSERT INTO "+table2 +" VALUES(NULL,'"+calc.mainCalc+"','"+calc.subText+"')";
        db.execSQL(sql);
        db.close();
    }

    // DELETE FROM memo table where seq = 0;
    public void deleteCalc(Integer position){
        db = opener.getWritableDatabase();
        String sql = "DELETE FROM "+table2+" WHERE seq = "+position+";";
        db.execSQL(sql);
        db.close();
    }

    public void updateCalc(Calc calc){
        db = opener.getWritableDatabase();
        String sql ="UPDATE "+table2+" SET mainCalc = '"+calc.mainCalc+"',subtext ='"+calc.subText+"' WHERE seq = "+setPOS+";";
        db.execSQL(sql);
        db.close();
    }

    //Select * from memotable;
    public ArrayList<Calc> selectAll(){
        db = opener.getWritableDatabase();

        String sql = "SELECT * FROM "+table2;

        ArrayList<Calc> list = new ArrayList<>();

        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();

        while(!results.isAfterLast()){
            Calc calc = new Calc(results.getInt(0),results.getString(1),results.getString(2));
            list.add(calc);
            results.moveToNext();
        }
        results.close();
        db.close();
        return list;
    }
}
