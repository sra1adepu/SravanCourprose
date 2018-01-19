package com.logicshore.courtsandprosecution;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
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
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * Created by admin on 10-10-2017.
 */

public class CourtTranfer extends Activity {
    TextView starcourtcase, star;
    Spinner spinneritem, reasonfortransfer;
    ArrayList<String> transferlist;
    ArrayAdapter arrayAdapter;
    ConnectionDetector cd;
    RequestQueue requestQueue, requestQueue1;
    TableRow otherstablerow;
    EditText dateoftrasfer, receivingcourtno, remarks, others;
    ArrayList<String> transferid;
    SimpleDateFormat mdformatpresent;
    Calendar call;
    Button cancel, submit;
    DatePickerDialog dpDialog;
    ArrayList<String> moarray;
    ArrayList<String> courtid;
    String other = "";
    JSONObject js;
    String transferreason;

    String transferdate, transfercourtcasenumber, transferredremarks;
    String transferredtoid, reasonfortransferid;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.courttransfer);
        starcourtcase = (TextView) findViewById(R.id.starcourtcase);
        star = (TextView) findViewById(R.id.star);
        starcourtcase.setTextColor(Color.RED);
        star.setTextColor(Color.RED);
        spinneritem = (Spinner) findViewById(R.id.spinneritem);
        reasonfortransfer = (Spinner) findViewById(R.id.reasonfortransfer);
        transferlist = new ArrayList<>();
        cd = new ConnectionDetector(CourtTranfer.this);
        requestQueue = Volley.newRequestQueue(CourtTranfer.this);
        requestQueue1 = Volley.newRequestQueue(CourtTranfer.this);
        dateoftrasfer = (EditText) findViewById(R.id.dateoftrasfer);
        receivingcourtno = (EditText) findViewById(R.id.receivingcourtno);
        others = (EditText) findViewById(R.id.others);
        remarks = (EditText) findViewById(R.id.remarks);
        submit = (Button) findViewById(R.id.submit);
        new TransferredTo().execute();
        otherstablerow = (TableRow) findViewById(R.id.otherstablerow);
        transferid = new ArrayList<>();
        moarray = new ArrayList<>();
        moarray.add("Select");
        transferlist.add("Select");
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        reasonfortransfer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), transferid.get(position), Toast.LENGTH_SHORT).show();
                transferredtoid = transferid.get(position);
                transferreason = transferlist.get(position);


                if (transferlist.get(position).equals("Others")) {
                    otherstablerow.setVisibility(View.VISIBLE);

                } else {
                    otherstablerow.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinneritem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), courtid.get(position), Toast.LENGTH_SHORT).show();
                reasonfortransferid = courtid.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dateoftrasfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdformatpresent = new SimpleDateFormat("dd-MMM-yyyy");
                call = Calendar.getInstance();
                dpDialog = new DatePickerDialog(CourtTranfer.this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        call = Calendar.getInstance();
                        call.set(year, monthOfYear, dayOfMonth);
                        dateoftrasfer.setText(mdformatpresent.format(call.getTime()));
                        //petto=petitionto.getText().toString();
                        //Log.d("weewewe",petitionto.getText().toString());
                    }
                }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));
                dpDialog.show();
            }
        });

        Log.d("MOARRAY", String.valueOf(moarray.size()));


        if (isNetworkAvailable()) {
            String url = "http://192.168.1.5:81/LogicShore.svc/DDCOURTSDetails";
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            courtid = new ArrayList<>();
                            courtid.add("");
                            try {
                                JSONArray motype = response.getJSONArray("Details");
                                for (int i = 0; i < motype.length(); i++) {
                                    JSONObject motypelist = motype.getJSONObject(i);
                                    String motypevalue = motypelist.getString("COURT");
                                    courtid.add(motypelist.getString("COURT_CD"));
                                    moarray.add(motypevalue);
                                    Log.d("moarray", String.valueOf(moarray));
                                }


                                CustomAdapter customAdapter = new CustomAdapter(CourtTranfer.this, moarray);
                                spinneritem.setAdapter(customAdapter);

//                                ArrayAdapter arrayAdapter = new ArrayAdapter(CourtTranfer.this, android.R.layout.simple_spinner_dropdown_item, moarray);
//                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                spinneritem.setAdapter(arrayAdapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // display response
                            Log.d("Response", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            //Log.d("Error.Response", response);
                        }
                    }
            );
            requestQueue.add(getRequest);
        } else {
            Toast.makeText(getApplicationContext(), "NO INTERNET AVAILABLE", Toast.LENGTH_SHORT).show();
        }




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("Others".equals(transferreason)){
                    other =others.getText().toString();
                    transferdate=dateoftrasfer.getText().toString();
                    transfercourtcasenumber=receivingcourtno.getText().toString();
                    transferredremarks=remarks.getText().toString();
                    try{
                        js= new JSONObject();
                        js.put("P_CRIME_NO","");
                        js.put("P_TRANSFER_DT",transferdate);
                        js.put("P_TRNF_COURT_CD",transferredtoid);
                        js.put("P_TRANSFERED_REASON_CD",reasonfortransferid);
                        js.put("P_OTHER_REASON",other);
                        js.put("P_TRNF_COURT_CASE_NUM",transfercourtcasenumber);
                        js.put("P_TRANSFERED_REMARKS",transferredremarks);


                    }catch (Exception e){

                    }


                }
                else{
                        try{
                            js= new JSONObject();
                            js.put("P_CRIME_NO",getIntent().getStringExtra("Courtcasenumber"));
                            js.put("P_TRANSFER_DT",dateoftrasfer.getText().toString());//
                            js.put("P_TRNF_COURT_CD",transferredtoid);
                            js.put("P_TRANSFERED_REASON_CD",reasonfortransferid);
                            js.put("P_OTHER_REASON","");
                            js.put("P_TRNF_COURT_CASE_NUM",receivingcourtno.getText().toString());//
                            js.put("P_TRANSFERED_REMARKS",remarks.getText().toString());//

                        }
                        catch (Exception e){

                        }


                }
                new TransferSubmit().execute();
                finish();
                clear();

            }

        });


    }





    private void clear() {
        dateoftrasfer.setText("");
        receivingcourtno.setText("");
        remarks.setText("");
        others.setText("");

    }


    private class TransferredTo extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String st;

        /* (non-Javadoc)
                * @see android.os.AsyncTask#onPreExecute()
                */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progressDialog = new ProgressDialog(CourtTranfer.this);
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
                HttpGet httppost = new HttpGet("http://192.168.1.5:81/LogicShore.svc/DDRFTRANSFERDetails");

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


                transferid.add("");
                JSONObject jobj = new JSONObject(st);
                JSONArray js = jobj.getJSONArray("Details");
                Log.d("MianObjects", String.valueOf(jobj.getJSONArray("Details")));
                if (js.length() != 0) {

                    for (int i = 0; i < js.length(); i++) {
                        JSONObject j = js.getJSONObject(i);
                        transferlist.add(j.getString("MASTER_VALUE"));
                        transferid.add(j.getString("MASTER_CD"));
                        Log.d("transferlist", j.getString("MASTER_VALUE"));
                    }


                    CustomAdapter customAdapter=new CustomAdapter(CourtTranfer.this,transferlist);
                    reasonfortransfer.setAdapter(customAdapter);


//                    arrayAdapter = new ArrayAdapter<String>(CourtTranfer.this, android.R.layout.simple_spinner_dropdown_item, transferlist);
//                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    reasonfortransfer.setAdapter(arrayAdapter);



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

    private class TransferSubmit extends AsyncTask<String,String,String>{
        ProgressDialog pd =new ProgressDialog(CourtTranfer.this);
        String s;
        boolean result;

        @Override
        protected void onPreExecute() {
            pd.setMessage("Please Wait Loading...");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{


                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.1.5:81/LogicShore.svc/CCTRANSFERINS");
                JSONObject jsonObject=new JSONObject();
//                      JSONObject js= new JSONObject();
//                        js.put("P_CRIME_NO",getIntent().getStringExtra("Courtcasenumber"));
//                        js.put("P_TRANSFER_DT",dateoftrasfer.getText().toString());
//                        js.put("P_TRNF_COURT_CD",transferredtoid);
//                        js.put("P_TRANSFERED_REASON_CD",reasonfortransferid);
//                        js.put("P_OTHER_REASON","");
//                        js.put("P_TRNF_COURT_CASE_NUM",receivingcourtno.getText().toString());
//                        js.put("P_TRANSFERED_REMARKS",remarks.getText().toString());
                Log.d("sra1","handler");
                Log.d("Receivingcourtcaseno",receivingcourtno.getText().toString());
                jsonObject.put("request",js);
                Log.d("Request",jsonObject.toString());

                httpPost.setEntity(new StringEntity(jsonObject.toString(),"UTF8"));
                HttpResponse response = httpClient.execute(httpPost);

                if (response != null) {
                    if (response.getStatusLine().getStatusCode() == 204)
                        result = true;
                    Log.d("Status line", "" + response.getStatusLine().getStatusCode());
                    cz.msebera.android.httpclient.HttpEntity hr = response.getEntity();


                    s = EntityUtils.toString(hr);
                    Log.d("Response_String", s);
                }

            }
            catch (Exception e){

            }

            return s;
        }
        protected void onPostExecute(String s){
            try{
                JSONObject js=new JSONObject(s);
                Log.d("response",js.getString("message"));

            }catch (Exception e){

            }

        }
    }
}







