package com.example.propertymanagementapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.Property
import com.example.propertymanagementapp.data.model.PropertyResponse
import com.example.propertymanagementapp.databinding.RowPropertyAdapterBinding
import com.squareup.picasso.Picasso

class AdapterProperty(var mContext: Context): RecyclerView.Adapter<AdapterProperty.MyViewHolder>(){

    private var mList: ArrayList<Property> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterProperty.MyViewHolder {
        var mBinding = RowPropertyAdapterBinding.inflate(LayoutInflater.from(mContext))
        return MyViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       var property = mList[position]
        holder.bind(property)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(response: PropertyResponse){
        if (response.count != 0) {
            mList = response.data
            notifyDataSetChanged()
        }
    }

    inner class MyViewHolder(var mBinding: RowPropertyAdapterBinding) : RecyclerView.ViewHolder(mBinding.root){
        fun bind(property: Property){
            var address = "Address: " + property.address
            mBinding.propertyAdapterAddress.text = address
            var city = "City: " + property.city
            mBinding.propertyAdapterCity.text = city
            var country = "Country: " + property.country
            mBinding.propertyAdapterCountry.text = country
            var state = "State: " + property.state
            mBinding.propertyAdapterState.text = state
            var userType = "User: " + property.userType
            mBinding.propertyAdapterUserType.text = userType
            if (property.image.isNotEmpty()) {
                Picasso.get().load(property.image)
                    .resize(120, 120)
                    .centerCrop().placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(mBinding.propertyAdapterImage)
            }
        }
    }


}