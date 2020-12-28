package nightfury.example.storedata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nightfury.example.storedata.R;
import nightfury.example.storedata.SinhVien;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<SinhVien> items;
    ArrayList<SinhVien> arrayList;

    public CustomAdapter(Context context, List<SinhVien> items) {
        this.context = context;
        this.items = items;

        this.arrayList = new ArrayList<SinhVien>();
        this.arrayList.addAll(items);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_custom_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.text_mssv = view.findViewById(R.id.text_mssv);
            viewHolder.text_hoten = view.findViewById(R.id.text_hoten);
            viewHolder.text_ngaysinh = view.findViewById(R.id.text_ngaysinh);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        SinhVien item = items.get(i);
        viewHolder.text_mssv.setText(String.valueOf(item.getMSSV()));
        viewHolder.text_hoten.setText(item.getHoTen());
        viewHolder.text_ngaysinh.setText(item.getNgaySinh());
        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if(charText.length() == 0) {
            items.addAll(arrayList);
        } else {
            for(SinhVien item: arrayList) {
                if(item.getHoTen().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        //        ImageView imageAvatar;
        TextView text_mssv;
        TextView text_hoten;
        TextView text_ngaysinh;
    }
}
