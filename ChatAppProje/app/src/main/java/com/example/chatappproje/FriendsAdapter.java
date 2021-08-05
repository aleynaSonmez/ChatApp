package com.example.chatappproje;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter {

   List<Veriler> fetcDataList;
    private Context context;

    public FriendsAdapter(List<Veriler> fetchData,Context context) {
       this.fetcDataList=fetchData;
       this.context=context;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
       ViewHolderClass viewHolderClass = new ViewHolderClass(view);

       return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
                Veriler fetchData=fetcDataList.get(position);
                 viewHolderClass.number.setText(fetchData.getNumara());
                viewHolderClass.name.setText(fetchData.getName());
                viewHolderClass.mesajGonder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i= new Intent(v.getContext(), MessageActivity.class);
                        i.putExtra("userId", fetchData.getId());
                        i.putExtra("name",fetchData.getName());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);


                    }
                });




    }

    @Override
    public int getItemCount() {
        return fetcDataList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView name,number;
        ImageView foto,mesajGonder;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
                foto=itemView.findViewById(R.id.foto);
               name=itemView.findViewById(R.id.name1);
               number=itemView.findViewById(R.id.numara2);
               mesajGonder=itemView.findViewById(R.id.mesajGonder);


        }



    }
}

