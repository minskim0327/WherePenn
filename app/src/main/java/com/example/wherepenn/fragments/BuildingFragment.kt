package com.example.wherepenn.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.example.wherepenn.R
import com.example.wherepenn.map.MapActivity
import com.example.wherepenn.utils.BuildingRVAdapter
import com.example.wherepenn.utils.FoodTruckRVAdapter
import com.example.wherepenn.utils.HomeFeed
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_building.*
import kotlinx.android.synthetic.main.fragment_food_truck.*

/**
 * A simple [Fragment] subclass.
 */
class BuildingFragment : Fragment() {

    private lateinit var mySearchView: View
    private lateinit var myList : ListView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_building, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mySearchView = view.findViewById(R.id.searchView)

        fetchJson()
    }

    fun fetchJson() {
        val URL = "https://api.pennlabs.org/buildings/search?q=hill"
        val requestQueue : RequestQueue = Volley.newRequestQueue(context)

        val objectRequest: JsonObjectRequest? = JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            Response.Listener { response ->
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(response.toString(), HomeFeed::class.java)

                this.activity!!.runOnUiThread {
                    updateRecyclerView(this.context!!, building_rv, homeFeed)
                }
                //Toast.makeText(context, homeFeed.result_data.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, homeFeed.buldings.toString(), Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error -> Log.e("Rest Response",  error.toString())}
        )

        requestQueue.add(objectRequest)
    }

    private fun updateRecyclerView(context: Context, view: RecyclerView, homeFeed: HomeFeed) {
        // add recyclerView
        val adapter = BuildingRVAdapter(this.context!!, homeFeed)
        view.adapter = adapter
        // add layoutManager
        val lm = LinearLayoutManager(context)
        view.layoutManager = lm
        view.setHasFixedSize(true)
/*
        // when the layout is onClicked
        adapter.setOnItemClickListener(object: BuildingRVAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
                // Intent
                val intent = Intent(context, MapActivity::class.java)
                intent.action = Intent.ACTION_VIEW
                intent.`package` = "com.google.android.apps.maps"
                startActivity(intent)
            }
        })*/
    }
}

