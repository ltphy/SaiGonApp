package com.example.phy.hochiminhcity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phy.hochiminhcity.Modules.DirectionFinder;
import com.example.phy.hochiminhcity.Modules.DirectionFinderListener;
import com.example.phy.hochiminhcity.Modules.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener,DirectionFinderListener {

    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText edtOrigin;
    Button btnIcFindPath;
    LinearLayout findPathLayout;
    EditText edtDestination;
    List<Marker> originMarkers = new ArrayList<>();
    List<Marker> destinationMarkers = new ArrayList<>();
    List<Polyline> polylinePaths = new ArrayList<>();
    ProgressDialog progressDialog;
    MapWrapperLayout mapWrapperLayout;
    View contentView;
    Button btnClick,btnClickPath;
    Intent intentThatCalled;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;
    String voice2text; //added

    //Map Info
    RecommendItem recommendItem;

    OnInterInfoWindowTouchListener lsClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        intentThatCalled = getIntent();
        voice2text = intentThatCalled.getStringExtra("v2txt");
        getLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_wrapper);
        initControls();
        btnFindPath.setOnClickListener(x);
        btnIcFindPath.setOnClickListener(show);
        //getMapInfo
        getData();
    }
    private void getData() {
        Intent intent = getIntent();
        byte[] byteArr = intent.getByteArrayExtra("avatar");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
        recommendItem = new RecommendItem(intent.getStringExtra("name"),intent.getStringExtra("address"),
                intent.getStringExtra("phoneNo"),intent.getDoubleExtra("latitude",0.00),intent.getDoubleExtra("longitude",0.00),intent.getStringExtra("hours"),
                bmp);
    }
    private void initControls() {
        btnFindPath = (Button) findViewById(R.id.btn_find_path);
        edtOrigin = (EditText) findViewById(R.id.edt_origin);
        edtDestination = (EditText) findViewById(R.id.edt_destination);
        btnIcFindPath =(Button) findViewById(R.id.ic_find_path);
        findPathLayout = (LinearLayout) findViewById(R.id.find_path);
    }
    View.OnClickListener show = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(findPathLayout.getVisibility() ==View.GONE)
            {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) findPathLayout.getLayoutParams();
                params.weight = 3;
                findPathLayout.setLayoutParams(params);
                findPathLayout.setVisibility(View.VISIBLE);
            }
            else if(findPathLayout.getVisibility()==View.VISIBLE)
            {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) findPathLayout.getLayoutParams();
                params.weight = 0;
                findPathLayout.setLayoutParams(params);
                findPathLayout.setVisibility(View.GONE);
            }
        }
    };
    View.OnClickListener x = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            sendRequest(0);
        }
    };
    private void sendRequest(int id) {
        String origin = edtOrigin.getText().toString();
        String destination = edtDestination.getText().toString();
        LatLng startPos = new LatLng(latitude,longitude);
        if (origin.isEmpty()&&id==0) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (destination.isEmpty()&&id==0) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if(id == 0) {

                new DirectionFinder(this, origin, destination).execute();
            }
            else
            {
                new DirectionFinder(this, startPos, destination).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getLocation() {
        if (isLocationEnabled(MapsActivity.this)) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(MapsActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
                searchNearestPlace(voice2text);
            }
            else{
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
        }
        else
        {
            //prompt user to enable location....
            //.................
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Hey, a non null location! Sweet!

        //remove location callback:
        locationManager.removeUpdates(this);

        //open the map:
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Toast.makeText(MapsActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapWrapperLayout.init(mMap, this);

        // enable the button to move to your current location on Map
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng curLocation = new LatLng(latitude, longitude);
        Marker myMarker = mMap.addMarker(new MarkerOptions()
                .position(curLocation)
                .title("Your Current Location")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start_blue)
                ));


//Create a LatLng object = position
        LatLng loPosition = new LatLng(recommendItem.getLatitude(), recommendItem.getLongitude());
// add a marker
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(loPosition)
                .title(recommendItem.getName())
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapMarkerBitmap(recommendItem.getAvatar(),100,100)))
        );
// create an an animation for map while moving to this location
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(loPosition) // Set the center of the map to HCMUS
                .zoom(18)    //Sets the zoom (1<=20)
                .bearing(90) //Sets the orientation of the camera to east
                .tilt(30)    //Sets the tilt of the camera to 30 degrees
                .build();
//
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        contentView = LayoutInflater.from(this).inflate(R.layout.content, null);
        btnClick = (Button) contentView.findViewById(R.id.click);

        lsClick = new OnInterInfoWindowTouchListener(btnClick) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                Log.d("INFO WINDOW", "Clicked");
                edtDestination.setText(recommendItem.getAddress());
                sendRequest(1);
            }
        };

        btnClick.setOnTouchListener(lsClick);
        //LatLng myHome = new LatLng(10.837683, 106.688067);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                lsClick.setMarker(marker);

                TextView name = (TextView) contentView.findViewById(R.id.name);
                TextView location = (TextView) contentView.findViewById(R.id.location);

                name.setText(marker.getTitle());
                location.setText(recommendItem.getAddress());

                mapWrapperLayout.setMarkerWithInfoWindow(marker, contentView);
                return contentView;
            }
        });
    }

    private boolean isLocationEnabled(MapsActivity mapsActivity) {
        return true;
    }

    private Bitmap resizeMapMarkerBitmap(Bitmap bmp, int width, int height)
    {
        Bitmap Marker = Bitmap.createScaledBitmap(bmp, width, height, false);
        return getCircularBitmapWithWhiteBorder(Marker,2);
    }
    private Bitmap resizeMapMarker(String iconName, int width, int height)
    {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "mipmap", getPackageName()));
        Bitmap Marker = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return getCircularBitmapWithWhiteBorder(Marker,2);
        //return getRoundedBitmapWithBorder(Marker,2);
    }

    public static Bitmap getCircularBitmapWithWhiteBorder(Bitmap bitmap,
                                                          int borderWidth) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        final int width = bitmap.getWidth() + borderWidth;
        final int height = bitmap.getHeight() + borderWidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP); //
        Paint paint = new Paint();
        paint.setAntiAlias(true);//
        paint.setShader(shader);// get image into paint

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);// draw image
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(borderWidth);//
        canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint); // draw border'
        return canvasBitmap;
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void searchNearestPlace(String v2txt) {
        //.....
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait", "Finding direction...", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView)findViewById(R.id.tv_distance)).setText(route.distance.text);
            ((TextView)findViewById(R.id.tv_time)).setText(route.duration.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));

            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions()
                    .geodesic(true)
                    .color(Color.BLUE)
                    .width(10);

            for (int i = 0; i < route.points.size(); i++) {
                polylineOptions.add(route.points.get(i));
            }

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
    public void recommendActivity(View view) {
        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void mylistActivity(View view) {
        Intent intent = new Intent(MapsActivity.this, MyList.class);
        startActivity(intent);
    }



}
