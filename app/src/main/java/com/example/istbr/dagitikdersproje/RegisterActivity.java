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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar actionbarRegister;
    private EditText txtUsername,txtEmail,txtPassword;
    private Button btnRegister;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    public void init(){

        actionbarRegister=(Toolbar)findViewById(R.id.actionbarRegister);
        setSupportActionBar(actionbarRegister);
        getSupportActionBar().setTitle("Hesap Oluştur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtUsername=(EditText)findViewById(R.id.txtUsernameRegister);
        txtEmail=(EditText)findViewById(R.id.txtEmailRegister);
        txtPassword=(EditText)findViewById(R.id.txtPasswordRegister);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        auth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {

        final String username=txtUsername.getText().toString();//txtUsernamedeki veriyi getir ve onu stringe çevir
        final String email=txtEmail.getText().toString();
        final String password=txtPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email alanı boş olamaz!",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Sifre alanı boş olamaz!",Toast.LENGTH_LONG).show();
        } else {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //EntityUser entityUser = new EntityUser(username,email,password,false,0);
                        //ilk kayıt olanın puanı ve izini sıfır olur
                        EntityUser entityUser = new EntityUser(username,email,password,false,0);

                        DatabaseReference myRef = firebaseDatabase.getReference("users");//firebasedeki userın altına chile oluşturuyoruz
                        myRef.child(auth.getCurrentUser().getUid()).setValue(entityUser);

                        Toast.makeText(RegisterActivity.this,"Hesabınız başarılı bir şekilde oluşturuldu !",Toast.LENGTH_LONG).show();
                        Intent loginIntent=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(loginIntent);
                        finish();//kullanıcı loginActivitye gidince geri butonunu basınca bir daha registra geleyecek
                    }
                    else{

                        Toast.makeText(RegisterActivity.this,"Hesabınız oluşturulamadı!",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

}
