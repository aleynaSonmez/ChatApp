package com.example.chatappproje;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;


import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MessageActivity extends AppCompatActivity {

    private ImageView send;
    private Toolbar tolbar;
    private TextView text1;
    private EditText edit1;
    private FirebaseDatabase firebaseDatabase;
    List<Chat> fetchData;
    List<Veriler> fetchDataV;
    RecyclerView recyclerView;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference dbRef;
    chatAdapter cAdapter;
    HashMap<String, String> profilHaritasi;
    Intent intent;

    public void init() {
        tolbar = (Toolbar) findViewById(R.id.tolbar);
        text1 = (TextView) findViewById(R.id.text1);
        send = (ImageView) findViewById(R.id.send);
        profilHaritasi = new HashMap<String, String>();
        fetchData = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        recyclerView = (RecyclerView) findViewById(R.id.rec1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        cAdapter = new chatAdapter(getApplicationContext(), fetchData);
        edit1 = (EditText) findViewById(R.id.edit);//mesaj girdi alanı ve verilerdeki id kullanıldı
        // btnAdd = (Button) findViewById(R.id.btnAdd);
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
        verileriGetir();



    }
    private void verileriGetir()
    {
        fetchDataV=new ArrayList<>();
        intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        final String name = intent.getStringExtra("name");
        assert userId != null;

        dbRef= FirebaseDatabase.getInstance().getReference("bilgiler").child(mUser.getUid()).child(userId);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot resultSnapshot: snapshot.getChildren()) {
                    text1.setText(name);
                    mesajOku(mUser.getUid(), userId);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesajgirdisi = edit1.getText().toString();
                if (!mesajgirdisi.equals("")) {
                    mesajGonderen(mUser.getUid(), userId, mesajgirdisi);
                } else {
                    Toast.makeText(MessageActivity.this, "Mesaj Alanı Boş Olamaz", Toast.LENGTH_SHORT).show();
                }
                edit1.setText(" ");
            }

        });
    }

    private void mesajOku(String benimId, String aliciId) {
        Intent intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        assert userId != null;
        FirebaseDatabase.getInstance().getReference("Mesajlar").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fetchData.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Chat data = ds.getValue(Chat.class);
                    assert data != null;
                    data.setAliciID(userId);
                    data.setGonderenID(mUser.getUid());
                    if (data.getAliciID().equals(aliciId) && data.getGonderenID().equals(benimId)) {

                        fetchData.add(data);
                    }

                }
                cAdapter = new chatAdapter(getApplicationContext(), fetchData);
                recyclerView.setAdapter(cAdapter);
                cAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void mesajGonderen(String gonderen, String alici, String mesaj) {
        profilHaritasi = new HashMap<>();
        profilHaritasi.put("gonderen", gonderen);
        profilHaritasi.put("alici", alici);
        profilHaritasi.put("mesaj", mesaj);
        FirebaseDatabase.getInstance().getReference("Mesajlar").child(mUser.getUid()).push().setValue(profilHaritasi);//kullanıcı mesajı silmek için childuserıd


    }

}
