package com.example.project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myviewholder> {

    ArrayList<com.example.project2.Model> data;

    public myadapter(ArrayList<com.example.project2.Model> data) {
        this.data = data;
    }

    @NonNull
    @Override

    //Creates views using layout inflator and returns those views to myviewholder
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    //Binds the views with the data obtained from the arraylist data

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(data.get(position).getHeader());
        holder.t2.setText(data.get(position).getDesc());
        holder.img.setImageResource(data.get(position).getImgname());
    }

    //Returns the size of array
    @Override
    public int getItemCount() {
        return data.size();
    }


}
