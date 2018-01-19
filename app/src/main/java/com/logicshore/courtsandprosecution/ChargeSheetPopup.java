package com.logicshore.courtsandprosecution;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by admin on 17-10-2017.
 */

public class ChargeSheetPopup extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinneritem, casetype;
    SimpleDateFormat mdformatpresent;
    Calendar call;
    DatePickerDialog dpDialog, dpDialog1;
    TableRow table1, table2, table3, table4;
    EditText ddrinward, dateoffilling, remarks;
    ArrayAdapter<String> arrayAdapter;
    Button cancel, submit;
    String[] items;
    TextView dynamictext, ddr, totaltext;
    ArrayList<String> chargesheetlist;
    ArrayList<String> casetypearray;
    ArrayList<String> chargesheetlistid;
    ArrayList<String> casetypearrayid;
    String[] casetypespinner;
    String click;
    JSONObject j;
    JSONObject j1;
    String feil_date_chargesheet = "",courtcasenumber="",filedddriward = "",filesremarks = "",others_date= "",othersddrinward= "",othersremarks= "",selected_case_taken_spinner= "",taken_date= "", return_date ="",returnddrinward= "",returnmemarks= "",takenfilecaseno= "",takenfileremarks= "", spinner_selected_item ="", spinner_selected_item_code ="";

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    boolean dateFLG = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.searchitempopup);




//        chargesheetlist.clear();
//        casetypearray.clear();
        this.setFinishOnTouchOutside(false);
        spinneritem = (Spinner) findViewById(R.id.spinneritem);
        spinneritem.setOnItemSelectedListener(this);
        casetype = (Spinner) findViewById(R.id.casetype);
        casetype.setOnItemSelectedListener(this);
        table1 = (TableRow) findViewById(R.id.table1);
        table2 = (TableRow) findViewById(R.id.table2);
        table3 = (TableRow) findViewById(R.id.table3);
        table4 = (TableRow) findViewById(R.id.table4);
        cancel = (Button) findViewById(R.id.cancel);
        submit = (Button) findViewById(R.id.submit);
        dateoffilling = (EditText) findViewById(R.id.dateoffilling);
        dynamictext = (TextView) findViewById(R.id.dynamictext);
        ddr = (TextView) findViewById(R.id.ddr);
        ddrinward = (EditText) findViewById(R.id.ddrinward);
        remarks = (EditText) findViewById(R.id.remarks);


        //click = getIntent().getStringExtra("clickvalue");
//        Log.d("clickvalue",click);

        if (isNetworkAvailable()) {
            if(!SearchItemPopup.serviceflg){
                Log.d("state","true condition");
                new ChargeSheetStatus().execute();
                new CaseType().execute();
            }
            else
            {
                Log.d("state","false condition");
            }




        } else {
            Toast.makeText(getApplicationContext(), "Please check Internet Connections", Toast.LENGTH_SHORT).show();
        }

//        spinneritem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                dateFLG = false;
//                Toast.makeText(getApplicationContext(), chargesheetlistid.get(position), Toast.LENGTH_SHORT).show();
//                cancel.setText("BACK");
//
//
//                clearStrings();
//                spinner_selected_item = ""+parent.getSelectedItem();
//                Log.i("spinner_selected_item 1 ","--->"+spinner_selected_item);
//                spinner_selected_item_code = ""+chargesheetlistid.get(position) ;
//
//                if (parent.getSelectedItem().equals("Filed/Check and Put Up")) {
//                    dynamictext.setText("Date of Filing ChargeSheet in the Court*");
//                    ddr.setText("DDR/Inward/SR No");
//                    table1.setVisibility(View.VISIBLE);
//                    table2.setVisibility(View.VISIBLE);
//                    table3.setVisibility(View.VISIBLE);
//                    table4.setVisibility(View.GONE);
//                    clear();
//                    Log.d("values", "" + dateFLG);
//                    dateFLG = true;
//                    Log.d("valuesdd", "" + dateFLG);
//                } else if (parent.getSelectedItem().equals("Others")) {
//                    dynamictext.setText("Date of Reinvestigation*");
//                    ddr.setText("DDR/Inward/SR No");
//                    table1.setVisibility(View.VISIBLE);
//                    table2.setVisibility(View.VISIBLE);
//                    table3.setVisibility(View.VISIBLE);
//                    table4.setVisibility(View.GONE);
//
//                    clear();
//                } else if (parent.getSelectedItem().equals("Returned With Objection")) {
//                    dynamictext.setText("Date of Objection*");
//                    ddr.setText("DDR/Inward/SR No");
//
//                    table1.setVisibility(View.VISIBLE);
//                    table2.setVisibility(View.VISIBLE);
//                    table3.setVisibility(View.VISIBLE);
//                    table4.setVisibility(View.GONE);
//                    clear();
//
//                } else if (parent.getSelectedItem().equals("Taken on File")) {
//                    dynamictext.setText("Date of Taken on File of ChargeSheet*");
//                    ddr.setText("Court Case No*");
//                    table2.setVisibility(View.VISIBLE);
//                    table4.setVisibility(View.VISIBLE);
//                    table3.setVisibility(View.VISIBLE);
//                    table1.setVisibility(View.VISIBLE);
//                    clear();
//
//                } else {
//                    clearStrings();
//                    table1.setVisibility(View.GONE);
//                    table2.setVisibility(View.GONE);
//                    table3.setVisibility(View.GONE);
//                    table4.setVisibility(View.GONE);
//
//                    clear();
//                }
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


            submit.setVisibility(View.GONE);
            if (!spinner_selected_item.equals("Select")){
                submit.setVisibility(View.VISIBLE);
            }

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            /*Filed/Check and Put Up
                            Others
                            Returned With Objection
                            Taken on File*/
                    Log.i("spinner_selected_item","-->"+spinner_selected_item);
                    Log.i("spinner_selected_item_code","-->"+spinner_selected_item_code);

                    if ("Filed/Check and Put Up".equals(spinner_selected_item)){
                        feil_date_chargesheet = dateoffilling.getText().toString();
                        filedddriward = ddrinward.getText().toString();
                        filesremarks = remarks.getText().toString();
                    }else if("Others".equals(spinner_selected_item)){
                        filedddriward = ddrinward.getText().toString();
                        feil_date_chargesheet = dateoffilling.getText().toString();
                        filesremarks = remarks.getText().toString();
                    }else if("Returned With Objection".equals(spinner_selected_item)){
                        return_date = dateoffilling.getText().toString();
                        filedddriward = ddrinward.getText().toString();
                        returnmemarks = remarks.getText().toString();
                    }else if("Taken on File".equals(spinner_selected_item)){
                        feil_date_chargesheet = dateoffilling.getText().toString();
                        courtcasenumber= ddrinward.getText().toString();
                        filesremarks = remarks.getText().toString();
                    }
                    new Submitrequest().execute();
                    clear();
                    finish();
                }
            });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        mdformatpresent = new SimpleDateFormat("dd-MMM-yyyy");
        call = Calendar.getInstance();

        clear();

        dateoffilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateFLG) {

                    dpDialog1 = new DatePickerDialog(ChargeSheetPopup.this,R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            call = Calendar.getInstance();
                            call.set(year, monthOfYear, dayOfMonth);
                            Log.i("dateFLG", "-->" + dateFLG);


                            dateoffilling.setText(mdformatpresent.format(call.getTime()));

                        }
                    }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));

                    dpDialog1.show();
                    dpDialog1.getWindow().setLayout(380, 540);
                    dpDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                    // getCalender();
                    // Log.d("sravan",""+dateFLG);
                } else {
                    dpDialog = new DatePickerDialog(ChargeSheetPopup.this,R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            call = Calendar.getInstance();
                            call.set(year, monthOfYear, dayOfMonth);
                            Log.i("dateFLG", "-->" + dateFLG);
//                 if(dateFLG) {
//                    dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//
//                }else{
//                     dpDialog.getDatePicker();
//                }
                            dateoffilling.setText(mdformatpresent.format(call.getTime()));

                        }
                    }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));

                    dpDialog.show();
                    dpDialog.getWindow().setLayout(380, 540);


                    // getCalender1();
                }
            }
        });

        casetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), casetypearrayid.get(position).toString(), Toast.LENGTH_SHORT).show();
                selected_case_taken_spinner = casetypearrayid.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void clearStrings() {
        feil_date_chargesheet = "";
        filedddriward = "";
        filesremarks = "";
        others_date= "";
        othersddrinward= "";
        othersremarks= "";
        selected_case_taken_spinner= "";
        taken_date= "";
        return_date ="";
        returnddrinward= "";
        returnmemarks= "";
        takenfilecaseno= "";
        takenfileremarks= "";
        spinner_selected_item ="";
        spinner_selected_item_code ="";
        courtcasenumber="";
    }


    private void clear() {
        dateoffilling.setText("");
        ddrinward.setText("");
        remarks.setText("");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dateFLG = false;
                Toast.makeText(getApplicationContext(), chargesheetlistid.get(position), Toast.LENGTH_SHORT).show();
                cancel.setText("BACK");


                clearStrings();
                spinner_selected_item = chargesheetlist.get(position);
                Log.i("spinner_selected_item 1 ","--->"+spinner_selected_item);
                spinner_selected_item_code = ""+chargesheetlistid.get(position) ;

                if (chargesheetlist.get(position).equals("Filed/Check and Put Up")) {
                    dynamictext.setText("Date of Filing ChargeSheet in the Court*");
                    ddr.setText("DDR/Inward/SR No");
                    table1.setVisibility(View.VISIBLE);
                    table2.setVisibility(View.VISIBLE);
                    table3.setVisibility(View.VISIBLE);
                    table4.setVisibility(View.GONE);
                    clear();
                    Log.d("values", "" + dateFLG);
                    dateFLG = true;
                    Log.d("valuesdd", "" + dateFLG);
                } else if (chargesheetlist.get(position).equals("Others")) {
                    dynamictext.setText("Date of Reinvestigation*");
                    ddr.setText("DDR/Inward/SR No");
                    table1.setVisibility(View.VISIBLE);
                    table2.setVisibility(View.VISIBLE);
                    table3.setVisibility(View.VISIBLE);
                    table4.setVisibility(View.GONE);

                    clear();
                } else if (chargesheetlist.get(position).equals("Returned With Objection")) {
                    dynamictext.setText("Date of Objection*");
                    ddr.setText("DDR/Inward/SR No");

                    table1.setVisibility(View.VISIBLE);
                    table2.setVisibility(View.VISIBLE);
                    table3.setVisibility(View.VISIBLE);
                    table4.setVisibility(View.GONE);
                    clear();

                } else if (chargesheetlist.get(position).equals("Taken on File")) {
                    dynamictext.setText("Date of Taken on File of ChargeSheet*");
                    ddr.setText("Court Case No*");
                    table2.setVisibility(View.VISIBLE);
                    table4.setVisibility(View.VISIBLE);
                    table3.setVisibility(View.VISIBLE);
                    table1.setVisibility(View.VISIBLE);
                    clear();

                } else {
                    clearStrings();
                    table1.setVisibility(View.GONE);
                    table2.setVisibility(View.GONE);
                    table3.setVisibility(View.GONE);
                    table4.setVisibility(View.GONE);

                    clear();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

    private class ChargeSheetStatus extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private String st;


        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progressDialog = new ProgressDialog(ChargeSheetPopup.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            //http://cotservice1.logicshore.co.in/Service1.svc/profileinsert?user_id=ravisoft6@gmail.com&name=Ravi&pfimg=http://citytommorow.logicshore.co.in/upload/CHAND_2499980f.jpg
            try {


                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httppost = new HttpGet("http://192.168.1.5:81/LogicShore.svc/DDCHARGESHEET_STATUSDetails");

                HttpResponse response = httpclient.execute(httppost);
                Log.d("http response", response.toString());
                st = EntityUtils.toString(response.getEntity());

                Log.d("HTTP status", st);

            } catch (UnknownHostException e) {

            } catch (Exception e) {
                e.printStackTrace();
            }
            return st;
        }


        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String st) {
            // TODO Auto-generated method stub

            chargesheetlistid = new ArrayList<>();
            chargesheetlist = new ArrayList<>();
            progressDialog.dismiss();
            chargesheetlist.add("Select");
            chargesheetlistid.add("");
            try {


                JSONObject jobj = new JSONObject(st);
                JSONArray js = jobj.getJSONArray("Details");
                if (js.length() != 0) {


                    for (int i = 0; i < js.length(); i++) {

                        j1 = js.getJSONObject(i);
                        chargesheetlist.add(j1.getString("MASTER_VALUE"));
                        chargesheetlistid.add(j1.getString("MASTER_CD"));
                        Log.d("mastervalue", j1.getString("MASTER_VALUE"));
                    }

                    CustomAdapter customAdapter=new CustomAdapter(ChargeSheetPopup.this,chargesheetlist);
                    spinneritem.setAdapter(customAdapter);



//                    arrayAdapter = new ArrayAdapter<String>(ChargeSheetPopup.this, android.R.layout.spinner_row_item, chargesheetlist);
//                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinneritem.setAdapter(arrayAdapter);


//                    spinneritem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            Toast.makeText(getApplicationContext(),chargesheetlistid.get(position),Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });


                } else {
                    Toast.makeText(getApplicationContext(), "No values found", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
               // serviceflg=true;
                clear();


            } catch (Exception e) {
                // TODO: handle exception
                //Toast.makeText(getApplicationContext(), "Network Glitch Please try again later", Toast.LENGTH_LONG).show();
            }


        }

    }

    private class CaseType extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private String st;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progressDialog = new ProgressDialog(ChargeSheetPopup.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            //http://cotservice1.logicshore.co.in/Service1.svc/profileinsert?user_id=ravisoft6@gmail.com&name=Ravi&pfimg=http://citytommorow.logicshore.co.in/upload/CHAND_2499980f.jpg
            try {


                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httppost = new HttpGet("http://192.168.1.5:81/LogicShore.svc/DDCASETYPESDetails");

                HttpResponse response = httpclient.execute(httppost);
                Log.d("http response", response.toString());
                st = EntityUtils.toString(response.getEntity());

                Log.d("HTTP status", st);

            } catch (UnknownHostException e) {

            } catch (Exception e) {
                e.printStackTrace();
            }
            return st;
        }


        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String st) {
            // TODO Auto-generated method stub

            progressDialog.dismiss();

            try {

                casetypearrayid = new ArrayList<>();
                casetypearray = new ArrayList<>();
                casetypearrayid.add("");
                casetypearray.add("Select");

                JSONObject jobj = new JSONObject(st);
                JSONArray js = jobj.getJSONArray("Details");
                if (js.length() != 0) {
                    for (int i = 0; i < js.length(); i++) {
                        j = js.getJSONObject(i);
                        casetypearray.add(j.getString("MASTER_VALUE"));
                        casetypearrayid.add(j.getString("MASTER_CD"));
                        Log.d("mastervalue", j.getString("MASTER_VALUE"));

                        Log.d("casetypearray", casetypearray.toString());

                    }

                    CustomAdapter customAdapter=new CustomAdapter(ChargeSheetPopup.this,casetypearray);
                    casetype.setAdapter(customAdapter);



//                    arrayAdapter = new ArrayAdapter<String>(ChargeSheetPopup.this, R.layout.spinner_row_item, casetypearray);
//                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    casetype.setAdapter(arrayAdapter);

                } else {
                    Toast.makeText(getApplicationContext(), "No values found", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
               // serviceflg=true;


            } catch (Exception e) {
                // TODO: handle exception
                //Toast.makeText(getApplicationContext(), "Network Glitch Please try again later", Toast.LENGTH_LONG).show();
            }


        }

    }

    private class Submitrequest extends AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(ChargeSheetPopup.this);

        String s;
        boolean result;
        protected void onPreExecute() {
            pd.setMessage("Loading...");
            pd.show();
            pd.setCancelable(false);
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            try {

                HttpClient hs = new DefaultHttpClient();
                HttpPost ps = new HttpPost("http://192.168.1.5:81/LogicShore.svc/CICINSERT");
                JSONObject js = new JSONObject();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("P_CS_FINAL_REP_SRNO",getIntent().getStringExtra("casenumber"));

                jsonObject.put("P_INWARD_SERIAL_NO", filedddriward);
                jsonObject.put("P_CHARGESHEET_DT",feil_date_chargesheet);

                jsonObject.put("P_OBJECT_DT", return_date);
                jsonObject.put("P_OBJECT_REMARKS",returnmemarks);
                jsonObject.put("P_OBJECTION_RSN_CD","");


                jsonObject.put("P_COURT_CASE_TYPE_CD",""+selected_case_taken_spinner);
                jsonObject.put("P_COURT_CASE_NUM",courtcasenumber);
                jsonObject.put("P_FILED_REMARKS",filesremarks);
                jsonObject.put("P_CHARGESHEET_STATUS_CD",""+spinner_selected_item_code);

                jsonObject.put("P_OTHER_REASON","");
                jsonObject.put("P_COURT_CASE_SRNO", "");
                jsonObject.put("P_USER_ID","sra1");
                js.put("request",jsonObject);

                String message;
                message = js.toString();
                Log.d("Main", js.toString());
                ps.setEntity(new StringEntity(message, "UTF8"));
                //	ps.setHeader("Accept", "application/json");
                ps.setHeader("Content-Type", "application/json");
                HttpResponse resp = hs.execute(ps);

                if (resp != null) {
                    if (resp.getStatusLine().getStatusCode() == 204)
                        result = true;
                    Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
                    HttpEntity hr = resp.getEntity();


                    s = EntityUtils.toString(hr);
                    Log.d("Response_String", s);
                }

            } catch (UnknownHostException e) {

            } catch (Exception e) {
                e.printStackTrace();
            }
            return s;
        }

        protected void onPostExecute(String s) {

            try {
                JSONObject jobj = new JSONObject(s);
                Log.d("meassgae",jobj.getString("message"));

                pd.dismiss();
            } catch (Exception e) {

            }


        }

    }

}
