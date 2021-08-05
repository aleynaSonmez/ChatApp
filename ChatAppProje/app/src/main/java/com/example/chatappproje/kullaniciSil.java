package com.example.chatappproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class kullaniciSil extends AppCompatActivity {
    List<Veriler> fetchData;
    private FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    TextView bilgi;
    Button btn_sil;
    DatabaseReference databaseReference;
    FirebaseUser mUser;

    public void init() {

        btn_sil = (Button) findViewById(R.id.btn_sil);
        bilgi = (TextView) findViewById(R.id.bilgi);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_sil);
        init();

        btn_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verileriGetir();

            }
        });

    }

    private void verileriGetir() {
        fetchData = new ArrayList<>();
        Intent intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        final String name = intent.getStringExtra("name");
        final String numara = intent.getStringExtra("numara");
        assert userId != null;

        databaseReference = FirebaseDatabase.getInstance().getReference("bilgiler").child(mUser.getUid()).child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot resultSnapshot : snapshot.getChildren()) {
                    bilgi.setText(name+" silindi.");
                    sil(userId);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    private void sil(String silinecekId) {
        Intent intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        assert userId != null;
        FirebaseDatabase.getInstance().getReference("bilgiler").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Veriler data = ds.getValue(Veriler.class);
                    assert data != null;
                    data.setId(userId);
                    if (data.getId().equals(silinecekId)) {
                        databaseReference.removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}