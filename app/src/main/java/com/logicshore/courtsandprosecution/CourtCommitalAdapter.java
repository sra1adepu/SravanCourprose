package com.logicshore.courtsandprosecution;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 23-10-2017.
 */

class CourtCommitalAdapter extends BaseAdapter {
    Context context;
    ArrayList<CourtCommitalContent> petitionList;
    private List<CourtCommitalContent> CriminalDetailslist = null;
    CourtCommitalAdapter.ViewHolder nHolder;
    LayoutInflater inflater;
    String commital_cd;

    public CourtCommitalAdapter(ArrayList<CourtCommitalContent> listitem , Context petition){
        context = petition;
        this.CriminalDetailslist=listitem;
        inflater = LayoutInflater.from(context);
        this.petitionList = new ArrayList<CourtCommitalContent>();
        this.petitionList.addAll(listitem);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return CriminalDetailslist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return CriminalDetailslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view=convertView;
        if (view == null)
        {

            view = inflater.inflate(R.layout.courtcommitalsearchitem, null);
            nHolder=new CourtCommitalAdapter.ViewHolder(view);
            view.setTag(nHolder);

        }
        else
        {
            nHolder =(CourtCommitalAdapter.ViewHolder) view.getTag();
        }

        if(CriminalDetailslist.get(position).getCourtcommitalcd().equals("null")){
            commital_cd="null";
        }else{
            commital_cd=CriminalDetailslist.get(position).getCourtcommitalcd();
        }


        if(CriminalDetailslist.get(position).getPolicestation().equals("null")){
            nHolder.policestation.setText("N/A");
        }
        else{
            nHolder.policestation.setText(CriminalDetailslist.get(position).getPolicestation());
        }

        if(CriminalDetailslist.get(position).getFirno().equals("null")){
            nHolder.firno.setText("N/A");
        }else{
            nHolder.firno.setText(CriminalDetailslist.get(position).getFirno());
        }

        if(CriminalDetailslist.get(position).getActsandsections().equals("null")){
            nHolder.actsandsections.setText("N/A");
        }else{
            nHolder.actsandsections.setText(CriminalDetailslist.get(position).getActsandsections());
        }

        if(CriminalDetailslist.get(position).getChargesheetno().equals("null")){
            nHolder.chargesheetno.setText("N/A");
        }else{
            nHolder.chargesheetno.setText(CriminalDetailslist.get(position).getChargesheetno());
        }

        if(CriminalDetailslist.get(position).getPrcno().equals("null")){
            nHolder.prcno.setText("N/A");
        }else{
            nHolder.prcno.setText(CriminalDetailslist.get(position).getPrcno());
        }

        if(CriminalDetailslist.get(position).getDateofcommital().equals("null")){
            nHolder.dateofcommital.setText("N/A");
        }else{
            nHolder.dateofcommital.setText(CriminalDetailslist.get(position).getDateofcommital());
        }

        if(CriminalDetailslist.get(position).getCommitalaction().equals("null")){
            nHolder.commitalaction.setText("N/A");
        }else{
            nHolder.commitalaction.setText(CriminalDetailslist.get(position).getCommitalaction());
        }

        if(CriminalDetailslist.get(position).getScno().equals("null")){
            nHolder.enterscno.setText("N/A");
        }else{
            nHolder.enterscno.setText(CriminalDetailslist.get(position).getScno());
        }

        if(CriminalDetailslist.get(position).getTransferto().equals("null")){
            nHolder.transferedto.setText("N/A");
        }else{
            nHolder.transferedto.setText(CriminalDetailslist.get(position).getTransferto());
        }


        if(CriminalDetailslist.get(position).getCount().equals("null")){
            nHolder.imagetext.setText("N/A");
        }else{
            nHolder.imagetext.setText(CriminalDetailslist.get(position).getCount());
        }



        final String transferfrom= CriminalDetailslist.get(position).getTransferfrom();
       // nHolder.commitalstatus.setText(CriminalDetailslist.get(position).getStatus());
        final String casenumber=CriminalDetailslist.get(position).getCasenumber();        //nHolder.updatestatus.setText(CriminalDetailslist.get(position).getEoname());

//        nHolder.commitalstatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context,CourtCommitalPopup.class);
//                i.putExtra("courtcasenumber",casenumber);
//                context.startActivity(i);
//            }
//        });

        if(CriminalDetailslist.get(position).getStatus()==null || CriminalDetailslist.get(position).getStatus().equals("null"))

        {
            clearText();
            nHolder.commitalstatus.setText(R.string.enter_status);
            nHolder.commitalstatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context,CourtCommitalPopup.class);
                    i.putExtra("courtcasenumber",casenumber);
                    i.putExtra("transfer",transferfrom);
                    i.putExtra("commitalcd",commital_cd);
                    context.startActivity(i);
                }

            });


        }

//        else if((CriminalDetailslist.get(position).getStatus().trim())=="A"||(CriminalDetailslist.get(position).getStatus().trim())=="M"){
//            clearText();
//            nHolder.commitalstatus.setText("Confirm");
//            nHolder.commitalstatus1.setText("Modify");
//            nHolder.updatestatus.setPaintFlags(nHolder.updatestatus.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
//            nHolder.updatestatus1.setPaintFlags(nHolder.updatestatus.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
//
//            nHolder.updatestatus1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(context,ChargeSheetPopup.class);
//                    context.startActivity(i);
//
//                }
//            });
//            nHolder.updatestatus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//                    alertDialog.setMessage("Do You Want Confirm Charge Sheet Status?");
//                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            // show crime number and status
//                        }
//                    });
//                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//                        }
//                    });
//
//
//                    alertDialog.show();
//
//
//                }
//            });


       // }

        else if((CriminalDetailslist.get(position).getStatus().trim()).equals("A")){
           // nHolder.commitalstatus.setText("Confirmed");
            nHolder.commitalstatus.setText("Confirm");
            nHolder.commitalstatus1.setText("Modify");
//           // nHolder.updatestatus1.setText("View");
//            nHolder.updatestatus1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(context,ChargeSheetPopup.class);
//                    i.putExtra("clickvalue","1");
//                    context.startActivity(i);
//                }
//            });
            //   nHolder.updatestatus1.setPaintFlags(nHolder.updatestatus.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        }







        return view;
    }

    private void clearText() {

        nHolder.commitalstatus.setText("");
        nHolder.commitalstatus1.setText("");
    }

    class ViewHolder
    {
        TextView policestation,firno,actsandsections,chargesheetno,prcno,dateofcommital,commitalaction,
                enterscno,transferedto,imagetext,commitalstatus,courtcasenumber,commitalstatus1;
        ImageView imageView2;

        public ViewHolder(View view) {
            //totaltext=(TextView)view.findViewById(R.id.totaltext);
            policestation=(TextView)view.findViewById(R.id.policestation);
            firno=(TextView)view.findViewById(R.id.firno);
            chargesheetno=(TextView)view.findViewById(R.id.chargesheetno);
            prcno=(TextView)view.findViewById(R.id.prcno);
            dateofcommital=(TextView)view.findViewById(R.id.dateofcommital);
            commitalaction=(TextView)view.findViewById(R.id.commitalaction);
            actsandsections=(TextView)view.findViewById(R.id.actsandsections);
            enterscno=(TextView)view.findViewById(R.id.enterscno);
            transferedto=(TextView)view.findViewById(R.id.transferedto);
            imagetext=(TextView)view.findViewById(R.id.imagetext);
            commitalstatus=(TextView)view.findViewById(R.id.commitalstatus);
            courtcasenumber=(TextView)view.findViewById(R.id.courtcasenumber);
            commitalstatus1=(TextView)view.findViewById(R.id.commitalstatus1);

        }

    }
}
