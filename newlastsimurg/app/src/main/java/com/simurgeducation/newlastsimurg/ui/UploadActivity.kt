package com.simurgeducation.newlastsimurg.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

import com.simurgeducation.newlastsimurg.R
import com.simurgeducation.newlastsimurg.databinding.ActivityUploadBinding
import com.simurgeducation.newlastsimurg.sinavturu
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.util.*
import kotlin.collections.ArrayList

class UploadActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUploadBinding


    var sinav:ArrayList<String> = ArrayList()
    var ders:ArrayList<String> = ArrayList()
    var konular:ArrayList<String> = ArrayList()

    private var selfuuid:String?=null



    var selectedPicture: Uri?=null
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var FBQestions:String


    private var questionrightanswer=""





    var sınavTürü=""
    var derstürü=""
    var konutürü=""


    private val TAG: String = "AppDebug"
    private val GALLERY_REQUEST_CODE = 1234



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        binding= ActivityUploadBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        binding.anahtarplaintext.visibility=View.INVISIBLE



        sinav.add("seçiniz")
        sinav.add("KPSS")


        ders.add("seçiniz")

        konular.add("seçiniz")


        auth= FirebaseAuth.getInstance()
        selfuuid=auth.currentUser!!.uid.toString()










        binding.spinner1.adapter= ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,sinav)
        binding.spinner1.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long)
            {
                if (binding.spinner1.selectedItemPosition==0){
                    ders.clear()
                    ders.add("seçiniz")
                    binding.spinner2.setSelection(0)
                }
                if (binding.spinner1.selectedItemPosition==1){
                    ders.clear()
                    ders.add("seçiniz")
                    ders.add("Türkçe")
                    ders.add("Matematik")
                    ders.add("Geometri")
                    ders.add("Tarih")
                    ders.add("Coğrafya")
                    ders.add("Vatandaşlık")
                    binding.spinner2.setSelection(0)
                }


                sınavTürü=sinav[position]
                println("sinav budur $sınavTürü")
            }

        }




        binding.spinner2.adapter= ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,ders)
        binding.spinner2.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long)
            {
                if ( binding.spinner2.selectedItemPosition==0){
                    konular.clear()
                    konular.add("seçiniz")
                    binding.spinner3.setSelection(0)
                }
                if (binding.spinner2.selectedItemPosition==1){//türkçe kısmı
                    konular.clear()
                    konular.add("seçiniz")
                    konular.add("Sözcükte Anlam")
                    konular.add("Cümlede Anlam")
                    konular.add("Paragrafta Anlam")
                    konular.add("Anlatım Bozuklukları")
                    konular.add("Ses Bilgisi")
                    konular.add("Yapı Bilgisi")
                    konular.add("Sözcük Bilgisi")
                    konular.add("Cümle Bilgisi")
                    konular.add("Yazım Kuralları")
                    konular.add("Noktalama İşaretleri")
                    konular.add("Sözel Mantık")

                    binding.spinner3.setSelection(0)
                }
                if ( binding.spinner2.selectedItemPosition==2){//matematik kısmı
                    konular.clear()
                    konular.add("seçiniz")
                    konular.add("Temel Kavramlar")
                    konular.add("Bölme Ve Bölünebilme Kuralları")
                    konular.add("Asal Çarpanlarına Ayırma")
                    konular.add("Birinci Dereceden Denklemler")
                    konular.add("Rasyonel Sayılar")
                    konular.add("Eşitsizlik Mutlak Değer")
                    konular.add("Üslü Sayılar")
                    konular.add("Köklü Sayılar")
                    konular.add("Çarpanlarına Ayırma")
                    konular.add("Oran Orantı")
                    konular.add("Problemler")
                    konular.add("Kümeler")
                    konular.add("İşlem ve Modüler Aritmetik")
                    konular.add("Permütasyon Kombinasyon Olasılık")
                    konular.add("Tablo ve Grafikler")
                    konular.add("Sayısal Mantık Problemleri")
                    konular.add("Fonksiyonlar")
                    binding.spinner3.setSelection(0)
                }
                if ( binding.spinner2.selectedItemPosition==3){
                    konular.clear()
                    konular.add("seçiniz")
                    konular.add("Geomtetrik Kavramlar Ve Doğruda Açılar")
                    konular.add("Üçgenler")
                    konular.add("Çokgenler Ve Dörtgenler")
                    konular.add("Çember Ve Daire")
                    konular.add("Analitik Geometri")
                    konular.add("Katı Cisimler")
                }

                if (binding.spinner2.selectedItemPosition==4){
                    konular.clear()
                    konular.add("seçiniz")
                    konular.add("İslamiyet Öncesi Türk Tarihi")
                    konular.add("Türk İslam Tarihi")
                    konular.add("Osmanlı Tarihi")
                    konular.add("Osmanlı Yenileşme Ve Demokratikleşme Hareketleri")
                    konular.add("Avrupa'da Yaşanan Gelişmeler")
                    konular.add("XX.Yüzyılda Osmanlı Devleti")
                    konular.add("Kurtuluş Savaşı'nın Hazırlık Dönemi")
                    konular.add("Kurtuluş Savaşı Muharebeler Ve Antlaşma Dönemi")
                    konular.add("Atatürk İlke Ve İnkılapları")
                    konular.add("Atatürk Dönemi Türk Dış Politikası")
                    konular.add("Çağdaş Türk Ve Dünya Tarihi")

                }
                if (binding.spinner2.selectedItemPosition==5){
                    konular.clear()
                    konular.add("seçiniz")
                    konular.add("Coğrafi Konum Ve Türkiye'nin Coğrafi Konumu")
                    konular.add("Türkiye'nin Yer Şekilleri Ve Özellikleri")
                    konular.add("Türkiye'nin İklimi Ve Bitki Örtüsü")
                    konular.add("Türkiye'de Nüfus Ve Yerleşme")
                    konular.add("Türkiye'nin Ekonomik Coğrafyası")
                    konular.add("Türkiye'de Madenler,Enerji Kaynakları Ve Sanayi")
                    konular.add("Türkiye'de Ulaşım, Ticaret Ve Turizm")
                    konular.add("Türkiye'nin Coğrafi Bölgeleri")
                    konular.add("Bölgesel Kalkınma Projeleri")

                }
                if (binding.spinner2.selectedItemPosition==6){
                    konular.clear()
                    konular.add("seçiniz")
                    konular.add("Hukukun Temel Kavramları")
                    konular.add("Devlet Biçimleri Ve Hükümet Sistemleri")
                    konular.add("Anayasa Hukukuna Giriş,Temel Kavramlar")
                    konular.add("1982 Anayasası'nın Temel İlkeleri")
                    konular.add("Yasama")
                    konular.add("Yürütme")
                    konular.add("Yargı")
                    konular.add("Temel Hak Ve Hürriyetler")
                    konular.add("İdare Hukuku")
                    konular.add("Uluslararası Kuruluşlar")
                    konular.add("Güncel Olaylar")
                }
                derstürü=ders[position]
                println("sinav budur $derstürü")
            }

        }




        binding.spinner3.adapter= ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,konular)
        binding.spinner3.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long)
            {

                konutürü=konular[position]
                println("tüm değerler bunlardır $sınavTürü $derstürü $konutürü")
            }
        }


        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()


        binding.ProfileImageView.setOnClickListener {
               pickFromGallery()
        }






        db.collection("users").document(selfuuid.toString()).addSnapshotListener{ task,e->

            if (task != null) {
                FBQestions= task.data?.getValue("question") as String
            }


        }
    }


    fun cevapanahtar(view: View){
        var buttonseçim=view as Button

        var seçim1=binding.checkBox1
        var seçim2=binding.checkBox2
        var seçim3=binding.checkBox3
        var seçim4=binding.checkBox4
        var seçim5=binding.checkBox5
        var seçim6=binding.checkBox6
        var seçim7=binding.checkBox7



        var liste=ArrayList<CheckBox>()
        liste.add(seçim1)
        liste.add(seçim2)
        liste.add(seçim3)
        liste.add(seçim4)
        liste.add(seçim5)
        liste.add(seçim6)
        liste.add(seçim7)


        fun resetleme(seçim:CheckBox){
            for (a in liste){
                if (a!=seçim){
                    a.setChecked(false)
                }
                if (seçim==seçim7){
                    binding.anahtarplaintext.visibility=view.visibility
                }
            }
        }





        when(buttonseçim){
            seçim1->{
                binding.checkBox1.setChecked(true)
                binding.anahtarplaintext.visibility=View.INVISIBLE
                questionrightanswer="A"
                resetleme(seçim1)
            }
            seçim2->{
                binding.checkBox2.setChecked(true)
                binding.anahtarplaintext.visibility=View.INVISIBLE
                questionrightanswer="B"
                resetleme(seçim2)
            }
            seçim3->{
                binding.checkBox3.setChecked(true)
                binding.anahtarplaintext.visibility=View.INVISIBLE
                questionrightanswer="C"
                resetleme(seçim3)
            }
            seçim4->{
                binding.checkBox4.setChecked(true)
                binding.anahtarplaintext.visibility=View.INVISIBLE
                questionrightanswer="D"
                resetleme(seçim4)
            }
            seçim5->{
                binding.checkBox5.setChecked(true)
                binding.anahtarplaintext.visibility=View.INVISIBLE
                questionrightanswer="E"
                resetleme(seçim5)
            }
            seçim6->{
                binding.checkBox6.setChecked(true)
                binding.anahtarplaintext.visibility=View.INVISIBLE
                questionrightanswer="Bilinmiyor"
                resetleme(seçim6)
            }
            seçim7->{
                binding.checkBox7.setChecked(true)
                resetleme(seçim7)
            }
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
            .into(binding.ProfileImageView)
        selectedPicture=uri
    }

    private fun launchImageCrop(uri: Uri){
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAutoZoomEnabled(false)
            .setMinCropResultSize(1875,1250)
            .setMaxCropResultSize(2500,1875)
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



    /*
    var derstürü=""
    var konutürü=""

 */


    //burada seçilen fotoğrafı upload ediyor


    fun uploadbtn(view: View){

        if (binding.checkBox7.isChecked){
            questionrightanswer=binding.anahtarplaintext.text.toString()
        }




        if (sınavTürü!="seçiniz" && derstürü!="seçiniz" && konutürü!="seçiniz" ){
        var kontrol1=false
        var kontrol2=false
        var kontrol3=false


        FBQestions=(1+FBQestions.toInt()).toString()
        var downloadUri:String?=null

        val uuid= UUID.randomUUID()
        val imagename="$uuid.jpg"
        val storage= FirebaseStorage.getInstance()
        val reference=storage.reference

        val imagesReference=reference.child(sınavTürü).child(derstürü).child(konutürü).child(imagename)
        imagesReference.putFile(selectedPicture!!).addOnSuccessListener { task1->
            kontrol1=true
            val uploadPictureReference= FirebaseStorage.getInstance().reference.child(sınavTürü).child(derstürü).child(konutürü).child(imagename)
            uploadPictureReference.downloadUrl.addOnSuccessListener {uri->
                kontrol2=true
                downloadUri=uri.toString()
                println(downloadUri)
            }.addOnSuccessListener {
                kontrol3=true
                val postMap2= hashMapOf<String,Any>()
                postMap2.put("QuestionUİD",uuid.toString())
                postMap2.put("date", Timestamp.now())
                postMap2.put("sinavturu",sinavturu)
                postMap2.put("FBDersler",derstürü)
                postMap2.put("FBKonular",konutürü)
                db.collection("users").document(selfuuid.toString()).collection("Questions").document(uuid.toString()).set(postMap2)
                val postMap= hashMapOf<String,Any>()
                var liste=ArrayList<String>()
                postMap.put("questionphotourl",downloadUri.toString())
                postMap.put("comment",binding.uploadcommenttext.text.toString())
                postMap.put("date", Timestamp.now())
                postMap.put("UserUID",selfuuid.toString())
                postMap.put("answercheck",false)
                postMap.put("commentcount","0")
                postMap.put("sinavturu",sinavturu)
                postMap.put("FBDersler",derstürü)
                postMap.put("FBKonular",konutürü)
                postMap.put("QuestionUİD",uuid.toString())
                postMap.put("FollowQuestionArrayList",liste)
                postMap.put("questionrightanswer",questionrightanswer)
                db.collection(sınavTürü).document(derstürü).collection(konutürü).document(uuid.toString()).set(postMap)
                val postMap3= hashMapOf<String,Any>()
                postMap3.put("question",FBQestions)
                db.collection("users").document(selfuuid.toString()).update(postMap3) }
        }



        val timer = object: CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (kontrol1==true && kontrol2==true && kontrol3==true){
                    finish()
                }
            }

            override fun onFinish() {

            }
        }
        timer.start()
        }
        else{

        }



                }












    }





