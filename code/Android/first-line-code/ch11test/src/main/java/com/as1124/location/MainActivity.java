package com.as1124.location;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 111;

    public LocationClient locationClient;

    private MapView baiduMapView = null;

    private BaiduMap baiduMap = null;

    private TextView positionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        positionView = findViewById(R.id.text_current_location);


        locationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); //设置定位模式，默认高精度
//        option.setCoorType("bd09ll"); //设置返回经纬度坐标类型，默认GCJ02
        option.setScanSpan(20000);
        option.setIsNeedAltitude(true);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append("纬度=").append(bdLocation.getLatitude()).append(", 经度=")
                        .append(bdLocation.getLongitude()).append(", 海拔=").append(bdLocation.getAltitude())
                        .append("\n定位方式=").append(bdLocation.getLocTypeDescription());
                positionView.setText(strBuilder.toString());
                baiduMap.setMyLocationData(new MyLocationData.Builder()
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build());
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude())));
            }
        });


        /*****************************初始化地图***********************************************/
        baiduMapView = findViewById(R.id.baidu_map);
        baiduMap = baiduMapView.getMap();
//        baiduMap.setBaiduHeatMapEnabled(true);
        baiduMap.setBuildingsEnabled(true);
        baiduMap.setCompassEnable(true);
        baiduMap.setIndoorEnable(true);
        baiduMap.setMyLocationEnabled(true);

        List<String> permissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CAMERA);
            }
            if (permissionList.isEmpty()) {
                // 没有权限需要授权
                showPosition();
            } else {
                requestPermissions(permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int flag : grantResults) {
                    if (flag != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, "需要同意所有权限才能使用定位功能！", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                showPosition();
            }
        }
    }

    private void showPosition() {
        locationClient.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        baiduMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baiduMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        locationClient.stop();
        baiduMapView.onDestroy();
        super.onDestroy();
    }
}
