package com.simurgeducation.newlastsimurg.cevaplama


import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.simurgeducation.newlastsimurg.databinding.RecylerCevaplamaRowBinding
import com.squareup.picasso.Picasso


class CevaplarRecylerAdapter(
    private val userNameFromFB: HashMap<Int, String>,
    private val commentList: HashMap<Int, String>,
    private val commenthashmap: HashMap<Int, Boolean>,
    private val answercheck: Boolean,
    private val listener: OnItemClickListener,
    private val commenturl: HashMap<Int, String>,
    private val answercomentuuddihasmap: HashMap<Int, String>,
    private val comentphotoHashmap: HashMap<Int, String>,
    private val questioncommentphotomap: HashMap<Int, String>
):RecyclerView.Adapter<CevaplarRecylerAdapter.PostHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding=RecylerCevaplamaRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.usernametexttvFB?.text=userNameFromFB?.get(position)
        holder.binding.commenttexttvFB?.text=commentList?.get(position)
        if (answercheck){
            holder.binding.Rightimage.visibility=View.GONE
            if (commenthashmap[position]==true){
                holder.binding.checkTv.visibility=View.VISIBLE
            }
        }
        if (answercheck==false){
            if (commenthashmap[position]==true){
                holder.binding.Rightimage.visibility=View.GONE
                holder.binding.checkTv.visibility=View.VISIBLE
            }
            if (commenthashmap[position]==false){
                holder.binding.Rightimage.visibility=View.VISIBLE
            }
        }

        Picasso.get().load(comentphotoHashmap[position]).into(holder.binding.ProfileImageView)

        if (questioncommentphotomap[position]!=null){

         //   Picasso.get().load(questioncommentphotomap[position]).into(holder.binding.imageView4)
        }
        if (questioncommentphotomap[position]==null){
            holder.binding.imageView4.visibility=View.GONE
        }
    }


    override fun getItemCount(): Int {
        return commenturl.size
    }

    inner class PostHolder(val binding:RecylerCevaplamaRowBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{
        var RightimageTV:ImageView?=null
        var check:TextView?=null
        var commentphoto:ImageView?=null

        init {
            RightimageTV=binding.Rightimage
            commentphoto=binding.imageView4
            check=binding.checkTv
            binding.Rightimage.setOnClickListener(this)
            binding.imageView4.setOnClickListener(this)
            visibility()
        }

        fun visibility(){
        }
        override fun onClick(p0: View?) {
           when(p0){
               binding.Rightimage->{
                   if (position!=RecyclerView.NO_POSITION){
                       var veri=commenturl[position].toString()
                       var commentuudi=answercomentuuddihasmap[position].toString()
                       listener.onItemClick(veri,commentuudi)
                   }
               }
               binding.imageView4->{
                   if (position!=RecyclerView.NO_POSITION){
                       var intentphotourl=questioncommentphotomap[position].toString()
                       listener.photoclick(intentphotourl)
                   }
               }
           }
        }




    }

    interface OnItemClickListener{
        fun onItemClick(commenturl: String,commentuudi:String)
        fun photoclick(intentphotourl: String)
    }


    }






