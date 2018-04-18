package com.example.phy.hochiminhcity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class RecommendItemDetail extends AppCompatActivity {

    ImageView ivAvatar;
    TextView tvName, tvPhoneNo,tvAddress,tvOpenHour,tvSave;
    Button btnMap,btnList, star;
    RecommendItem recommendItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_item_detail);
        initComponents();
        getData();
        updateLayout();
    }

    private void updateLayout() {
        ivAvatar.setImageBitmap(recommendItem.getAvatar());
        tvName.setText(recommendItem.getName());
        tvAddress.setText(recommendItem.getAddress());
        tvOpenHour.setText(recommendItem.getHours());
        tvPhoneNo.setText(recommendItem.getPhoneNo());
    }

    private void initComponents() {
        ivAvatar = (ImageView) findViewById(R.id.iv_recommend_item_detail);
        tvName =(TextView) findViewById(R.id.detail_name);
        tvPhoneNo = (TextView) findViewById(R.id.detail_phone);
        tvAddress = (TextView) findViewById(R.id.detail_address);
        tvOpenHour = (TextView) findViewById(R.id.detail_hours);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnList = (Button) findViewById(R.id.btn_mylists);
        star = (Button) findViewById(R.id.bookmark);
        tvSave = (TextView)  findViewById(R.id.save);
    }
    private void getData() {
        Intent intent = getIntent();
        byte[] byteArr = intent.getByteArrayExtra("avatar");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
        recommendItem = new RecommendItem(intent.getStringExtra("name"),intent.getStringExtra("address"),
                intent.getStringExtra("phoneNo"),intent.getDoubleExtra("latitude",0.00),intent.getDoubleExtra("longitude",0.00),intent.getStringExtra("hours"),
                bmp);
    }
    public void onClickCall(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel: " + recommendItem.getPhoneNo()));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    public void onShowMapCLick(View view) {
                Intent intent = new Intent(RecommendItemDetail.this, MapsActivity.class);
                intent.putExtra("name", recommendItem.getName());
                intent.putExtra("address",recommendItem.getAddress());
                intent.putExtra("phoneNo",recommendItem.getPhoneNo());
                intent.putExtra("hours",recommendItem.getHours());
                intent.putExtra("latitude",recommendItem.getLatitude());
                intent.putExtra("longitude",recommendItem.getLongitude());
                // convert bitmap to byte[] in order to send through intent
                Bitmap bmp = recommendItem.getAvatar();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("avatar", byteArray);
                startActivity(intent);
    }

    public void mapActivity(View view) {
        Intent intent = new Intent(RecommendItemDetail.this, MapsActivity.class);
        startActivity(intent);
    }

    public void mylistActivity(View view) {
        Intent intent = new Intent(RecommendItemDetail.this, MyList.class);
        startActivity(intent);
    }

    public void bookMarkMyList(View view) {

        if(!star.isEnabled()) {
            star.setBackgroundResource(R.mipmap.saved);
            recommendItem.setBookmark(true);
            tvSave.setText("saved");
            star.setEnabled(true);
        }
        else
        {
            recommendItem.setBookmark(false);
            star.setBackgroundResource(R.mipmap.save);
            tvSave.setText("save");
            star.setEnabled(false);
        }
    }
}
