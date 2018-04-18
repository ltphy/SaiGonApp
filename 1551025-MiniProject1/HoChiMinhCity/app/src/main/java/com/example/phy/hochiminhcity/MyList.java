package com.example.phy.hochiminhcity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyList extends AppCompatActivity {

    Button btnMap, btnRecommend;
    ListView listView;
    ArrayList<RecommendItem> myRecommendList;
    MyListAdapter MyListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        initComponents();
        loadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecommendItem item = myRecommendList.get(position);

                Intent intent = new Intent(MyList.this, RecommendItemDetail.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("address",item.getAddress());
                intent.putExtra("phoneNo",item.getPhoneNo());
                intent.putExtra("hours",item.getHours());
                intent.putExtra("latitude",item.getLatitude());
                intent.putExtra("longitude",item.getLongitude());
                // convert bitmap to byte[] in order to send through intent
                Bitmap bmp = item.getAvatar();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("avatar", byteArray);
                startActivity(intent);
            }
        });
    }
    private void loadData() {
        FoodRecommend foodRecommend = new FoodRecommend();
        SpotRecommend spotRecommend = new SpotRecommend();
        ShopRecommend shopRecommend = new ShopRecommend();
        for(RecommendItem i: foodRecommend.getRecommendList())
        {
            if(i.getBookmark())
            {
                myRecommendList.add(i);
            }
        }
        for(RecommendItem i: shopRecommend.getRecommendList())
        {
            if(i.getBookmark())
            {
                myRecommendList.add(i);
            }
        }
        for(RecommendItem i: spotRecommend.getRecommendList())
        {
            if(i.getBookmark())
            {
                myRecommendList.add(i);
            }
        }
       MyListAdapter = new MyListAdapter(this, R.layout.layout_item_recommend, myRecommendList);
        listView.setAdapter(MyListAdapter);
    }
        private void initComponents() {
        listView = (ListView) this.findViewById(R.id.lv_mylist);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnRecommend = (Button) findViewById(R.id.btn_recommend);
    }

    public void recommendActivity(View view) {
        Intent intent = new Intent(MyList.this, MainActivity.class);
        startActivity(intent);
    }

    public void mapActivity(View view) {
        Intent intent = new Intent(MyList.this, MapsActivity.class);
        startActivity(intent);
    }
}
