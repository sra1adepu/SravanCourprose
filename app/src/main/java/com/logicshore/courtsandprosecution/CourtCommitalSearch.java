package com.logicshore.courtsandprosecution;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * Created by admin on 23-10-2017.
 */

public class CourtCommitalSearch extends AppCompatActivity {

    TextView totaltext;
    ListView listitem;
    AutoCompleteTextView auto;

    CourtCommitalContent courtcommitalcontent;
    ArrayList<CourtCommitalContent> courtcommitaldata;
    CourtCommitalAdapter courtcommitaladapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.courtcommitalsearch);
        courtcommitaldata=new ArrayList<>();
        listitem=(ListView)findViewById(R.id.listitem);
        totaltext=(TextView)findViewById(R.id.totaltext);
        auto=(AutoCompleteTextView)findViewById(R.id.auto);
        // updatestatus.setText(R.string.enter_status);
//        updatestatus.setPaintFlags(updatestatus.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);



        new CourtCommital().execute();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private class CourtCommital extends AsyncTask<String,String,String> {
        ProgressDialog pd;
        boolean result;
        String s;

        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(CourtCommitalSearch.this);
            pd.setMessage("Please Wait...");
            pd.show();
            pd.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient hs = new DefaultHttpClient();
                HttpPost ps = new HttpPost("http://192.168.1.5:81/LogicShore.svc/COMMITALSSEARCH");
                JSONObject jsonobj = new JSONObject();
                JSONObject js = new JSONObject();
                js.put("P_LANG_CD","99");
                js.put("P_PS_CD","2022026");
                jsonobj.put("request", js);

                String message= jsonobj.toString();
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

            try {

                JSONObject jsobj1 = new JSONObject(s);
                JSONArray jsonarray=jsobj1.getJSONArray("Details");

                Log.d("jsonarraypetlist", String.valueOf(jsonarray));

                totaltext.setText(String.valueOf(jsonarray.length()));
                pd.dismiss();
                if(jsonarray.length()>0){
                    courtcommitaldata.clear();
                    for (int i=0;i<jsonarray.length();i++){
                        courtcommitalcontent=new CourtCommitalContent();
                        JSONObject jsobj = jsonarray.getJSONObject(i);
                        Log.d("length", String.valueOf(jsonarray.length()));
                        courtcommitalcontent.setCount(String.valueOf(i+1));
                        courtcommitalcontent.setPolicestation(jsobj.getString("PS_NAME"));
                        Log.d("firno",jsobj.getString("FIR_NO"));
                        courtcommitalcontent.setActsandsections(jsobj.getString("ACTS_DESC"));
                        courtcommitalcontent.setChargesheetno(jsobj.getString("CHARGESHEET_NO"));
                        courtcommitalcontent.setPrcno("prcno");
                        courtcommitalcontent.setDateofcommital(jsobj.getString("COMMITTAL_REG_DATE"));
                        courtcommitalcontent.setCommitalaction("commitalaction");
                        courtcommitalcontent.setScno(jsobj.getString("SC_CASE_NUM"));
                        courtcommitalcontent.setTransferto(jsobj.getString("TRANSFERRED_TO"));
                        courtcommitalcontent.setFirno(jsobj.getString("FIR_NO"));
                        courtcommitalcontent.setStatus(jsobj.getString("COMMITTAL_STATUS"));
                        courtcommitalcontent.setCasenumber(jsobj.getString("COURT_CASE_SRNO"));
                        courtcommitalcontent.setTransferfrom(jsobj.getString("TRANSFERRED_FROM"));
                        courtcommitalcontent.setCourtcommitalcd(jsobj.getString("COMMITTAL_COURT_CD"));
                        courtcommitaldata.add(courtcommitalcontent);
                        Log.d("Repsonse", String.valueOf(courtcommitaldata));
                    }
                    courtcommitaladapter =new CourtCommitalAdapter(courtcommitaldata, CourtCommitalSearch.this);
                    listitem.setAdapter(courtcommitaladapter);
                    Log.d("adapterr", String.valueOf(courtcommitaladapter));
                }
                else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_SHORT).show();
                }
            }


            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume() {
        new CourtCommital().execute();
        super.onResume();
    }
}