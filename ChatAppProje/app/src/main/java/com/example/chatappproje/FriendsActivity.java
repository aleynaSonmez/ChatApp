package com.example.chatappproje;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class FriendsActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private Button arkadas_gtr, btn_yeniKisi;
    private EditText isim1;
    private EditText numara1;

    private RecyclerView recycler1;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser mUser;
    HashMap<String, Object> kisiler;

    FirebaseAuth mAuth;
    FriendsAdapter cAdapter;
    public FriendsActivity() {
    }

    public void init() {
        numara1 = (EditText) findViewById(R.id.numara);
        isim1 = (EditText) findViewById(R.id.isim);
        btn_yeniKisi = (Button) findViewById(R.id.yeniKisi);
        arkadas_gtr = (Button) findViewById(R.id.arkadaslar);
        recycler1 = (RecyclerView) findViewById(R.id.recycler1);
        firebaseDatabase = FirebaseDatabase.getInstance();
            mAuth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        init();
        btn_yeniKisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad = isim1.getText().toString();
                String numara =numara1.getText().toString();
                if(TextUtils.isEmpty(ad))
                {
                    Toast.makeText(FriendsActivity.this, "İsim Alanı Boş Olamaz !",Toast.LENGTH_LONG).show();

                }
                else if(TextUtils.isEmpty(numara))
                {
                    Toast.makeText(FriendsActivity.this, "Numara Alanı Boş Olamaz !",Toast.LENGTH_LONG).show();

                }
                else
                {
                    yeniKisi();


                }
            }
        });

        arkadas_gtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FriendsActivity.this,DenemeActivity.class);
                startActivity(i);
            }
        });

    }

 private void yeniKisi() {
      DatabaseReference dbRef ;
      mUser=mAuth.getCurrentUser();
      dbRef= firebaseDatabase.getReference("bilgiler").child(mUser.getUid());

      Veriler model = new Veriler();
      String id=dbRef.push().getKey();
      model.setId(id);
      model.setNumara(numara1.getText().toString());
      model.setName(isim1.getText().toString());
      assert id != null;
      dbRef.child(model.getId()).setValue(model);

    }
    }

