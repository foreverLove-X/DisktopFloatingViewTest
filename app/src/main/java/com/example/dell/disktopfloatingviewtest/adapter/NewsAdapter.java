package com.example.dell.disktopfloatingviewtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.disktopfloatingviewtest.db.Data;
import com.example.dell.disktopfloatingviewtest.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private Context context = null;
    private List<Data> datas = new ArrayList<> ();

    public NewsAdapter(Context context, List<Data> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size ();
    }

    @Override
    public Object getItem(int i) {
        return datas.get (i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder ();
            view = LayoutInflater.from (context).inflate (R.layout.item, null);
            viewHolder.user_img = view.findViewById (R.id.user_img);
            viewHolder.news_tit = view.findViewById (R.id.news_tit);
            viewHolder.news_txt = view.findViewById (R.id.news_txt);
            viewHolder.send_time = view.findViewById (R.id.send_time);
            view.setTag (viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag ();
        }
        viewHolder.user_img.setImageResource (datas.get (i).user_img);
        viewHolder.news_tit.setText (datas.get (i).news_tit);
        viewHolder.news_txt.setText (datas.get (i).news_txt);
        viewHolder.send_time.setText (datas.get (i).send_time);

        return view;
    }

    public final class ViewHolder {
        public ImageView user_img;
        public TextView news_tit;
        public TextView news_txt;
        public TextView send_time;
    }
}
