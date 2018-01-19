package com.logicshore.courtsandprosecution;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 07-12-2017.
 */

class CustomRecyclierAdapter extends RecyclerView.Adapter{
    Context context;
    ArrayList<String> names;
    ArrayList<Integer> images;
    public CustomRecyclierAdapter(CourtsandProsecutionMain courtsandProsecutionMain, ArrayList names, ArrayList images) {
        context=courtsandProsecutionMain;
        this.names=names;
        this.images=images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_grid_item,parent,false);
        MyViewHolder vh= new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myviewholder=(MyViewHolder) holder;
        myviewholder.imageview.setImageResource(images.get(position));
        myviewholder.nameofimage.setText(names.get(position));


        myviewholder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ClickedPosition", names.get(position).toString());
                if(names.get(position).toString().equals("Charge Sheet Status")){
                    Intent intent= new Intent(context,SearchItemPopup.class);
                    context.startActivity(intent);
                }
                else if(names.get(position).toString().equals("Court Commital")){
                    Intent intent= new Intent(context,CourtCommitalSearch.class);
                    context.startActivity(intent);
                }
                else if(names.get(position).toString().equals("Court Transfer")){
                    Intent intent= new Intent(context,CourtTransferSearch.class);
                    context.startActivity(intent);

                }else if(names.get(position).toString().equals("Court Case Dairy")){
                    Intent intent= new Intent(context,CourtandProsecutionStages.class);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView nameofimage;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageview=(ImageView)itemView.findViewById(R.id.imageview);
            nameofimage=(TextView)itemView.findViewById(R.id.nameofimage);
        }
    }
}
