package com.example.lab2_ph44245.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2_ph44245.R;
import com.example.lab2_ph44245.dao.congviecDAO;
import com.example.lab2_ph44245.model.congviec;

import java.util.ArrayList;

public class cvAdapter extends RecyclerView.Adapter<cvAdapter.viewholder> {
    private final Context context;
    private final ArrayList<congviec> list;

    congviecDAO cvDAO;

    public cvAdapter(Context context, ArrayList<congviec> list) {
        this.context = context;
        this.list = list;
        cvDAO = new congviecDAO(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_cv, null);
        return new viewholder(view);

    }

    //cập nhật dữ liệu lên viewholder
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.txttieude.setText(list.get(position).getTitle());
        holder.txtnoidung.setText(list.get(position).getContent());
        holder.txtngay.setText(list.get(position).getDate());
        holder.txtloai.setText(list.get(position).getType());
        if (list.get(position).getTrangThai() == 1) {
            holder.chkcv.setChecked(true);
        } else {
            holder.chkcv.setChecked(false);
        }

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);// tạo đối tượng
                builder.setIcon(R.drawable.warning);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa không");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        congviec cv = list.get(position); //truy xuất đến đối tượng tại vị trí position
                        if (cvDAO.delete(cv.getId())) {
                            list.clear();
                            list.addAll(cvDAO.selectAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Không xóa", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();//tạo hộp thoại
                dialog.show();

            }
        });
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dialogSua();
            }
        });
    }

    //trả về số lượng phần tử trong rcv
    @Override
    public int getItemCount() {
        return list.size();
    }

    //tạo class tĩnh, ánh xạ các thành phần widget
    public class viewholder extends RecyclerView.ViewHolder {
        TextView txttieude, txtnoidung, txtngay, txtloai;
        CheckBox chkcv;
        ImageButton btnupdate, btndelete;
        EditText edttiles, edtcontents, edtdates, edttypes;
        Button btnCancels, btnSaves;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txttieude = itemView.findViewById(R.id.txttieude);
            txtnoidung = itemView.findViewById(R.id.txtnoidung);
            txtngay = itemView.findViewById(R.id.txtngay);
            txtloai = itemView.findViewById(R.id.txtloai);
            chkcv = itemView.findViewById(R.id.chkCV);
            btnupdate = itemView.findViewById(R.id.btnUpdate);
            btndelete = itemView.findViewById(R.id.btnDelete);
            //
            //
        }

        public void dialogSua() {
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_upd, null);
            builder.setView(view);
            Dialog dialog = builder.create();
            dialog.show();
            //
            edttiles = view.findViewById(R.id.edttiles);
            edtcontents = view.findViewById(R.id.edtcontents);
            edtdates = view.findViewById(R.id.edtdates);
            edttypes = view.findViewById(R.id.edttypes);
            //
            btnCancels = view.findViewById(R.id.btnCancels);
            btnSaves = view.findViewById(R.id.btnSaves);
            //
            edttiles.setText(list.get(getAdapterPosition()).getTitle());
            edtcontents.setText(list.get(getAdapterPosition()).getContent());
            edtdates.setText(list.get(getAdapterPosition()).getDate());
            edttypes.setText(list.get(getAdapterPosition()).getType());
            //
            //xử lý event cho edtType
            edttypes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1= new AlertDialog.Builder(context);
                    builder1.setTitle("Chọn mức độ khó của công việc");
                    String [] loai ={"Dễ","Trung bình","Khó"};
                    builder1.setItems(loai, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edttypes.setText(loai[i]);
                        }
                    });
                    AlertDialog dialog1 = builder1.create();
                    dialog1.show();
                }
            });

            btnSaves.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    save();
                    dialog.dismiss();
                }
            });
            btnCancels.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

        public void save() {
            congviec cv = list.get(getAdapterPosition());
            cv.setTitle(edttiles.getText().toString());
            cv.setContent(edtcontents.getText().toString());
            cv.setDate(edtdates.getText().toString());
            cv.setType(edttypes.getText().toString());

            //nếu cập nhật thành công trong bảng công việc thì sẽ load lại dữ liệu lên view
            if (cvDAO.update(cv)) {
                list.clear();//xóa toàn bộ dữ liệu trong list
                list.addAll(cvDAO.selectAll());//add lại toàn bộ dữ liệu từ bảng công việc và add vàolisst
                notifyDataSetChanged();//thay đổi dữ liệu trong adapter
                Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
