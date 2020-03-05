package com.example.istbr.dagitikdersproje;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private Toolbar actionbarLogin; //Burada login ekranındaki actionbar için değişken oluşturduk
    private EditText txtEmail,txtPassword;
    private Button btnLogin;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    public void init(){
        actionbarLogin=(Toolbar)findViewById(R.id.actionbarLogin); // login erkarndaki actionbarı aldık
        setSupportActionBar(actionbarLogin);//Login ekranına set ettik actionbarı
        getSupportActionBar().setTitle("Giriş Yap");// Actionbarımızın ismini verdik burada
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //geri butonunu aktif ediyoruz bu şekilde

        txtEmail=(EditText)findViewById(R.id.txtEmailLogin);
        txtPassword=(EditText)findViewById(R.id.txtPasswordLogin);
        btnLogin=(Button)findViewById(R.id.btnLogin);

        auth=FirebaseAuth.getInstance();// firebasedeki authun içini doldurduk
        currentUser=auth.getCurrentUser();//aktif kullanıcılar buarda tutulacaktır

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();

            }
        });
    }

    private void loginUser() {

        String email=txtEmail.getText().toString();
        String password=txtPassword.getText().toString();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"Email alanı boş olamaz!",Toast.LENGTH_LONG).show();// uzun sure ekranda kalması için lengt long
        }
        else if(TextUtils.isEmpty(password)){

            Toast.makeText(this,"Sifre alanı boş olamaz!",Toast.LENGTH_LONG).show();
        }
        else {

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        Toast.makeText(LoginActivity.this,"Giriş başarılı!",Toast.LENGTH_LONG).show();
                        Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                    else{

                        Toast.makeText(LoginActivity.this,"Giriş başarısız!",Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
    }
}
