package com.example.TravelDay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteHelper1 {

    private final  String dbName = "myMemo";
    private static String table1= "MemoTable";
    private final int dbVersion = 1;
    public int setPOS;
    private SQLiteOpenHelper opener;
    private SQLiteDatabase db;

    private Context context;
    public SQLiteHelper1(Context context){
        this.context = context;
        this.opener = new OpenHelper(context,dbName,null,dbVersion);
        db = opener.getWritableDatabase();
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
            String create ="CREATE TABLE "+table1+"("+
                    "seq integer PRIMARY KEY AUTOINCREMENT,"+
                    "maintext text ,"+
                    "subtext text )";
            db.execSQL(create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS "+table1);
            onCreate(db);
        }
    }

    public void insertMemo(Memo memo){
        db = opener.getWritableDatabase();

        //INSERT INTO MemoTable Values(null,'maintext','subtext');
        String sql = "INSERT INTO "+table1 +" VALUES(NULL,'"+memo.maintext+"','"+memo.subText+"')";
        db.execSQL(sql);
        db.close();

    }

    // DELETE FROM memo table where seq = 0;
    public void deleteMemo(int position){
        db = opener.getWritableDatabase();

        String sql = "DELETE FROM "+table1+" WHERE seq = "+position+";";
        db.execSQL(sql);
        db.close();

    }

    public void updateMemo(Memo memo){
        db = opener.getWritableDatabase();

        String sql ="UPDATE "+table1+" SET maintext = '"+memo.maintext+"',subtext ='"+memo.subText+"' WHERE seq = "+setPOS+";";
        db.execSQL(sql);
//        ContentValues cv = new ContentValues();
//        cv.put("maintext",memo.maintext);
//        cv.put("subtext",memo.subText);
//        long result = db.update(table1,cv,"seq = ?",new String[] {Integer.toString(position)});
//
//        if(result == -1){
//            Toast.makeText(context,"수정 실패",Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(context,result+" 수정 완료",Toast.LENGTH_SHORT).show();
//        }
        db.close();

    }

    //Select * from memotable;
    public ArrayList<Memo> selectAll(){
        String sql = "SELECT * FROM "+table1;

        ArrayList<Memo> list = new ArrayList<>();

        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();

        while(!results.isAfterLast()){
            Memo memo = new Memo(results.getInt(0),results.getString(1),results.getString(2));
            list.add(memo);
            results.moveToNext();
        }
        results.close();
        return list;
    }
}
