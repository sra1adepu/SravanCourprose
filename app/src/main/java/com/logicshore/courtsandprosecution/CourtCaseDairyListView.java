package com.logicshore.courtsandprosecution;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CourtCaseDairyListView extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
  //  LayoutInflater layoutInflater;
    HashMap<Integer,ArrayList> local_courtCaseHomeListDetailses;
    //  ArrayList<ArrayList> local_casesatges_arraylist;
    Context local_context;
    Boolean state=false;
    public CourtCaseDairyListView(HashMap<Integer,ArrayList> DetailsArrayList, Context context){
        local_courtCaseHomeListDetailses=DetailsArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        local_context=context;
    }
    @Override
    public int getCount() {
        return local_courtCaseHomeListDetailses.size();
    }

    @Override
    public Object getItem(int position) {
        return local_courtCaseHomeListDetailses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view=convertView;
        final ViewHolder holder;
        if (view == null)
        {
            view = inflater.inflate(R.layout.court_case_dairy_list_row, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }
        else
        {
            holder =(ViewHolder) view.getTag();
        }
        final ArrayList<CourtCaseHomeListDetails> data=local_courtCaseHomeListDetailses.get(position);
        Log.d("datasize",""+data.size());
            if (data.size()>0) {


                if(state)
                {
                    holder.stage_case_table.removeAllViews();
                }
                for (int i = 0; i < data.size(); i++) {
                        state = true;
                        TextView crime_number = (TextView) view.findViewById(R.id.ccd_crime_number);
                        holder.crime_number.setText(data.get(i).getCrime_number());
                        holder.courtcase_dairy.setText(data.get(i).getCourtcase_number());
                        holder.charge_sheet_number.setText(data.get(i).getCharge_sheet_number());
                        holder.charge_sheet_dt.setText(data.get(i).getCharge_sheet_dt());
                        holder.court_name.setText(data.get(i).getCourt_name());
                        holder.ccd_psnamme.setText(data.get(i).getPolicestation());
                   // final int finalI = i;
                    final int finalI = i;
                    holder.create_ccd_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("ccsCourtname",holder.court_name.getText().toString());
                            Intent intent=new Intent(local_context,CourtandProsecutionStages.class);
                            intent.putExtra("ccdpolicastationname",data.get(finalI).getPolicestation());
                            intent.putExtra("ccsCourtname",holder.court_name.getText().toString());
                            intent.putExtra("ccdfirnumber",data.get(finalI).getFir_reg_number());
                            intent.putExtra("ccdcourtcasenumber",holder.courtcase_dairy.getText().toString());
                            intent.putExtra("ccdadjournmentno",data.get(finalI).getAdjumenentno());
                            intent.putExtra("trailserialnumber",data.get(finalI).getTrialserialno());
                            local_context.startActivity(intent);
                            }
                         });
                        TableRow tbrow;
                        TableRow.LayoutParams layoutParams_tbrow = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 5f);
                        layoutParams_tbrow.setMargins(10, 0, 10, 0);
                        tbrow = new TableRow(local_context);

                        tbrow.setLayoutParams(layoutParams_tbrow);

                        TableRow.LayoutParams adj_no_tv_textview_lp = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f);
                        TextView adj_no_tv = new TextView(local_context);
                        adj_no_tv.setText(data.get(i).getAdjumenentno());
                        adj_no_tv.setGravity(Gravity.CENTER);
                        adj_no_tv.setBackgroundResource(R.drawable.table_border);
                        adj_no_tv.setLayoutParams(adj_no_tv_textview_lp);
                        tbrow.addView(adj_no_tv);

                        TableRow.LayoutParams stage_case_tv_textview_lp = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2.3f);
                        TextView stage_case_tv = new TextView(local_context);
                        stage_case_tv.setText(data.get(i).getStageofthe_case());
                        Log.d("state", data.get(i).getStageofthe_case() + "\t" + data.get(i).getNextadjence_dt());
                        stage_case_tv.setGravity(Gravity.CENTER);
                        stage_case_tv.setBackgroundResource(R.drawable.table_border);
                        stage_case_tv.setLayoutParams(stage_case_tv_textview_lp);
                        tbrow.addView(stage_case_tv);

                        TableRow.LayoutParams nextadj_dt_tv_textview_lp = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.3f);
                        TextView nextadj_dt_tv = new TextView(local_context);
                        nextadj_dt_tv.setText(data.get(i).getNextadjence_dt());
                        nextadj_dt_tv.setGravity(Gravity.CENTER);
                        nextadj_dt_tv.setBackgroundResource(R.drawable.table_border);
                        nextadj_dt_tv.setLayoutParams(nextadj_dt_tv_textview_lp);
                        tbrow.addView(nextadj_dt_tv);

                        TableRow.LayoutParams action_tv_textview_lp = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                        TextView action_tv = new TextView(local_context);
                        if(data.get(i).getAction().contains("C")) {
                            action_tv.setText("Confirmed");
                        }else if(data.get(i).getAction().contains("M")){
                            action_tv.setText("Modified");
                        }else if(data.get(i).getAction().contains("A")){
                            action_tv.setText("Confirmed/Modified");
                        }
                        action_tv.setGravity(Gravity.CENTER);
                        action_tv.setBackgroundResource(R.drawable.table_border);
                        action_tv.setLayoutParams(action_tv_textview_lp);
                        tbrow.addView(action_tv);
                        holder.stage_case_table.addView(tbrow);
                    }

            }
        return view;
    }

    class ViewHolder
    {
        TextView crime_number,courtcase_dairy,charge_sheet_number,charge_sheet_dt,court_name,ccd_psnamme;
        TableLayout stage_case_table,stageofthecase_table_layout1;
        Button create_ccd_bt;

        public ViewHolder(View view) {
            crime_number=(TextView)view.findViewById(R.id.ccd_crime_number);
            courtcase_dairy=(TextView)view.findViewById(R.id.ccd_courtcase_sheet);
            charge_sheet_number=(TextView)view.findViewById(R.id.ccd_chargesheet);
            charge_sheet_dt=(TextView)view.findViewById(R.id.ccd_chargesheet_dt);
            court_name=(TextView)view.findViewById(R.id.ccd_courtname);
            stage_case_table=(TableLayout)view.findViewById(R.id.stageofthecase_table_layout);
            stageofthecase_table_layout1=(TableLayout)view.findViewById(R.id.stageofthecase_table_layout1);
            create_ccd_bt=(Button)view.findViewById(R.id.create_ccd_bt);
            ccd_psnamme=(TextView)view.findViewById(R.id.ccd_psnamme);
        }

    }
}
