package com.simurgeducation.newlastsimurg


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.simurgeducation.newlastsimurg.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){



    lateinit var sharedPreferences:SharedPreferences







    private  lateinit var binding:ActivityMainBinding// burada hangi aktivite ise o bindin adı alınıcak

    private lateinit var  auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences=getSharedPreferences("com.simurgeducation.newlastsimurg", Context.MODE_PRIVATE)

        auth= FirebaseAuth.getInstance()
        binding= ActivityMainBinding.inflate(layoutInflater)
        db=Firebase.firestore
        val  view=binding.root
        setContentView(view)

        var id=sharedPreferences.getString("emailadres",null)
        var sifre=sharedPreferences.getString("sifre",null)


        if(id!=null){
            binding.idtv.setText(id.toString())
        }
        if (sifre!=null){
            binding.sifretv.setText(sifre.toString())
        }





    }





    fun girisbtn(view: View){
            var id=binding.idtv.text.toString()
            var pass=binding.sifretv.text.toString()
            auth.signInWithEmailAndPassword(id,pass).addOnCompleteListener { task->

                if (task.isSuccessful){
                    if (binding.checkBox.isChecked){
                        //kayit yeri
                        var emailadres=binding.idtv.text.toString()
                        var şifre=binding.sifretv.text.toString()
                        val prefences = getSharedPreferences("com.simurgeducation.newlastsimurg", Context.MODE_PRIVATE)
                        prefences.edit().putString("emailadres",emailadres).apply()
                        prefences.edit().putString("sifre",şifre).apply()
                        val intent= Intent(applicationContext,MainActivity2::class.java)
                        startActivity(intent)
                        finish()
                    }

                    if (!binding.checkBox.isChecked){
                        val prefences = getSharedPreferences("com.simurgeducation.newlastsimurg", Context.MODE_PRIVATE)
                        prefences.edit().remove("emailadres").apply()
                        prefences.edit().remove("sifre").apply()
                        val intent= Intent(applicationContext,MainActivity2::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }.addOnFailureListener { exception->
                Toast.makeText(this,exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            }
    }




    fun kayitbtn(view: View){
        val intent= Intent(applicationContext,KayitActivity::class.java)
        startActivity(intent)
    }










}


