package com.example.propertymanagementapp.ui.properties

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.adapters.AdapterProperty
import com.example.propertymanagementapp.data.model.Property
import com.example.propertymanagementapp.data.model.PropertyResponse
import com.example.propertymanagementapp.databinding.ActivityPropertyBinding
import com.example.propertymanagementapp.helpers.SessionManager
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.helpers.toast
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_property.*

class PropertyActivity : AppCompatActivity(), PropertyListener {

    lateinit var mBinding: ActivityPropertyBinding

    lateinit var adapterProperty: AdapterProperty

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_property)
        val viewModel: PropertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)
        mBinding.viewModel = viewModel
        viewModel.propertyListener = this
        init()
    }
    private fun init(){
        // Set up toolbar
        var toolbar = toolbar
        toolbar.title = "Properties"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mBinding.viewModel!!.getProperties(this)
        adapterProperty = AdapterProperty(this)
        property_recycler_view.layoutManager = LinearLayoutManager(this)
        property_recycler_view.adapter = adapterProperty

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onStarted() {
        this.toast("onStarted")
    }

    override fun onSuccess(response: LiveData<PropertyResponse>) {
        sessionManager = SessionManager(this)
        var userId = sessionManager.getUserId()
        response.observe(this, Observer{
            this.d(response.value.toString())
            val responseValue: ArrayList<Property> = ArrayList()
            for (i in (0..response.value!!.data.size)){
                if (response.value!!.data[i].userId == userId){
                    responseValue.add(response.value!!.data[i])
                }
            }
            adapterProperty.setData(responseValue)
        })
    }

    override fun failure(message: String) {
        this.toast("Big Oof")
    }
}