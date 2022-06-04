package com.simurgeducation.newlastsimurg.ui.takipedilenler

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.simurgeducation.newlastsimurg.CevaplamaActivity
import com.simurgeducation.newlastsimurg.FBDersler
import com.simurgeducation.newlastsimurg.FBKonular
import com.simurgeducation.newlastsimurg.databinding.FragmentTakipBinding
import com.simurgeducation.newlastsimurg.sinavturu


class TakipFragment:Fragment(),TakipEdilenSoruRecylerAdapter.OnItemClickListener {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var selfuuid:String

    private var _binding: FragmentTakipBinding? = null

    private val binding get() = _binding!!



    var adapter:TakipEdilenSoruRecylerAdapter?=null



    var a1hliste=HashMap<Int,String>()
    var a2hliste=HashMap<Int,String>()
    var a3hliste=HashMap<Int,String>()
    var a4hliste=HashMap<Int,String>()




    var usernamelist=HashMap<Int,String>()
    var ProfileSınavTürü=HashMap<Int,Any>()
    var ProfileFBDersler=HashMap<Int,Any>()
    var ProfileKonular=HashMap<Int,Any>()
    var ProfileQuestionUİD=HashMap<Int,Any>()
    var photoUrlList:HashMap<Int,String> = HashMap()
    var answercheckmap=HashMap<Int,Boolean>()
    var FollowQuestionArrayList:HashMap<Int,List<String>> =HashMap()
    var userphotomap:HashMap<Int,String> = HashMap()
    var useruuidlist:HashMap<Int,String> = HashMap()





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentTakipBinding.inflate(inflater)
        var view=binding.root
        db=Firebase.firestore
        auth= FirebaseAuth.getInstance()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        

        fun cekim2(){
            for (c in 0..useruuidlist.size-1){
                db.collection("users").document(useruuidlist[c].toString()).addSnapshotListener { usernametask,e->
                    var usernames=usernametask!!.get("username") as String
                    var userphoto=usernametask!!.get("UserProfilePhoto") as String
                    usernamelist.put(c,usernames)
                    userphotomap.put(c,userphoto)
                    println("gelen kullanıcı adı budur kontrol 1 "+usernames)
                    adapter!!.notifyDataSetChanged()
                }
            }
        }

         selfuuid=auth.currentUser!!.uid.toString()



        a1hliste.clear()
        a2hliste.clear()
        a3hliste.clear()
        a4hliste.clear()

        db.collection("users").document(selfuuid).collection("followquestion").orderBy("date",Query.Direction.DESCENDING).get().addOnSuccessListener { task->
            var a=0
            for (documents in task){
                var dersler:String?=null
                var konular:String?=null
                var sinavturuu:String?=null
                var QuestionUİD:String?=null


                sinavturuu=documents["sinavturu"] as String
                dersler=documents["FBDersler"] as String
                konular=documents["FBKonular"] as String
                QuestionUİD=documents["QuestionUİD"] as String

                println("gelen veriler bunlardur ${sinavturuu.toString()} $dersler $konular $QuestionUİD")


                a1hliste.put(a,sinavturuu.toString())
                a2hliste.put(a,dersler.toString())
                a3hliste.put(a,konular.toString())
                a4hliste.put(a,QuestionUİD.toString())
                a+=1
            }
        }.addOnSuccessListener {


            usernamelist.clear()
            ProfileSınavTürü.clear()
            ProfileFBDersler.clear()
            ProfileKonular.clear()
            ProfileQuestionUİD.clear()
            photoUrlList.clear()
            answercheckmap.clear()
            FollowQuestionArrayList.clear()
            var Anumber=0
            var sayi2=0
            for (b in 0..a4hliste.size-1){
                var g1=a1hliste.getValue(b).toString()
                var g2=a2hliste.getValue(b).toString()
                var g3=a3hliste.getValue(b).toString()
                var g4=a4hliste.getValue(b).toString()
                db.collection(g1).document(g2).collection(g3).document(g4).get().addOnSuccessListener { value->
                    val Aphotourl=value!!.get("questionphotourl") as String
                    val Asinavtürü=value!!.get("sinavturu") as String
                    val Akonular=value!!.get("FBKonular") as String
                    val Adersler=value!!.get("FBDersler") as String
                    val Adocid=value!!.get("QuestionUİD") as String
                    val Aanswercheck=value!!.get("answercheck") as Boolean
                    var uuid=value!!.get("UserUID") as String
                    var liste: List<String> =value?.get("FollowQuestionArrayList") as List<String>


                    useruuidlist.put(Anumber,uuid)
                    ProfileSınavTürü.put(Anumber,Asinavtürü)
                    ProfileFBDersler.put(Anumber,Adersler)
                    ProfileKonular.put(Anumber,Akonular)
                    FollowQuestionArrayList.put(Anumber,liste)
                    ProfileQuestionUİD.put(Anumber,Adocid)
                    photoUrlList.put(Anumber,Aphotourl)
                    answercheckmap.put(Anumber,Aanswercheck)
                    Anumber+=1


                    println("sayi kotnrol 1")
                    println(a4hliste.size-1)
                    println(b)
                    println(ProfileQuestionUİD[b].toString())
                    if (a4hliste.size-1==b){
                        cekim2()
                    }

                    if (liste.contains(selfuuid)){
                        println("içerisinde var")
                    }
                }

            }

        }

        val layoutManager= LinearLayoutManager(activity)
        binding.recylerview.layoutManager=layoutManager
        adapter=TakipEdilenSoruRecylerAdapter(usernamelist,ProfileSınavTürü,ProfileFBDersler,ProfileKonular,ProfileQuestionUİD,photoUrlList,answercheckmap,this,FollowQuestionArrayList,selfuuid,userphotomap)
        binding.recylerview.adapter=adapter
        adapter!!.notifyDataSetChanged()


    }


















    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onItemClick(a:String,b:String,c:String,soruuuid: String) {
        sinavturu =a
        FBDersler =b
        FBKonular =c
        var intent= Intent(activity, CevaplamaActivity::class.java)
        intent.putExtra("questionurl",soruuuid)
        startActivity(intent)
    }





}