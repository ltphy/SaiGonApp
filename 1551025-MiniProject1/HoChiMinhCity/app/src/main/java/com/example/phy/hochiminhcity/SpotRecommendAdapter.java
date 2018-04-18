package com.example.phy.hochiminhcity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Phy on 6/19/2017.
 */

public class SpotRecommendAdapter  extends ArrayAdapter<RecommendItem>   {

    private Context context;
    private int reID;
    private ArrayList<RecommendItem> recommendItems;

    public SpotRecommendAdapter(Context context, int resource, ArrayList<RecommendItem> data) {
        super(context, resource, data);
        this.context = context;
        this.reID = resource;
        this.recommendItems = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = ((Activity)
                    this.context).getLayoutInflater();
            convertView = layoutInflater.inflate(this.reID, parent, false);
            holder.imageView = (ImageView) convertView .findViewById(R.id.imgview_recommend_item);
            holder.textViewName =(TextView) convertView .findViewById(R.id.tv_name);
            holder.textViewType =(TextView) convertView .findViewById(R.id.tv_type);
            holder.textViewAddress =(TextView) convertView .findViewById(R.id.tv_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        RecommendItem item = recommendItems.get(position);

        holder.imageView.setImageBitmap(item.getAvatar());
        holder.textViewName.setText(item.getName());
        holder.textViewType.setText(item.getType());
        holder.textViewAddress.setText(item.getAddress());
        return convertView;
    }

    class ViewHolder {
        public TextView textViewName;
        public ImageView imageView;
        public TextView textViewType;
        public TextView textViewAddress;

    }
}
