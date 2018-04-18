package com.example.phy.hochiminhcity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView recommendList;
    ArrayList<Recommend> recommends;
    RecommendListAdapter recommendListAdapter;
    Button btnMap, btnMyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        loadData();
        recommendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FoodRecommend.class);
                if(position ==0) {
                    intent = new Intent(MainActivity.this, FoodRecommend.class);
                }
                else if(position == 1) {
                  intent = new Intent(MainActivity.this, SpotRecommend.class);
                }
                else if(position == 2)
                {
                   intent = new Intent(MainActivity.this, ShopRecommend.class);

                }
                Recommend item = recommends.get(position);

                intent.putExtra("id",item.getId());
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        recommends = new ArrayList<>();
        recommends.add(new Recommend(BitmapFactory.decodeResource(getResources(),R.mipmap.topfood),0));
        recommends.add(new Recommend(BitmapFactory.decodeResource(getResources(),R.mipmap.topspot),1));
        recommends.add(new Recommend(BitmapFactory.decodeResource(getResources(),R.mipmap.topshop),2));
        recommendListAdapter = new RecommendListAdapter(this,R.layout.layout_recommend, recommends);
        recommendList.setAdapter(recommendListAdapter);
    }

    private void initComponents() {
        recommendList = (ListView) this.findViewById(R.id.recommend_list);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnMyList = (Button) findViewById(R.id.btn_mylists);
    }

    public void mapActivity(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void mylistActivity(View view) {
        Intent intent = new Intent(MainActivity.this, MyList.class);
        startActivity(intent);
    }
}
