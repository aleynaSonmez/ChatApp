package com.example.chatappproje;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends  AppCompatActivity{
    private Toolbar actionbarLogin;
    private EditText txtEmail, txtPassword;
    private Button btnLogin ,btnRegisterLogin;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    public void init()
    {
        actionbarLogin =(Toolbar) findViewById(R.id.actionbarLogin);
        setSupportActionBar(actionbarLogin);
        getSupportActionBar().setTitle("Giris Yap");

        auth =FirebaseAuth.getInstance();
        currentUser =auth.getCurrentUser();
        txtEmail = (EditText) findViewById(R.id.txtemailLogin);
        txtPassword = (EditText) findViewById(R.id.txtPasswordLogin);
        btnLogin =(Button) findViewById(R.id.btnLogin);
        btnRegisterLogin=(Button) findViewById(R.id.btnRegisterLogin);
    }
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            init();
            btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToRegisterActivity();
                }

                private void goToRegisterActivity() {
                    Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(registerIntent);
                    finish();
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginUser();
                }

                private void loginUser() {
                    String email = txtEmail.getText().toString();
                    String password =txtPassword.getText().toString();
                    if(TextUtils.isEmpty(email))
                    {
                        Toast.makeText(LoginActivity.this, "Email Alanı Boş Olamaz !",Toast.LENGTH_LONG).show();

                    }
                    else if(TextUtils.isEmpty(password))
                    {
                        Toast.makeText(LoginActivity.this, "Şifre Alanı Boş Olamaz !",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        btnLogin.setEnabled(false);//ard arda girişi engeller.
                        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(LoginActivity.this, "Giriş Başarılı !",Toast.LENGTH_LONG).show();
                                    Intent mainIntent= new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Giriş Başarısız !",Toast.LENGTH_LONG).show();
                                        btnLogin.setEnabled(true);
                                }
                            }
                        });
                    }
                }
            });

        }
}
