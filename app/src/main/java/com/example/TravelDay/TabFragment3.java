package com.example.TravelDay;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;


import java.io.IOException;
import java.util.List;
import java.util.Vector;

import static android.Manifest.permission.*;
public class TabFragment3 extends Fragment implements OnMapReadyCallback{

    private static final String TAG = "TabFragment3";

    private static final int PERMISSION_REQUEST_CODE = 100;

    private static final String[] PERMISSIONS = {
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    //지도 객체 변수
    private MapView mapView;
    private NaverMap mNaverMap;

    EditText editText;//지도 입력
    Button button;//입력받은 위치 찾기 버튼
    public TabFragment3(){}

    public static TabFragment3 newInstance(){
        TabFragment3 tabFragment3 = new TabFragment3();
        return tabFragment3;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.tab_fragment_3, container, false);

        editText = view.findViewById(R.id.edit_location);
        button = view.findViewById(R.id.btn_search);

        mapView = (MapView) view.findViewById(R.id.map_fragment);
        mapView.onCreate(savedInstanceState);

        //getMapAsync를 호출하여 비동기로 onMapReady 콜백 메소드 호출
        //onMapReady에서 NaverMap 객체를 받는다
        mapView.getMapAsync(this);

        //위치를 반환하는 구현체인  FusedLocationSource생성
        mLocationSource = new FusedLocationSource(this,PERMISSION_REQUEST_CODE);
        return view;
    }

    @Override
    public void onMapReady(NaverMap naverMap) {
        Log.d(TAG,"onMapReady");


        //NaverMap 객체 받아서 Naver 객체에 위치 소스 지정
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);


        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(true);//기본값 :ture
        uiSettings.setScaleBarEnabled(true);
        uiSettings.setZoomControlEnabled(true);
        uiSettings.setLocationButtonEnabled(true);
        //권한확인. 결과는 onRequestPermissionsResult 콜백 메소드 호출
        ActivityCompat.requestPermissions(getActivity(),PERMISSIONS,PERMISSION_REQUEST_CODE);


        //////////////////////////////////////////


        //배경 지도 선택
        mNaverMap.setMapType(NaverMap.MapType.Basic);

        //건물 표시
        mNaverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING,true);

        //위치 및 각도 조정
        CameraPosition cameraPosition = new CameraPosition(
          new LatLng(33.38,126.55), //시작 위치 지정 제주도로 고정했음
                9,                              //줌 레벨
                0,                             //기울기 각도
                0                              //방향
        );
        mNaverMap.setCameraPosition(cameraPosition);

        Geocoder geocoder = new Geocoder(getContext());

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();

                List<Address> addressList = null;
                try{
                    addressList=geocoder.getFromLocationName(str,//주소
                            10);//최대 검색 결과 개수
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"")+1,splitStr[0].length()-2);//주소

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=")+1);//위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=")+1);//경도

                //좌표(위도,경도)생성
                LatLng point = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

                //마커 생성
                Marker marker = new Marker();
                marker.setPosition(point);

                freeActiveMarkers();
                //마커추가
                marker.setMap(mNaverMap);

                //해당 좌표로 카메라 이동동
               CameraUpdate cameraUpdate = CameraUpdate.scrollTo(point);
                mNaverMap.moveCamera(cameraUpdate);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }

    //마커 정보 저장시킬 변수들 선언
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;

    //현재 카메라가 보고있는 위치
    public LatLng getCurrentPosition(NaverMap naverMap){
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(cameraPosition.target.latitude,cameraPosition.target.longitude);
    }

    //지도상에 표시되고 있는 마커들 지도에서 삭제
    private  void freeActiveMarkers(){
        if(activeMarkers == null){
            activeMarkers = new Vector<Marker>();
            return;
        }
        for(Marker activeMarker:activeMarkers){
            activeMarker.setMap(null);
        }
        activeMarkers = new Vector<Marker>();
    }

    @Override
    public void onStart() {
        String addr;

        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
