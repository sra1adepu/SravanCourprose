package com.logicshore.courtsandprosecution;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
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
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * Created by admin on 31-08-2017.
 */

public class SearchItemPopup extends AppCompatActivity {
    static boolean serviceflg=false;
    TextView totaltext;
    ListView listitem;
    EditText searchedittext;
    RadioGroup radiogroup;
    RadioButton firnoradio,radiochargesheetno,radiochargesheetdate,prcno;
    Button search;
    ArrayList<String> datalist;
    JSONObject js;
    String fir="";
    String caseno="";
    String prc="";
    String date="";
 String select="";
    DatePickerDialog dpDialog;
    Calendar call;
    SimpleDateFormat mdformatpresent;
    int pagecount=0;
    int pagenumber=1;
    int totalpages=-1;
    int pagenumberrequeusting=20;

    RequestQueue requestQueue;
    ChargeSheetContent chargeSheetContent;
    ArrayList<ChargeSheetContent> chargesheetdata;
    ChargeSheetAdapter chargeSheetAdapter;

    public static boolean callflag=false;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.search_listitem);
        chargesheetdata=new ArrayList<>();
        listitem=(ListView)findViewById(R.id.listitem);
        totaltext=(TextView)findViewById(R.id.totaltext);
        searchedittext=(EditText)findViewById(R.id.searchedittext);

        //searchedittext.setFocusable(false);
        radiogroup=(RadioGroup)findViewById(R.id.radiogroup);
        firnoradio=(RadioButton)findViewById(R.id.firnoradio);
        radiochargesheetno=(RadioButton)findViewById(R.id.radiochargesheetno);
        radiochargesheetdate=(RadioButton)findViewById(R.id.radiochargesheetdate);
        prcno=(RadioButton)findViewById(R.id.prcno);
        search=(Button)findViewById(R.id.search);
        datalist=new ArrayList<>();

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


        mdformatpresent = new SimpleDateFormat("dd-MMM-yyyy");
        call = Calendar.getInstance();



        requestQueue= Volley.newRequestQueue(SearchItemPopup.this);


        prcno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertEditTextFeilds(searchedittext, "PRC NO");
                //searchedittext.setEnabled(true);
                select="prc";

            }
        });

        firnoradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertEditTextFeilds(searchedittext, "FIR NO");
                //searchedittext.setEnabled(true);
                select="firno";

            }
        });
        radiochargesheetno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertEditTextFeilds(searchedittext, "Charge Sheet No");
               // searchedittext.setEnabled(true);
                select="caseno";



            }
        });
        radiochargesheetdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // searchedittext.setEnabled(true);
                dpDialog = new DatePickerDialog(SearchItemPopup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        call = Calendar.getInstance();
                        call.set(year, monthOfYear, dayOfMonth);

                        searchedittext.setText(mdformatpresent.format(call.getTime()));

                    }
                }, call.get(Calendar.YEAR), call.get(Calendar.MONTH), call.get(Calendar.DAY_OF_MONTH));

                dpDialog.show();
                dpDialog.getWindow().setLayout(380, 520);

                alertEditTextFeilds(searchedittext, "Charge Sheet Date");
                select="date";

            }
        });

        searchedittext.getText().toString();
        if(!callflag){
            new ChargeSheetMainStatus().execute();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()){


                    datalist.clear();
                   listitem.setAdapter(null);
                    if(searchedittext.getText().toString().length()==0){
                        totaltext.setText("");
                        searchedittext.setError("Please Enter The Value... ");
                        searchedittext.setFocusable(true);
                        Toast.makeText(getApplicationContext(),"Enter The value",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        searchedittext.getText().toString();
                        caseno=searchedittext.getText().toString();
                        date=searchedittext.getText().toString();
                        prc=searchedittext.getText().toString();
                        new ChargeSheetMainStatus().execute();
                    }

                    //clearText();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Check Internet Connection",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getApplicationContext(),searchedittext.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void alertEditTextFeilds(EditText searchedittext, String s) {
        searchedittext.setHint("Please Enter"+s);
        searchedittext.setText("");
        listitem.setAdapter(null);
        totaltext.setText("");
    }






    private class ChargeSheetMainStatus extends AsyncTask<String,String,String> {
        ProgressDialog pd;
        boolean result;
        String s;

        @Override
        protected void onPreExecute() {
            pd=new ProgressDialog(SearchItemPopup.this);
            pd.setMessage("Please Wait...");
            pd.show();
            pd.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient hs = new DefaultHttpClient();
                HttpPost ps = new HttpPost("http://192.168.1.5:81/LogicShore.svc/CSCASE");
                JSONObject jsonobj = new JSONObject();
                js = new JSONObject();

                js.put("P_FIR_NO", searchedittext.getText().toString());
                js.put("P_CASE_NO", "");
                js.put("P_PRC_TYPE", "");
                js.put("P_UNIT_CD","2023001");
                js.put("P_CS_DATE", "");

                if(select.equals("date")){
                    js.put("P_FIR_NO", "");
                    js.put("P_CASE_NO", "");
                    js.put("P_PRC_TYPE", "");
                    js.put("P_UNIT_CD","2023001");
                    js.put("P_CS_DATE", searchedittext.getText().toString());
                }
                else if(select.equals("caseno")){
                    js.put("P_FIR_NO","");
                    js.put("P_CASE_NO", searchedittext.getText().toString());
                    js.put("P_PRC_TYPE","");
                    js.put("P_UNIT_CD","2023001");
                    js.put("P_CS_DATE", "");
                }
                else if(select.equals("prc")){
                    js.put("P_FIR_NO","");
                    js.put("P_CASE_NO", "");
                    js.put("P_PRC_TYPE",searchedittext.getText().toString());
                    js.put("P_UNIT_CD","2023001");
                    js.put("P_CS_DATE", "");
                }
                else if(select.equals("firno")) {
                    js.put("P_FIR_NO",searchedittext.getText().toString());
                    js.put("P_CASE_NO", "");
                    js.put("P_PRC_TYPE","");
                    js.put("P_UNIT_CD","2023001");
                    js.put("P_CS_DATE", "");

                }




                jsonobj.put("request", js);

                String message;
                message = jsonobj.toString();
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
                JSONArray jsonarray=jsobj1.getJSONArray("Petlist");

                Log.d("jsonarraypetlist", String.valueOf(jsonarray));

                    totaltext.setText(String.valueOf(jsonarray.length()));
                    pd.dismiss();
                if(jsonarray.length()>0){
                    chargesheetdata.clear();
                    for (int i=0;i<jsonarray.length();i++){
                        chargeSheetContent=new ChargeSheetContent();
                        JSONObject jsobj = jsonarray.getJSONObject(i);
                        Log.d("length", String.valueOf(jsonarray.length()));
                        chargeSheetContent.setCount(String.valueOf(i+1));
                        chargeSheetContent.setPolicestation(jsobj.getString("PS_NAME"))   ;
                        chargeSheetContent.setCrimeno(jsobj.getString("fir_no"));
                        chargeSheetContent.setChargesheetno(jsobj.getString("CHARGE_SHEET_NO"));
                        chargeSheetContent.setChargesheetdate(jsobj.getString("CHARGE_SHEET_DATE"));
                        chargeSheetContent.setCourtcaseno(jsobj.getString("COURT_CASE_NUM"));
                        chargeSheetContent.setChargesheetstatus(jsobj.getString("CHARGESHEET_STATUS"));
                        chargeSheetContent.setUpdatestatus(jsobj.getString("cs_status"));
                        chargeSheetContent.setCourtcasenumber(jsobj.getString("cs_final_rep_srno"));
                        chargeSheetContent.setCourtcasesrnumber(jsobj.getString("COURT_CASE_SRNO"));
                        chargeSheetContent.setChangetypetocc("bind data");
                        chargesheetdata.add(chargeSheetContent);
                        Log.d("Repsonse", String.valueOf(chargesheetdata));
                    }
                    chargeSheetAdapter =new ChargeSheetAdapter(chargesheetdata, SearchItemPopup.this);
                   // listitem.setAdapter(chargeSheetAdapter);
                    if(pagecount>1) {
                        Log.d("page count inner","");
                        int position = listitem.getLastVisiblePosition();
                        Log.d("positionvalue", String.valueOf(position));
                        listitem.setAdapter(chargeSheetAdapter);
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
        if(callflag){
            new ChargeSheetMainStatus().execute();
        }

        super.onResume();
    }
}
