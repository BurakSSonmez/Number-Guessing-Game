package com.example.istbr.dagitikdersproje;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TahminActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;

    private TextView textViewKalanHak,textViewYardim;
    private EditText editTextGirdi;
    private Button buttonTahmin;
    private int rasgeleSayi;
    private int sayac=5;

    public void init(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        buttonTahmin=(Button)findViewById(R.id.buttonTahmin);
        textViewKalanHak=(TextView)findViewById(R.id.textWiewKalanHak);
        textViewYardim=(TextView)findViewById(R.id.textWiewYardim);
        editTextGirdi=(EditText)findViewById(R.id.editTextGirdi);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahmin);
        init();

        DatabaseReference myRef = firebaseDatabase.getReference("tutulanSayi");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rasgeleSayi = Integer.valueOf(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Rastgele sayıyı gormek icin teset yap
        Log.e("Rastgele Sayi",String.valueOf(rasgeleSayi));

        buttonTahmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Oncelikle kisinin girdigi sayiyi alıyoruz
                sayac=sayac-1;
                //Girdiler her zaman stringtir bu stringleri int yapmamız lazım girilen sayiyi buradan alıyoruz
                int tahmin=Integer.parseInt(editTextGirdi.getText().toString());

                if(sayac!=0){
                    if(tahmin==rasgeleSayi){
                        final DatabaseReference myRef = firebaseDatabase.getReference("users");
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int puan = Integer.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("puan").getValue().toString());
                                puan = puan + 50;

                                myRef.child(auth.getCurrentUser().getUid()).child("puan").setValue(puan);

                                SonucActivity snc = new SonucActivity();
                                snc.setDeger(puan);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        Intent i=new Intent(TahminActivity.this,SonucActivity.class);
                        //Sayfalar arasında geçiş yaparken veri gonderme!!!
                        //Sonucu dogru bildi true verisini yolluyoruz
                        i.putExtra("sonuc",true);
                        startActivity(i);
                        finish();
                    }

                    if(tahmin > rasgeleSayi){
                        //yardım textinin içine istediğimizi yazdırıyoruz
                        textViewYardim.setText("Azalt");
                    }

                    if(tahmin < rasgeleSayi){
                        //yardım textinin içine istediğimizi yazdırıyoruz
                        textViewYardim.setText("Arttır");
                    }
                    textViewKalanHak.setText("Kalan Hak:"+sayac);
                }else {
                     //Sayac 0 olduysa kaybettiniz
                     Intent i=new Intent(TahminActivity.this,SonucActivity.class);
                     //Sayfalar arasında geçiş yaparken veri gonderme!!!
                    //Kaybettiysek false sonucunu yolluyoruz
                     i.putExtra("sonuc",false);
                     startActivity(i);
                     finish();
                }

                //en son edittexte kalan sayıyı siliyoruz bir daha oynandığında biz silmeyelim
                editTextGirdi.setText("");



            }
        });
    }
}
