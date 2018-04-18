package com.example.phy.hochiminhcity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Phy on 6/19/2017.
 */

public class RecommendListAdapter extends ArrayAdapter<Recommend>{

    private Context context;
    private int reID;
    private ArrayList<Recommend> recommends;

    public RecommendListAdapter(Context context, int resource, ArrayList<Recommend> data) {
        super(context, resource, data);
        this.context = context;
        this.reID = resource;
        this.recommends = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = ((Activity)
                    this.context).getLayoutInflater();
            convertView = layoutInflater.inflate(this.reID, parent, false);
            holder.imageView = (ImageView) convertView .findViewById(R.id.iv_recommend);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Recommend item = recommends.get(position);

        holder.imageView.setImageBitmap(item.getBmpRecommend());
        return convertView;
    }

    class ViewHolder {
        public ImageView imageView;
    }
}
