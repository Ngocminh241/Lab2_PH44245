package com.example.lab2_ph44245.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String Db_name = "QLCV";
    public DBHelper(Context context){
        super(context, Db_name, null, 1);
    }
    //tạo bảng
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tb_congviec="CREATE TABLE congviec(ID integer PRIMARY KEY autoincrement," +
                "Title text NOT NULL," +
                "Content text NOT NULL, " +
                "Date text NOT NULL, " +
                "Type text NOT NULL,"+
                "trangthai interger)";
        sqLiteDatabase.execSQL(tb_congviec);

        //chèn dữ liệu
        String data ="INSERT INTO congviec VALUES (0,'Java','Java cơ bản','04/04/2000','Dễ',1)," +
                "(1,'C++','Nhập môn C++','04/05/2001','Bình thường',0)," +
                "(2,'Kotlin','Kotlin cơ bản','04/06/2002','Khó',0)";
        sqLiteDatabase.execSQL(data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i!=i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS congviec");
            onCreate(sqLiteDatabase);
        }
    }

}
