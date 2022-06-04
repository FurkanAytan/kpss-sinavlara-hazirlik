package com.simurgeducation.newlastsimurg.takip_takipciActivity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.simurgeducation.newlastsimurg.R
import com.simurgeducation.newlastsimurg.cevaplama.CevaplarRecylerAdapter
import com.simurgeducation.newlastsimurg.databinding.ActivityTakipTakipciBinding



class Takip_TakipciActivity : AppCompatActivity(),Takip_TakipciActivityRecylerAdapter.OnItemClickListener {


    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    private lateinit var binding:ActivityTakipTakipciBinding


    private  var FBUseruuidMap:HashMap<Int,String> = HashMap()
    private  var FBusernameMap:HashMap<Int,String> = HashMap()
    private  var FBuserphotoMap:HashMap<Int,String> = HashMap()

    private var intentuserkontrol: Boolean = false


    private lateinit var intentuuid:String
    private lateinit var selfuuid:String
    private lateinit var intentsecim:String



    var adapter:Takip_TakipciActivityRecylerAdapter?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_takip_takipci)
        binding=ActivityTakipTakipciBinding.inflate(layoutInflater)

        val view=binding.root
        setContentView(view)

        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        var Intent=intent

        intentuuid = Intent.getStringExtra("intentuuid").toString()
        intentsecim= Intent.getStringExtra("intentsecim").toString()
        intentuserkontrol = Intent.getBooleanExtra("intentuserkontrol",false)

        println(intentuserkontrol.toString() + "intent kontrolü")

        println(intentuuid+" "+intentsecim)

        selfuuid=auth.currentUser!!.uid

        getdocument()





        val layoutManager=LinearLayoutManager(this)
        binding.recylerview.layoutManager=layoutManager
        adapter=Takip_TakipciActivityRecylerAdapter(FBusernameMap,FBuserphotoMap,intentuserkontrol,intentsecim,this)
        binding.recylerview.adapter=adapter
    }


    //FBden uudi bilgisi ile username ve fotoğraf url'sinin getirildiği alan
    fun getusername(){
        FBusernameMap.clear()
        FBuserphotoMap.clear()
        var number2=0
        var size=FBUseruuidMap.size-1
        for (a in 0..size){
            var isim=FBUseruuidMap.getValue(a).toString()
            db.collection("users").document(isim).addSnapshotListener{task2,e->
                var username=task2!!.get("username") as String
                var userphoto=task2!!.get("UserProfilePhoto") as String
                FBusernameMap.put(number2,username)
                FBuserphotoMap.put(number2,userphoto)
                number2+=1
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    fun getdocument(){
            FBUseruuidMap.clear()
            FBusernameMap.clear()
            FBuserphotoMap.clear()
            if (intentsecim=="takip"){
                db.collection("users").document(intentuuid).collection("takip").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener{ task, e->
                    var veri=task?.documents
                    var size=veri?.size
                    if (veri != null) {
                        var caunt=0
                        for (a in veri){
                            var FBfollowUserUID=a.get("UserUID") as String
                            FBUseruuidMap.put(caunt,FBfollowUserUID)
                            caunt+=1
                            if (size==caunt){
                                getusername()
                            }
                        }
                    }


                }
            }

            if (intentsecim=="takipci"){
                db.collection("users").document(intentuuid).collection("takipçi").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener{ task, e->
                    var veri=task?.documents
                    var size=veri?.size
                    if (veri != null) {
                        var caunt=0
                        for (a in veri){
                            var FBfollowUserUID=a.get("UserUID") as String
                            FBUseruuidMap.put(caunt,FBfollowUserUID)
                            caunt+=1
                            if (size==caunt){
                                getusername()
                            }
                        }
                    }


                }
            }





    }

    override fun onItemClick(adapterposition:Int) {
        if(intentsecim=="takipci"){
            db.collection("users").document(FBUseruuidMap[adapterposition].toString()).collection("takip").document(selfuuid).delete()
            db.collection("users").document(selfuuid).collection("takipçi").document(FBUseruuidMap[adapterposition].toString()).delete()
            println(FBUseruuidMap[adapterposition].toString())
            getdocument()
        }

        if (intentsecim=="takip"){
            db.collection("users").document(selfuuid).collection("takip").document(FBUseruuidMap[adapterposition].toString()).delete()
            db.collection("users").document(FBUseruuidMap[adapterposition].toString()).collection("takipçi").document(selfuuid).delete()
            getdocument()
        }
        getdocument()
        adapter?.notifyDataSetChanged()

    }


}