package com.example.project2;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class myviewholder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
    ImageView img;
    TextView t1,t2;
    CardView c;
    static int x;
    static int y;
    public myviewholder(@NonNull View itemView) {
        super(itemView);
        //Storing all the references from view and passing them to myadapter
        img  = (ImageView)itemView.findViewById(R.id.img1);
        t1 = (TextView)itemView.findViewById(R.id.t1);
        t2 = (TextView)itemView.findViewById(R.id.t2);
        c = (CardView)itemView.findViewById(R.id.sr);
        c.setOnCreateContextMenuListener(this::onCreateContextMenu);
    }

    @Override

    //Used to add data to the context menu, adapter position recorded by variable x through which context menu displays appropriate data
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.add(this.getAdapterPosition(),100, 0,"Video clip");
        menu.add(this.getAdapterPosition(),101, 0,"Song's Wikipedia page");
        menu.add(this.getAdapterPosition(),102, 0,"Artists/Band Wikipedia page");
        x = this.getAdapterPosition();
    }





}
