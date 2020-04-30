package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisActivity extends AppCompatActivity {

    EditText kullaniciAdiEditText;
    Button btn_giris;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimlama();

    }
    public void tanimlama(){
        kullaniciAdiEditText = findViewById(R.id.kullaniciAdiEditText);
        btn_giris = findViewById(R.id.btn_giris);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String k_adi = kullaniciAdiEditText.getText().toString();
                kullaniciAdiEditText.setText("");
                ekle(k_adi);
            }
        });
    }

    public void ekle(final String k_adi){
        reference.child("Kullanıcılar").child(k_adi).child("kullaniciadi").setValue(k_adi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Başarılı kayıt yaptınız.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GirisActivity.this,MainActivity.class);
                    intent.putExtra("k_adi",k_adi);
                    startActivity(intent);
                }
            }
        });
    }
}
