package com.simurgeducation.newlastsimurg

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.simurgeducation.newlastsimurg.databinding.ActivityMain2Binding
import com.simurgeducation.newlastsimurg.ui.UploadActivity
import com.squareup.picasso.Picasso

// VERİ TEKRARINI ÖNLEMEK AMACIYLA KULLANICNIN VERİLERİNİ BU EKRANDA BİR KERELİĞİNE ÇEKİYORUZ VE BU BİLGİ DEĞİŞTİRİLDİĞİNDE DEĞİŞENLE DEĞİŞİCEK
var sinavturu="KPSS"
var FBDersler="seçiniz"
var FBKonular="Sseçiniz"















class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding


    private lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    private lateinit var selfuuid: String


    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        binding.appBarMain.fab.setOnClickListener { view ->
            var intent=Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profil, R.id.nav_sorular, R.id.nav_takip,R.id.nav_siralama,R.id.nav_bildirim
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        selfuuid=auth.currentUser!!.uid

        db.collection("users").document(selfuuid).get().addOnSuccessListener { task->
            var FBusername= task.data?.get("username") as String
            var FBprofilphoto=task.data?.get("UserProfilePhoto") as String

            var usernametv=navView.findViewById<TextView>(R.id.username)
            var profilphotoIM=navView.findViewById<ImageView>(R.id.ProfilePhoto)

            usernametv.text=FBusername.toString()
            Picasso.get().load(FBprofilphoto).into(profilphotoIM)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity2, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}