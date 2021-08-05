package com.example.chatappproje;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DenemeActivity extends AppCompatActivity {
    List<Veriler> fetchData;
    RecyclerView recyclerView;
    FriendsAdapter adapter;
    DatabaseReference mbase;
    FirebaseDatabase database;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    public void init()
    {

        recyclerView= (RecyclerView) findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        fetchData= new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.deneme);
        init();

            database=FirebaseDatabase.getInstance();
        mbase= database.getReference("bilgiler").child(mUser.getUid());
       mbase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> keys = snapshot.getChildren();
                for(DataSnapshot ds : snapshot.getChildren())
                {
                   Veriler data = ds.getValue(Veriler.class);
                                fetchData.add(data);

                }
                adapter=new FriendsAdapter(fetchData, getApplicationContext());
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
