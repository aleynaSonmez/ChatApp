package com.example.chatappproje;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter<HelperAdapter.ViewHolderClass> {
    List<Veriler> fetchDataList;
    private Context context;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    ClickListener clickListener;
    public HelperAdapter(Context context ,List<Veriler> fetchDataList) {
        this.fetchDataList = fetchDataList;
        this.context=context;
    }

    @NonNull
    @Override
    public HelperAdapter.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_list, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        Veriler fetchData = fetchDataList.get(position);
        viewHolderClass.name.setText(fetchData.getName());
        viewHolderClass.numara.setText(String.valueOf(fetchData.getNumara()));
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = context.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("id",fetchData.getId());
                editor.apply();

            }
        });
            viewHolderClass.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Intent i= new Intent(v.getContext(), kullaniciSil.class);
                    i.putExtra("userId", fetchData.getId());
                    i.putExtra("name", fetchData.getName());
                    i.putExtra("numara", fetchData.getNumara());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     context.startActivity(i);

                }
            });
    }
    @Override
    public int getItemCount() {
        return fetchDataList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, numara, desc;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.desc);
            name = itemView.findViewById(R.id.name);
            numara = itemView.findViewById(R.id.number);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

}
