package com.example.lab2_ph44245;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab2_ph44245.adapter.cvAdapter;
import com.example.lab2_ph44245.dao.congviecDAO;
import com.example.lab2_ph44245.model.congviec;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcvcongviec;
    FloatingActionButton fltadd;

    EditText edttile, edtcontent, edtdate, edttype;
    Button btnCancel, btnSave;
    private ArrayList<congviec> list = new ArrayList<>();
    cvAdapter adapter;
    congviecDAO cvDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        rcvcongviec = findViewById(R.id.rcvCV);
        fltadd = findViewById(R.id.fltadd);
        //
        cvDao = new congviecDAO(this);
        list = cvDao.selectAll();//lấy toàn bộ dữ liệu từ bảng congviec, add dữ liệu vào list
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvcongviec.setLayoutManager(linearLayoutManager);
        //đổ dữ liệu lên recyclerview
        adapter = new cvAdapter(this,list);
        rcvcongviec.setAdapter(adapter);
        //
        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThem();
            }
        });
    }
    public void dialogThem() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = ((Activity) MainActivity.this).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //
        edttile = view.findViewById(R.id.edttile);
        edtcontent = view.findViewById(R.id.edtcontent);
        edtdate = view.findViewById(R.id.edtdate);
        edttype = view.findViewById(R.id.edttype);
        //
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);
        //
        edttype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1= new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("Chọn mức độ khó của công việc");
                String [] loai ={"Dễ","Trung bình","Khó"};
                builder1.setItems(loai, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edttype.setText(loai[i]);
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void save() {
        String title = edttile.getText().toString();
        String content = edtcontent.getText().toString();
        String day = edtdate.getText().toString();
        String type = edttype.getText().toString();
        congviec cv = new congviec(title, content, day, type);//tạo đối tượng
        if (cvDao.add(cv)) {
            list.clear();//xóa toàn bộ dữ liệu trong list
            list.addAll(cvDao.selectAll());//add lại toàn bộ dữ liệu từ bảng công việc và add vàolisst
            adapter.notifyDataSetChanged();//thay đổi dữ liệu trong adapter
            Toast.makeText(MainActivity.this, "Add thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Add thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}