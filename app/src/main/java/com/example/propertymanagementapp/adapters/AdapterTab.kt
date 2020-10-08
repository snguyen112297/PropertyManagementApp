package com.example.propertymanagementapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.propertymanagementapp.ui.auth.LoginFragment
import com.example.propertymanagementapp.ui.auth.RegisterFragment

class AdapterTab(fm: FragmentManager, mode: Int):FragmentPagerAdapter(fm){
    private val adapterMode = mode
    override fun getItem(position: Int): Fragment {
        if (adapterMode == 1) {
            return when (position) {
                0 -> LoginFragment.newInstance("tenant")
                else -> LoginFragment.newInstance("landlord")
            }
        }
        else {
            return when (position) {
                0 -> RegisterFragment.newInstance("tenant")
                else -> RegisterFragment.newInstance("landlord")
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Tenant"
            else -> "Landlord"
        }
    }

}