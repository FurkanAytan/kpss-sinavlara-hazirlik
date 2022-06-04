package com.simurgeducation.newlastsimurg.ui.bildirim

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.AdapterView
import android.widget.ArrayAdapter

import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.simurgeducation.newlastsimurg.CevaplamaActivity
import com.simurgeducation.newlastsimurg.FBDersler
import com.simurgeducation.newlastsimurg.FBKonular
import com.simurgeducation.newlastsimurg.databinding.FragmentBildirimBinding
import com.simurgeducation.newlastsimurg.sinavturu

class BildirimFragment : Fragment() {


    private var _binding: FragmentBildirimBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var selfuuid:String

    private  var FBbildirimList=ArrayList<String>()
    private  var bidlirimmessage:String?=null

    private var FBsinavtürüList=ArrayList<String>()
    private var FBderslerList=ArrayList<String>()
    private var FBkonularList=ArrayList<String>()
    private  var FBQuestionUİDList=ArrayList<String>()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


     //   _binding = FragmentBildirimBinding.inflate(inflater, container, false)
        _binding = FragmentBildirimBinding.inflate(inflater)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        selfuuid=auth.currentUser!!.uid



        var adapter: ArrayAdapter<String>? =activity?.let { ArrayAdapter(it,android.R.layout.simple_list_item_1,FBbildirimList) }



        db.collection("users").document(selfuuid.toString()).collection("bildirim").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener{snapshot,e->
            FBbildirimList.clear()
            FBQuestionUİDList.clear()
            FBsinavtürüList.clear()
            FBderslerList.clear()
            FBkonularList.clear()
            var document=snapshot?.documents
            if (document != null) {
                for (a in document){

                    FBsinavtürüList.add(a.data?.get("sinavturu") as String )
                    FBderslerList.add(a.data?.get("FBDersler") as String )
                    FBkonularList.add(a.data?.get("FBKonular") as String )



                    bidlirimmessage=a.data?.get("bildirim") as String
                    FBQuestionUİDList.add(a.data?.get("QuestionUİD") as String )
                  FBbildirimList.add(bidlirimmessage!!)
                    binding.listView.adapter=adapter
                    adapter!!.notifyDataSetChanged()
                }
            }
        }


        binding.listView.onItemClickListener=AdapterView.OnItemClickListener { adapterView, view, i, l ->
            sinavturu=FBsinavtürüList[l.toInt()].toString()
            FBDersler=FBderslerList[l.toInt()].toString()
            FBKonular=FBkonularList[l.toInt()].toString()

            println(FBQuestionUİDList[l.toInt()].toString())
            println(FBbildirimList[l.toInt()].toString())
            var intent=Intent(activity,CevaplamaActivity::class.java)
            intent.putExtra("questionurl",FBQuestionUİDList[l.toInt()].toString())
            println(FBbildirimList[l.toInt()])
            startActivity(intent)
        }





    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}