package com.example.wherepenn.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_map.*
import androidx.fragment.app.FragmentActivity
import com.example.wherepenn.R
import com.example.wherepenn.utils.FoodTruck
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.maps.android.SphericalUtil

import kotlinx.android.synthetic.main.activity_main.*

class MapActivity : AppCompatActivity(){

    // 런타임에서 권한이 필요한 permission 목록
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    // 퍼미션 승인 요청 시 사용한는 요청 코드
    private val REQUEST_PERMISSION_CODE = 1

    // 기본 앱 줌 레벨
    private val DEFAULT_ZOOM_LEVEL = 14f

    // 기본 위치(Map Default Center)
    val DEFAULT_PENN = LatLng(39.9529, -75.197098)

    // 구글 맵 객체를 참조할 멤버 변수
    var googleMap: GoogleMap? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)


        // 맵 뷰에 onCreate 함수 호출
        mapView.onCreate(savedInstanceState)

        // 앱이 실행될 때 런타임에서 위치 서비스 관련 권한 체크
        if(hasPermissions()) {
            initMap()
        } else {
            // 권한 요청
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        // 현재 위치 버튼 클릭 이벤트 리스너 설정
        myLocationButton.setOnClickListener { onMyLocationButtonClick() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 맵 초기화
        initMap()
    }

    fun hasPermissions(): Boolean {
        // 퍼미션 목록중 하나라도 권한이 없으면 false 반환
        for(permission in PERMISSIONS) {
            if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    // 맵 초기화 하는 함수
    @SuppressLint("MissingPermission")
    fun initMap() {
        // 맵뷰에서 구글 맵을 불러오는 함수. 컬백함수에서 구글 맵 객체가 전달됨
        mapView.getMapAsync {
            googleMap = it
            // 현재위치로 이동 버튼 비활성화
            it.uiSettings.isMyLocationButtonEnabled = false
            // 위치 사용 권한이 있는 경우
            val json = assets.open("foodTruck.json").reader().readText()
            val ftArray = Gson().fromJson<ArrayList<FoodTruck>>(json, object: TypeToken<ArrayList<FoodTruck>>() {}.type)

            val xval = intent.getDoubleExtra("ITEM_XVAL", 0.0)
            val yval = intent.getDoubleExtra("ITEM_YVAL", 0.0)
            var rvPosition = -1
            for (index in 0 until ftArray.size) {
                if(ftArray[index].xVal == xval && ftArray[index].yVal == yval)
                    rvPosition = index
            }

            when{
                hasPermissions() -> {
                    // 현재 위치 표시 활성화
                    it.isMyLocationEnabled = true
                    // 현재 위치로 카메라 이동

                    for (item in ftArray){
                        it.addMarker(
                            MarkerOptions()
                                .position(LatLng(item.xVal, item.yVal))
                                .title(item.name)
                                .snippet(item.description)
                        )
                    }
                    if (rvPosition != -1){
                        it.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(LatLng(ftArray[rvPosition].xVal, ftArray[rvPosition].yVal), DEFAULT_ZOOM_LEVEL + 1f)
                        )
                        it.addMarker(MarkerOptions()
                            .position(LatLng(ftArray[rvPosition].xVal, ftArray[rvPosition].yVal))
                            .title(ftArray[rvPosition].name)
                            .snippet(ftArray[rvPosition].description)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round)))
                    } else {
                        it.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL)
                        )
                    }
                }
                else -> {
                    it.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(DEFAULT_PENN, DEFAULT_ZOOM_LEVEL)
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng{
        //googleMap?.isMyLocationEnabled = true
        // 위치를 측정하는 프로바이더를 GPS 센서로 지정
        val locationProvider : String = LocationManager.GPS_PROVIDER
        // 위치 서비스 객체를 불러옴
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 마지막으로 업데이트된 위치를 가져옴
        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
        // 위치 경도 객체로 반환
        return LatLng(lastKnownLocation!!.latitude, lastKnownLocation.longitude)
    }

    // 현재 위치 버튼 클릭한 경우
    fun onMyLocationButtonClick() {
        when {
            hasPermissions() -> googleMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
            else -> Toast.makeText(
                applicationContext, "Agree to the permission", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}

