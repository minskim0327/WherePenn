package com.example.wherepenn.utils

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wherepenn.R
import com.example.wherepenn.fragments.FoodTruckFragment
import org.w3c.dom.Text

// RecyclerView Adapter for FoodTruck Fragment
class FoodTruckRVAdapter(val context: Context, val truckList: ArrayList<FoodTruck>) :
        RecyclerView.Adapter<FoodTruckRVAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.food_truck_rv_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(truckList[position], context)
    }

    override fun getItemCount(): Int {
        return truckList.size
    }

    // Instantiate OnClickListener for RecyclerView Items
    interface OnItemClickListener{
        fun onItemClick(v: View, position: Int)
    }

    private var mListener: OnItemClickListener? = null

    private fun locationConverter(street: Int, avenue: String) : String {
        return when (street % 10) {
            1 -> "${street}st, ${avenue} St"
            2 -> "${street}nd, ${avenue} St"
            3 -> "${street}rd, ${avenue} St"
            else -> "${street}th, ${avenue} St"
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    // Define a holder class for implementing UIs
    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        private val ftLayout = itemView?.findViewById<View>(R.id.food_truck_rv_item)
        private val ftName = itemView?.findViewById<TextView>(R.id.ftName)
        private val ftDescription = itemView?.findViewById<TextView>(R.id.ftDescription)
        private val ftLocation = itemView?.findViewById<TextView>(R.id.ftLocation)
        private val ftRating = itemView?.findViewById<TextView>(R.id.ftRating)

        // Instantiate onClickListener for RecyclerView
        init{
            ftLayout!!.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if(mListener != null) {
                        mListener!!.onItemClick(ftLayout, position)
                    }
                }
            }
        }

        fun bind (ft: FoodTruck, context: Context) {
            ftName?.text = ft.name
            ftDescription?.text = ft.description
            ftLocation?.text = locationConverter(ft.avenue, ft.street)
            ftRating?.text = "${String.format("%.1f", ft.rating).toDouble()}/5.0"
        }
    }

}