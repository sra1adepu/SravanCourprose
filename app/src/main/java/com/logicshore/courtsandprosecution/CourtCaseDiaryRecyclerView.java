package com.logicshore.courtsandprosecution;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LENOVO on 02-08-2017.
 */

public class CourtCaseDiaryRecyclerView extends RecyclerView.Adapter<CourtCaseDiaryRecyclerView.MyViewHolder> {
    HashMap<Integer,ArrayList> local_courtCaseHomeListDetailses;
  //  ArrayList<ArrayList> local_casesatges_arraylist;
    Context local_context;
    LayoutInflater layoutInflater;
    HashMap<Integer,Bitmap> map;
    LayoutInflater inflater;
    RecyclerView.ViewHolder holder;
    ArrayList<Boolean> state=new ArrayList<>();
    int i=1;

    public CourtCaseDiaryRecyclerView(HashMap<Integer,ArrayList> DetailsArrayList, Context context){
        local_courtCaseHomeListDetailses=DetailsArrayList;

        local_context=context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView crime_number,courtcase_dairy,charge_sheet_number,charge_sheet_dt,court_name;
        TableLayout stage_case_table;


        public MyViewHolder(View view) {
            super(view);
            crime_number=(TextView)view.findViewById(R.id.ccd_crime_number);
            courtcase_dairy=(TextView)view.findViewById(R.id.ccd_courtcase_sheet);
            charge_sheet_number=(TextView)view.findViewById(R.id.ccd_chargesheet);
            charge_sheet_dt=(TextView)view.findViewById(R.id.ccd_chargesheet_dt);
            court_name=(TextView)view.findViewById(R.id.ccd_courtname);
            stage_case_table=(TableLayout)view.findViewById(R.id.stageofthecase_table_layout);
            Log.d("i vlues is",i+"");
            i++;
//            Log.d("case stage array ", String.valueOf(local_casesatges_arraylist.size()));
//              Log.d("i value",""+i);
//            ArrayList<CourtCaseHomeListDetails> data=local_courtCaseHomeListDetailses.get(i);
//            i++;
//
//            for(int k=0;k<data.size();k++) {
//                TableRow tbrow;
//                TableRow.LayoutParams layoutParams_tbrow = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 5);
//                tbrow = new TableRow(local_context);
//                tbrow.setLayoutParams(layoutParams_tbrow);
//
//                TableRow.LayoutParams adj_no_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f);
//                TextView adj_no_tv = new TextView(local_context);
//                adj_no_tv.setText(data.get(k).getAdjumenentno());
//                adj_no_tv.setGravity(Gravity.CENTER);
//                adj_no_tv.setBackgroundResource(R.drawable.table_border);
//                adj_no_tv.setLayoutParams(adj_no_tv_textview_lp);
//                tbrow.addView(adj_no_tv);
//
//                TableRow.LayoutParams stage_case_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
//                TextView stage_case_tv = new TextView(local_context);
//                stage_case_tv.setText(data.get(k).getStageofthe_case());
//                stage_case_tv.setGravity(Gravity.CENTER);
//                stage_case_tv.setBackgroundResource(R.drawable.table_border);
//                stage_case_tv.setLayoutParams(stage_case_tv_textview_lp);
//                tbrow.addView(stage_case_tv);
//
//                TableRow.LayoutParams nextadj_dt_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f);
//                TextView nextadj_dt_tv = new TextView(local_context);
//                nextadj_dt_tv.setText(data.get(k).getNextadjence_dt());
//                nextadj_dt_tv.setGravity(Gravity.CENTER);
//                nextadj_dt_tv.setBackgroundResource(R.drawable.table_border);
//                nextadj_dt_tv.setLayoutParams(nextadj_dt_tv_textview_lp);
//                tbrow.addView(nextadj_dt_tv);
//
//                TableRow.LayoutParams action_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.6f);
//                TextView action_tv = new TextView(local_context);
//                action_tv.setText(data.get(k).getAction());
//                action_tv.setGravity(Gravity.CENTER);
//                action_tv.setBackgroundResource(R.drawable.table_border);
//                action_tv.setLayoutParams(action_tv_textview_lp);
//                tbrow.addView(action_tv);
//                stage_case_table.addView(tbrow);
//
//            }

        }
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.court_case_dairy_list_row, parent, false);
        return new MyViewHolder(itemView);
    }


    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position) + "arralistindex" + local_courtCaseHomeListDetailses.size());
        ArrayList<CourtCaseHomeListDetails> data=local_courtCaseHomeListDetailses.get(position);
        if(!state.get(position)) {
            Log.d("bind state"+position+":",state.get(position)+"");
            state.add(position,true);
        if (data.size()>0) {
            for (int i = 0; i < data.size(); i++) {
                holder.crime_number.setText(data.get(i).getCrime_number());
                holder.courtcase_dairy.setText(data.get(i).getCourtcase_number());
                holder.charge_sheet_number.setText(data.get(i).getCharge_sheet_number());
                holder.charge_sheet_dt.setText(data.get(i).getCharge_sheet_dt());
                holder.court_name.setText(data.get(i).getCourt_name());
                TableRow tbrow;
                TableRow.LayoutParams layoutParams_tbrow = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,6);
                tbrow = new TableRow(local_context);
                tbrow.setLayoutParams(layoutParams_tbrow);

                TableRow.LayoutParams adj_no_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f);
                TextView adj_no_tv = new TextView(local_context);
                adj_no_tv.setText(data.get(i).getAdjumenentno());
                adj_no_tv.setGravity(Gravity.CENTER);
                adj_no_tv.setBackgroundResource(R.drawable.table_border);
                adj_no_tv.setLayoutParams(adj_no_tv_textview_lp);
                tbrow.addView(adj_no_tv);

                TableRow.LayoutParams stage_case_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
                TextView stage_case_tv = new TextView(local_context);
                stage_case_tv.setText(data.get(i).getStageofthe_case());
                Log.d("state", data.get(i).getStageofthe_case() + "\t" + data.get(i).getNextadjence_dt());
                stage_case_tv.setGravity(Gravity.CENTER);
                stage_case_tv.setBackgroundResource(R.drawable.table_border);
                stage_case_tv.setLayoutParams(stage_case_tv_textview_lp);
                tbrow.addView(stage_case_tv);

                TableRow.LayoutParams nextadj_dt_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f);
                TextView nextadj_dt_tv = new TextView(local_context);
                nextadj_dt_tv.setText(data.get(i).getNextadjence_dt());
                nextadj_dt_tv.setGravity(Gravity.CENTER);
                nextadj_dt_tv.setBackgroundResource(R.drawable.table_border);
                nextadj_dt_tv.setLayoutParams(nextadj_dt_tv_textview_lp);
                tbrow.addView(nextadj_dt_tv);

                TableRow.LayoutParams action_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.6f);
                TextView action_tv = new TextView(local_context);
                action_tv.setText(data.get(i).getAction());
                action_tv.setGravity(Gravity.CENTER);
                action_tv.setBackgroundResource(R.drawable.table_border);
                action_tv.setLayoutParams(action_tv_textview_lp);
                tbrow.addView(action_tv);
                holder.stage_case_table.addView(tbrow);
             //   holder.stage_case_table.setId(position);
            }
        }
        }
        else
        {
            Log.d("binding",position+"");

        //   holder.stage_case_table.removeViewAt(position);
            Log.d("bind state"+position+":",state.get(position)+"");


        }
    }
//
//    @Override
//    public void onViewRecycled(MyViewHolder holder) {
//      //  holder.getAdapterPosition();
//
//        Log.d("holder position",""+holder.getAdapterPosition());
//        ArrayList<CourtCaseHomeListDetails> data=local_courtCaseHomeListDetailses.get(holder.getAdapterPosition());
//        for(int k=0;k<data.size();k++) {
//            TableRow tbrow;
//            TableRow.LayoutParams layoutParams_tbrow = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 5);
//            tbrow = new TableRow(local_context);
//            tbrow.setLayoutParams(layoutParams_tbrow);
//
//            TableRow.LayoutParams adj_no_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f);
//            TextView adj_no_tv = new TextView(local_context);
//            adj_no_tv.setText(data.get(k).getAdjumenentno());
//            adj_no_tv.setGravity(Gravity.CENTER);
//            adj_no_tv.setBackgroundResource(R.drawable.table_border);
//            adj_no_tv.setLayoutParams(adj_no_tv_textview_lp);
//            tbrow.addView(adj_no_tv);
//
//            TableRow.LayoutParams stage_case_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
//            TextView stage_case_tv = new TextView(local_context);
//            stage_case_tv.setText(data.get(k).getStageofthe_case());
//            stage_case_tv.setGravity(Gravity.CENTER);
//            stage_case_tv.setBackgroundResource(R.drawable.table_border);
//            stage_case_tv.setLayoutParams(stage_case_tv_textview_lp);
//            tbrow.addView(stage_case_tv);
//
//            TableRow.LayoutParams nextadj_dt_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f);
//            TextView nextadj_dt_tv = new TextView(local_context);
//            nextadj_dt_tv.setText(data.get(k).getNextadjence_dt());
//            nextadj_dt_tv.setGravity(Gravity.CENTER);
//            nextadj_dt_tv.setBackgroundResource(R.drawable.table_border);
//            nextadj_dt_tv.setLayoutParams(nextadj_dt_tv_textview_lp);
//            tbrow.addView(nextadj_dt_tv);
//
//            TableRow.LayoutParams action_tv_textview_lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.6f);
//            TextView action_tv = new TextView(local_context);
//            action_tv.setText(data.get(k).getAction());
//            action_tv.setGravity(Gravity.CENTER);
//            action_tv.setBackgroundResource(R.drawable.table_border);
//            action_tv.setLayoutParams(action_tv_textview_lp);
//            tbrow.addView(action_tv);
//            holder.stage_case_table.addView(tbrow);
//
//        }
//        super.onViewRecycled(holder);
//    }

    @Override
    public int getItemCount() {

        return local_courtCaseHomeListDetailses.size();
    }

}
