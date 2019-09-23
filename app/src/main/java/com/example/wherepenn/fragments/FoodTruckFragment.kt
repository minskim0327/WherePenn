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
 * A Fragment Class for FoodTruck
 */
class FoodTruckFragment : Fragment(){

    //private lateInit var currentLocation: LatLng
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

        // Initially instantiate recyclerView
        updateRecyclerView(this.context!!, ftRecyclerView)

        // Sort recyclerView by ratings
        ratingSort.setOnClickListener {
            updateRecyclerView(this.context!!, ftRecyclerView, 0)
        }

        // Sort recyclerView by location proximity
        locationSort.setOnClickListener {
            // Request permission
            if(hasPermissions()) {
                // Calls the most-recent current location
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity!!)
                val task : Task<Location> = fusedLocationClient.lastLocation
                task.addOnSuccessListener {location: Location? ->
                    currentLocation = LatLng(location!!.latitude, location.longitude)
                }
                updateRecyclerView(this.context!!, ftRecyclerView, 1)
            } else {
                // Request Permission
                ActivityCompat.requestPermissions(
                    activity!!,
                    MapActivity.PERMISSIONS,
                    MapActivity.REQUEST_PERMISSION_CODE
                )
            }
        }

        // View Map with all foodTrucks marked
        viewMap.setOnClickListener {
            val intent = Intent(this.context!!, MapActivity::class.java)
            intent.action = Intent.ACTION_VIEW
            intent.putExtra("VIEW_MODE", "ENTIRE_VIEW")
            intent.`package` = "com.google.android.apps.maps"
            startActivity(intent)
        }
    }

    // Checks to see if there are omitted permissions
    private fun hasPermissions(): Boolean {
        // Return False if any permissions is missing
        for(permission in MapActivity.PERMISSIONS) {
            if(ActivityCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    // Returns a sorted ArrayList<FoodTruck> for creating recyclerViews
    private fun loadFTdata(context: Context, type: Int) : ArrayList<FoodTruck> {
        // load an ArrayList<FoodTruck> from foodTruck.json in assets folder.
        val json = context.assets.open("foodTruck.json").reader().readText()
        val ftArray = Gson().fromJson<ArrayList<FoodTruck>>(json, object: TypeToken<ArrayList<FoodTruck>>() {}.type)


        // If location button is onClicked, calculate location for each foodTruck
        if (type == 1){
            for (item in ftArray){
                item.distance = SphericalUtil
                    .computeDistanceBetween(
                    currentLocation, LatLng(item.xVal, item.yVal))
            }
        }

        // Create sorted array
        return when(type){
            // sort by rating
            0 -> ArrayList(ftArray.sortedWith(compareBy(FoodTruck::rating, FoodTruck::description)).asReversed())
            // sort by location proximity
            else ->
                ArrayList(ftArray.sortedWith(compareBy(FoodTruck::distance)))
        }
    }

    // Updates recyclerView whenever there are changes for FoodTruckFragment
    private fun updateRecyclerView(context: Context, view: RecyclerView, type: Int = 0) {

        // add recyclerView
        val arrayFTlist = loadFTdata(context, type)
        val adapter = FoodTruckRVAdapter(context, arrayFTlist)
        view.adapter = adapter

        // add layoutManager
        val lm = LinearLayoutManager(context)
        view.layoutManager = lm
        view.setHasFixedSize(true)

        // Instantiate OnClickListener for recyclerView items
        adapter.setOnItemClickListener(object: FoodTruckRVAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()

                // Pass the intent to MapActivity
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
