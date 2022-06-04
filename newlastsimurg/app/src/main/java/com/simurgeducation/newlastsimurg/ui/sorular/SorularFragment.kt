package com.simurgeducation.newlastsimurg.ui.sorular


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.simurgeducation.newlastsimurg.FBDersler
import com.simurgeducation.newlastsimurg.FBKonular
import com.simurgeducation.newlastsimurg.databinding.FragmentSorularBinding


class SorularFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    val dersler= mutableListOf<String>()
    val konular= mutableListOf<String>()

    var adapter:SorularRecrylerAdapter?=null




    private var _binding: FragmentSorularBinding? = null
    private val binding get() = _binding!!

    private lateinit var selfuuid:String










    var userNameFromFB :HashMap<Int,String> = HashMap()
    var userCommentFromFB:HashMap<Int,String> = HashMap()
    var questionphotourlFromFB : HashMap<Int,String> = HashMap()
    var positionhasmap:HashMap<Int,String> = HashMap()
    var userUID : HashMap<Int,String> = HashMap()
    var answercheck: HashMap<Int,Boolean> = HashMap()
    var photohashmap:HashMap<Int,String> = HashMap()




    var FollowQuestionArrayList:HashMap<Int,List<String>> =HashMap()





    /*
  var userNameFromFB : ArrayList<String> = ArrayList()
    var userCommentFromFB:ArrayList<String> = ArrayList()
    var questionphotourlFromFB : ArrayList<String> = ArrayList()
    var positionhasmap:HashMap<Int,String> = HashMap()
    var userUID : ArrayList<String> = ArrayList()
    var answercheck: HashMap<Int,Boolean> = HashMap()


    var FollowQuestionArrayList:HashMap<Int,List<String>> =HashMap()
    */






    private lateinit var DersSpinner:Spinner
    private lateinit var KonuSpinner:Spinner






    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= FragmentSorularBinding.inflate(inflater)

        var view=binding.root

        auth= FirebaseAuth.getInstance()
        return view





    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selfuuid=auth.currentUser!!.uid.toString()


        userNameFromFB.clear()
        userCommentFromFB.clear()
        questionphotourlFromFB.clear()
        positionhasmap.clear()
        userUID.clear()
        answercheck.clear()
        FollowQuestionArrayList.clear()

        dersler.clear()
        dersler.add("Türkçe")
        dersler.add("Matematik")
        dersler.add("Geometri")
        dersler.add("Tarih")
        dersler.add("Coğrafya")
        dersler.add("Vatandaşlık")


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









        db=Firebase.firestore


/*
        var lastVisible:DocumentSnapshot?=null
        fun getDataFromFirestore2(){
            println("burda 1")
            var dbsize=0
            fun getir2(){
                var number2=0
                var size=userUID.size-1

                for (a in 0..size){
                    var isim=userUID.getValue(a).toString()
                    db.collection("users").document(isim).addSnapshotListener { task3,e->
                        var username=task3!!.get("username") as String
                        userNameFromFB.put(number2,username)
                        println("-------")
                        println("$a "+userUID.getValue(a).toString())
                        println("$a "+username)
                        println("-------")
                        number2+=1
                        println("burda 6")
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }

            db.collection("KPSS").document(FBDersler).collection(FBKonular).orderBy("date",Query.Direction.DESCENDING).startAfter(lastVisible).limit(10).addSnapshotListener(MetadataChanges.INCLUDE) { task, e->
                var number=-1
                println("burda 2")
                if (task != null) {
                    println("burda 3")
                    dbsize=task.documents.size

                    var sayi=0
                    println(dbsize.toString())
                    for (a in task.documents){
                        println("burda 4")
                        sayi+=1
                        val comment= a.get("comment") as String
                        val questionphotourl = a.get("questionphotourl") as String
                        val timestamp = a.get("date") as Timestamp
                        var UserUID= a.get("UserUID") as String
                        var answercheckget=a.get("answercheck") as Boolean
                        val adres=a.id

                        var liste: List<String> =a?.get("FollowQuestionArrayList") as List<String>

                        val date = timestamp.toDate()

                        number+=1
                        userUID.put(number,UserUID)
                        FollowQuestionArrayList.put(number,liste)
                        answercheck.put(number,answercheckget)
                        userCommentFromFB.put(number,comment)
                        questionphotourlFromFB.put(number,questionphotourl)
                        positionhasmap.put(number,adres.toString())
                        adapter!!.notifyDataSetChanged()
                        if (sayi==dbsize){
                            println("burda 5")
                            getir2()
                        }
                    }
                }
            }
        }

 */







        fun getDataFromFirestore(){
                userNameFromFB.clear()
                userCommentFromFB.clear()
                questionphotourlFromFB.clear()
                positionhasmap.clear()
                userUID.clear()
                answercheck.clear()
                FollowQuestionArrayList.clear()
                photohashmap.clear()


                var dbsize=0
                fun getir2(){
                    var number2=0
                    var size=userUID.size-1
                    for (a in 0..size){
                        var isim=userUID.getValue(a).toString()
                        db.collection("users").document(isim).addSnapshotListener { task3,e->
                            var username=task3!!.get("username") as String
                            var photourl=task3!!.get("UserProfilePhoto") as String
                            photohashmap.put(number2,photourl)
                            userNameFromFB.put(number2,username)
                            println("-------")
                            println("$a "+userUID.getValue(a).toString())
                            println("$a "+username)
                            println("-------")
                            number2+=1
                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }


                db.collection("KPSS").document(FBDersler).collection(FBKonular).orderBy("date",Query.Direction.DESCENDING).limit(30).addSnapshotListener() { task,e->
                    var number=-1
                    if (task != null) {
                        dbsize=task.documents.size
                        var sayi=0
                    //    sonra= task.documents[task.size()-1]

                        userNameFromFB.clear()
                        userCommentFromFB.clear()
                        questionphotourlFromFB.clear()
                        positionhasmap.clear()
                        userUID.clear()
                        answercheck.clear()
                        FollowQuestionArrayList.clear()
                        photohashmap.clear()

                        for (a in task.documents){
                            sayi+=1
                            val comment= a?.get("comment") as String
                            val questionphotourl = a?.get("questionphotourl") as String
                            val timestamp = a?.get("date") as Timestamp
                            var UserUID= a?.get("UserUID") as String
                            var answercheckget=a?.get("answercheck") as Boolean
                            val adres=a?.id

                            var liste: List<String> =a?.get("FollowQuestionArrayList") as List<String>

                            val date = timestamp.toDate()

                            number+=1
                            userUID.put(number,UserUID)
                            FollowQuestionArrayList.put(number,liste)
                            answercheck.put(number,answercheckget)
                            userCommentFromFB.put(number,comment)
                            questionphotourlFromFB.put(number,questionphotourl)
                            positionhasmap.put(number,adres.toString())
                            adapter!!.notifyDataSetChanged()
                            if (sayi==dbsize){
                                getir2()
                            }
                        }
                    }
                }





        }







        // konuSpinner
        binding.konuSpinner.adapter=activity?.let { ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,konular)  }
        KonuSpinner=binding.konuSpinner
        KonuSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                KonuSpinner(konular,binding.konuSpinner.selectedItemPosition,activity)
                println("firestore deneme")
                println(FBDersler)
                println(FBKonular)
                println("firestore deneme")
                getDataFromFirestore()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //DERS SPİNNER
        binding.dersSpinner.adapter= activity?.let { ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,dersler) }
        DersSpinner=binding.dersSpinner
        DersSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                DersSpinner(konular,binding.dersSpinner.selectedItemPosition,KonuSpinner,dersler,activity)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }




        /*
        //burada recylerview temizleniyor
        userNameFromFB.clear()
        userCommentFromFB.clear()
        questionphotourlFromFB.clear()
        positionhasmap.clear()
        userUID.clear()
        answercheck.clear()

         */



        val layoutManager= LinearLayoutManager(activity)
        binding.recylerview.layoutManager=layoutManager
        adapter= SorularRecrylerAdapter(userNameFromFB,userCommentFromFB,questionphotourlFromFB,
             positionhasmap,userUID,activity,answercheck,view,selfuuid,db,FollowQuestionArrayList,photohashmap)
        binding.recylerview.adapter=adapter






        //burası recylerview en aşağısına gelince yapılıcak methodları yazmamızı saglıyor
        /*
        binding.recylerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                  //  getDataFromFirestore2()
                    Toast.makeText(activity, "Last", Toast.LENGTH_LONG).show()
                }
            }
        })

         */





    }

    override fun onPause() {
        super.onPause()
        println("durdu")
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}