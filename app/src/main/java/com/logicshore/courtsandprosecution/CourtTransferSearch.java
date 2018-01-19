package com.logicshore.courtsandprosecution;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
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
 * Created by admin on 18-10-2017.
 */

public class CourtTransferSearch extends AppCompatActivity {

    TextView totaltext;
    ListView listitem;
    int pagecount=0;
    int pagenumber=1;
    int totalpages=-1;
    int pagenumberrequeusting=20;
    CourtTransferContent courttransfercontent;
    ArrayList<CourtTransferContent> courttransferdata;
    CourtTransferAdapter courttransferadapter;
    public static boolean callflag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.courttransfersearch);
        courttransferdata=new ArrayList<>();
        listitem=(ListView)findViewById(R.id.listitem);
        totaltext=(TextView)findViewById(R.id.totaltext);
        // updatestatus.setText(R.string.enter_status);
//        updatestatus.setPaintFlags(updatestatus.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        if(!callflag){
            new CourtTransferMainStatus().execute();
        }
        pagenumber = 1;
        pagenumberrequeusting = 20;
        pagecount = 20;

        if(isNetworkAvailable()){
            listitem.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    Log.d("list onScroll", " entered");
                    int count = listitem.getCount();
                    Log.d("count", String.valueOf(count));
                    int threshold = 1;
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (listitem.getLastVisiblePosition() >= count - threshold) {
                            Log.d("get last position", "position");
                            if (pagenumber > totalpages) {
                                pagenumber = pagenumber + 1;
                                pagecount = pagecount + pagenumberrequeusting;
                                Log.d("pagecount", String.valueOf(pagecount));
                                pagenumberrequeusting = 20;
                                // new Petitiondatails().execute();
                            }
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"NO Internet connection",Toast.LENGTH_SHORT).show();
        }




    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private class CourtTransferMainStatus extends AsyncTask<String,String,String> {
        ProgressDialog pd;
        boolean result;
        String s;

        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(CourtTransferSearch.this);
            pd.setMessage("Please Wait...");
            pd.show();
            pd.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient hs = new DefaultHttpClient();
                HttpPost ps = new HttpPost("http://192.168.1.5:81/LogicShore.svc/CCTRANSFER_SEARCH");
                JSONObject jsonobj = new JSONObject();
                JSONObject js = new JSONObject();
                js.put("P_LANG_CD","99");
                js.put("P_PS_CD","2023001");
                jsonobj.put("request", js);

                String message = jsonobj.toString();
                Log.d("Main", jsonobj.toString());
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
                    for (int i=0;i<jsonarray.length();i++){
                        courttransfercontent=new CourtTransferContent();
                        JSONObject jsobj = jsonarray.getJSONObject(i);
                        Log.d("length", String.valueOf(jsonarray.length()));
                        courttransfercontent.setCount(String.valueOf(i+1));
                        courttransfercontent.setPolicestation(jsobj.getString("PS_NAME"));
                        Log.d("firno",jsobj.getString("FIR_NO"));
                        courttransfercontent.setCrimeno(jsobj.getString("FIR_NO"));
                        courttransfercontent.setChargesheetno(jsobj.getString("CHARGESHEET_NO"));
                        courttransfercontent.setCaseno(jsobj.getString("COURT_CASE_NO"));
                        courttransfercontent.setCourtnogivenbyreceived(jsobj.getString("TRANSFERED_COURT_CASE_NO"));
                        courttransfercontent.setTransferedfrom(jsobj.getString("TRANSFERRED_FROM"));
                        courttransfercontent.setActsndsections("ACTS_DESC");
                        courttransfercontent.setAction("Action");
                        courttransfercontent.setTransferedto(jsobj.getString("TRANSFERRED_TO"));
                        courttransferdata.add(courttransfercontent);
                        Log.d("Repsonse", String.valueOf(courttransferdata));
                    }
                    courttransferadapter =new CourtTransferAdapter(courttransferdata, CourtTransferSearch.this);
                   // listitem.setAdapter(courttransferadapter);
                    Log.d("adapterr", String.valueOf(courttransferadapter));
                    if(pagecount>1) {
                        Log.d("page count inner","");
                        int position = listitem.getLastVisiblePosition();
                        Log.d("positionvalue", String.valueOf(position));
                        listitem.setAdapter(courttransferadapter);
                        listitem.setSelectionFromTop(position, 0);
                    }


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
//        if(callflag){
            new CourtTransferMainStatus().execute();
        //}

        super.onResume();
    }
}
