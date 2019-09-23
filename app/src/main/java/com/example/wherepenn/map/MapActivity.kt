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

    // Permission Request Variables
    companion object{
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        const val REQUEST_PERMISSION_CODE = 1
    }

    // Default Zoom Level
    private val DEFAULT_ZOOM_LEVEL = 14f

    // Map Default Center
    val DEFAULT_PENN = LatLng(39.9529, -75.197098)

    // Member Variable for GoogleMap
    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Call onCreate on mapView
        mapView.onCreate(savedInstanceState)

        // Check Permission regarding location on runtime
        if(hasPermissions()) {
            // Initialize Map
            initMap()
        } else {
            // Request Permission if there are no current request granted
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        // OnClickListener for Floating button to show current location
        myLocationButton.setOnClickListener { onMyLocationButtonClick() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Initialize Map
        initMap()
    }

    // Checks to see if there are omitted permissions
    fun hasPermissions(): Boolean {
        // Return False if any permissions is missing
        for(permission in PERMISSIONS) {
            if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    // Initialize Map
    @SuppressLint("MissingPermission")
    fun initMap() {
        // Get GoogleMap in mapView. GoogleMap Instance is provided in callBacks
        mapView.getMapAsync {
            googleMap = it

            // Deactivate currentLocation until Permission is checked
            it.uiSettings.isMyLocationButtonEnabled = false

            // load an ArrayList<FoodTruck> from foodTruck.json in assets folder.
            val json = assets.open("foodTruck.json").reader().readText()
            val ftArray = Gson().fromJson<ArrayList<FoodTruck>>(json, object: TypeToken<ArrayList<FoodTruck>>() {}.type)


            // RecyclerViewPosition is initialized as -1
            var rvPosition = -1
            // Find the original index of the recyclerView item(passed from FoodTruck Fragment)
            val xVal = intent.getDoubleExtra("ITEM_XVAL", 0.0)
            val yVal = intent.getDoubleExtra("ITEM_YVAL", 0.0)
            for (index in 0 until ftArray.size) {
                // If item is onClicked from RecyclerView, save the index corresponding to ftARray
                if(ftArray[index].xVal == xVal && ftArray[index].yVal == yVal)
                    rvPosition = index
            }

            when{
                hasPermissions() -> {
                    // Activate Current Location
                    it.isMyLocationEnabled = true

                    // If the information is passed from RecyclerView Item OnClick
                    if (rvPosition != -1){
                        it.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(LatLng(ftArray[rvPosition].xVal, ftArray[rvPosition].yVal), DEFAULT_ZOOM_LEVEL + 1f)
                        )
                        for (index in 0 until ftArray.size){
                            // Mark the designated recycler view item with different icon
                            if (index == rvPosition) {
                                it.addMarker(
                                MarkerOptions()
                                    .position(LatLng(ftArray[index].xVal, ftArray[index].yVal))
                                    .title(ftArray[index].name)
                                    .snippet(ftArray[index].description)
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round)))
                            }
                            // Mark the rest of the foodTrucks as original markers
                            else {
                                it.addMarker(MarkerOptions()
                                    .position(LatLng(ftArray[rvPosition].xVal, ftArray[rvPosition].yVal))
                                    .title(ftArray[rvPosition].name)
                                    .snippet(ftArray[rvPosition].description))
                            }
                        }
                    } else {
                        it.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL)
                        )
                        for (item in ftArray){
                            it.addMarker(
                                MarkerOptions()
                                    .position(LatLng(item.xVal, item.yVal))
                                    .title(item.name)
                                    .snippet(item.description)
                            )
                        }

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

    // Get current location
    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng{
        // Instantiate a GPS Provider
        val locationProvider : String = LocationManager.GPS_PROVIDER
        // Instantiate a locationManager
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Bring the last known current location
        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
        // return location as LatLng Class
        return LatLng(lastKnownLocation!!.latitude, lastKnownLocation.longitude)
    }

    // When the floating button for current location is OnClicked
    private fun onMyLocationButtonClick() {
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

