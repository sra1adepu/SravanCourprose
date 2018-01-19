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
 * Created by admin on 18-10-2017.
 */

class CourtTransferAdapter extends BaseAdapter {
    Context context;
    ArrayList<CourtTransferContent> petitionList;
    private List<CourtTransferContent> CriminalDetailslist = null;
    LayoutInflater inflater;

    public CourtTransferAdapter(ArrayList<CourtTransferContent> listitem , Context petition){
        context = petition;
        this.CriminalDetailslist=listitem;
        inflater = LayoutInflater.from(context);
        this.petitionList = new ArrayList<CourtTransferContent>();
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view=convertView;
        final CourtTransferAdapter.ViewHolder nHolder;
        if (view == null)
        {

            view = inflater.inflate(R.layout.courttransfersearchitem, null);
            nHolder=new CourtTransferAdapter.ViewHolder(view);
            view.setTag(nHolder);

        }
        else
        {
            nHolder =(CourtTransferAdapter.ViewHolder) view.getTag();
        }


        if(CriminalDetailslist.get(position).getPolicestation().equals("null")){
            nHolder.policestation.setText("N/A");
        }else{
            nHolder.policestation.setText(CriminalDetailslist.get(position).getPolicestation());
        }

        if(CriminalDetailslist.get(position).getCrimeno().equals("null")){
            nHolder.courtno.setText("N/A");
        }
        else{
            nHolder.courtno.setText(CriminalDetailslist.get(position).getCrimeno());
        }

        if(CriminalDetailslist.get(position).getChargesheetno().equals("null")){
            nHolder.chargesheetno.setText("N/A");
        }
        else{
            nHolder.chargesheetno.setText(CriminalDetailslist.get(position).getChargesheetno());
        }

        if(CriminalDetailslist.get(position).getCaseno().equals("null")){
            nHolder.caseno.setText("N/A");
        }
        else{
            nHolder.caseno.setText(CriminalDetailslist.get(position).getCaseno());
        }

        if(CriminalDetailslist.get(position).getCourtnogivenbyreceived().equals("null")){
            nHolder.givenbrcourt.setText("N/A");
        }
        else{
            nHolder.givenbrcourt.setText(CriminalDetailslist.get(position).getCourtnogivenbyreceived());
        }

        if(CriminalDetailslist.get(position).getTransferedfrom().equals("null")){
            nHolder.trasferedfrom.setText("N/A");
        }
        else{
            nHolder.trasferedfrom.setText(CriminalDetailslist.get(position).getTransferedfrom());
        }

        if(CriminalDetailslist.get(position).getActsndsections().equals("null")){
            nHolder.actndsections.setText("N/A");
        }
        else{
            nHolder.actndsections.setText(CriminalDetailslist.get(position).getActsndsections());
        }

        if(CriminalDetailslist.get(position).getAction().equals("null")){
            nHolder.action.setText("N/A");
        }
        else{
            nHolder.action.setText(CriminalDetailslist.get(position).getAction());
        }

//        if(CriminalDetailslist.get(position).getAction().equals("null")){
//            nHolder.action.setText("N/A");
//        }
//        else{
//            nHolder.action.setText(CriminalDetailslist.get(position).getAction());
//        }


        nHolder.transferedto.setText(CriminalDetailslist.get(position).getTransferedto());
        nHolder.imagetext.setText(CriminalDetailslist.get(position).getCount());
        //nHolder.updatestatus.setText(CriminalDetailslist.get(position).getEoname());
//        nHolder.transferedto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent i = new Intent(context,CourtTranfer.class);
//                i.putExtra("Courtcasenumber",CriminalDetailslist.get(position).getCaseno());
//                context.startActivity(i);
//            }
//        });


        if(CriminalDetailslist.get(position).getTransferedto()==null || CriminalDetailslist.get(position).getTransferedto().equals("null"))

        {
            nHolder.transferedto.setText(R.string.enter_status);
            nHolder.transferedto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //CourtTransferSearch.callflag=true;
                    Intent i = new Intent(context,CourtTranfer.class);
                    i.putExtra("Courtcasenumber",CriminalDetailslist.get(position).getCaseno());
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

        else if((CriminalDetailslist.get(position).getTransferedto().trim()).equals("A")){
            // nHolder.commitalstatus.setText("Confirmed");
            nHolder.transferedto.setText("Confirm");
            nHolder.transferedto1.setText("Modify");
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
    class ViewHolder
    {
        TextView policestation,courtno,chargesheetno,caseno,givenbrcourt,trasferedfrom,actndsections,
                action,transferedto,imagetext,transferedto1;
        ImageView imageView2;

        public ViewHolder(View view) {
            //totaltext=(TextView)view.findViewById(R.id.totaltext);
            policestation=(TextView)view.findViewById(R.id.policestation);
            courtno=(TextView)view.findViewById(R.id.courtno);
            chargesheetno=(TextView)view.findViewById(R.id.chargesheetno);
            caseno=(TextView)view.findViewById(R.id.caseno);
            givenbrcourt=(TextView)view.findViewById(R.id.givenbrcourt);
            trasferedfrom=(TextView)view.findViewById(R.id.trasferedfrom);
            actndsections=(TextView)view.findViewById(R.id.actndsections);
            action=(TextView)view.findViewById(R.id.action);
            transferedto=(TextView)view.findViewById(R.id.transferedto);
            imagetext=(TextView)view.findViewById(R.id.imagetext);
            transferedto1=(TextView)view.findViewById(R.id.transferedto1);

        }

    }
}


