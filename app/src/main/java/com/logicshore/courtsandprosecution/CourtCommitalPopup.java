package com.logicshore.courtsandprosecution;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by admin on 10-10-2017.
 */

public class CourtCommitalPopup extends Activity {
    TextView star,commitalstar;
    Spinner spinneritem;
    EditText dateofcommital,remarks,transferredfrom;
    ArrayList<String> transferlist;
    ArrayList<String> transferid;
    ArrayAdapter arrayAdapter;
    SimpleDateFormat mdformatpresent;
    Calendar call;
    DatePickerDialog dpDialog;
    Button cancel,submit;
    String selectedspinner;
    String caseid;


    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.courtcommitalpopup);
        star=(TextView)findViewById(R.id.star);

        commitalstar=(TextView)findViewById(R.id.commitalstar);
        star.setTextColor(Color.RED);
        commitalstar.setTextColor(Color.RED);
        spinneritem=(Spinner)findViewById(R.id.spinneritem);
        dateofcommital=(EditText)findViewById(R.id.dateofcommital);
        cancel=(Button)findViewById(R.id.cancel);
        remarks=(EditText)findViewById(R.id.remarks);
        transferredfrom=(EditText)findViewById(R.id.transferredfrom);
        submit=(Button)findViewById(R.id.submit);
        transferlist=new ArrayList<>();
        transferid=new ArrayList<>();


            new TransferTo().execute();






        transferredfrom.setText(getIntent().getStringExtra("transfer"));
        transferredfrom.setEnabled(false);

        caseid=getIntent().getStringExtra("courtcasenumber");
        Log.d("casenumber",caseid);

        dateofcommital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdformatpresent = new SimpleDateFormat("dd-MMM-yyyy");
                call = Calendar.getInstance();
                dpDialog=new DatePickerDialog(CourtCommitalPopup.this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        call = Calendar.getInstance();
                        call.set(year, monthOfYear, dayOfMonth);
                        dateofcommital.setText(mdformatpresent.format(call.getTime()));
                        //petto=petitionto.getText().toString();
                        //Log.d("weewewe",petitionto.getText().toString());
                    }
                }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
                dpDialog.show();
                dpDialog.getWindow().setLayout(380, 550);
                dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("date",dateofcommital.getText().toString());

                new ServiceSubmit().execute();

                Toast.makeText(getApplicationContext(),caseid,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),transferredfrom.getText().toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),dateofcommital.getText().toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),remarks.getText().toString(),Toast.LENGTH_SHORT).show();
                clear();
                finish();
            }
        });
    }

    private void clear() {
        //transferredfrom.setText("");
        dateofcommital.setText("");
        remarks.setText("");
        selectedspinner="";
    }


    private class TransferTo extends AsyncTask<String,String,String> {

        ProgressDialog progressDialog;
        private String st;

        /* (non-Javadoc)
                * @see android.os.AsyncTask#onPreExecute()
                */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progressDialog = new ProgressDialog(CourtCommitalPopup.this);
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
                HttpGet httppost = new HttpGet("http://192.168.1.5:81/LogicShore.svc/DDCOURTSDetails");

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

                transferlist.add("Select");
                transferid.add("");
                JSONObject jobj = new JSONObject(st);
                JSONArray js = jobj.getJSONArray("Details");
                Log.d("MianObjects", String.valueOf(jobj.getJSONArray("Details")));
                if (js.length() != 0) {

                    for (int i = 0; i < js.length(); i++) {
                        JSONObject j = js.getJSONObject(i);
                        transferlist.add(j.getString("COURT"));
                        transferid.add(j.getString("COURT_CD"));
                        Log.d("court", j.getString("COURT"));
                    }


                   CustomAdapter customAdapter=new CustomAdapter(CourtCommitalPopup.this,transferlist);
                    spinneritem.setAdapter(customAdapter);

//                    arrayAdapter = new ArrayAdapter<String>(CourtCommitalPopup.this, android.R.layout.simple_spinner_dropdown_item, transferlist);
//                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinneritem.setAdapter(arrayAdapter);
//

                    spinneritem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //transferid.get(position).toString();
                            Toast.makeText(getApplicationContext(),transferid.get(position).toString(),Toast.LENGTH_SHORT).show();
                            selectedspinner=transferid.get(position).toString();
                            Log.d("commitalcd",selectedspinner);


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "No values found", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();


            } catch (Exception e) {
                // TODO: handle exception
                //Toast.makeText(getApplicationContext(), "Network Glitch Please try again later", Toast.LENGTH_LONG).show();
            }


        }

    }


    private class ServiceSubmit extends  AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(CourtCommitalPopup.this);

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
                HttpPost ps = new HttpPost("http://192.168.1.5:81/LogicShore.svc/CCCOMMITTALSINS");
                JSONObject js = new JSONObject();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("P_LANG_CD","99");
                jsonObject.put("P_COURT_CASE_SRNO",getIntent().getStringExtra("courtcasenumber"));
                jsonObject.put("P_COMMITTAL_REG_DATE",dateofcommital.getText().toString());
                jsonObject.put("P_COMMITTAL_COURT_CD",selectedspinner);
                jsonObject.put("P_COMMITTAL_REMARKS",remarks.getText().toString());
                jsonObject.put("P_USER_ID","99");
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
                    cz.msebera.android.httpclient.HttpEntity hr = resp.getEntity();


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
            if(pd.isShowing()){
                pd.dismiss();
            }

            try {
                JSONObject jobj = new JSONObject(s);
                Log.d("meassgae",jobj.getString("message"));


            } catch (Exception e) {

            }


        }
    }
}
