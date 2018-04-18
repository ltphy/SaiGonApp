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

public class FoodRecommend extends AppCompatActivity {
    ListView listViewFood;
    ArrayList<RecommendItem> recommendItems;
    FoodRecommendAdapter foodRecommendAdapter;
    Button btnMap, btnMyList;
    int id;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recommend);
        initComponents();
        loadData();

        listViewFood.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecommendItem item = recommendItems.get(position);

                Intent intent = new Intent(FoodRecommend.this, RecommendItemDetail.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("address", item.getAddress());
                intent.putExtra("phoneNo", item.getPhoneNo());
                intent.putExtra("hours", item.getHours());
                intent.putExtra("latitude", item.getLatitude());
                intent.putExtra("longitude", item.getLongitude());
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

    public ArrayList<RecommendItem> getRecommendList() {

        return recommendItems;
    }
    private void loadData() {
        recommendItems = new ArrayList<>();
            tvTitle.setText("Top 10 Must-Eat Food");
            recommendItems.add(new RecommendItem("Bánh Mì", "Food", "26 Lê Thị Riêng, Ben Thanh, District 1", "0839250885", 10.771535, 106.6916908, "About 3:30 pm – midnight daily",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.banhmi)));
            recommendItems.add(new RecommendItem("Phở", "Food", "19 Nguyễn Thị Minh Khai, District 1", "", 10.7845433, 106.6982113, "All day and night – they are open 24 hours",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.pho)));
            recommendItems.add(new RecommendItem("Bún Thịt Nướng", "Food", "195 Cô Giang, District 1", "0908538079", 10.7612963, 106.6908522, "7 am – 10 pm daily",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.bunthitnuong)));
            recommendItems.add(new RecommendItem("Bánh Cuốn", "Food", "11A Cao Thắng, District 3", "0838393394", 10.7687133, 106.6811751, "7:30 am – 10:45 pm daily",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.banhcuon)));
            recommendItems.add(new RecommendItem("Bánh Xèo", "Food", "46 Ðinh Công Tráng, Tân Định, District 1", "0838241110", 10.7895523, 106.6891293, "9 am – 9 pm daily",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.banhxeo)));
            recommendItems.add(new RecommendItem("Cơm Tấm", "Food", "84 Ðặng Văn Ngữ, Phú Nhuận District", " 0838461073", 10.7944554, 106.6671656, "6 am – 10 pm daily",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.comtam)));
            recommendItems.add(new RecommendItem("Hủ Tiếu Nam Vang", "Food", "122 Trần Quang Khải, Tân Ðịnh, District 1", "", 10.7920485, 106.6900696, "About 5 pm – 10 pm",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.hutieunamvang)));
            recommendItems.add(new RecommendItem("Bánh Canh Cua", "Food", "146 Trần Khắc Chân, 9, Phú Nhuận District", "0938261665", 10.8002362, 106.6753675, "2 pm – 9:30 pm daily",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.banhcanhcua)));
            recommendItems.add(new RecommendItem("Gỏi Cuốn", "Food", "650 Ðiện Biên Phủ, 11, District 10", "0989054086", 10.7898752, 106.6948678, "",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.goicuon)));
            recommendItems.add(new RecommendItem("Bánh Tráng Trộn", "Food", "38 Nguyễn Thượng Hiền, District 3", "01674271236", 10.7759, 106.6807089, "3 pm – 9 pm daily",
                    BitmapFactory.decodeResource(getResources(), R.mipmap.banhtrangtron)));

        foodRecommendAdapter = new FoodRecommendAdapter(this, R.layout.layout_item_recommend, recommendItems);
        listViewFood.setAdapter(foodRecommendAdapter);
    }

    private void initComponents() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        listViewFood = (ListView) this.findViewById(R.id.food_recommend_list);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnMyList = (Button) findViewById(R.id.btn_mylists);
    }

    public void mapActivity(View view) {
        Intent intent = new Intent(FoodRecommend.this, MapsActivity.class);
        startActivity(intent);
    }

    public void mylistActivity(View view) {
        Intent intent = new Intent(FoodRecommend.this, MyList.class);
        startActivity(intent);
    }

}
