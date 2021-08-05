package com.example.chatappproje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static java.text.DateFormat.getDateInstance;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolderClass>  {
    List<Chat> fetchDataList;
    private Context context;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    private static final int Mesaj_Sag =0;
    public chatAdapter(Context context ,List<Chat> fetchDataList) {
        this.fetchDataList = fetchDataList;
        this.context=context;
    }

    @NonNull
    @Override
    public chatAdapter.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == Mesaj_Sag)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cadapter_layout_sag, parent, false);
            chatAdapter.ViewHolderClass viewHolderClass = new chatAdapter.ViewHolderClass(view);
            return viewHolderClass;
        }
        else
        {
            return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        Chat fetchData = fetchDataList.get(position);
        viewHolderClass.mesaj.setText(fetchData.getMesaj());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

    }

    @Override
    public int getItemCount() {
        return fetchDataList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder  {

       TextView mesaj;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            mesaj=itemView.findViewById(R.id.mesaj);



        }

    }

}


