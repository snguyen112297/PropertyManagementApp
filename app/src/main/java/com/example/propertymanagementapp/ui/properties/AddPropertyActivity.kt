package com.example.propertymanagementapp.ui.properties

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.AddPropertyResponse
import com.example.propertymanagementapp.data.model.ImageUploadResponse
import com.example.propertymanagementapp.data.network.MyApi
import com.example.propertymanagementapp.databinding.ActivityAddPropertyBinding
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.helpers.toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.action_bar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class AddPropertyActivity : AppCompatActivity(), AddPropertyListener {

    private val CAMERA_REQUEST_CODE = 100
    private val MULTIPLE_PERMISSON_CODE = 200
    private val PICK_IMAGE = 300

    private val image_url = "https://apolis-property-management.s3.ap-south-1.amazonaws.com/images/"

    lateinit var mBinding: ActivityAddPropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_property)
        val viewModel: AddPropertyViewModel = ViewModelProviders.of(this).get(AddPropertyViewModel::class.java)
        mBinding.viewModel = viewModel
        viewModel.addPropertyListener = this
        init()
    }

    private fun init(){
        var toolbar = toolbar
        toolbar.title = "Add Property"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mBinding.addPropertyCamera.setOnClickListener{
            onButtonCameraClick()
        }
        mBinding.addPropertyOpenFiles.setOnClickListener {
            onButtonGalleryClick()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onStarted() {
        this.toast("onStarted")
    }

    override fun onSuccess(response: LiveData<AddPropertyResponse>) {
        response.observe(this, Observer{
            this.toast("Success")
            startActivity(Intent(this, PropertyActivity::class.java))
            finish()
        })
    }

    override fun failure(message: String) {
        this.toast("Big Oof")
    }

    // get URI from bitmap
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    // get actual path
    fun getRealPathFromURI(uri: Uri?): String? {
        val cursor: Cursor? = contentResolver.query(uri!!, null, null, null, null)
        cursor!!.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

    // Upload Image
    fun uploadImage(path: String){
        var file = File(path)
        var requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        var body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        MyApi().uploadImage(body).enqueue(object: Callback<ImageUploadResponse>{
            override fun onResponse(call: Call<ImageUploadResponse>, response: Response<ImageUploadResponse>) {
                if (response.isSuccessful){
                    applicationContext.d("Success")
                    mBinding.viewModel.addImage(response.body()!!.data.location)
                }
            }

            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                applicationContext.d(t.message.toString())
            }

        })
    }

    private fun onButtonGalleryClick(){
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // check if all permission are granter
                    if(report!!.areAllPermissionsGranted()){
                        applicationContext.toast("All permission granted")
                        openGallery()
                    }
                    // check for permanent denial of any permission
                    if(report!!.isAnyPermissionPermanentlyDenied){
                        showDialogue()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

            })
            .onSameThread()
            .check()
    }

    // Open Gallery
    fun openGallery(){
        var intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
    }


    // Open Camera

    private fun onButtonCameraClick(){
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // check if all permission are granter
                    if(report!!.areAllPermissionsGranted()){
                        applicationContext.toast("All permission granted")
                        openCamera()
                    }
                    // check for permanent denial of any permission
                    if(report!!.isAnyPermissionPermanentlyDenied){
                        showDialogue()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

            })
            .onSameThread()
            .check()
    }

    private fun openCamera() {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun showDialogue(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permission")
        builder.setMessage("Please please please please give us the permission")
        builder.setPositiveButton("Go To Setting", object: DialogInterface.OnClickListener{
            override fun onClick(dialoge: DialogInterface?, p1: Int) {
                dialoge?.dismiss()
                openAppSettings()
            }
        })
        builder.setNegativeButton("Cancel", object: DialogInterface.OnClickListener{
            override fun onClick(dialoge: DialogInterface?, p1: Int) {
                dialoge?.dismiss()
            }

        })
        builder.show()
    }

    private fun openAppSettings(){
        var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        var uri = Uri.fromParts("package", packageName, null)
        intent.setData(uri)
        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            var imageUri = data!!.data
            uploadImage(getRealPathFromURI(imageUri)!!)
        }
    }
}