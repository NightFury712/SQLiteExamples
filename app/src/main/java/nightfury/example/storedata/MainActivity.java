package nightfury.example.storedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import nightfury.example.storedata.adapter.CustomAdapter;

public class MainActivity extends AppCompatActivity {
    ListView listSV;
    Button btn_add;
    ArrayList<SinhVien> sv ;
//    ArrayList<String> arrSinhVien;
//    ArrayAdapter<CustomAdapter> arrayAdapter;
    CustomAdapter adapter;

    SQLite db = new SQLite(this, "QuanLySV.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = findViewById(R.id.btn_add);

        try {
//            SQLite db = new SQLite(this, "QuanLySV.sqlite", null, 1);

            //tao bang
//            db.QueryData("create table if not exists QLsinhvien ("
//                    + " MSSV integer PRIMARY KEY , "
//                    + " hoten text, "
//                    + " ngaysinh text, "
//                    + " email text, "
//                    + " diachi text );");

//            db.QueryData( "insert into QLsinhvien(MSSV, hoten, ngaysinh, email, diachi) values (20183877, 'Hoang Hai Dang', '7/12/2000', 'modimat@gmail.com', 'Yen Lap, Phu Tho');" );
//            db.QueryData( "insert into QLsinhvien(MSSV, hoten, ngaysinh, email, diachi) values (20183874, 'Nguyen Manh Cuong', '6/6/2000', 'cuongnm@gmail.com', 'Ha Noi');" );
//            db.QueryData( "insert into QLsinhvien(MSSV, hoten, ngaysinh, email, diachi) values (20183964, 'Nguyen Viet Chinh', '5/8/2000', 'chinhvc@gmail.com', 'Ha Noi');" );
//            db.QueryData( "insert into QLsinhvien(MSSV, hoten, ngaysinh, email, diachi) values (20183977, 'Nguyen Dao Duy Kien', '7/5/2000', 'kienndd@gmail.com', 'Vinh Phuc');" );
//            db.QueryData( "insert into QLsinhvien(MSSV, hoten, ngaysinh, email, diachi) values (20183954, 'Le Duy Tuan', '7/8/2000', 'tuanld@gmail.com', 'Nghe An');" );

            addListView();

            listSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SinhVien sinhVien = sv.get(position);

                    xemSV(sinhVien);
//                    Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                }
            });
//            txtMsg.append("\nCreate table success!");
        } catch (SQLiteException e) {
            Log.d("error ", e.getMessage());
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themMoi();
            }
        });

        registerForContextMenu(listSV);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, 101, 0, "Sửa");
        menu.add(0, 102, 0, "Xóa");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int id = item.getItemId();
        if(id == 101) {
            suaSV(sv.get(info.position));
        } else if(id == 102) {
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Are you sure you want to delete?")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setTitle("delete infomation of student")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            xoaSV(sv.get(info.position).getMSSV());
                            addListView();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setNeutralButton("Cancel", null)
                    .create();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }
        return super.onContextItemSelected(item);
    }

    private void addListView() {
        listSV = findViewById(R.id.listSV);

//        arrSinhVien = new ArrayList<>();
        sv = new ArrayList<>();

        Cursor c1 = db.getData("select * from QLsinhvien");

        while (c1.moveToNext()) {
            SinhVien sinhVien = new SinhVien(c1.getInt(0), c1.getString(1), c1.getString(2), c1.getString(3), c1.getString(4));
            sv.add(sinhVien);
//            arrSinhVien.add(c1.getInt(0) + " - " + c1.getString(1) + " - " + c1.getString(2) + " - " + c1.getString(3) + " - " + c1.getString(4));
        }

//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrSinhVien);
//        listSV.setAdapter(arrayAdapter);

        adapter = new CustomAdapter(this, sv);
        listSV.setAdapter(adapter);

    }

    private void xoaSV(int MSSV) {
        db.QueryData("delete from QLsinhvien where MSSV = " + MSSV);
    }

    private void xemSV(SinhVien sinhVien) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);

        EditText et_mssv = dialog.findViewById(R.id.et_mssv);
        EditText et_hoten = dialog.findViewById(R.id.et_hoten);
        EditText et_ngaysinh = dialog.findViewById(R.id.et_ngaysinh);
        EditText et_email = dialog.findViewById(R.id.et_email);
        EditText et_diachi = dialog.findViewById(R.id.et_diachi);

        et_mssv.setText(String.valueOf(sinhVien.getMSSV()));
        et_hoten.setText(sinhVien.getHoTen());
        et_ngaysinh.setText(sinhVien.getNgaySinh());
        et_diachi.setText(sinhVien.getDiaChi());
        et_email.setText(sinhVien.getEmail());

        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void suaSV(SinhVien sinhVien) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);

        EditText et_mssv = dialog.findViewById(R.id.et_mssv);
        EditText et_hoten = dialog.findViewById(R.id.et_hoten);
        EditText et_ngaysinh = dialog.findViewById(R.id.et_ngaysinh);
        EditText et_email = dialog.findViewById(R.id.et_email);
        EditText et_diachi = dialog.findViewById(R.id.et_diachi);

        et_mssv.setText(String.valueOf(sinhVien.getMSSV()));
        et_hoten.setText(sinhVien.getHoTen());
        et_ngaysinh.setText(sinhVien.getNgaySinh());
        et_diachi.setText(sinhVien.getDiaChi());
        et_email.setText(sinhVien.getEmail());

        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.QueryData("update QLsinhvien set MSSV = " + Integer.parseInt(et_mssv.getText().toString()) + ","
                            + "hoten = " + "'" + et_hoten.getText().toString() + "'" + ","
                            + "ngaysinh = " + "'" + et_ngaysinh.getText().toString() + "'" + ","
                            + "email = " + "'" + et_email.getText().toString() + "'" + ","
                            + "diachi = " + "'" + et_diachi.getText().toString() + "'"
                            + " where MSSV = " + sinhVien.getMSSV());
                    addListView();
                } catch (SQLiteException e) {
                    Log.d("error ", e.getMessage());
                }

                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void themMoi() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog);

        EditText et_mssv = dialog.findViewById(R.id.et_mssv);
        EditText et_hoten = dialog.findViewById(R.id.et_hoten);
        EditText et_ngaysinh = dialog.findViewById(R.id.et_ngaysinh);
        EditText et_email = dialog.findViewById(R.id.et_email);
        EditText et_diachi = dialog.findViewById(R.id.et_diachi);


        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.QueryData("insert into QLsinhvien(MSSV, hoten, ngaysinh, email, diachi) values ("
                            + Integer.parseInt(et_mssv.getText().toString()) + ","
                            + "'" + et_hoten.getText().toString() + "'" + ","
                            + "'" + et_ngaysinh.getText().toString() + "'" + ","
                            + "'" + et_email.getText().toString() + "'" + ","
                            + "'" + et_diachi.getText().toString() + "')");
                    addListView();
                } catch (SQLiteException e) {
                    Log.d("error ", e.getMessage());
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}