package com.simurgeducation.newlastsimurg.ui.profil

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

fun allqestionget(
    db: FirebaseFirestore,
    uuid: String,
    ProfileSınavTürü: HashMap<Int, Any>,
    ProfileFBDersler: HashMap<Int, Any>,
    ProfileKonular: HashMap<Int, Any>,
    ProfileQuestionUİD: HashMap<Int, Any>,
    photoUrlList: HashMap<Int, String>,
    answercheckmap: HashMap<Int, Boolean>,
    adapter: ProfilRecylerAdapter
){
    var a1hliste=HashMap<Int,String>()
    var a2hliste=HashMap<Int,String>()
    var a3hliste=HashMap<Int,String>()
    var a4hliste=HashMap<Int,String>()

    a1hliste.clear()
    a2hliste.clear()
    a3hliste.clear()
    a4hliste.clear()
    db.collection("users").document(uuid).collection("Questions").orderBy("date",Query.Direction.DESCENDING).get().addOnSuccessListener{ task->
        var a=0
        for (documents in task){
            var dersler:String?=null
            var konular:String?=null
            var sinavturuu:String?=null
            var QuestionUİD:String?=null

            sinavturuu=documents["sinavturu"] as String
            dersler=documents["FBDersler"] as String
            konular=documents["FBKonular"] as String
            QuestionUİD=documents["QuestionUİD"] as String

            println(QuestionUİD.toString())


            a1hliste.put(a,sinavturuu.toString())
            a2hliste.put(a,dersler.toString())
            a3hliste.put(a,konular.toString())
            a4hliste.put(a,QuestionUİD.toString())
            a+=1
        }
    }.addOnSuccessListener {
        ProfileSınavTürü.clear()
        ProfileFBDersler.clear()
        ProfileKonular.clear()
        ProfileQuestionUİD.clear()
        photoUrlList.clear()
        answercheckmap.clear()

        var Anumber=0
        for (b in 0..a4hliste.size-1){
            var g1=a1hliste.getValue(b).toString()
            var g2=a2hliste.getValue(b).toString()
            var g3=a3hliste.getValue(b).toString()
            var g4=a4hliste.getValue(b).toString()
            db.collection(g1).document(g2).collection(g3).document(g4).get().addOnSuccessListener { value->
                val Aphotourl=value!!.get("questionphotourl") as String
                val Asinavtürü=value!!.get("sinavturu") as String
                val Akonular=value!!.get("FBKonular") as String
                val Adersler=value!!.get("FBDersler") as String
                val Adocid=value!!.get("QuestionUİD") as String
                val Aanswercheck=value!!.get("answercheck") as Boolean



                ProfileSınavTürü.put(Anumber,Asinavtürü)
                ProfileFBDersler.put(Anumber,Adersler)
                ProfileKonular.put(Anumber,Akonular)

                ProfileQuestionUİD.put(Anumber,Adocid)
                photoUrlList.put(Anumber,Aphotourl)
                answercheckmap.put(Anumber,Aanswercheck)



                Anumber+=1


                adapter!!.notifyDataSetChanged()

            }

        }

    }






}


fun truequestionsget(    db: FirebaseFirestore,
                         uuid: String,

                         ProfileSınavTürü: HashMap<Int, Any>,
                         ProfileFBDersler: HashMap<Int, Any>,
                         ProfileKonular: HashMap<Int, Any>,
                         ProfileQuestionUİD: HashMap<Int, Any>,
                         photoUrlList: HashMap<Int, String>,
                         answercheckmap: HashMap<Int, Boolean>,
                         adapter: ProfilRecylerAdapter)
{
    var a1hliste=HashMap<Int,String>()
    var a2hliste=HashMap<Int,String>()
    var a3hliste=HashMap<Int,String>()
    var a4hliste=HashMap<Int,String>()

    a1hliste.clear()
    a2hliste.clear()
    a3hliste.clear()
    a4hliste.clear()
    db.collection("users").document(uuid).collection("Questions").orderBy("date",Query.Direction.DESCENDING).get().addOnSuccessListener{ task->
        var a=0
        for (documents in task){
            var dersler:String?=null
            var konular:String?=null
            var sinavturuu:String?=null
            var QuestionUİD:String?=null

            sinavturuu=documents["sinavturu"] as String
            dersler=documents["FBDersler"] as String
            konular=documents["FBKonular"] as String
            QuestionUİD=documents["QuestionUİD"] as String



            a1hliste.put(a,sinavturuu.toString())
            a2hliste.put(a,dersler.toString())
            a3hliste.put(a,konular.toString())
            a4hliste.put(a,QuestionUİD.toString())
            a+=1
        }
    }.addOnSuccessListener {

        ProfileSınavTürü.clear()
        ProfileFBDersler.clear()
        ProfileKonular.clear()
        ProfileQuestionUİD.clear()
        photoUrlList.clear()
        answercheckmap.clear()


        var Anumber=0
        for (b in 0..a4hliste.size-1){
            var g1=a1hliste.getValue(b).toString()
            var g2=a2hliste.getValue(b).toString()
            var g3=a3hliste.getValue(b).toString()
            var g4=a4hliste.getValue(b).toString()
            db.collection(g1).document(g2).collection(g3).document(g4).addSnapshotListener { value,e->
                val Aphotourl=value!!.get("questionphotourl") as String
                val Asinavtürü=value!!.get("sinavturu") as String
                val Akonular=value!!.get("FBKonular") as String
                val Adersler=value!!.get("FBDersler") as String
                val Adocid=value!!.get("QuestionUİD") as String

                val Aanswercheck=value!!.get("answercheck") as Boolean

                if (Aanswercheck==true){

                    ProfileSınavTürü.put(Anumber,Asinavtürü)
                    ProfileFBDersler.put(Anumber,Adersler)
                    ProfileKonular.put(Anumber,Akonular)

                    ProfileQuestionUİD.put(Anumber,Adocid)
                    photoUrlList.put(Anumber,Aphotourl)
                    answercheckmap.put(Anumber,Aanswercheck)
                    Anumber+=1
                    adapter!!.notifyDataSetChanged()
                }
                adapter!!.notifyDataSetChanged()
            }

        }

    }


}


    fun notanswered(db: FirebaseFirestore,
                    uuid: String,

                    ProfileSınavTürü: HashMap<Int, Any>,
                    ProfileFBDersler: HashMap<Int, Any>,
                    ProfileKonular: HashMap<Int, Any>,
                    ProfileQuestionUİD: HashMap<Int, Any>,
                    photoUrlList: HashMap<Int, String>,
                    answercheckmap: HashMap<Int, Boolean>,
                    adapter: ProfilRecylerAdapter)
    {
        var a1hliste=HashMap<Int,String>()
        var a2hliste=HashMap<Int,String>()
        var a3hliste=HashMap<Int,String>()
        var a4hliste=HashMap<Int,String>()

        a1hliste.clear()
        a2hliste.clear()
        a3hliste.clear()
        a4hliste.clear()
        db.collection("users").document(uuid).collection("Questions").orderBy("date",Query.Direction.DESCENDING).get().addOnSuccessListener{ task->
            var a=0
            for (documents in task){
                var dersler:String?=null
                var konular:String?=null
                var sinavturuu:String?=null
                var QuestionUİD:String?=null

                sinavturuu=documents["sinavturu"] as String
                dersler=documents["FBDersler"] as String
                konular=documents["FBKonular"] as String
                QuestionUİD=documents["QuestionUİD"] as String

                println(QuestionUİD.toString())


                a1hliste.put(a,sinavturuu.toString())
                a2hliste.put(a,dersler.toString())
                a3hliste.put(a,konular.toString())
                a4hliste.put(a,QuestionUİD.toString())
                a+=1
            }
        }.addOnSuccessListener {

            ProfileSınavTürü.clear()
            ProfileFBDersler.clear()
            ProfileKonular.clear()
            ProfileQuestionUİD.clear()
            photoUrlList.clear()
            answercheckmap.clear()


            var Anumber=0
            for (b in 0..a4hliste.size-1){
                var g1=a1hliste.getValue(b).toString()
                var g2=a2hliste.getValue(b).toString()
                var g3=a3hliste.getValue(b).toString()
                var g4=a4hliste.getValue(b).toString()
                db.collection(g1).document(g2).collection(g3).document(g4).addSnapshotListener { value,e->
                    val Aphotourl=value!!.get("questionphotourl") as String
                    val Asinavtürü=value!!.get("sinavturu") as String
                    val Akonular=value!!.get("FBKonular") as String
                    val Adersler=value!!.get("FBDersler") as String
                    val Adocid=value!!.get("QuestionUİD") as String
                    val Aanswercheck=value!!.get("answercheck") as Boolean
                    if (Aanswercheck==false){
                        ProfileSınavTürü.put(Anumber,Asinavtürü)
                        ProfileFBDersler.put(Anumber,Adersler)
                        ProfileKonular.put(Anumber,Akonular)
                        ProfileQuestionUİD.put(Anumber,Adocid)
                        photoUrlList.put(Anumber,Aphotourl)
                        answercheckmap.put(Anumber,Aanswercheck)
                        Anumber+=1
                        adapter!!.notifyDataSetChanged()
                    }
                    adapter!!.notifyDataSetChanged()
                }

            }

        }


    }