package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String k_adi,diger_kadi;
    TextView chatKullaniciname;
    ImageView backImage, SendImage;
    EditText chatEdittext;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView chatRecyclerView;
    MesajAdapter mesajAdapter;
    List<MesajModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tanimla();
        loadMesaj();
    }

    public void tanimla(){
        list = new ArrayList();
        k_adi = getIntent().getExtras().getString("k_adi");
        diger_kadi = getIntent().getExtras().getString("diger_kadi");
        //Log.i("alinandegerler",k_adi+"-----"+diger_kadi);
        chatKullaniciname = findViewById(R.id.chatKullaniciname);
        backImage = findViewById(R.id.backImage);
        SendImage = findViewById(R.id.SendImage);
        chatEdittext = findViewById(R.id.chatEdittext);
        chatKullaniciname.setText(diger_kadi);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this,MainActivity.class);
                intent.putExtra("k_adi",k_adi);
                startActivity(intent);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        SendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesaj = chatEdittext.getText().toString();
                chatEdittext.setText("");
                mesajGönder(mesaj);
            }
        });

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChatActivity.this,1);
        chatRecyclerView.setLayoutManager(layoutManager);

        mesajAdapter = new MesajAdapter(ChatActivity.this,list,ChatActivity.this,k_adi);
        chatRecyclerView.setAdapter(mesajAdapter);
    }

    public void mesajGönder(String text){

        final String key = reference.child("Mesajlar").child(k_adi).child(diger_kadi).push().getKey();
        final Map mesajMap = new HashMap();
        mesajMap.put("text",text);
        mesajMap.put("from",k_adi);
        reference.child("Mesajlar").child(k_adi).child(diger_kadi).child(key).setValue(mesajMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    reference.child("Mesajlar").child(diger_kadi).child(k_adi).child(key).setValue(mesajMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }

            }
        });
    }

    public void loadMesaj(){
        reference.child("Mesajlar").child(k_adi).child(diger_kadi).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    MesajModel mesajModel = dataSnapshot.getValue(MesajModel.class);
                    list.add(mesajModel);
                    mesajAdapter.notifyDataSetChanged();
                    chatRecyclerView.scrollToPosition(list.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
