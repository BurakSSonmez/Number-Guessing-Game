package com.example.istbr.dagitikdersproje;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SonucActivity extends AppCompatActivity {

    private Toolbar actionBar;
    private Button buttonTekrar;
    private ImageView ımageViewSonuc;
    private TextView textViewSonuc,textViewPuan;
    private boolean sonuc;  // tahminActivityden gelecek olan sonuc için degiskeni olusturduk
    private int puan;
    private int deger;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;



    public void init(){

        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();

        buttonTekrar=(Button)findViewById(R.id.buttonTekrar);
        ımageViewSonuc=(ImageView)findViewById(R.id.ImageWiewSonuc);
        textViewSonuc=(TextView)findViewById(R.id.textWiewSonuc);
        textViewPuan=(TextView)findViewById(R.id.textWiewPuan);

        sonuc = getIntent().getBooleanExtra("sonuc",false);

        if (sonuc){
            puan = puan+50;
            int a = getDeger();
            textViewPuan.setText("Puan "+a);
            ımageViewSonuc.setImageResource(R.drawable.win);
            textViewSonuc.setText("Nasılda bildim");

        }else{

            puan=puan-50;
            textViewPuan.setText("Puan"+puan);
            ımageViewSonuc.setImageResource(R.drawable.kaybettiniz);
            textViewSonuc.setText("Kaybettiniz");
        }

        //tahmin sayfasından gelen sonuc degerini alıyoruz
        actionBar=(Toolbar)findViewById(R.id.actionBar);
        setSupportActionBar(actionBar);
        getSupportActionBar().setTitle("Online Sayı Tahmin Oyunu");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);
        init();
        buttonTekrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SonucActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
        protected void onStart() {
        if(currentUser==null){

            Intent welcomeIntent=new Intent(SonucActivity.this,WelcomeActivity.class);
            startActivity(welcomeIntent);
            finish();
        }

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.mainLogout){

            auth.signOut();
            Intent loginIntent=new Intent(SonucActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
        return true;
    }

    public int getDeger() {
        return deger;
    }

    public void setDeger(int deger) {
        this.deger = deger;
    }
}

