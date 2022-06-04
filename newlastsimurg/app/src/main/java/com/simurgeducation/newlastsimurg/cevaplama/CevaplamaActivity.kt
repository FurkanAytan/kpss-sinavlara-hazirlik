 package com.simurgeducation.newlastsimurg



import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.simurgeducation.newlastsimurg.cevaplama.CevaplarRecylerAdapter
import com.simurgeducation.newlastsimurg.databinding.ActivityCevaplamaBinding
import com.simurgeducation.newlastsimurg.photofull.PhotoActivity
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap





class CevaplamaActivity : AppCompatActivity(),CevaplarRecylerAdapter.OnItemClickListener {

    lateinit var questionurl : String
     var answercheck:Boolean?=null



    var takipsorulistesi=ArrayList<String>()





    private val TAG: String = "AppDebug"
    private val GALLERY_REQUEST_CODE = 1234
    var selectedPicture: Uri?=null




//furkan35@hotmail.com

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var userUUID: String



    private lateinit var selfusername:String
    private lateinit var selfphotourl:String






    var useranswercaunt:String?=null  //kullanıcının vermiş oldugu cevap sayısı
    var qestionanswercaunt:String?=null //sorunun almıs oldugu yorum sayısı
    var questionrightanswer:String?=null





    private lateinit var binding:ActivityCevaplamaBinding

    var adapter:CevaplarRecylerAdapter?=null



    val usernameList= HashMap<Int,String>()
    val commentList=HashMap<Int,String>()
    val commenthashmap=HashMap<Int,Boolean>()
    val commenturl=HashMap<Int,String>()
    val answercomentuuddihasmap=HashMap<Int,String>()
    val comentphotoHashmap = HashMap<Int,String>()
    val questioncommentphotomap = HashMap<Int,String>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cevaplama)

        binding= ActivityCevaplamaBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        userUUID=auth.currentUser!!.uid





        println("kullanıcı uudsi budur $userUUID")

        db.collection("users").document(userUUID).get().addOnSuccessListener {result->
            if (result.data.isNullOrEmpty())
            println("kontrol alanı 55")


            selfusername=result.data!!.get("username") as String
            selfphotourl=result.data!!.get("UserProfilePhoto") as String


            println(selfusername)
            println(selfphotourl)
            println("kontrol alanı 55")
        }




        var intent=intent
        questionurl=intent.getStringExtra("questionurl").toString()
        println("********")
        println(sinavturu)
        println(FBDersler)
        println(FBKonular)
        println(questionurl)
        println("********")


      //  answercheck=intent.getBooleanExtra("answercheck",false)







        val layoutManager= LinearLayoutManager(this)
        binding.recylerview.layoutManager=layoutManager
        adapter= answercheck?.let {
            CevaplarRecylerAdapter(
                usernameList,
                commentList,
                commenthashmap,
                it,
                this,
                commenturl,
                answercomentuuddihasmap,comentphotoHashmap,questioncommentphotomap)
        }
        binding.recylerview.adapter=adapter


        getquestionFB()
        getcommentFB()
        useranwsercaunt()



        binding.imageView6.setOnClickListener {
            pickFromGallery()
        }








    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }
                else{
                    Log.e(TAG, "Image selection error: Couldn't select that image from memory." )
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    setImage(result.uri)
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.e(TAG, "Crop error: ${result.getError()}" )
                }
            }
        }
    }

    private fun setImage(uri: Uri){
        Glide.with(this)
            .load(uri)
            .into(binding.imageView6)
             selectedPicture=uri
    }

    private fun launchImageCrop(uri: Uri){
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAutoZoomEnabled(false)
            .setMinCropResultSize(1875,2500)
            .setMaxCropResultSize(2500,4000)
            .setCropShape(CropImageView.CropShape.RECTANGLE) // default is rectangle
            .start(this)
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }












        fun useranwsercaunt(){
            db.collection("users").document(userUUID).addSnapshotListener(MetadataChanges.INCLUDE){snapshot,e->
                useranswercaunt=snapshot!!.get("answer") as String
                println("kullanıcın cevap sayısı $useranswercaunt")
            }
        }













    fun getquestionFB(){



        var useruuid:String?=null
        var profilephoto:String?=null



        fun kullanicibilgi(){
            db.collection("users").document(useruuid.toString()).get().addOnSuccessListener { task->
                var username=task?.get("username") as String
                binding.usernametexttv.text=username.toString()
                profilephoto=task.get("UserProfilePhoto") as String
                Picasso.get().load(profilephoto).into(binding.ProfileImageView)

                adapter!!.notifyDataSetChanged()
            }
        }


        db.collection(sinavturu).document(FBDersler).collection(FBKonular).document(questionurl).addSnapshotListener(MetadataChanges.INCLUDE){snapshot,e->

            if (e!=null){
                Toast.makeText(applicationContext,e.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            }
            else{
                var commentFB=snapshot!!.get("comment") as String
                var questionphotourlFB=snapshot.get("questionphotourl") as String

                answercheck=snapshot.get("answercheck") as Boolean
                qestionanswercaunt=snapshot.get("commentcount") as String

                useruuid=snapshot.get("UserUID") as String

                questionrightanswer=snapshot.get("questionrightanswer") as String
                binding.questionRightprovisions.text="Cevap : "+questionrightanswer.toString()




                println("$qestionanswercaunt sayisi budur")


                binding.commenttexttv.setText(commentFB)



                //glidede bazen soruyu doğru cevaplayıp cıkınca glide hata veridiğinden picasso kullanılıyor

                /*
                Glide.with(this)
                    .load(questionphotourlFB)
                    .into(binding.questionimageviewtv)

                 */

                Picasso.get().load(questionphotourlFB.toString()).into(binding.questionimageviewtv)



                kullanicibilgi()
                //BURADA comment olan recylerview yeniliyor
                adapter= CevaplarRecylerAdapter(
                    usernameList,
                    commentList,
                    commenthashmap,
                    answercheck!!,
                    this,
                    commenturl,
                    answercomentuuddihasmap,
                    comentphotoHashmap,
                    questioncommentphotomap
                )
                binding.recylerview.adapter=adapter
                adapter!!.notifyDataSetChanged()



            }
        }



    }






    fun getcommentFB(){
        //yorum yapan kullanıcın kullanıcı adını getiriyor
        fun kullanicibilgi2(){
            var number2=0
            var size=answercomentuuddihasmap.size-1
            println("büyüklüğü db2 "+answercomentuuddihasmap.size)
            for (a in 0..size){
                db.collection("users").document(answercomentuuddihasmap.getValue(a).toString()).addSnapshotListener(MetadataChanges.INCLUDE) { task,e->
                    var username=task?.get("username") as String
                    var photourl=task?.get("UserProfilePhoto") as String
                    comentphotoHashmap.put(number2,photourl)
                    binding.usernametexttv.text=username.toString()
                    usernameList.put(number2,username)
                    println("isimler bunlardır "+usernameList?.getValue(number2).toString())
                    number2+=1
                    adapter!!.notifyDataSetChanged()
                }
            }

        }

        db.collection(sinavturu).document(FBDersler).collection(FBKonular).document(questionurl).collection("yorumlar").orderBy("date",Query.Direction.ASCENDING).addSnapshotListener(MetadataChanges.INCLUDE){ snapshot, e->
            if (e!=null){
                println("burda11111111111111111111111111111111111")
                Toast.makeText(applicationContext,e.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
            }
            else{
                println("burda2")
                if (snapshot!=null){
                    println("burda3")
                    answercomentuuddihasmap.clear()
                    commenturl.clear()
                    commenthashmap.clear()
                    usernameList.clear()
                    commentList.clear()

                    if (!snapshot.isEmpty){
                        val documents=snapshot.documents
                        var number=0
                        var dbsize=documents.size
                        for (document in documents){
                            val comment =document.get("comment") as String
                            val commentanswercheck=document.get("answercheck") as Boolean
                            val coomentuseruuid=document.get("UserUID") as String
                            var questioncommentphoto=document?.get("questioncommentphoto") as String ?
                            val adres=document.id


                            println("*1")
                            println("yorum uuidsi "+coomentuseruuid)
                            println(comment)
                            println(commentanswercheck)
                            println("*2")

                            if (questioncommentphoto != null) {
                                questioncommentphotomap.put(number,questioncommentphoto)
                            }
                            answercomentuuddihasmap.put(number,coomentuseruuid)
                            commenturl.put(number,adres)
                            commenthashmap.put(number,commentanswercheck)
                            commentList.put(number,comment)
                            number+=1
                            adapter!!.notifyDataSetChanged()

                            if (dbsize==number){
                                kullanicibilgi2()
                            }
                        }
                    }
                }
            }
        }
    }



    override fun onItemClick(commenturl: String,commentuudi:String) {
        var data= hashMapOf<String,Any>()
        var scoremap=HashMap<String,Any>()
        scoremap.put("score",15)
        println(commenturl)
        data.put("answercheck",true)
        println(FBDersler)
        println(FBKonular)
        println("commenturl budur 1212 $commenturl")
        println("commentuudi budur 1313 $commentuudi")

        db.collection("KPSS").document(FBDersler).collection(FBKonular).document(questionurl).collection("yorumlar").document(commenturl).update(data)

        //burada doğru cevaplayan kullanıcıya firestorda puanına +1 puan ekliyor
        db.collection("users").document(commentuudi)
            .get()
            .addOnSuccessListener { result ->
                var score= result.data?.get("score").toString()
                score= (1+score.toInt()).toString()
                scoremap.put("score",score)
                db.collection("users").document(commentuudi).update(scoremap)//doğru cevaplayan kullanıcın puanını updateliyor
                db.collection("KPSS").document(FBDersler).collection(FBKonular).document(questionurl).update("answercheck",true)
                //db.collection("Questions")
            }
            .addOnFailureListener { exception ->
            }


    }

    override fun photoclick(intentphotourl: String) {
        var intent=Intent(applicationContext,PhotoActivity::class.java)
        intent.putExtra("intentphotourl",intentphotourl)
        startActivity(intent)
    }


    fun cevapbtn(view:View){

        fun temizleme(){
            binding.answertexttv.setText("")
            selectedPicture=null
            binding.imageView6.setImageResource(R.drawable.photoicon)
        }


        if (selectedPicture!=null || binding.answertexttv.text.toString()!=""){
            val uuid= UUID.randomUUID()
            val imagename="$uuid.jpg"
            val storage= FirebaseStorage.getInstance()
            val reference=storage.reference
            var downloadUri:String?=null


            takipsorulistesi.clear()
            var random=UUID.randomUUID().toString()
            useranswercaunt= (1+useranswercaunt!!.toInt()).toString()
            qestionanswercaunt= (1+qestionanswercaunt!!.toInt()).toString()



            var  answer=binding.answertexttv.text.toString()

            var cevappostmap=HashMap<String,Any>()
            cevappostmap.put("UserUID",userUUID)
            cevappostmap.put("answercheck",false)
            cevappostmap.put("UserProfilePhoto",selfphotourl)
            cevappostmap.put("username",selfusername)
            cevappostmap.put("comment",answer)
            cevappostmap.put("date", Timestamp.now())





            if (selectedPicture!=null){
                val imagesReference=reference.child("KPSS").child(FBDersler).child(FBKonular).child(imagename)
                imagesReference.putFile(selectedPicture!!).addOnSuccessListener { task1->
                    val uploadPictureReference=FirebaseStorage.getInstance().reference.child("KPSS").child(FBDersler).child(FBKonular).child(imagename)
                    uploadPictureReference.downloadUrl.addOnSuccessListener { uri->
                        downloadUri=uri.toString()
                        cevappostmap.put("UserUID",userUUID)
                        cevappostmap.put("answercheck",false)
                        cevappostmap.put("UserProfilePhoto",selfphotourl)
                        cevappostmap.put("username",selfusername)
                        cevappostmap.put("comment",answer)
                        cevappostmap.put("date", Timestamp.now())
                        cevappostmap.put("questioncommentphoto",downloadUri!!)
                        db.collection("KPSS").document(FBDersler).collection(FBKonular).document(questionurl).collection("yorumlar").add(cevappostmap)//burada cevap buytonuna basıldıgında yorumu firestore yüklüyor
                    }.addOnSuccessListener {
                        temizleme()
                    }
                }
            }

            if (selectedPicture==null){
                cevappostmap.put("UserUID",userUUID)
                cevappostmap.put("answercheck",false)
                cevappostmap.put("UserProfilePhoto",selfphotourl)
                cevappostmap.put("username",selfusername)
                cevappostmap.put("comment",answer)
                cevappostmap.put("date", Timestamp.now())
                db.collection("KPSS").document(FBDersler).collection(FBKonular).document(questionurl).collection("yorumlar").add(cevappostmap)//burada cevap buytonuna basıldıgında yorumu firestore yüklüyor.
                    .addOnSuccessListener {
                        temizleme()
                    }
            }


            db.collection("users").document(userUUID).update("answer",useranswercaunt.toString())//yorum yapan kullanıcın yorum yazmıs oldugu sayıyı updateliyor
            db.collection("KPSS").document(FBDersler).collection(FBKonular).document(questionurl).update("commentcount",qestionanswercaunt)//sorunun almıs oldugu yorum sayısı updateliyor






            //bildirimlerin firestore update edildiği kısım burası
            db.collection("KPSS").document(FBDersler).collection(FBKonular).document(questionurl).get().addOnSuccessListener { task->
                for (a in task.data?.get("FollowQuestionArrayList") as List<String>){
                    takipsorulistesi.add(a.toString())
                }
            }.addOnSuccessListener {
                for (a in 0..takipsorulistesi.size-1){
                    var map=HashMap<String,Any>()
                    if (userUUID!=takipsorulistesi[a].toString()){
                        map.put("FBDersler",FBDersler)
                        map.put("FBKonular",FBKonular)
                        map.put("QuestionUİD",questionurl)
                        map.put("date",Timestamp.now())
                        map.put("sinavturu",sinavturu)
                        map.put("username",selfusername)
                        map.put("bildirim","takip ettiğin soruya yorum geldi")
                    }
                    else{
                        continue
                    }
                    db.collection("users").document(takipsorulistesi[a]).collection("bildirim").document(random).set(map)
                    println("sayı budur $a"+takipsorulistesi[a])
                }



            }









        }





    }








    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }











}
