package com.simurgeducation.newlastsimurg.ui.sorular


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.navigation.Navigation

import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.simurgeducation.newlastsimurg.*

import com.simurgeducation.newlastsimurg.databinding.RecylerViewRowBinding
import com.simurgeducation.newlastsimurg.profileActivity.ProfileActivity

import com.squareup.picasso.Picasso

class SorularRecrylerAdapter(
    private val userNameFromFB: HashMap<Int, String>,
    private val userCommentFromFB: HashMap<Int, String>,
    private val questionphotourlFromFB: HashMap<Int, String>,
    private val positionhasmap: HashMap<Int, String>,
    private val userUID: HashMap<Int, String>,
    private val activity: Context?,
    private val answercheck: HashMap<Int, Boolean>,
    private val view: View,
    private val selfuuid: String,
    private val db: FirebaseFirestore,
    private val FollowQuestionArrayList: HashMap<Int, List<String>>,
    private val photohashmap: HashMap<Int, String>


) :RecyclerView.Adapter<SorularRecrylerAdapter.PostHolder>() {

    class PostHolder(val binding :RecylerViewRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
       val binding=RecylerViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }
    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.profiletexttv?.text =userNameFromFB.get(position)
        Picasso.get()?.load(questionphotourlFromFB[position])?.into(holder.binding.recrylerImageView)


        println("*********************************")
        println(userNameFromFB[position])
        println(userCommentFromFB[position])
        println(questionphotourlFromFB[position])
        println(positionhasmap[position])
        println(userUID[position])
        println(answercheck[position])
        println(selfuuid)
        println(FollowQuestionArrayList[position])
        println(photohashmap[position])
        println("*********************************")






        if (answercheck[position]==true){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/simurgeducation.appspot.com/o/icons%2Fcevap.jpg?alt=media&token=c41d396d-044d-448a-88f9-fe454116b8cb").into(holder.binding.questionchecktv)
        }
        if(answercheck[position]==false){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/simurgeducation.appspot.com/o/icons%2Fckechk.png?alt=media&token=cb656529-b49b-4bfc-98e8-166322cd5ccf").into(holder.binding.questionchecktv)
        }

        if (FollowQuestionArrayList[position]?.contains(selfuuid) == true){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/simurgeducation.appspot.com/o/icons%2Fheart.png?alt=media&token=c754dd46-b55d-4cc0-aa5c-c80ba7aefa05").into(holder.binding.followquestionbtn)
            println("içerisinde yer alıyor ${FollowQuestionArrayList[position].toString()} ${userCommentFromFB[position].toString()}")
        }

        if (FollowQuestionArrayList[position]?.contains(selfuuid)==false){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/simurgeducation.appspot.com/o/icons%2Fheart-outline.png?alt=media&token=09da00ef-1cd4-457d-a070-d993b8023383").into(holder.binding.followquestionbtn)
        }
        Picasso.get().load(photohashmap[position]).into(holder.binding.ProfileImageView)




        holder.binding.recrylerImageView.setOnClickListener{
            activity?.let {
                try {
                    val intent= Intent(it,CevaplamaActivity::class.java)
                    intent.putExtra("questionurl",positionhasmap[position].toString())
                    intent.putExtra("answercheck",answercheck[position])
                    it.startActivity(intent)
                }catch (e:Exception){
                    println(e.toString())
                }
            }

        }

        holder.binding.profiletexttv.setOnClickListener{
            activity?.let {
                if (selfuuid==userUID[position]){
                    var bundle=Bundle()
                    bundle.putString("intentuuid",userUID[position].toString())
                    println("aaaaaaaaaaaaa"+userUID[position])
                    Navigation.findNavController(view).navigate(R.id.action_nav_sorular_to_nav_profil,bundle)
                }
                else{
                    val intent=Intent(it,ProfileActivity::class.java)
                    intent.putExtra("intentuuid",userUID[position].toString())
                    println("bbbbbbbbbbbb"+userUID[position].toString())
                    it.startActivity(intent)
                }

            }
        }


        holder.binding.followquestionbtn.setOnClickListener {
            if (FollowQuestionArrayList[position]!!.contains(selfuuid)){
                var followhasmap=HashMap<String,Any>()
                followhasmap.put("sinavturu","KPSS")
                followhasmap.put("FBDersler",FBDersler)
                followhasmap.put("FBKonular",FBKonular)
                followhasmap.put("QuestionUİD",positionhasmap[position].toString())
                db.collection("users").document(selfuuid).collection("followquestion").document(positionhasmap[position].toString()).delete()
                db.collection(sinavturu).document(FBDersler).collection(FBKonular).document(positionhasmap[position].toString()).update("FollowQuestionArrayList",FieldValue.arrayRemove(selfuuid))
            }

            if (!FollowQuestionArrayList[position]!!.contains(selfuuid)){
                var followhasmap=HashMap<String,Any>()
                followhasmap.put("sinavturu","KPSS")
                followhasmap.put("FBDersler",FBDersler)
                followhasmap.put("FBKonular",FBKonular)
                followhasmap.put("QuestionUİD",positionhasmap[position].toString())
                followhasmap.put("date", Timestamp.now())
                followhasmap.put("UserUID",userUID[position].toString())
                db.collection("users").document(selfuuid).collection("followquestion").document(positionhasmap[position].toString()).set(followhasmap)
                db.collection(sinavturu).document(FBDersler).collection(FBKonular).document(positionhasmap[position].toString()).update("FollowQuestionArrayList",FieldValue.arrayUnion(selfuuid.toString()))
            }
        }


    }
    override fun getItemCount(): Int {
        return userNameFromFB.size
    }
}
