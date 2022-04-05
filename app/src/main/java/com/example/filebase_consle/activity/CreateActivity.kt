package com.example.filebase_consle.activity

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.filebase_consle.R
import com.example.filebase_consle.manager.DatabaseHandler
import com.example.filebase_consle.manager.DatabaseManager
import com.example.filebase_consle.manager.StorageHandler
import com.example.filebase_consle.manager.StorageManager
import com.example.filebase_consle.manager.StorageManager.Companion.uploadPhoto
import com.example.filebase_consle.model.Post
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter

class CreateActivity: BaseActivity() {

    lateinit var iv_photo: ImageView
    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initViews()
    }

    fun initViews() {
        var iv_close = findViewById<ImageView>(R.id.iv_close)
        var et_title = findViewById<EditText>(R.id.et_title)
        var et_body = findViewById<EditText>(R.id.et_body)
        var b_create = findViewById<Button>(R.id.b_create)
        iv_photo = findViewById<ImageView>(R.id.iv_photo)
        var iv_camera = findViewById<ImageView>(R.id.iv_camera)

        iv_camera.setOnClickListener {
            pickUserPhoto()
        }
        iv_close.setOnClickListener {
            finish()
        }
        b_create.setOnClickListener {
            val title = et_title.text.toString().trim()
            val body = et_body.text.toString().trim()
            val post = Post(title, body)
            storePost(post)
        }
    }

    fun pickUserPhoto() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                allPhotos = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
                pickedPhoto = allPhotos.get(0)
                iv_photo.setImageURI(pickedPhoto)
            }
        }

    fun storePost(post: Post) {
        if (pickedPhoto != null) {
            storeStorage(post)
        } else {
            storeDatabase(post)
        }
    }

    fun storeStorage(post: Post) {
        showLoading(this)
        StorageManager.uploadPhoto(pickedPhoto!!, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                post.img = imgUrl
                storeDatabase(post)
            }

            override fun onError(exception: Exception?) {
                storeDatabase(post)
            }
        })
    }

    fun storeDatabase(post: Post) {
        DatabaseManager.storePost(post, object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                Log.d("@@@", "post is saved")
                dismissLoading()
                finishIntent()
            }

            override fun onError() {
                dismissLoading()
                Log.d("@@@", "post is not saved")
            }
        })
    }

    fun finishIntent() {
        val returnIntent = intent
        setResult(RESULT_OK, returnIntent)
        finish()
    }
}
