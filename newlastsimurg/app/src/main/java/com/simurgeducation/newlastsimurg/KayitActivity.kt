package com.simurgeducation.newlastsimurg

import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.simurgeducation.newlastsimurg.databinding.ActivityKayitBinding


class KayitActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var  binding:ActivityKayitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kayit)

        binding= ActivityKayitBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
    }









fun KayitBtn(view: View){

    var username=binding.userNametv.text.toString()
    var email=binding.idtv.text.toString()
    var passaword=binding.sifretv.text.toString()


    //kayıt olmak istediği kullanıcı ile veri tabanın baska yoksa kayıt işlemini yapıyor
    fun kayitaşaması(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,passaword).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this@KayitActivity,"${auth.currentUser!!.email.toString()}",
                    Toast.LENGTH_LONG).show()
                Toast.makeText(this@KayitActivity,"basarılı", Toast.LENGTH_LONG).show()
                val users = db.collection("users")
                val data1 = hashMapOf(
                    "UserUID" to "${auth.currentUser!!.uid}",
                    "email" to "$email",
                    "username" to "$username",
                    "answer" to "0",
                    "question" to "0",
                    "score" to "0",
                    "UserProfilePhoto" to "https://firebasestorage.googleapis.com/v0/b/simurgeducation.appspot.com/o/icons%2Fprofile.png?alt=media&token=f1d5a8ea-19cf-430c-a645-22cbcaf3ff36"
                )
                users.document(auth.currentUser!!.uid).set(data1).addOnSuccessListener { task->
                    var intent= Intent(applicationContext,MainActivity2::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }.addOnFailureListener { exeption->
            Toast.makeText(applicationContext,exeption.toString(), Toast.LENGTH_LONG).show() }
    }


    if (username==""){
        Toast.makeText(applicationContext,"lütfen kullanıcı adı girin",Toast.LENGTH_LONG).show()
    }
    if (username==""){
        Toast.makeText(applicationContext,"lütfen email  girin",Toast.LENGTH_LONG).show()
    }
    if (username==""){
        Toast.makeText(applicationContext,"lütfen şifre girin",Toast.LENGTH_LONG).show()
    }

    else{
        //BURADA kullanıcın olup olmadıgını kontrol ediyor
        db.collection("users").whereEqualTo("username",username).get().addOnSuccessListener {task->
            println(task.size().toString())
            if (task.size()==0){
                kayitaşaması()
                println("kullanıcı yok")
            }
            if (0<task.size()){
                println("kullanıcı var")
                Toast.makeText(applicationContext,"Bu kullanıcı adından var",Toast.LENGTH_LONG).show()
            }
        }
    }
}






}


















