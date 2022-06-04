package com.simurgeducation.newlastsimurg.ui.profil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simurgeducation.newlastsimurg.databinding.RecylerViewRowBinding
import com.squareup.picasso.Picasso
import kotlin.collections.HashMap

class ProfilRecylerAdapter(
    private val usernamelist:String,
    private val ProfileSınavTürü: HashMap<Int, Any>,
    private val ProfileFBDersler: HashMap<Int, Any>,
    private val ProfileKonular: HashMap<Int, Any>,
    private val ProfileQuestionUİD: HashMap<Int, Any>,
    private val photoUrlList: HashMap<Int, String>,
    private val answercheckmap: HashMap<Int, Boolean>,
    private val listener: OnItemClickListener,
    ):RecyclerView.Adapter<ProfilRecylerAdapter.PostHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder {
        val binding=RecylerViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)

    }

    override fun onBindViewHolder(holder: ProfilRecylerAdapter.PostHolder, position: Int) {
        holder.binding.profiletexttv.text=usernamelist.toString()
        Picasso.get().load(photoUrlList[position]).into(holder.binding.recrylerImageView)

        if (answercheckmap[position]==true){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/simurgeducation.appspot.com/o/icons%2Fcevap.jpg?alt=media&token=c41d396d-044d-448a-88f9-fe454116b8cb").into(holder.binding.questionchecktv)
        }
        if(answercheckmap[position]==false){
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/simurgeducation.appspot.com/o/icons%2Fckechk.png?alt=media&token=cb656529-b49b-4bfc-98e8-166322cd5ccf").into(holder.binding.questionchecktv)
        }

    }


    override fun getItemCount(): Int {
        return ProfileQuestionUİD.count()
    }


    inner class PostHolder(val binding:RecylerViewRowBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener {
        var username:TextView?=null
        var photoquestion:ImageView?=null


        init {

            photoquestion=binding.recrylerImageView
            username=binding.profiletexttv
            binding.recrylerImageView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
           when(p0){
               binding.recrylerImageView->{
                   var a=ProfileSınavTürü[position].toString()
                   var b=ProfileFBDersler[position].toString()
                   var c=ProfileKonular[position].toString()
                   var soruuuid=ProfileQuestionUİD[position].toString()



                   listener.onItemClick(a,b,c,soruuuid)
               }
           }
            }
        }


    interface OnItemClickListener{
        fun onItemClick(a:String,b:String,c:String,soruuuid: String)

    }

}