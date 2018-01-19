package com.logicshore.courtsandprosecution;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17-10-2017.
 */

class ChargeSheetAdapter extends BaseAdapter {
    Context context;
    ArrayList<ChargeSheetContent> petitionList;
    private List<ChargeSheetContent> CriminalDetailslist = null;
    LayoutInflater inflater;

    public ChargeSheetAdapter(ArrayList<ChargeSheetContent> listitem , Context petition){
        context = petition;
        this.CriminalDetailslist=listitem;
        inflater = LayoutInflater.from(context);
        this.petitionList = new ArrayList<ChargeSheetContent>();
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
        final ViewHolder nHolder;
        if (view == null)
        {

            view = inflater.inflate(R.layout.searchitem, null);
            nHolder=new ViewHolder(view);
            view.setTag(nHolder);

        }
        else
        {
            nHolder =(ViewHolder) view.getTag();
        }

//        nHolder.policestation.setText(CriminalDetailslist.get(position).getPolicestation());
//        nHolder.courtno.setText(CriminalDetailslist.get(position).getCrimeno());
//        nHolder.chargesheetno.setText(CriminalDetailslist.get(position).getChargesheetno());
//        nHolder.chargesheetdate.setText(CriminalDetailslist.get(position).getChargesheetdate());
//        nHolder.courtcaseno.setText(CriminalDetailslist.get(position).getCourtcaseno());
//        nHolder.chargesheetstatus.setText(CriminalDetailslist.get(position).getChargesheetstatus());
//        nHolder.changecasetypetocc.setText(CriminalDetailslist.get(position).getChangetypetocc());
//        nHolder.imagetext.setText(CriminalDetailslist.get(position).getCount());
//        nHolder.casenumber.setText(CriminalDetailslist.get(position).getCourtcasenumber());
//        nHolder.courtcasesrno.setText(CriminalDetailslist.get(position).getCourtcasesrnumber());


        if(CriminalDetailslist.get(position).getPolicestation().equals("null")){
            nHolder.policestation.setText("N/A") ;
        }
        else{
            nHolder.policestation.setText(CriminalDetailslist.get(position).getPolicestation());
        }

        if(CriminalDetailslist.get(position).getCrimeno().equals("null")){
            nHolder.courtno.setText("N/A") ;
        }
        else{
            nHolder.courtno.setText(CriminalDetailslist.get(position).getCrimeno());
        }

        if(CriminalDetailslist.get(position).getChargesheetno().equals("null")){
            nHolder.chargesheetno.setText("N/A") ;
        }
        else{
            nHolder.chargesheetno.setText(CriminalDetailslist.get(position).getChargesheetno());
        }

        if(CriminalDetailslist.get(position).getChargesheetdate().equals("null")){
            nHolder.chargesheetdate.setText("N/A") ;
        }
        else{
            nHolder.chargesheetdate.setText(CriminalDetailslist.get(position).getChargesheetdate());
        }



        if(CriminalDetailslist.get(position).getCourtcaseno().equals("null")){
            nHolder.courtcaseno.setText("N/A") ;
        }
        else{
            nHolder.courtcaseno.setText(CriminalDetailslist.get(position).getCourtcaseno());
        }

        if(CriminalDetailslist.get(position).getChargesheetstatus().equals("null")){
            nHolder.chargesheetstatus.setText("N/A") ;
        }
        else{
            nHolder.chargesheetstatus.setText(CriminalDetailslist.get(position).getChargesheetstatus());
        }


        nHolder.changecasetypetocc.setText(CriminalDetailslist.get(position).getChangetypetocc());


        if(CriminalDetailslist.get(position).getCourtcasenumber().equals("null")){
            nHolder.casenumber.setText("N/A") ;
        }
        else{
            nHolder.casenumber.setText(CriminalDetailslist.get(position).getCourtcasenumber());
        }

        nHolder.imagetext.setText(CriminalDetailslist.get(position).getCount());

        if(CriminalDetailslist.get(position).getCourtcasesrnumber().equals("null")){
            nHolder.courtcasesrno.setText("N/A") ;
        }
        else{
            nHolder.courtcasesrno.setText(CriminalDetailslist.get(position).getCourtcasesrnumber());
        }




        Log.d("sra1","-->"+CriminalDetailslist.get(position).getCourtcasenumber());
        String status=CriminalDetailslist.get(position).getUpdatestatus();

        nHolder.updatestatus.setText("");
        nHolder.updatestatus1.setText("");
        if(CriminalDetailslist.get(position).getUpdatestatus()==null || CriminalDetailslist.get(position).getUpdatestatus().equals("null"))

        {
            nHolder.updatestatus.setText(R.string.enter_status);
            nHolder.updatestatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchItemPopup.callflag=true;
                    Intent i = new Intent(context,ChargeSheetPopup.class);

                    i.putExtra("casenumber",CriminalDetailslist.get(position).getCourtcasenumber());
                    i.putExtra("courtcasenumber",CriminalDetailslist.get(position).getCourtcaseno());
                    i.putExtra("courtcaseSRNumber",CriminalDetailslist.get(position).getCourtcasesrnumber());
                    context.startActivity(i);
                }

            });
           // SearchItemPopup.serviceflg=true;
        }

//        else if((CriminalDetailslist.get(position).getUpdatestatus())=="A"||(CriminalDetailslist.get(position).getUpdatestatus())=="M"){
//            nHolder.updatestatus.setText("Confirm");
//            nHolder.updatestatus1.setText("Modify");
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
//
//
//        }

        else if((CriminalDetailslist.get(position).getUpdatestatus().trim()).equals("C")){
            nHolder.updatestatus.setText("Confirmed");

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
    static class ViewHolder
    {
        TextView updatestatus,policestation,courtno,chargesheetno,casenumber,courtcasesrno,
                chargesheetdate,courtcaseno,chargesheetstatus,changecasetypetocc,imagetext,updatestatus1;
        ImageView imageView2;


        public ViewHolder(View view) {
            //totaltext=(TextView)view.findViewById(R.id.totaltext);
            policestation=(TextView)view.findViewById(R.id.policestation);
            courtno=(TextView)view.findViewById(R.id.courtno);
            chargesheetno=(TextView)view.findViewById(R.id.chargesheetno);
            chargesheetdate=(TextView)view.findViewById(R.id.chargesheetdate);
            courtcaseno=(TextView)view.findViewById(R.id.courtcaseno);
            chargesheetstatus=(TextView)view.findViewById(R.id.chargesheetstatus);
            changecasetypetocc=(TextView)view.findViewById(R.id.changecasetypetocc);
            updatestatus=(TextView)view.findViewById(R.id.updatestatus);
            imagetext=(TextView)view.findViewById(R.id.imagetext);
            updatestatus1=(TextView)view.findViewById(R.id.updatestatus1);
            casenumber=(TextView)view.findViewById(R.id.casenumber);
            courtcasesrno=(TextView)view.findViewById(R.id.courtcasesrno);

        }

    }
}

