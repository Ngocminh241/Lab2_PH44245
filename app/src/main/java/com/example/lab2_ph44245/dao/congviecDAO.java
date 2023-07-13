package com.example.lab2_ph44245.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.lab2_ph44245.database.DBHelper;
import com.example.lab2_ph44245.model.congviec;

import java.util.ArrayList;

public class congviecDAO {
    private final DBHelper dbHelper;
    private SQLiteDatabase data;
    public congviecDAO(Context context){
        dbHelper = new DBHelper(context);
    }
    //selectAll: lấy toàn bộ dữ liệu từ bảng công việc, add dữ liệu vào list
    public ArrayList<congviec> selectAll(){
        ArrayList<congviec> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM congviec",null);
            if (cursor.getCount()>0){
                cursor.moveToFirst();//đưa con trỏ về dòng đầu tiên
                while (!cursor.isAfterLast()){
                    congviec cv= new congviec();//tạo đối tượng
                    cv.setId(cursor.getInt(0));
                    cv.setTitle(cursor.getString(1));
                    cv.setContent(cursor.getString(2));
                    cv.setDate(cursor.getString(3));
                    cv.setType(cursor.getString(4));
                    cv.setTrangThai(cursor.getInt(5));
                    list.add(cv);//add dữ liệu vào list
                    cursor.moveToNext();//đưa con trỏ dòng tiếp theo
                }
            }
        }catch (Exception e){
            Log.e(TAG,"Lỗi: "+e);
        }finally {
            database.endTransaction();
        }
        return list;
    }

    public boolean add(congviec cv) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();//gửi dữ liệu vào
        ContentValues values = new ContentValues();//ContentValues: đưa dữ liệu vào database
        values.put("Title",cv.getTitle());//đưa giá trị vào bảng ở cột tiêu đề
        values.put("Content",cv.getContent());
        values.put("Date",cv.getDate());//
        values.put("Type",cv.getType());
        values.put("trangthai", cv.getTrangThai());
        //nếu add dữ liệu thành công thì trả về giá trị tương ứng với số dòng được chèn vào
        long check = database.insert("congviec",null,values);
        return (check>0);
    }
    public boolean update(congviec cv){
        SQLiteDatabase database = dbHelper.getWritableDatabase();//gửi dữ liệu vào
        ContentValues values = new ContentValues();//ContentValues: đưa dữ liệu vào database
        values.put("Title",cv.getTitle());//đưa giá trị vào bảng ở cột tiêu đề
        values.put("Content",cv.getContent());
        values.put("Date",cv.getDate());
        values.put("Type",cv.getType());
        values.put("trangthai", cv.getTrangThai());
        //nếu add dữ liệu thành công thì trả về giá trị tương ứng với số dòng được chèn vào
        long check = database.update("congviec",values,"ID=?",
                new String[]{String.valueOf(cv.getId())});
        return (check>0);
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row= database.delete("congviec","ID=?",new String[]{String.valueOf(id)});
        return (row>0);
    }

}
