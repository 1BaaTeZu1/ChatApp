package com.example.chatapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KullaniciAdapter extends RecyclerView.Adapter<KullaniciAdapter.ViewHolder> {
    Context context;
    List<String> list;
    Activity activity;
    String k_adi;

    public KullaniciAdapter(Context context, List<String> list, Activity activity, String k_adi) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.k_adi = k_adi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kullanici_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textView.setText(list.get(position).toString());
        holder.kullaniciAnalayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ChatActivity.class);
                intent.putExtra("k_adi",k_adi);
                intent.putExtra("diger_kadi",list.get(position).toString());
                activity.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        LinearLayout kullaniciAnalayout;
        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.k_adi);
            kullaniciAnalayout = itemView.findViewById(R.id.kullaniciAnalayout);
        }
    }
}
