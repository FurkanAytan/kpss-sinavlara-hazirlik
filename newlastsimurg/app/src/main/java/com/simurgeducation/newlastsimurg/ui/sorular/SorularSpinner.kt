package com.simurgeducation.newlastsimurg.ui.sorular

import android.widget.Spinner
import androidx.fragment.app.FragmentActivity
import com.simurgeducation.newlastsimurg.FBDersler
import com.simurgeducation.newlastsimurg.FBKonular


fun DersSpinner(
    konular: MutableList<String>,
    p1: Int,
    KonuSpinner: Spinner,
    dersler: MutableList<String>,
    activity: FragmentActivity?
) {
    when(p1){
        0->{
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
            FBDersler=dersler[0]
            KonuSpinner.setSelection(0)
        }
        1->{
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
            FBDersler=dersler[1]
            KonuSpinner.setSelection(0)
        }
        2->{
            konular.clear()
            konular.add("seçiniz")
            konular.add("Geomtetrik Kavramlar Ve Doğruda Açılar")
            konular.add("Üçgenler")
            konular.add("Çokgenler Ve Dörtgenler")
            konular.add("Çember Ve Daire")
            konular.add("Analitik Geometri")
            konular.add("Katı Cisimler")
            FBDersler=dersler[2]
            KonuSpinner.setSelection(0)
        }
        3->{
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
            FBDersler=dersler[3]
            KonuSpinner.setSelection(0)
        }
        4->{
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
            FBDersler=dersler[4]
            KonuSpinner.setSelection(0)
        }
        5->{
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
            FBDersler=dersler[p1]
            KonuSpinner.setSelection(0)
        }

    }
}

fun KonuSpinner(
    konular: MutableList<String>,
    selectedItemPosition: Int,
    activity: FragmentActivity?
){
    FBKonular=konular[selectedItemPosition]
}
