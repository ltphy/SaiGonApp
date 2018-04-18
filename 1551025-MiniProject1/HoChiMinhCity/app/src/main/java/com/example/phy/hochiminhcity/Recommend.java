package com.example.phy.hochiminhcity;

import android.graphics.Bitmap;

/**
 * Created by Phy on 6/19/2017.
 */

public class Recommend {
    private Bitmap bmpRecommend;
    int id;
    public Recommend(Bitmap bmpRecommend) {
        this.bmpRecommend = bmpRecommend;
    }

    public Bitmap getBmpRecommend() {
        return bmpRecommend;
    }

    public Recommend(Bitmap bmpRecommend, int id) {
        this.bmpRecommend = bmpRecommend;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBmpRecommend(Bitmap bmpRecommend) {
        this.bmpRecommend = bmpRecommend;
    }
}
