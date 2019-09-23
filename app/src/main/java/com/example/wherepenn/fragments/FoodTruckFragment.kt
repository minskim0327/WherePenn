package com.example.wherepenn.fragments


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.example.wherepenn.map.MapActivity
import com.example.wherepenn.utils.FoodTruck
import com.example.wherepenn.utils.FoodTruckRVAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_food_truck.*
import org.json.JSONArray
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName
import com.example.wherepenn.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.maps.android.SphericalUtil


/**
 * A simple [Fragment] subclass.
 */
class FoodTruckFragment : Fragment(){


    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    // 퍼미션 승인 요청 시 사용한는 요청 코드
    private val REQUEST_PERMISSION_CODE = 1

    //private lateinit var currentLocation: LatLng
    private var currentLocation = LatLng(0.0, 0.0)
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_truck, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateRecyclerView(this.context!!, ftRecyclerView)


        /** update recycler view**/
        ratingSort.setOnClickListener {
            updateRecyclerView(this.context!!, ftRecyclerView, 0)
        }

        locationSort.setOnClickListener {
            // request permission
            if(hasPermissions()) {
                // 마지막으로 업데이트된 위치를 가져옴
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity!!)
                //fetchLastLocation()
                val task : Task<Location> = fusedLocationClient.lastLocation
                task.addOnSuccessListener {location: Location? ->
                    currentLocation = LatLng(location!!.latitude, location.longitude)
                }
                //Toast.makeText(this.context!!, currentLocation.longitude.toString(), Toast.LENGTH_SHORT).show()
                updateRecyclerView(this.context!!, ftRecyclerView, 1)
            } else {
                // 권한 요청
                ActivityCompat.requestPermissions(activity!!, PERMISSIONS, REQUEST_PERMISSION_CODE)
            }
        }

        viewMap.setOnClickListener {
            val intent = Intent(this.context!!, MapActivity::class.java)
            intent.action = Intent.ACTION_VIEW
            intent.putExtra("VIEW_MODE", "ENTIRE_VIEW")
            intent.`package` = "com.google.android.apps.maps"
            startActivity(intent)
        }
    }

    // Checks to see if there are permissions
    fun hasPermissions(): Boolean {
        // 퍼미션 목록중 하나라도 권한이 없으면 false 반환
        for(permission in PERMISSIONS) {
            if(ActivityCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    private fun loadFTdata(context: Context, type: Int) : ArrayList<FoodTruck> {
        val json = context.assets.open("foodTruck.json").reader().readText()
        val ftArray = Gson().fromJson<ArrayList<FoodTruck>>(json, object: TypeToken<ArrayList<FoodTruck>>() {}.type)
        // calculate location

        if (type == 1){
            for (item in ftArray){
                item.distance = SphericalUtil
                    .computeDistanceBetween(
                    currentLocation, LatLng(item.xVal, item.yVal))
            }
        }


        // create sorted array
        return when(type){
            0 -> ArrayList(ftArray.sortedWith(compareBy(FoodTruck::rating, FoodTruck::description)).asReversed())
            else ->
                ArrayList(ftArray.sortedWith(compareBy(FoodTruck::distance)))
        }
    }

    private fun updateRecyclerView(context: Context, view: RecyclerView, type: Int = 0) {
        // add recyclerView
        val arrayFTlist = loadFTdata(context, type)
        val adapter = FoodTruckRVAdapter(context, arrayFTlist)
        view.adapter = adapter
        // add layoutManager
        val lm = LinearLayoutManager(context)
        view.layoutManager = lm
        view.setHasFixedSize(true)

        // when the layout is onClicked
        adapter.setOnItemClickListener(object: FoodTruckRVAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
                // Intent
                val intent = Intent(context, MapActivity::class.java)
                intent.action = Intent.ACTION_VIEW

                intent.putExtra("ITEM_XVAL", arrayFTlist[position].xVal)
                intent.putExtra("ITEM_YVAL", arrayFTlist[position].yVal)
                intent.`package` = "com.google.android.apps.maps"
                startActivity(intent)
            }
        })
    }
}
