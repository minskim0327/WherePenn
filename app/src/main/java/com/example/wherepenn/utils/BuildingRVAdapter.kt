package com.example.wherepenn.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wherepenn.R

// RecyclerViewAdapter for Building Fragment
class BuildingRVAdapter(val context: Context, homeFeed: HomeFeed) :
    RecyclerView.Adapter<BuildingRVAdapter.Holder>(){

    private val buildingList = homeFeed.result_data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.building_rv_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(buildingList[position], context)
    }

    override fun getItemCount(): Int {
        return buildingList.count()
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        private val fbLayout = itemView?.findViewById<View>(R.id.building_rv_layout)
        private val fbName = itemView?.findViewById<TextView>(R.id.buildingText)

        fun bind (building: Building, context: Context) {
            fbName?.text = building.title
        }
    }

}