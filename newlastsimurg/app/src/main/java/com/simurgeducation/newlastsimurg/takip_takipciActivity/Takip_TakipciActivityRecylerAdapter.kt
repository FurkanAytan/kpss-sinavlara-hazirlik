package com.simurgeducation.newlastsimurg.takip_takipciActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simurgeducation.newlastsimurg.databinding.RecylerTakipTakipciBinding
import com.squareup.picasso.Picasso

class Takip_TakipciActivityRecylerAdapter(
    private val FBusernameMap: HashMap<Int, String>,
    private val FBuserphotoMap: HashMap<Int, String>,
    private val userkontrol: Boolean,
    private val intentsecim: String,
    private val listener:OnItemClickListener,


    ):RecyclerView.Adapter<Takip_TakipciActivityRecylerAdapter.PostHolder>() {

//  class PostHolder(val binding:RecylerTakipTakipciBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder {
        val binding=RecylerTakipTakipciBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PostHolder,
        position: Int
    ) {
        holder.binding.username.text=FBusernameMap.get(position)
        Picasso.get().load(FBuserphotoMap.getValue(position).toString()).into(holder.binding.ProfileImageView)

        if(userkontrol==false){
            holder.takipbtn!!.visibility=View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return FBusernameMap.size
    }

    inner class PostHolder(val binding:RecylerTakipTakipciBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{

        var username:TextView?=null
        var takipbtn:Button?=null
        init {
            username = binding.username
            takipbtn=binding.takip
            visibility()

            binding.takip.setOnClickListener(this)
        }

        fun visibility(){
            if (userkontrol==true){
                binding.takip.visibility==View.VISIBLE
                if (intentsecim=="takip"){
                    takipbtn!!.text ="takipten çık"
                }
                if (intentsecim=="takipci"){
                    takipbtn!!.text="takipten çıkar"
                }
            }
            if (userkontrol==false){
                takipbtn!!.visibility==View.INVISIBLE
            }

        }

        override fun onClick(p0: View?) {
            when(p0){
                binding.takip->{
                    var position:Int=adapterPosition
                    println("basılan pozizyon burası $position")
                    listener.onItemClick(position)
                }
            }
        }


    }

    interface OnItemClickListener{
        fun onItemClick(adapterposition:Int)
    }




}