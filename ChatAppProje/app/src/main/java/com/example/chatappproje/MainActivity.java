package com.example.chatappproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    private Toolbar actionbar;
    private ViewPager vpMain;
    private TabLayout tabsMain;
    private TabsAdapter tabsAdapter;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private FriendsFragment frg;
    TextView username;


    DatabaseReference reference;
    public void init()
    {

        actionbar =(Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionbar);
        actionbar.setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth =FirebaseAuth.getInstance();
        currentUser =auth.getCurrentUser();

        username = findViewById(R.id.username);

        currentUser =FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        frg= new FriendsFragment();
        frg.getFragmentManager();

        vpMain = (ViewPager) findViewById(R.id.vpMain);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        vpMain.setAdapter(tabsAdapter);
        tabsMain = (TabLayout) findViewById(R.id.tabsMain);
        tabsMain.setupWithViewPager(vpMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



    }
    @Override
    protected void onStart() {

        if (currentUser == null) {
            Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(welcomeIntent);
            finish();
        }
        super.onStart();
    }
    @Override
     public boolean onCreateOptionsMenu(Menu menu)
     {
         super.onCreateOptionsMenu(menu);
         getMenuInflater().inflate(R.menu.menu_main,menu);
         return true;
     }
     public boolean onOptionsItemSelected (MenuItem item)
     {

         super.onOptionsItemSelected(item);

         if(item.getItemId() == R.id.kullaniciayarlari)
         {
             Intent kullaniciAyarlari = new Intent(MainActivity.this, com.example.chatappproje.kullaniciAyarlari.class);
             startActivity(kullaniciAyarlari);
             finish();
         }
         if(item.getItemId() == R.id.mainLogout)
         {
             auth.signOut();
             Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
             startActivity(loginIntent);
             finish();
         }
         return true;
     }
}