package com.simurgeducation.newlastsimurg.ui.siralama

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simurgeducation.newlastsimurg.databinding.RecylerSiralamaRowBinding
import com.squareup.picasso.Picasso

class SiralamaRecylerAdapter(
            private val FBscorelist:ArrayList<String>,
            private val FBuserphotourllist:ArrayList<String>,
            private val FBusernamelist:ArrayList<String>,
            private val FBuseruuid:ArrayList<String>,
):RecyclerView.Adapter<SiralamaRecylerAdapter.PostHolder>() {

    class PostHolder(val binding:RecylerSiralamaRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHolder {
        val binding=RecylerSiralamaRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
         Picasso.get().load(FBuserphotourllist[position]).into(holder.binding.ProfileImageView)
         holder.binding.username.text=FBusernamelist.get(position)
         holder.binding.score.text=FBscorelist.get(position)
    }

    override fun getItemCount(): Int {
        return FBusernamelist.size
    }


}