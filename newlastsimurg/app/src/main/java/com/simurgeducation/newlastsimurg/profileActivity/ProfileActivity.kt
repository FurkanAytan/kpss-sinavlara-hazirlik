package com.simurgeducation.newlastsimurg.profileActivity

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.simurgeducation.newlastsimurg.*


import com.simurgeducation.newlastsimurg.databinding.FragmentProfilBinding
import com.simurgeducation.newlastsimurg.takip_takipciActivity.Takip_TakipciActivity
import com.squareup.picasso.Picasso



class ProfileActivity:AppCompatActivity(),ProfilActivityRecylerAdapter.OnItemClickListener {


    var adapter: ProfilActivityRecylerAdapter?=null


    private lateinit var intentDBusername:String //intent ile gelen uudinin db den nicname'ini buluyor





    private lateinit var questioncount:String
    private lateinit  var answercount:String
    private lateinit  var score:String
    private lateinit var selfuuid:String


    private  var followcheckh : Boolean = false


    var ProfileSınavTürü:HashMap<Int,Any> = HashMap()
    var ProfileFBDersler:HashMap<Int,Any> = HashMap()
    var ProfileKonular:HashMap<Int,Any> = HashMap()
    var ProfileQuestionUİD:HashMap<Int,Any> = HashMap()
    var answercheckmap:HashMap<Int,Boolean> = HashMap()
    var photoUrlList:HashMap<Int,String> = HashMap()






    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth



    private lateinit var binding: FragmentProfilBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profil)
        binding= FragmentProfilBinding.inflate(layoutInflater)

        val view=binding.root
        setContentView(view)

        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()


        var intent=intent
        var intentuuid=intent.getStringExtra("intentuuid") as String

        println("aaaa23232" +intentuuid.toString())

        selfuuid=auth.currentUser!!.uid



        db.collection("users").document(intentuuid).get().addOnSuccessListener { task->
            intentDBusername=task.data!!.get("username") as String
            var userphotourl=task.data!!.get("UserProfilePhoto") as String
            Picasso.get().load(userphotourl).into(binding.ProfileImageView)
            questioncount=task.data!!.get("question") as String
            answercount=task.data!!.get("answer") as String
            score=task.data!!.get("score") as String
            binding.QuestionTv.text="Soru\n"+questioncount
            binding.AnswerTv.text="Cevap\n"+answercount
            binding.ScoreTv.text="Puan\n"+score
            binding.UserNametv.text=intentDBusername
        }.addOnSuccessListener {
            val layoutManager=LinearLayoutManager(this)
            binding.recylerview.layoutManager=layoutManager
            adapter= ProfilActivityRecylerAdapter(intentDBusername,ProfileSınavTürü,ProfileFBDersler,ProfileKonular,ProfileQuestionUİD,photoUrlList,answercheckmap,this)
            binding.recylerview.adapter=adapter
            adapter!!.notifyDataSetChanged()
            binding.AllAskedBtn.callOnClick()
        }


        //takip edip etmeme bilgis burda alınıyor
        db.collection("users").document(selfuuid).collection("takip").whereEqualTo("UserUID",intentuuid).addSnapshotListener {task,e->
            if (task?.isEmpty == true){
                followcheckh=false
                println("takip yok")
                binding.takipetbtn.text="takip et"
            }
            else{
                println("takip var")
                binding.takipetbtn.text="takipten çık"
                followcheckh=true

            }
        }



        db.collection("users").document(intentuuid).collection("takipçi").addSnapshotListener{task,e->
            binding.takipci.text="takipçi \n"+task?.documents?.size.toString()
        }
        db.collection("users").document(intentuuid).collection("takip").addSnapshotListener{task,e->
            binding.takip.text="takip \n"+task?.documents?.size.toString()
        }










        binding.AllAskedBtn.setOnClickListener {
           //cağrılan fonksiyon profilefragment'in içinde
           allqestiongetActivity(db,intentuuid,ProfileSınavTürü,ProfileFBDersler,ProfileKonular,ProfileQuestionUİD,photoUrlList,answercheckmap,adapter!!)
       }

        binding.AnsweredBtn.setOnClickListener {
            //cağrılan fonksiyon profilefragment'in içinde
            truequestionsgetActivity(db,intentuuid,ProfileSınavTürü,ProfileFBDersler,ProfileKonular,ProfileQuestionUİD,photoUrlList,answercheckmap,adapter!!)
        }
        binding.WaitAnswerBtn.setOnClickListener {
            //cağrılan fonksiyon profilefragment'in içinde
            notansweredActivity(db,intentuuid,ProfileSınavTürü,ProfileFBDersler,ProfileKonular,ProfileQuestionUİD,photoUrlList,answercheckmap,adapter!!)
        }





       binding.takipetbtn.setOnClickListener {
           //takip etmiyorsa takip ediyor
           if (followcheckh==false){
               val updatehashmap= hashMapOf<String,Any>()
               val updatehashmap2= hashMapOf<String,Any>()

               updatehashmap.put("date",com.google.firebase.Timestamp.now())
               updatehashmap.put("UserUID",selfuuid)

               updatehashmap2.put("date",com.google.firebase.Timestamp.now())
               updatehashmap2.put("UserUID",intentuuid)

               db.collection("users").document(selfuuid).collection("takip").document(intentuuid).set(updatehashmap2)
               db.collection("users").document(intentuuid).collection("takipçi").document(selfuuid).set(updatehashmap)
                println("takip tusuna basuldıgındaki kontrol $intentuuid")
           }
            //takip ediyorsa takipten cıkıyor
           if (followcheckh==true){
               db.collection("users").document(selfuuid).collection("takip").document(intentuuid).delete()
               db.collection("users").document(intentuuid).collection("takipçi").document(selfuuid).delete()
           }
       }





        binding.takip.setOnClickListener {
            var intent=Intent(applicationContext, Takip_TakipciActivity::class.java)
            intent.putExtra("intentuuid",intentuuid)
            intent.putExtra("intentsecim","takip")
            intent.putExtra("intentuserkontrol",false)
            startActivity(intent)

        }
        binding.takipci.setOnClickListener {
            var intent=Intent(applicationContext, Takip_TakipciActivity::class.java)
            intent.putExtra("intentuuid",intentuuid)
            intent.putExtra("intentsecim","takipci")
            intent.putExtra("intentuserkontrol",false)
            startActivity(intent)
        }


    }






    override fun onItemClick(a: String, b: String, c: String, soruuuid: String) {
        sinavturu =a
        FBDersler =b
        FBKonular =c
        var intent=Intent(applicationContext, CevaplamaActivity::class.java)
        intent.putExtra("questionurl",soruuuid)
        startActivity(intent)
    }


}