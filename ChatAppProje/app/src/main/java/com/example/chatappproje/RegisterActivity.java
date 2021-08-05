package com.example.chatappproje;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity{
    private EditText txtMail;
    private EditText txtPassword, txtUserName ;
    private FirebaseAuth auth;
    private Button btnRegister;
    private Button btnLoginRegister;
    private FirebaseDatabase firebaseDatabase ;



    public void init()
    {
        Toolbar actionbarRegister = (Toolbar) findViewById(R.id.actionbarRegister);
        setSupportActionBar(actionbarRegister);
        getSupportActionBar().setTitle("Hesap Oluştur");

        txtMail = (EditText) findViewById(R.id.emailRegister);
        txtPassword = (EditText) findViewById(R.id.txtpasswordRegister);
        txtUserName =  (EditText) findViewById(R.id.usernameRegister);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLoginRegister=(Button) findViewById(R.id.btnLoginRegister);

        firebaseDatabase =FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

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
        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }

            private void goToLoginActivity() {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

    }


    private void createNewAccount() {
        String username = txtUserName.getText().toString();
        String email = txtMail.getText().toString();
        String password = txtPassword.getText().toString();

        DatabaseReference dbRef = firebaseDatabase.getReference("email");
        String key = dbRef.push().getKey();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email Alanı Boş Olamaz !", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Sifre Alanı Boş Olamaz !", Toast.LENGTH_LONG).show();

        }
        else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                                Toast.makeText(RegisterActivity.this, "Hesabınız Başarılı Bir Şekilde Oluşturuldu !", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "Bir Hata Oluştu !", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }


    }
}
