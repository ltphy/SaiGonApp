package com.example.phy.hochiminhcity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SpotRecommend extends AppCompatActivity {
    ListView listView;
    ArrayList<RecommendItem> recommendItems;
    SpotRecommendAdapter SpotRecommendAdapter;
    Button btnMap, btnMyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_recommend);
        initComponents();
        loadData();

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecommendItem item = recommendItems.get(position);

                Intent intent = new Intent(SpotRecommend.this, RecommendItemDetail.class);
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
        recommendItems = new ArrayList<>();
        recommendItems.add(new RecommendItem("Independence Palace","Spot","135 Nam Kì Khởi Nghĩa, Bến Thành","0838223652",10.7747254,106.6971125,"7:30-11 AM, 1-5PM",
                BitmapFactory.decodeResource(getResources(),R.mipmap.dinhdoclap)));
        recommendItems.add(new RecommendItem("Saigon Notre-Dame Basilica","Spot","1, Công xã Paris, Bến Nghé, District 1","0838220477",10.7792453,106.6969178,"",
                BitmapFactory.decodeResource(getResources(),R.mipmap.nhathoducba)));
        recommendItems.add(new RecommendItem("War Remnants Museum","Spot","28 Võ Van Tần, 6, District 3","0839305587",10.7777793,106.6898063,"7:30AM - 6 PM",
                BitmapFactory.decodeResource(getResources(),R.mipmap.baotang)));
        recommendItems.add(new RecommendItem("Củ Chi Tunnels","Spot","Phú Hiệp, Phú Mỹ Hưng, Củ Chi","0837948830",11.1427406,106.4515547,"7AM - 5PM",
                BitmapFactory.decodeResource(getResources(),R.mipmap.cuchi)));
        recommendItems.add(new RecommendItem("Saigon Central Post Office","Spot","2 Công xã Paris, Bến Nghé, District 1","02838221677",10.7797793,106.6971561,"8AM - 6PM",
                BitmapFactory.decodeResource(getResources(),R.mipmap.buudien)));
        recommendItems.add(new RecommendItem("Municipal Theatre","Spot","7 Lam Son, Ben Nghe, District 1","0862704450",10.7765223,106.7009075,"",
                BitmapFactory.decodeResource(getResources(),R.mipmap.nhahat)));

        SpotRecommendAdapter = new SpotRecommendAdapter(this, R.layout.layout_item_recommend, recommendItems);
        listView.setAdapter(SpotRecommendAdapter);
    }
    public ArrayList<RecommendItem> getRecommendList() {

        return recommendItems;
    }
    private void initComponents() {
        listView = (ListView) this.findViewById(R.id.spot_recommend_list);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnMyList = (Button) findViewById(R.id.btn_mylists);
    }

    public void mapActivity(View view) {
        Intent intent = new Intent(SpotRecommend.this, MapsActivity.class);
        startActivity(intent);
    }

    public void mylistActivity(View view) {
        Intent intent = new Intent(SpotRecommend.this, MyList.class);
        startActivity(intent);
    }

}
