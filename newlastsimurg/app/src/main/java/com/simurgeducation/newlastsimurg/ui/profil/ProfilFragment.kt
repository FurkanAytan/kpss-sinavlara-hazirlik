package com.simurgeducation.newlastsimurg.ui.profil


import android.app.Activity.RESULT_OK


import android.content.Intent

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

import com.simurgeducation.newlastsimurg.*
import com.simurgeducation.newlastsimurg.databinding.FragmentProfilBinding
import com.simurgeducation.newlastsimurg.takip_takipciActivity.Takip_TakipciActivity
import com.squareup.picasso.Picasso


import kotlin.collections.HashMap


class ProfilFragment : Fragment(),ProfilRecylerAdapter.OnItemClickListener {












    private val pickImage = 100
    private var imageUri: Uri? = null


    private lateinit var intentuuid:String//bu diğer aktivitelerden buraya uuid yolluyor profil sahibimi yoksa baska bir kullanımı ayırt etmeye yarıyor

    var userphotohash:HashMap<String,Any> = HashMap()








    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var uuid: String



    private lateinit var username:String
    private lateinit var userprofilephoto:String


    private lateinit var questioncount:String
    private lateinit var answercount:String
    private lateinit var score:String





    var adapter:ProfilRecylerAdapter?=null








    var ProfileSınavTürü:HashMap<Int,Any> = HashMap()
    var ProfileFBDersler:HashMap<Int,Any> = HashMap()
    var ProfileKonular:HashMap<Int,Any> = HashMap()
    var ProfileQuestionUİD:HashMap<Int,Any> = HashMap()
    var answercheckmap:HashMap<Int,Boolean> = HashMap()
    var photoUrlList:HashMap<Int,String> = HashMap()







    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        intentuuid=arguments?.getString("intentuuid") as String
        _binding = FragmentProfilBinding.inflate(inflater)
        var root: View = binding.root
        auth= FirebaseAuth.getInstance()

        println("gelen inten uuid "+intentuuid)

       binding.takipetbtn.visibility=View.INVISIBLE

        println("burda profil 1")
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("burda profil 2")
        db= FirebaseFirestore.getInstance()
        uuid=auth.currentUser!!.uid


        //burada
        db.collection("users").document(uuid).get().addOnSuccessListener { result->
            username=result.data!!.get("username") as String
            userprofilephoto=result!!.get("UserProfilePhoto") as String

            questioncount=result.data!!.get("question") as String
            answercount=result.data!!.get("answer") as String
            score=result.data!!.get("score") as String


            binding.UserNametv.text=username
            Picasso.get().load(userprofilephoto).into(binding.ProfileImageView)

            binding.QuestionTv.text="Soru\n"+questioncount
            binding.AnswerTv.text="Cevap\n"+answercount
            binding.ScoreTv.text="Puan\n"+score
        }.addOnSuccessListener {
            val layoutManager=LinearLayoutManager(activity)
            binding.recylerview.layoutManager=layoutManager
            adapter= ProfilRecylerAdapter(username,ProfileSınavTürü,ProfileFBDersler,ProfileKonular,ProfileQuestionUİD,photoUrlList,answercheckmap,this)
            binding.recylerview.adapter=adapter
            binding.AllAskedBtn.callOnClick()
            adapter!!.notifyDataSetChanged()
        }

















        binding.takip.setOnClickListener {
            var intent=Intent(activity,Takip_TakipciActivity::class.java)
            intent.putExtra("intentuuid",uuid)
            intent.putExtra("intentsecim","takip")
            intent.putExtra("intentuserkontrol",true)
            startActivity(intent)
        }


        binding.takipci.setOnClickListener {
            var intent=Intent(activity,Takip_TakipciActivity::class.java)
            intent.putExtra("intentuuid",uuid)
            intent.putExtra("intentsecim","takipci")
            intent.putExtra("intentuserkontrol",true)
            startActivity(intent)
        }

        //burada allaskedbtn tusuna basıldıgında sorulmus tüm soruları getirmeye carıyor getqestion file'sinde kodları
        binding.AllAskedBtn.setOnClickListener {
            allqestionget(db,uuid,ProfileSınavTürü,ProfileFBDersler,ProfileKonular,ProfileQuestionUİD,photoUrlList,answercheckmap,adapter!!)
        }



        binding.AnsweredBtn.setOnClickListener{
            truequestionsget(db,uuid,ProfileSınavTürü,ProfileFBDersler,ProfileKonular,ProfileQuestionUİD,photoUrlList,answercheckmap,
                adapter!!)
        }

        binding.WaitAnswerBtn.setOnClickListener{
            notanswered(db,uuid,ProfileSınavTürü,ProfileFBDersler,ProfileKonular,ProfileQuestionUİD,photoUrlList,answercheckmap,
                adapter!!)
        }




        binding.ProfileImageView.setOnClickListener{

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)

            /*
            if (uuid==intentuuid){
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery,pickImage)
            }
             */
        }




        db.collection("users").document(uuid).collection("takipçi").get().addOnSuccessListener {task->
            var takipçisayi=task.size()
            binding.takipci.text="takipçi \n$takipçisayi"
        }
        db.collection("users").document(uuid).collection("takip").get().addOnSuccessListener {task->
            var takip=task.size()
            binding.takip.text="takip \n$takip"
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.ProfileImageView.setImageURI(imageUri)
            val storage= FirebaseStorage.getInstance()
            val reference=storage.reference
            val imagesReference=reference.child(uuid)

            imageUri?.let { imagesReference.putFile(it) }?.addOnSuccessListener {
                object : CountDownTimer(10000,1000){
                    override fun onFinish() {
                        val uploadPictureReference= FirebaseStorage.getInstance().reference.child(uuid)
                        uploadPictureReference.downloadUrl.addOnSuccessListener { uri->
                            println(uri.toString())
                            userphotohash.put("UserProfilePhoto",uri.toString())
                            db.collection("users").document(uuid).update(userphotohash)
                        }
                    }

                    override fun onTick(millisUntilFinished: Long) {//her coundownda olması istenilen görevi yapıyor

                    }
                }.start()
            }


        }
    }










    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(a:String,b:String,c:String,soruuuid: String) {
        sinavturu=a
        FBDersler=b
        FBKonular=c
        var intent=Intent(activity,CevaplamaActivity::class.java)
        intent.putExtra("questionurl",soruuuid)
        startActivity(intent)
    }





}