package com.example.chatappproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class kullaniciAyarlari extends AppCompatActivity {

    private Button ayarlariguncelleme, geridönüsbuton;
    private EditText kullaniciadi, kullanicidurumu;
    private CircleImageView kullanici_resmi;
    private FirebaseAuth mYetki;
    private String mevcutkullaniciid;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private DatabaseReference veriyolu;

    public void init() {
        kullanici_resmi = (CircleImageView) findViewById(R.id.circularimageview);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_ayarlari);
        init();
        mYetki = FirebaseAuth.getInstance();

        mevcutkullaniciid = mYetki.getCurrentUser().getUid();
        ayarlariguncelleme = findViewById(R.id.ayarlariguncellemebutonu);
        kullaniciadi = findViewById(R.id.kullaniciadiayarla);
        kullanicidurumu = findViewById(R.id.kullanicidurum);
        kullanici_resmi = findViewById(R.id.circularimageview);
        geridönüsbuton = (Button) findViewById(R.id.geridönüs);


        veriyolu = FirebaseDatabase.getInstance().getReference();
        kullanici_resmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        ayarlariguncelleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AyarlariGuncelle();
            }
        });

        geridönüsbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mesajekrani = new Intent(kullaniciAyarlari.this, MainActivity.class);
                startActivity(mesajekrani);
                finish();
            }
        });


        KullaniciBilgisiAl();
    }

    private void KullaniciBilgisiAl() {
        veriyolu.child("Kullanicilar").child(mevcutkullaniciid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.exists()) && (snapshot.hasChild("ad") && (snapshot.hasChild("resim")))) {

                    String kullaniciadinial = snapshot.child("ad").getValue().toString();
                    String kullanicidurumunual = snapshot.child("durum").getValue().toString();
                    String kullaniciresminial = snapshot.child("resim").getValue().toString();

                    kullaniciadi.setText(kullaniciadinial);
                    kullanicidurumu.setText(kullanicidurumunual);

                } else if ((snapshot.exists()) && (snapshot.hasChild("ad"))) {


                    String kullaniciadinial = snapshot.child("ad").getValue().toString();
                    String kullanicidurumunual = snapshot.child("durum").getValue().toString();


                    kullaniciadi.setText(kullaniciadinial);
                    kullanicidurumu.setText(kullanicidurumunual);

                } else {
                    Toast.makeText(kullaniciAyarlari.this, "Lütfen profil bilgilerinizi ayarlayın", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void AyarlariGuncelle() {
        String kullaniciadiayarla = kullaniciadi.getText().toString();
        String kullanicidurumuayarla = kullanicidurumu.getText().toString();

        if (TextUtils.isEmpty(kullaniciadiayarla)) {
            Toast.makeText(kullaniciAyarlari.this, "Lütfen adinizi yaziniz...", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(kullanicidurumuayarla)) {
            Toast.makeText(kullaniciAyarlari.this, "Lütfen durumunuzu yazınız...", Toast.LENGTH_LONG).show();

        } else {
            HashMap<String, String> profilHaritasi = new HashMap<>();
            profilHaritasi.put("uid", mevcutkullaniciid);
            profilHaritasi.put("ad", kullaniciadiayarla);
            profilHaritasi.put("durum", kullanicidurumuayarla);
            veriyolu.child("Kullanicilar").child(mevcutkullaniciid).setValue(profilHaritasi).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(kullaniciAyarlari.this, "Profiliniz basarili bir sekilde guncellendi...", Toast.LENGTH_LONG).show();
                        Intent anasayfa = new Intent(kullaniciAyarlari.this, MainActivity.class);
                        anasayfa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(anasayfa);
                        finish();
                    } else {
                        String mesaj = task.getException().toString();
                        Toast.makeText(kullaniciAyarlari.this, "Hata" + mesaj, Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                kullanici_resmi.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

 /*   private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("yükleniyor...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(kullaniciAyarlari.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(kullaniciAyarlari.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }


    }
*/
}
