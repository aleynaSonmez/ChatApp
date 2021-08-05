package com.example.chatappproje;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends   AppCompatActivity {

    private Button btnwelcomeLogin, getBtnwelcomeRegister;
    FirebaseUser firebaseUser;
    public void init()
    {
        btnwelcomeLogin=(Button) findViewById(R.id.btnwelcomeLogin);
        getBtnwelcomeRegister=(Button) findViewById(R.id.btnwelcomeRegister);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null)
        {
            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();

        btnwelcomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intentLogin);

            }
        });
        getBtnwelcomeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(WelcomeActivity.this,RegisterActivity.class);
                startActivity(intentRegister);

            }
        });
    }
}
