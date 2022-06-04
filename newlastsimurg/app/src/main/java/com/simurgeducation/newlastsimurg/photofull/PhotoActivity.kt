package com.simurgeducation.newlastsimurg.photofull

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simurgeducation.newlastsimurg.R
import com.simurgeducation.newlastsimurg.databinding.ActivityPhotoBinding
import com.squareup.picasso.Picasso

class PhotoActivity : AppCompatActivity() {



    private lateinit var binding:ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        binding= ActivityPhotoBinding.inflate(layoutInflater)
        var view=binding.root
        var intent=intent
        setContentView(view)
       var intentphotourl=intent?.getStringExtra("intentphotourl") as String ?



        if (intentphotourl!=null && !intentphotourl.isEmpty()){
            println("gelen intent $intentphotourl")
            Picasso.get().load(intentphotourl).into(binding.imageView)
        }

    }




}