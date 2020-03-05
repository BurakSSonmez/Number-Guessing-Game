package com.example.istbr.dagitikdersproje;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Toolbar actionBar;
    private Button buttonBasla,butonRandom;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;

    private int sayac=0;
    private boolean permission;
    Random rnd = new Random();

    public void init(){
        actionBar=(Toolbar)findViewById(R.id.actionBar);
        setSupportActionBar(actionBar);
        getSupportActionBar().setTitle("Online Sayı Tahmin Oyunu");

        buttonBasla=(Button)findViewById(R.id.buttonBasla);//Butonu burada dinledim
        butonRandom=(Button)findViewById(R.id.butonRandom);//Butonu burada dinledim

        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        butonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRefRnd = firebaseDatabase.getReference("tutulanSayi");
                myRefRnd.setValue(rnd.nextInt(51));
            }
        });

        buttonBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // myRef.setValue(rnd.nextInt(51));
                Intent i=new Intent(MainActivity.this,TahminActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    @Override
    protected void onStart() {
        if(currentUser==null){
            Intent welcomeIntent=new Intent(MainActivity.this,WelcomeActivity.class);
            startActivity(welcomeIntent);
            finish();
        }else{
            DatabaseReference myRefUser = firebaseDatabase.getReference("users");
            myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    permission = Boolean.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("permission").getValue().toString());
                    if(permission==true)
                        butonRandom.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
             Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
             startActivity(loginIntent);
             finish();

         }
        return true;
    }
}
