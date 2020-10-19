package com.example.propertymanagementapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.Property
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_property_adapter.view.*

class AdapterProperty(var mContext: Context): RecyclerView.Adapter<AdapterProperty.MyViewHolder>(){

    private var mList: ArrayList<Property> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_property_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       var property = mList[position]
        holder.bind(property)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(response: ArrayList<Property>){
        mList = response
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(property: Property){
            var address = "Address: " + property.address
            itemView.property_adapter_address.text = address
            var city = "City: " + property.city
            itemView.property_adapter_city.text = city
            var country = "Country: " + property.country
            itemView.property_adapter_country.text = country
            var state = "State: " + property.state
            itemView.property_adapter_state.text = state
            var userType = "User: " + property.userType
            itemView.property_adapter_userType.text = userType
            if (property.image.isNotEmpty()) {
                Picasso.get().load(property.image)
                    .resize(120, 120)
                    .centerCrop().placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(itemView.property_adapter_image)
            }
        }
    }


}