package com.simurgeducation.newlastsimurg.ui.siralama


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

import com.simurgeducation.newlastsimurg.databinding.FragmentSiralamaBinding



class SiralamaFragment : Fragment() {


    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var selfuuid:String


    var number=0


    var adapter:SiralamaRecylerAdapter?=null
    private var FBscorelist=ArrayList<String>()
    private var FBuserphotourllist=ArrayList<String>()
    private var FBusernamelist=ArrayList<String>()
    private var FBuseruuid=ArrayList<String>()




    private var _binding: FragmentSiralamaBinding? = null




    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSiralamaBinding.inflate(inflater)
        var root:View=binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        selfuuid=auth.currentUser!!.uid









        db.collection("siralama").orderBy("score",Query.Direction.DESCENDING).limit(10).get().addOnSuccessListener { task->
            FBuserphotourllist.clear()
            FBusernamelist.clear()
            FBuseruuid.clear()
            for (a in task){
                var UserUID=a["UserUID"] as String
                FBuseruuid.add(UserUID)
                adapter!!.notifyDataSetChanged()
            }
        }.addOnSuccessListener {
            //BURASI DİNAMİK USERNAME VE PHOTO ADRESİ kullanıcın adresinden kullanıcı adını ve profil fotoğrafını getirip ekrana yazdırıyor
            FBuserphotourllist.clear()
            FBusernamelist.clear()
            FBscorelist.clear()
            for (a in 0..number){
                db.collection("users").document(FBuseruuid[0]).get().addOnSuccessListener { task2->
                    var score=task2["score"] as String
                    var username=task2["username"] as String
                    var UserProfilePhoto=task2["UserProfilePhoto"] as String
                    FBscorelist.add(score)
                    FBuserphotourllist.add(UserProfilePhoto)
                    FBusernamelist.add(username)

                    adapter!!.notifyDataSetChanged()
                }
            }
        }





        val layoutManager=LinearLayoutManager(activity)
        binding.recylerview.layoutManager=layoutManager
        adapter=SiralamaRecylerAdapter(FBscorelist,FBuserphotourllist,FBusernamelist,FBuseruuid)

        binding.recylerview.adapter=adapter



    }















    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}