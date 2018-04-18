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

public class ShopRecommend extends AppCompatActivity {
    ListView listViewShop;
    ArrayList<RecommendItem> recommendItems;
    ShopRecommendAdapter ShopRecommendAdapter;
    Button btnMap, btnMyList;
    int id;
    TextView tvTitle;

    public ShopRecommend() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_recommend);

        initComponents();
        loadData();

        listViewShop.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecommendItem item = recommendItems.get(position);

                Intent intent = new Intent(ShopRecommend.this, RecommendItemDetail.class);
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
            recommendItems.add(new RecommendItem("Bến Thành Market","Shop","Bến Thành","84838299274",10.7724283,106.6958173, "Daily 6:00 –24:00",
                    BitmapFactory.decodeResource(getResources(),R.mipmap.benthanh1)));
            recommendItems.add(new RecommendItem("Vincom Center","Shop","171 Dong Khoi, District 1","84839369999",10.7764824,106.6998189,"Daily 09:00 – 22:00",
                    BitmapFactory.decodeResource(getResources(),R.mipmap.vincomcenter)));
            recommendItems.add(new RecommendItem("Chợ Lớn Chinatown","Shop","57 Thap Muoi, District 6","",10.7501196,106.6494336,"Daily 07:00 – 18:00",
                    BitmapFactory.decodeResource(getResources(),R.mipmap.cholon)));
            recommendItems.add(new RecommendItem("Fashion Boutiques","Shop","54 Nguyễn Văn Tráng, Bến Thành, District 1","84935088108",10.7507014,106.6166024,"Daily 10:00 - 22:00",
                    BitmapFactory.decodeResource(getResources(),R.mipmap.saigonboutique)));
            recommendItems.add(new RecommendItem("Saigon Square","Shop","77 Nam Ky Khoi Nghia, District 1","",10.7726927,106.6981619,"Daily 08:00 - 22:00",
                    BitmapFactory.decodeResource(getResources(),R.mipmap.saigonsquare)));
            recommendItems.add(new RecommendItem("Diamond Plaza","Shop","34 Le Duan Street, District 1","84838225500",10.7813384,106.6963937,"Daily 09:30 – 22:30",
                    BitmapFactory.decodeResource(getResources(),R.mipmap.diamondplaza)));
            recommendItems.add(new RecommendItem("An Đông Market","Shop","An Duong Vuong Street, District 10","",10.7571203,106.669916,"Daily 7:00 - 18:00",
                    BitmapFactory.decodeResource(getResources(),R.mipmap.andongmarket)));
            recommendItems.add(new RecommendItem("Parkson Plaza Department Store","Shop","45 Le Thanh Ton Street, District 1","",10.7771785,106.6674362, "Daily 9:30 - 22:00",
                    BitmapFactory.decodeResource(getResources(),R.mipmap.parkson)));
        ShopRecommendAdapter = new ShopRecommendAdapter(this, R.layout.layout_item_recommend, recommendItems);
        listViewShop.setAdapter(ShopRecommendAdapter);
    }
    public ArrayList<RecommendItem> getRecommendList() {

        return recommendItems;
    }
    private void initComponents() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        listViewShop = (ListView) this.findViewById(R.id.shop_recommend_list);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnMyList = (Button) findViewById(R.id.btn_mylists);
    }

    public void mapActivity(View view) {
        Intent intent = new Intent(ShopRecommend.this, MapsActivity.class);
        startActivity(intent);
    }

    public void mylistActivity(View view) {
        Intent intent = new Intent(ShopRecommend.this, MyList.class);
        startActivity(intent);
    }

}
