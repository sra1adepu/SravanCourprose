package com.logicshore.courtsandprosecution;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by admin on 20-11-2017.
 */

public class CourtandProsecutionStages extends Activity {
    Spinner selectstagespinner;
    RadioButton yes,radiono;
    LinearLayout selectno;
    LinearLayout tableRow;
    EditText ioname,iorank,ppappname,ppappdesignation,defencecouncilname,defencecouncildesignation,ccddate;
    TextView textview,ioedit,ppappedit,defenceedit,policestation1,policestation,firno,courtcaseno,adjournment;
    JSONObject js;
    TableRow tables;
    JSONObject jsmulti;
    JSONObject jsno;
    Switch on_off_switch;
    String MasterYes;
    ArrayList<TextView> witnessname;
    ArrayList<String> master_value_petition,master_id_petition,master_value_presence,master_id_presence;
    JSONArray jsonArrayno;
    TableLayout table1,table2,table3,accusedtable;
    RadioButton ioradiobuttonyes,ioradiobuttonno,ppappradiobuttonyes,ppappradiobuttonno,defenceradiobuttonyes,defenceradiobuttonno;
    TableRow accusedexamination,witnesstable,defencewitnessexamination,selectstage;
    Button savebutton,cancelbutton;
    String Selectedstageid;
    ArrayList<String> stageid;
    ArrayList<String> stage;
    String personcode;
    String accusesrno;
    String masteridpresence,masteridpetition;
    boolean attenededchecked,brifedbychecked,examinedchecked,suppotedchecked,givenupchecked,crossedchecked;


   ArrayList<CheckBox> arraylistattend,arraylistbrifed,arraylistexamined,arraylistsupported,arraylistgivenup,arraylistcrossed;
    String Witnessname;

    String examinevalue,supportvalue,giveupvalue,brifedvalue;

    JSONObject parent;
    String selectvalue;
    static String s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courtandprosecutions_1);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        selectstagespinner=(Spinner)findViewById(R.id.selectstagespinner);
        accusedexamination=(TableRow)findViewById(R.id.accusedexamination);
        witnesstable=(TableRow)findViewById(R.id.witnesstable);
        defencewitnessexamination=(TableRow)findViewById(R.id.defencewitnessexamination);
        selectstage=(TableRow)findViewById(R.id.selectstage);
        yes=(RadioButton)findViewById(R.id.yes);
        selectno=(LinearLayout)findViewById(R.id.selectno);
        table1=(TableLayout)findViewById(R.id.table1);
        table2=(TableLayout)findViewById(R.id.table2);
        table3=(TableLayout)findViewById(R.id.table3);
        accusedtable=(TableLayout)findViewById(R.id.accusedtable);
        ioedit=(TextView)findViewById(R.id.ioedit);
        ppappedit=(TextView)findViewById(R.id.ppappedit);
        defenceedit=(TextView)findViewById(R.id.defenceedit);
        tables=(TableRow)findViewById(R.id.tables);
        ccddate=(EditText)findViewById(R.id.ccddate);
        savebutton=(Button)findViewById(R.id.savebutton);
        cancelbutton=(Button)findViewById(R.id.cancelbutton);
        policestation1=(TextView)findViewById(R.id.policestation1);
        policestation=(TextView)findViewById(R.id.policestation);
        firno=(TextView)findViewById(R.id.firno);
        courtcaseno=(TextView)findViewById(R.id.courtcaseno);
        adjournment=(TextView)findViewById(R.id.adjournment);




        arraylistbrifed= new ArrayList<>();
        arraylistexamined= new ArrayList<>();
        arraylistsupported= new ArrayList<>();
        arraylistgivenup= new ArrayList<>();
        arraylistattend= new ArrayList<>();
        witnessname= new ArrayList<>();
        arraylistcrossed=new ArrayList<>();

        ioname=(EditText)findViewById(R.id.ioname);
        iorank=(EditText)findViewById(R.id.iorank);
        ppappname=(EditText)findViewById(R.id.ppappname);
        ppappdesignation=(EditText)findViewById(R.id.ppappdesignation);
        defencecouncilname=(EditText)findViewById(R.id.defencecouncilname);
        defencecouncildesignation=(EditText)findViewById(R.id.defencecouncildesignation);
        ppappradiobuttonno=(RadioButton)findViewById(R.id.ppappradiobuttonno);
        defenceradiobuttonno=(RadioButton)findViewById(R.id.defenceradiobuttonno);
        ioradiobuttonno=(RadioButton)findViewById(R.id.ioradiobuttonno);
            ioname.setEnabled(false);
            iorank.setEnabled(false);
            ppappname.setEnabled(false);
            ppappdesignation.setEnabled(false);
            defencecouncilname.setEnabled(false);
            defencecouncildesignation.setEnabled(false);

        radiono=(RadioButton)findViewById(R.id.radiono);
        ioradiobuttonyes=(RadioButton)findViewById(R.id.ioradiobuttonyes);
        ppappradiobuttonyes=(RadioButton)findViewById(R.id.ppappradiobuttonyes);
        defenceradiobuttonyes=(RadioButton)findViewById(R.id.defenceradiobuttonyes);


        policestation1.setText(getIntent().getStringExtra("ccdpolicastationname"));
        policestation.setText(getIntent().getStringExtra("ccsCourtname"));
        firno.setText(getIntent().getStringExtra("ccdfirnumber"));
        courtcaseno.setText(getIntent().getStringExtra("ccdcourtcasenumber"));
        adjournment.setText(getIntent().getStringExtra("ccdadjournmentno"));

        ccddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ioedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ioname.setEnabled(true);
                iorank.setEnabled(true);
                ioname.requestFocus();
                ioname.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(ioname, InputMethodManager.SHOW_FORCED);

            }
        });
        ppappedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ppappname.setEnabled(true);
                ppappdesignation.setEnabled(true);
                ppappname.requestFocus();
                ppappname.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(ppappname, InputMethodManager.SHOW_FORCED);
            }
        });
        defenceedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defencecouncilname.setEnabled(true);
                defencecouncildesignation.setEnabled(true);
                defencecouncilname.requestFocus();

                defencecouncilname.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(defencecouncilname, InputMethodManager.SHOW_FORCED);
            }
        });

        new CourtOfficerDetails().execute();

        if(radiono.isChecked()){
            Log.d("ischecked","checked");
            new NoServiceCalled().execute();
        }

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SelectStage().execute();
                table1.removeAllViews();
                selectstage.setVisibility(View.VISIBLE);
                selectno.setVisibility(View.GONE);
            }
        });
        radiono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table1.removeAllViews();
                table2.removeAllViews();
                table3.removeAllViews();
                accusedtable.removeAllViews();
                witnesstable.setVisibility(View.GONE);
                accusedexamination.setVisibility(View.GONE);
                defencewitnessexamination.setVisibility(View.GONE);
                selectno.setVisibility(View.VISIBLE);
                selectstage.setVisibility(View.GONE);
                new NoServiceCalled().execute();
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectvalue.equals("Witness Examination")||selectvalue.equals("Defence Witness Examination")){
                    new SubmitService().execute();
                }
                else if(selectvalue.equals("Accused Examination Under Section 313")){
                    new AccusedSubmit().execute();
                }


                Intent i = new Intent(CourtandProsecutionStages.this,CourtCaseDiaryHome.class);
                startActivity(i);

            }
        });
    }

    private class SelectStage extends AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);
        String s;

        @Override
        protected void onPreExecute() {
            pd.setMessage("Please Wait...");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://192.168.1.5:81/LogicShore.svc/CCDStagesDetails");
            HttpResponse response=httpClient.execute(httpGet);
                Log.d("Httpresponse",response.toString());
                s= EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return s;
        }
        protected void onPostExecute(String s) {
            pd.dismiss();
            stage= new ArrayList<>();
            stageid= new ArrayList<>();
            stage.add("---Select---");
            stageid.add("");

            try{
                final JSONObject jsonObject= new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("Details");

                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                          js= jsonArray.getJSONObject(i);
                          stage.add(js.getString("MASTER_VALUE"));
                          stageid.add(js.getString("MASTER_CD"));

                    }

                }
                ArrayAdapter arrayAdapter=new ArrayAdapter(CourtandProsecutionStages.this,android.R.layout.simple_spinner_dropdown_item,stage);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                selectstagespinner.setAdapter(arrayAdapter);
                selectstagespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Selectedstageid=stageid.get(position).toString();
                        Log.d("Selectedstageid",Selectedstageid);
                        if(parent.getSelectedItem().toString().trim().equals("Accused Examination Under Section 313")){
                            table3.removeAllViews();
                            table2.removeAllViews();
                            accusedtable.removeAllViews();
                            new Petition_Filled_by_Defence().execute();
                            new PresenceService().execute();
                            new AccusedList().execute();
                            selectvalue="Accused Examination Under Section 313";
                            accusedexamination.setVisibility(View.VISIBLE);
                            witnesstable.setVisibility(View.GONE);
                            defencewitnessexamination.setVisibility(View.GONE);

                        }
                        else if(parent.getSelectedItem().toString().trim().equals("Witness Examination")){
                            new WitnessExamination().execute();
                            selectvalue="Witness Examination";
                            accusedtable.removeAllViews();
                            table3.removeAllViews();
                            witnesstable.setVisibility(View.VISIBLE);
                            accusedexamination.setVisibility(View.GONE);
                            defencewitnessexamination.setVisibility(View.GONE);
                            //Selectedstageid=stageid.get(position).toString();
                        }
                       else if(parent.getSelectedItem().toString().trim().equals("Defence Witness Examination")){
                            new DefenceWitnessExamination().execute();
                            selectvalue="Defence Witness Examination";
                            table2.removeAllViews();
                            accusedtable.removeAllViews();
                            defencewitnessexamination.setVisibility(View.VISIBLE);
                            witnesstable.setVisibility(View.GONE);
                            accusedexamination.setVisibility(View.GONE);
                            //Selectedstageid=stageid.get(position).toString();
                        }
                        else if (parent.getSelectedItem().toString().trim().equals("Appearance of Accused")||parent.getSelectedItem().toString().trim().equals("Arguments")||parent.getSelectedItem().toString().trim().equals("Framing of Charges")||parent.getSelectedItem().toString().trim().equals("IO Examination")||
                                parent.getSelectedItem().toString().trim().equals("Judgement")||parent.getSelectedItem().toString().trim().equals("Summons to Accused")){
                            table3.removeAllViews();
                            table2.removeAllViews();
                            accusedtable.removeAllViews();
                            witnesstable.setVisibility(View.GONE);
                            accusedexamination.setVisibility(View.GONE);
                            defencewitnessexamination.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }catch (Exception e){

            }

        }
    }

//    private void accusedExaminationUnderSection() throws JSONException {
//
//        if(jsonArrayno.length()>0) {
//            for (int i = 0; i < jsonArrayno.length(); i++) {
//                JSONObject js = jsonArrayno.getJSONObject(i);
//
//                Log.d("Sravan", js.toString());
//
//            }
//        }
//
//
//
//
//    }

    private class NoServiceCalled extends AsyncTask<String,String,String>{
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);
        String st;
        boolean result;
        @Override
        protected void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.show();
            pd.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                HttpClient httpClient =new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.1.5:81/LogicShore.svc/ACCUSEDACTSINFO");
                JSONObject jsonObject=new JSONObject();
                JSONObject js = new JSONObject();
                jsonObject.put("P_LANG_CD","99");
                jsonObject.put("P_COURT_CASE_SRNO","20410031500160101");
                js.put("request",jsonObject);

                String passedrequest= js.toString();
                Log.d("passedrequest", js.toString());

                httpPost.setEntity(new StringEntity(passedrequest, "UTF8"));
                httpPost.setHeader("Content-Type", "application/json");

                HttpResponse response=httpClient.execute(httpPost);

                if (response != null) {
                    if (response.getStatusLine().getStatusCode() == 204)
                        result = true;
                    Log.d("Status line", "" + response.getStatusLine().getStatusCode());
                    cz.msebera.android.httpclient.HttpEntity hr = response.getEntity();

                    st = EntityUtils.toString(hr);
                    Log.d("Response_String", st);
                }

            }catch (Exception e){

            }
            return st;
        }

        @Override
        protected void onPostExecute(String st) {
            pd.dismiss();
            try{
                JSONObject jsonObjectno= new JSONObject(st);
                jsonArrayno=jsonObjectno.getJSONArray("Details");
                if(jsonArrayno.length()>0){
                    for(int i=0;i<jsonArrayno.length();i++){
                        jsno=jsonArrayno.getJSONObject(i);
                        Log.d("Kiran",jsno.toString());

                        LinearLayout.LayoutParams llhead_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        llhead_layoutParams.setMargins(0, 10, 0, 10);
                        tableRow = new LinearLayout(CourtandProsecutionStages.this);
                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                        tableRow.setWeightSum(4);
                        tableRow.setBackgroundResource(R.drawable.table_border);
//                    tableRow.setBackgroundColor(Color.parseColor("#135b96"));
                        tableRow.setLayoutParams(llhead_layoutParams);



                        LinearLayout.LayoutParams layoutParams_tv1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv1.setMargins(0, 10, 0, 10);
                        textview = new TextView(CourtandProsecutionStages.this);
                        textview.setLayoutParams(layoutParams_tv1);
                        textview.setTextColor(Color.parseColor("#717272"));
                        textview.setGravity(Gravity.CENTER);
                        textview.setText(jsno.getString("ACCUSED_ALPHANUM"));
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v1 = new View(CourtandProsecutionStages.this);
                        v1.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v1.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v1);


                        LinearLayout.LayoutParams layoutParams_tv2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv2.setMargins(0, 10, 0, 10);
                        textview = new TextView(CourtandProsecutionStages.this);
                        textview.setLayoutParams(layoutParams_tv2);
                        textview.setTextColor(Color.parseColor("#717272"));
                        textview.setGravity(Gravity.CENTER);
                        textview.setText(jsno.getString("ACCUSED_NAME"));
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v2 = new View(CourtandProsecutionStages.this);
                        v2.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v2.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v2);

                        LinearLayout cb_param = new LinearLayout(getApplicationContext());
                        cb_param.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        cb_param.setOrientation(LinearLayout.HORIZONTAL);
                        //cb_param.setBackgroundResource(R.drawable.mycustomcheckboxtheme);
                        cb_param.setGravity(Gravity.CENTER);

                        TextView empty = new TextView(getApplicationContext());
                        empty.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty.setGravity(Gravity.CENTER);
                        empty.setTextSize(16);
                        cb_param.addView(empty);


                        final CheckBox check_box = new CheckBox(CourtandProsecutionStages.this);
                        cb_param.addView(check_box);
                        tableRow.addView(cb_param);


                        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Log.d("check box id",buttonView.isChecked()+"");
                                    if(buttonView.isChecked()){
                                        //check_box.setButtonDrawable(R.mipmap.checked);
                                    }
                                    else{
                                       // check_box.setButtonDrawable(R.drawable.unchecked);
                                    }

                            }
                        });

                        View v3 = new View(CourtandProsecutionStages.this);
                        v3.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v3.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v3);



                        LinearLayout cb_param1 = new LinearLayout(getApplicationContext());
                        cb_param1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                        cb_param1.setOrientation(LinearLayout.HORIZONTAL);
                        cb_param1.setGravity(Gravity.CENTER);

                        TextView empty1 = new TextView(getApplicationContext());
                        empty1.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty1.setGravity(Gravity.CENTER);
                        empty1.setTextSize(16);
                        cb_param1.addView(empty1);


                        final Switch swithbutton = new Switch(CourtandProsecutionStages.this);
                        swithbutton.setText("No");
                        cb_param1.addView(swithbutton);
                        tableRow.addView(cb_param1);
                        table1.addView(tableRow);

                        swithbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked){
                                    swithbutton.setText("Yes");
                                }
                                else{
                                    swithbutton.setText("No");
                                }
                            }
                        });
                        super.onPostExecute(st);



                    }
                }

            }catch (Exception e){

            }


        }
    }


    private class CourtOfficerDetails extends AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);
        boolean result;
        String s;

        @Override
        protected void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.show();
            pd.setCanceledOnTouchOutside(true);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.1.5:81/LogicShore.svc/CourtOfficerDetails");
                JSONObject jsonObject =new JSONObject();
                JSONObject js = new JSONObject();
                js.put("P_LANG_CD","2022026");
                js.put("P_FIR_REG_NUM","2011012150017");
                jsonObject.put("request",js);
                String reqestmessage=jsonObject.toString();
                Log.d("reqestmessage",jsonObject.toString());


                httpPost.setEntity(new StringEntity(reqestmessage,"UTF-8"));
                httpPost.setHeader("Content-Type", "application/json");

                HttpResponse response=httpClient.execute(httpPost);
                HttpEntity hr = response.getEntity();


                if (response != null) {
                    if (response.getStatusLine().getStatusCode() == 204)
                        result = true;
                    Log.d("Status line", "" + response.getStatusLine().getStatusCode());

                    s = EntityUtils.toString(hr);
                    Log.d("Response_StringCourtDetails", s);
                }


            }catch(Exception e){

            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("details");
                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        ioname.setText(js.getString("IO_NAME"));
                        iorank.setText(js.getString("IO_DESIGNATION"));
                        Log.d("JsonDataarray",js.getString("IO_DESIGNATION"));
                        ppappname.setText(js.getString("APP_NAME"));
                        ppappdesignation.setText(js.getString("PP_DESIGNATION"));
                        defencecouncilname.setText(js.getString("DEFEFNCE_COUNCIL_NAME"));
                        defencecouncildesignation.setText(js.getString("PROSECUTOR_RANK"));
                        ppappradiobuttonno.setChecked(true);

                        if(js.getString("APP_PRESENT").toString().trim().equals("N")){
                            ppappradiobuttonno.setChecked(true);
                        }
                        else {

                            ppappradiobuttonyes.setChecked(true);
                        }
                        if(js.getString("DEFENCE_COUNCL_PRESENT").toString().trim().equals("N")){
                            defenceradiobuttonno.setChecked(true);

                        }
                        else{
                            defenceradiobuttonyes.setChecked(true);

                        }
                         if(js.getString("IS_IO_PRESENT").toString().trim().equals("N")){
                             ioradiobuttonno.setChecked(true);

                        }
                        else {
                             ioradiobuttonyes.setChecked(true);
                         }


                    }
                }


            }catch (Exception e){

            }


            super.onPostExecute(s);
        }
    }

    private class WitnessExamination extends AsyncTask<String,String,String>{
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);

        private boolean result;
        @Override
        protected void onPreExecute() {
            pd.setMessage("Please Wait...");
            pd.show();
            pd.setCanceledOnTouchOutside(true);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

                HttpClient httpClient= new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.1.5:81/LogicShore.svc/GETWITNESSLISTCD");
                try{
                    JSONObject jsonObject = new JSONObject();
                    JSONObject js = new JSONObject();
                    js.put("P_LANG_CD","99");
                    js.put("P_COURT_CASE_SRNO","20250251001450101");
                    js.put("P_USER_ID","NULL");
                    jsonObject.put("request",js);
                    String message= jsonObject.toString();
                    Log.d("requestmessage",message);
                    httpPost.setEntity(new StringEntity(message, "UTF8"));
                    httpPost.setHeader("Content-Type", "application/json");

                    HttpResponse response=httpClient.execute(httpPost);

                    if (response != null) {
                        if (response.getStatusLine().getStatusCode() == 204)
                            result = true;
                        Log.d("Status line", "" + response.getStatusLine().getStatusCode());
                        cz.msebera.android.httpclient.HttpEntity hr = response.getEntity();

                        s = EntityUtils.toString(hr);
                        Log.d("Response_String", s);
                    }

                }catch (Exception e){

                }


            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            try{
                //LinearLayout linearLayout;
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("SHOPet");

                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject js=jsonArray.getJSONObject(i);

                        personcode=js.getString("WITNESS_PERSONCODE");

                        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0,10,0,10);
                        tableRow= new LinearLayout(CourtandProsecutionStages.this);
                        tableRow.setLayoutParams(layoutParams);
                        tableRow.setWeightSum(8);
                        tableRow.setBackgroundResource(R.drawable.table_border);
                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                        tableRow.setGravity(Gravity.CENTER);


                        LinearLayout.LayoutParams first=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);
                        first.setMargins(0,10,0,10);
                       final TextView witness_code = new TextView(CourtandProsecutionStages.this);
                        witness_code.setLayoutParams(first);
                        witness_code.setTextColor(Color.parseColor("#717272"));
                        witness_code.setGravity(Gravity.CENTER);
                        witness_code.setText(js.getString("WITNESS_TYPE_CODE"));
                        witness_code.setTextSize(16);
                        tableRow.addView(witness_code);

                        View v1 = new View(CourtandProsecutionStages.this);
                        v1.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v1.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v1);

                        final LinearLayout.LayoutParams second=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);
                        second.setMargins(0,10,0,10);
                        final TextView witness_name_textview = new TextView(CourtandProsecutionStages.this);
                        witness_name_textview.setLayoutParams(second);
                        witness_name_textview.setTextColor(Color.parseColor("#717272"));
                        witness_name_textview.setGravity(Gravity.CENTER);
                        witness_name_textview.setText(js.getString("WITNESS_NAME"));
                        witness_name_textview.setTextSize(16);
                        witnessname.add(witness_name_textview);
                        tableRow.addView(witness_name_textview);



                        View v2 = new View(CourtandProsecutionStages.this);
                        v2.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v2.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v2);

                        LinearLayout.LayoutParams third=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);
                        third.setMargins(0,10,0,10);
                        final TextView witness_type = new TextView(CourtandProsecutionStages.this);
                        witness_type.setLayoutParams(third);
                        witness_type.setTextColor(Color.parseColor("#717272"));
                        witness_type.setGravity(Gravity.CENTER);
                        witness_type.setText(js.getString("WITNESS_TYPE"));
                        witness_type.setTextSize(16);
                        tableRow.addView(witness_type);

                        View v3 = new View(CourtandProsecutionStages.this);
                        v3.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v3.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v3);

                        final LinearLayout four = new LinearLayout(getApplicationContext());
                        four.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        four.setOrientation(LinearLayout.HORIZONTAL);
                        //cb_param.setBackgroundResource(R.drawable.mycustomcheckboxtheme);
                        four.setGravity(Gravity.CENTER);

                        TextView empty4 = new TextView(getApplicationContext());
                        empty4.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty4.setGravity(Gravity.CENTER);
                        empty4.setTextSize(16);
                        four.addView(empty4);


                        final CheckBox witness_fourthcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        four.addView(witness_fourthcheck_box);
                        arraylistattend.add(witness_fourthcheck_box);
                        tableRow.addView(four);

                        View v4 = new View(CourtandProsecutionStages.this);
                        v4.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v4.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v4);


                        LinearLayout five = new LinearLayout(getApplicationContext());
                        five.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        five.setOrientation(LinearLayout.HORIZONTAL);
                        //cb_param.setBackgroundResource(R.drawable.mycustomcheckboxtheme);
                        five.setGravity(Gravity.CENTER);

                        TextView empty5 = new TextView(getApplicationContext());
                        empty5.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty5.setGravity(Gravity.CENTER);
                        empty5.setTextSize(16);
                        five.addView(empty5);


                        final CheckBox witness_fifthcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        five.addView(witness_fifthcheck_box);
                        arraylistbrifed.add(witness_fifthcheck_box);
                        tableRow.addView(five);


                        View v5 = new View(CourtandProsecutionStages.this);
                        v5.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v5.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v5);



                        LinearLayout six = new LinearLayout(getApplicationContext());
                        six.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        six.setOrientation(LinearLayout.HORIZONTAL);
                        six.setGravity(Gravity.CENTER);

                        TextView empty6 = new TextView(getApplicationContext());
                        empty6.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty6.setGravity(Gravity.CENTER);
                        empty6.setTextSize(16);
                        six.addView(empty6);


                        final CheckBox witness_sixthcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        six.addView(witness_sixthcheck_box);
                        arraylistexamined.add(witness_sixthcheck_box);
                        tableRow.addView(six);


                        View v6 = new View(CourtandProsecutionStages.this);
                        v6.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v6.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v6);


                        LinearLayout seven = new LinearLayout(getApplicationContext());
                        seven.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        seven.setOrientation(LinearLayout.HORIZONTAL);
                        seven.setGravity(Gravity.CENTER);

                        TextView empty7 = new TextView(getApplicationContext());
                        empty7.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty7.setGravity(Gravity.CENTER);
                        empty7.setTextSize(16);
                        seven.addView(empty7);


                        final CheckBox witness_seventhhcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        seven.addView(witness_seventhhcheck_box);
                        arraylistsupported.add(witness_seventhhcheck_box);
                        tableRow.addView(seven);



                        View v7 = new View(CourtandProsecutionStages.this);
                        v7.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v7.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v7);


                        LinearLayout eight = new LinearLayout(getApplicationContext());
                        eight.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                        eight.setOrientation(LinearLayout.HORIZONTAL);
                        eight.setGravity(Gravity.CENTER);

                        TextView empty8 = new TextView(getApplicationContext());
                        empty8.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty8.setGravity(Gravity.CENTER);
                        empty8.setTextSize(16);
                        eight.addView(empty8);


                        final CheckBox witness_eigthhcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        eight.addView(witness_eigthhcheck_box);
                        arraylistgivenup.add(witness_eigthhcheck_box);
                        tableRow.addView(eight);



                        table2.addView(tableRow);


                       Log.d("witness",js.getString("WITNESS_TYPE_CODE"));


                    }



                }

            }catch (Exception e){

            }
            super.onPostExecute(s);
        }
    }


    private class DefenceWitnessExamination extends AsyncTask<String,String,String>{
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);
        private String s;
        private boolean result;
        @Override
        protected void onPreExecute() {
            pd.setMessage("Please Wait...");
            pd.show();
            pd.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient= new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.1.5:81/LogicShore.svc/GETWITNESSLISTCD");
            try{
                JSONObject jsonObject = new JSONObject();
                JSONObject js = new JSONObject();
                js.put("P_LANG_CD","99");
                js.put("P_COURT_CASE_SRNO","20250251001450101");
                js.put("P_USER_ID","NULL");
                jsonObject.put("request",js);
                String message= jsonObject.toString();
                Log.d("requestmessage",message);
                httpPost.setEntity(new StringEntity(message, "UTF8"));
                httpPost.setHeader("Content-Type", "application/json");

                HttpResponse response=httpClient.execute(httpPost);

                if (response != null) {
                    if (response.getStatusLine().getStatusCode() == 204)
                        result = true;
                    Log.d("Status line", "" + response.getStatusLine().getStatusCode());
                    cz.msebera.android.httpclient.HttpEntity hr = response.getEntity();

                    s = EntityUtils.toString(hr);
                    Log.d("Response_String", s);
                }

            }catch (Exception e){

            }


            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            try{
                //LinearLayout linearLayout;
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("SHOPet");
                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject js=jsonArray.getJSONObject(i);

                        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0,10,0,10);
                        tableRow= new LinearLayout(CourtandProsecutionStages.this);
                        tableRow.setLayoutParams(layoutParams);
                        tableRow.setWeightSum(9);
                        tableRow.setBackgroundResource(R.drawable.table_border);
                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                        tableRow.setGravity(Gravity.CENTER);


                        LinearLayout.LayoutParams first=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);
                        first.setMargins(0,10,0,10);
                        textview = new TextView(CourtandProsecutionStages.this);
                        textview.setLayoutParams(first);
                        textview.setTextColor(Color.parseColor("#717272"));
                        textview.setGravity(Gravity.CENTER);
                        textview.setText(js.getString("WITNESS_TYPE_CODE"));
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v1 = new View(CourtandProsecutionStages.this);
                        v1.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v1.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v1);

                        LinearLayout.LayoutParams second=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);
                        second.setMargins(0,10,0,10);
                       final TextView dwitnessname = new TextView(CourtandProsecutionStages.this);
                        dwitnessname.setLayoutParams(second);
                        dwitnessname.setTextColor(Color.parseColor("#717272"));
                        dwitnessname.setGravity(Gravity.CENTER);
                        dwitnessname.setText(js.getString("WITNESS_NAME"));
                        witnessname.add(dwitnessname);
                        textview.setTextSize(16);
                        tableRow.addView(dwitnessname);

                        View v2 = new View(CourtandProsecutionStages.this);
                        v2.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v2.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v2);

                        LinearLayout.LayoutParams third=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1);
                        third.setMargins(0,10,0,10);
                        textview = new TextView(CourtandProsecutionStages.this);
                        textview.setLayoutParams(third);
                        textview.setTextColor(Color.parseColor("#717272"));
                        textview.setGravity(Gravity.CENTER);
                        textview.setText(js.getString("WITNESS_TYPE"));
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v3 = new View(CourtandProsecutionStages.this);
                        v3.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v3.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v3);



                        LinearLayout four = new LinearLayout(getApplicationContext());
                        four.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        four.setOrientation(LinearLayout.HORIZONTAL);
                        four.setGravity(Gravity.CENTER);

                        TextView empty4 = new TextView(getApplicationContext());
                        empty4.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty4.setGravity(Gravity.CENTER);
                        empty4.setTextSize(16);
                        four.addView(empty4);


                        final CheckBox fourthcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        four.addView(fourthcheck_box);
                        arraylistattend.add(fourthcheck_box);
                        tableRow.addView(four);



                        View v4 = new View(CourtandProsecutionStages.this);
                        v4.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v4.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v4);


                        LinearLayout five = new LinearLayout(getApplicationContext());
                        five.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        five.setOrientation(LinearLayout.HORIZONTAL);
                        five.setGravity(Gravity.CENTER);

                        TextView empty5 = new TextView(getApplicationContext());
                        empty5.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty5.setGravity(Gravity.CENTER);
                        empty5.setTextSize(16);
                        five.addView(empty5);


                        final CheckBox fifthcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        five.addView(fifthcheck_box);
                        arraylistbrifed.add(fifthcheck_box);
                        tableRow.addView(five);


                        View v5 = new View(CourtandProsecutionStages.this);
                        v5.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v5.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v5);



                        LinearLayout six = new LinearLayout(getApplicationContext());
                        six.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        six.setOrientation(LinearLayout.HORIZONTAL);
                        six.setGravity(Gravity.CENTER);

                        TextView empty6 = new TextView(getApplicationContext());
                        empty6.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty6.setGravity(Gravity.CENTER);
                        empty6.setTextSize(16);
                        six.addView(empty6);


                        final CheckBox sixthcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        six.addView(sixthcheck_box);
                        arraylistexamined.add(sixthcheck_box);
                        tableRow.addView(six);

                        View v6 = new View(CourtandProsecutionStages.this);
                        v6.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v6.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v6);


                        LinearLayout seven = new LinearLayout(getApplicationContext());
                        seven.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        seven.setOrientation(LinearLayout.HORIZONTAL);
                        seven.setGravity(Gravity.CENTER);

                        TextView empty7 = new TextView(getApplicationContext());
                        empty7.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty7.setGravity(Gravity.CENTER);
                        empty7.setTextSize(16);
                        seven.addView(empty7);


                        final CheckBox seventhhcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        seven.addView(seventhhcheck_box);
                        arraylistsupported.add(seventhhcheck_box);
                        tableRow.addView(seven);


                        View v7 = new View(CourtandProsecutionStages.this);
                        v7.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v7.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v7);


                        LinearLayout eight = new LinearLayout(getApplicationContext());
                        eight.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        eight.setOrientation(LinearLayout.HORIZONTAL);
                        eight.setGravity(Gravity.CENTER);

                        TextView empty8 = new TextView(getApplicationContext());
                        empty8.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty8.setGravity(Gravity.CENTER);
                        empty8.setTextSize(16);
                        eight.addView(empty8);


                        final CheckBox eigthhcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        eight.addView(eigthhcheck_box);
                        arraylistgivenup.add(eigthhcheck_box);
                        tableRow.addView(eight);

                        View v8 = new View(CourtandProsecutionStages.this);
                        v8.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v8.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v8);

                        LinearLayout nine = new LinearLayout(getApplicationContext());
                        nine.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                        nine.setOrientation(LinearLayout.HORIZONTAL);
                        nine.setGravity(Gravity.CENTER);

                        TextView empty9 = new TextView(getApplicationContext());
                        empty9.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                        empty9.setGravity(Gravity.CENTER);
                        empty9.setTextSize(16);
                        nine.addView(empty9);


                        final CheckBox ninghtcheck_box = new CheckBox(CourtandProsecutionStages.this);
                        nine.addView(ninghtcheck_box);
                        tableRow.addView(nine);
                        arraylistcrossed.add(ninghtcheck_box);

                        table3.addView(tableRow);

                        Log.d("witness",js.getString("WITNESS_TYPE_CODE"));
                    }
                }

            }catch (Exception e){

            }
            super.onPostExecute(s);
        }
    }

    private class AccusedList extends AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);
        String s;
        boolean result;

        @Override
        protected void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.1.5:81/LogicShore.svc/GetAccusedLst");
                JSONObject js= new JSONObject();
                JSONObject jsonObject = new JSONObject();
                js.put("P_LANG_CD","99");
                js.put("P_COURT_CASE_SRNO","20220260900090101");
                js.put("P_USER_ID","ARUNA");
                jsonObject.put("request",js);
                String message=jsonObject.toString();
                httpPost.setEntity(new StringEntity(message, "UTF8"));
                httpPost.setHeader("Content-Type", "application/json");
                HttpResponse response =httpClient.execute(httpPost);

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

        @Override
        protected void onPostExecute(String s) {
            try{
                pd.dismiss();
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Accused");
                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject js =jsonArray.getJSONObject(i);
                        accusesrno=js.getString("ACCUSED_SRNO");
                        LinearLayout.LayoutParams llhead_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        llhead_layoutParams.setMargins(10, 10, 10, 10);
                        tableRow = new LinearLayout(CourtandProsecutionStages.this);
                        tableRow.setOrientation(LinearLayout.HORIZONTAL);
                        tableRow.setWeightSum(7);
                        tableRow.setBackgroundResource(R.drawable.table_borderaccused);
//                    tableRow.setBackgroundColor(Color.parseColor("#135b96"));
                        tableRow.setLayoutParams(llhead_layoutParams);


                        LinearLayout.LayoutParams layoutParams_tv1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv1.setMargins(10, 10, 10, 10);
                        textview = new TextView(CourtandProsecutionStages.this);
                        textview.setLayoutParams(layoutParams_tv1);
                        textview.setTextColor(Color.parseColor("#717272"));
                        textview.setGravity(Gravity.CENTER);
                        textview.setText(js.getString("ACCUSED_ALPHANUM"));
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v1 = new View(CourtandProsecutionStages.this);
                        v1.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v1.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v1);


                        LinearLayout.LayoutParams layoutParams_tv2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv2.setMargins(10, 10, 10, 10);
                        textview = new TextView(CourtandProsecutionStages.this);
                        textview.setLayoutParams(layoutParams_tv2);
                        textview.setTextColor(Color.parseColor("#717272"));
                        textview.setGravity(Gravity.CENTER);
                        textview.setText(js.getString("FULL_NAME"));
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v2 = new View(CourtandProsecutionStages.this);
                        v2.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v2.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v2);


                        LinearLayout.LayoutParams layoutParams_spinner = new LinearLayout.LayoutParams(0, 40, 1);
                        layoutParams_spinner.setMargins(10, 10, 10, 10);
                        Spinner spinner = new Spinner(CourtandProsecutionStages.this);
                        spinner.setLayoutParams(layoutParams_spinner);
                        spinner.setBackgroundResource(R.drawable.spinneraccused);
                        spinner.setGravity(Gravity.CENTER);

                        ArrayAdapter arrayAdapterspinner= new ArrayAdapter(CourtandProsecutionStages.this,android.R.layout.simple_spinner_dropdown_item,master_value_presence);
                        arrayAdapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(arrayAdapterspinner);
                        Log.d("qwerty",spinner.toString());

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(CourtandProsecutionStages.this,master_id_presence.get(position).toString(),Toast.LENGTH_SHORT).show();
                                masteridpresence=master_id_presence.get(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        tableRow.addView(spinner);



                        View v3 = new View(CourtandProsecutionStages.this);
                        v3.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v3.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v3);

                        LinearLayout.LayoutParams layoutParams_spinner1 = new LinearLayout.LayoutParams(0, 40, 1);
                        layoutParams_spinner1.setMargins(10, 10, 10, 10);
                        Spinner spinner1 = new Spinner(CourtandProsecutionStages.this);
                        spinner1.setLayoutParams(layoutParams_spinner1);
                        spinner1.setBackgroundResource(R.drawable.spinneraccused);
                        spinner1.setGravity(Gravity.CENTER);


                        ArrayAdapter arrayAdapterspinner1= new ArrayAdapter(CourtandProsecutionStages.this,android.R.layout.simple_spinner_dropdown_item,master_value_petition);
                        arrayAdapterspinner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(arrayAdapterspinner1);
                        Log.d("qwerty",spinner.toString());

                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(CourtandProsecutionStages.this,master_id_petition.get(position).toString(),Toast.LENGTH_SHORT).show();
                                masteridpetition=master_id_petition.get(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        tableRow.addView(spinner1);


                        View v4 = new View(CourtandProsecutionStages.this);
                        v4.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v4.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v4);

                        LinearLayout.LayoutParams layoutParams_tv21 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv21.setMargins(10, 10, 10, 10);
                        textview = new TextView(CourtandProsecutionStages.this);
                        textview.setLayoutParams(layoutParams_tv21);
                        textview.setTextColor(Color.parseColor("#135b97"));
                        textview.setGravity(Gravity.CENTER);
                        textview.setText("Warrants");
                        textview.setPaintFlags(textview.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v5 = new View(CourtandProsecutionStages.this);
                        v5.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v5.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v5);


                        LinearLayout.LayoutParams layoutParams_tv211 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv211.setMargins(10, 10, 10, 10);
                        textview = new TextView(CourtandProsecutionStages.this);
                        textview.setLayoutParams(layoutParams_tv211);
                        textview.setTextColor(Color.parseColor("#135b97"));
                        textview.setGravity(Gravity.CENTER);
                        textview.setText("Notice");
                        textview.setPaintFlags(textview.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        View v6 = new View(CourtandProsecutionStages.this);
                        v6.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
                        v6.setBackgroundColor(Color.parseColor("#9fa1a3"));
                        tableRow.addView(v6);

                        LinearLayout.LayoutParams layoutParams_tv22 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams_tv22.setMargins(10, 10, 10, 10);
                        textview = new TextView(CourtandProsecutionStages.this);
                        textview.setLayoutParams(layoutParams_tv22);
                        textview.setTextColor(Color.parseColor("#135b97"));
                        textview.setGravity(Gravity.CENTER);
                        textview.setText("Proclamation");
                        textview.setPaintFlags(textview.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                        textview.setTextSize(16);
                        tableRow.addView(textview);

                        accusedtable.addView(tableRow);

                    }
                }
            }catch (Exception e){

            }
            super.onPostExecute(s);
        }
    }

    private class Petition_Filled_by_Defence extends AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);
        String s;
        @Override
        protected void onPreExecute() {
            pd.setMessage("please wait...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet= new HttpGet("http://192.168.1.5:81//LogicShore.svc/YES_NO");
            HttpResponse response = httpClient.execute(httpGet);

            s=EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            master_value_petition=new ArrayList<>();
            master_id_petition=new ArrayList<>();
            try{
                JSONObject jsonObject =new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("Details");
                if(jsonArray.length()>0){
                    for(int i =0;i<jsonArray.length();i++){
                      JSONObject js=jsonArray.getJSONObject(i);
                        Log.d("MASTER_CD",js.getString("MASTER_CD").toString());
                        MasterYes=js.getString("MASTER_VALUE").toString();
                        master_value_petition.add(js.getString("MASTER_VALUE").toString());
                        master_id_petition.add(js.getString("MASTER_CD").toString());
                    }
                }

            }catch (Exception e){

            }
            super.onPostExecute(s);
        }
    }

    private class PresenceService extends AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);
        String s;
        @Override
        protected void onPreExecute() {
            pd.setMessage("please wait...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet= new HttpGet("http://192.168.1.5:81//LogicShore.svc/PRESENT_NOTPRESENT");
                HttpResponse response = httpClient.execute(httpGet);

                s=EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            master_value_presence=new ArrayList<>();
            master_id_presence=new ArrayList<>();
            try{
                JSONObject jsonObject =new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("Details");
                if(jsonArray.length()>0){
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject js=jsonArray.getJSONObject(i);
                        Log.d("MASTER_CD",js.getString("MASTER_CD").toString());
                        MasterYes=js.getString("MASTER_VALUE").toString();
                        master_value_presence.add(js.getString("MASTER_VALUE").toString());
                        master_id_presence.add(js.getString("MASTER_CD").toString());
                    }
                }

            }catch (Exception e){

            }
            super.onPostExecute(s);
        }
    }

    private class SubmitService extends AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);
        boolean result;
        String s;

        @Override
        protected void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCanceledOnTouchOutside(true);
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.1.5:81/LogicShore.svc/WE_DWEinsert");
            JSONObject jsonmain= new JSONObject();
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < arraylistattend.size(); i++) {
                String attendvalue, brefiedvalue, examined, supported, givenup, crossed = null;

                Witnessname = witnessname.get(i).getText().toString();
                JSONObject witness_examination = new JSONObject();

                if(selectvalue.equals("Defence Witness Examination")){
                    try {
                        witness_examination.put("TYPE", "DWE");
                        witness_examination.put("P_LANG_CD", "99");
                        witness_examination.put("P_TRIAL_SRNO", getIntent().getStringExtra("trailserialnumber"));
                        witness_examination.put("P_TRIAL_STAGE_CD", Selectedstageid);
                        witness_examination.put("P_FIR_REG_NUM", firno.getText().toString());
                        witness_examination.put("P_WITNESS_NAME", Witnessname);//Witnessname
                        witness_examination.put("P_PERSON_CODE", personcode);
                        witness_examination.put("P_EXAMINATION_STATUS_CD", examinevalue);
                        //witness_examination.put("P_CROSS_EXAMIN_STATUS_CD", "");
                        witness_examination.put("P_NEXT_TRIAL_STAG_CD", Selectedstageid);
                        witness_examination.put("P_USERID", "Aruna");
                        attenededchecked = arraylistattend.get(i).isChecked();
                        if (attenededchecked) {
                            attendvalue = "3000";
                        } else {
                            attendvalue = "3001";
                        }
                        witness_examination.put("P_WITNESS_PRESENCE_CD", attendvalue);

                        brifedbychecked = arraylistbrifed.get(i).isChecked();
                        if (brifedbychecked) {
                            brefiedvalue = "3000";
                        } else {
                            brefiedvalue = "3001";
                        }
                        witness_examination.put("P_BRIEFED_BY_PP", brefiedvalue);

                        examinedchecked = arraylistexamined.get(i).isChecked();
                        if (examinedchecked) {
                            examined = "3000";
                        } else {
                            examined = "3001";
                        }
                        witness_examination.put("P_EXAMINATION_STATUS_CD", examined);
                        suppotedchecked = arraylistsupported.get(i).isChecked();
                        if (suppotedchecked) {
                            supported = "3000";
                        } else {
                            supported = "3001";
                        }
                        witness_examination.put("P_SUPPRTED_PROSECUTN_CD", supported);
                        givenupchecked = arraylistgivenup.get(i).isChecked();
                        if (givenupchecked) {
                            givenup = "3000";
                        } else {
                            givenup = "3001";
                        }
                        witness_examination.put("P_GIVEN_UP", givenup);

//                        if (arraylistcrossed.size() > 0) {
                            crossedchecked = arraylistcrossed.get(i).isChecked();
                            if (crossedchecked) {
                                crossed = "3000";
                            } else {
                                crossed = "3001";
                            }
                        //}

                        witness_examination.put("P_CROSS_EXAMIN_STATUS_CD", crossed);

                    } catch (Exception e) {

                    }
                }
                else if(selectvalue.equals("Witness Examination")) {

                    try {
                        witness_examination.put("TYPE", "WE");
                        witness_examination.put("P_LANG_CD", "99");
                        witness_examination.put("P_TRIAL_SRNO", getIntent().getStringExtra("trailserialnumber"));
                        witness_examination.put("P_TRIAL_STAGE_CD", Selectedstageid);
                        witness_examination.put("P_FIR_REG_NUM", firno.getText().toString());
                        witness_examination.put("P_WITNESS_NAME", Witnessname);//Witnessname
                        witness_examination.put("P_PERSON_CODE", personcode);
                        witness_examination.put("P_EXAMINATION_STATUS_CD", examinevalue);
                        //witness_examination.put("P_CROSS_EXAMIN_STATUS_CD", "");
                        witness_examination.put("P_NEXT_TRIAL_STAG_CD", Selectedstageid);
                        witness_examination.put("P_USERID", "Aruna");
                        attenededchecked = arraylistattend.get(i).isChecked();
                        if (attenededchecked) {
                            attendvalue = "3000";
                        } else {
                            attendvalue = "3001";
                        }
                        witness_examination.put("P_WITNESS_PRESENCE_CD", attendvalue);

                        brifedbychecked = arraylistbrifed.get(i).isChecked();
                        if (brifedbychecked) {
                            brefiedvalue = "3000";
                        } else {
                            brefiedvalue = "3001";
                        }
                        witness_examination.put("P_BRIEFED_BY_PP", brefiedvalue);

                        examinedchecked = arraylistexamined.get(i).isChecked();
                        if (examinedchecked) {
                            examined = "3000";
                        } else {
                            examined = "3001";
                        }
                        witness_examination.put("P_EXAMINATION_STATUS_CD", examined);
                        suppotedchecked = arraylistsupported.get(i).isChecked();
                        if (suppotedchecked) {
                            supported = "3000";
                        } else {
                            supported = "3001";
                        }
                        witness_examination.put("P_SUPPRTED_PROSECUTN_CD", supported);
                        givenupchecked = arraylistgivenup.get(i).isChecked();
                        if (givenupchecked) {
                            givenup = "3000";
                        } else {
                            givenup = "3001";
                        }
                        witness_examination.put("P_GIVEN_UP", givenup);

//                        if (arraylistcrossed.size() > 0) {
//                            crossedchecked = arraylistcrossed.get(i).isChecked();
//                            if (crossedchecked) {
//                                crossed = "3000";
//                            } else {
//                                crossed = "3001";
//                            }
//                        }

                        witness_examination.put("P_CROSS_EXAMIN_STATUS_CD", "3001");

                    } catch (Exception e) {

                    }
                }
                     jsonArray.put(witness_examination);

            }
            try {
                jsonObject.put("reqWE_DWE",jsonArray);
                jsonmain.put("erequest",jsonObject);
                String message=jsonmain.toString();
                Log.d("jsondata",message);
                httpPost.setEntity(new StringEntity(message, "UTF8"));
                httpPost.setHeader("Content-Type", "application/json");
                HttpResponse response = null;
                response = httpClient.execute(httpPost);
                if (response != null) {
                    if (response.getStatusLine().getStatusCode() == 204)
                        result = true;
                    Log.d("Status line", "" + response.getStatusLine().getStatusCode());
                    cz.msebera.android.httpclient.HttpEntity hr = response.getEntity();
                    s = EntityUtils.toString(hr);
                    Log.d("SubmitResponse_String", s);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }



            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                pd.dismiss();
                JSONObject jsonObject=new JSONObject(s);
                String finalresponse=jsonObject.getString("message");
                if(finalresponse.equals("Sucess")){
                    Log.d("FinalResponse In",jsonObject.getString("message"));
                    arraylistattend.clear();
                    arraylistbrifed.clear();
                    arraylistexamined.clear();
                    arraylistsupported.clear();
                    arraylistgivenup.clear();
                    arraylistcrossed.clear();
                    finish();

                }
                Log.d("FinalResponse",jsonObject.getString("message"));
            }catch (Exception e){

            }

            super.onPostExecute(s);
        }
    }


    private class AccusedSubmit extends AsyncTask<String,String,String> {
        ProgressDialog pd = new ProgressDialog(CourtandProsecutionStages.this);
        String s;
        boolean result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please wait...");
            pd.setCanceledOnTouchOutside(true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.1.5:81/LogicShore.svc/InsertAccusedExamin");
                JSONObject accusedmain= new JSONObject();
                    JSONObject accusedjson= new JSONObject();
                    JSONArray accusedarray= new JSONArray();
                    JSONObject jsonObjectaccused= new JSONObject();

                        accusedjson.put("P_LANG_CD","99");
                        accusedjson.put("P_TRIAL_SRNO",getIntent().getStringExtra("trailserialnumber"));//getIntent().getStringExtra("trailserialnumber")
                        accusedjson.put("P_FIR_REG_NUM",firno.getText().toString());
                        accusedjson.put("P_TRIAL_STAGE_CD",Selectedstageid);
                        accusedjson.put("P_NEXT_TRIAL_STAG_CD",Selectedstageid);
                        accusedjson.put("P_USERID","Aruna");
                        accusedjson.put("P_REMARKS","testremarks");
                        accusedjson.put("P_GIVEN_UP","3000");

                        accusedarray.put(jsonObjectaccused);
                        accusedmain.put("request",accusedjson);
                        jsonObjectaccused.put("P_IS_ARGUMENTS","3000");
                        jsonObjectaccused.put("P_ACCUSED_SRNO",accusesrno);
                        jsonObjectaccused.put("P_ACCUSED_STATUS_CD","0");
                        jsonObjectaccused.put("P_ACCUSED_PRESENCE_CD",masteridpresence);//dropdown
                        jsonObjectaccused.put("P_EXAMINATION_STATUS_CD","0");
                        jsonObjectaccused.put("P_CROSS_EXAMIN_STATUS_CD","3000");
                        jsonObjectaccused.put("P_CONFESSED_FLG","3001");
                        jsonObjectaccused.put("P_IS_DEFNC_FILED_PETITION",masteridpetition);//dropdown
                        jsonObjectaccused.put("P_IS_ISSUE_WARRANT","N");
                        jsonObjectaccused.put("P_IS_ISSUE_NTC_SUERTY","N");
                        jsonObjectaccused.put("P_IS_PROCLAIMED","N");
                        jsonObjectaccused.put("P_IS_COURTDISPOSAL","N");
                        jsonObjectaccused.put("P_IS_DEFNC_WIT_EXAMNTN","3000");
                        jsonObjectaccused.put("P_ACU_PENDING_RSN","0");
                        jsonObjectaccused.put("P_IS_ALL_ACCUSED_PLEADED","3001");

                         accusedjson.put("AccReq",accusedarray);

                        String meassage=accusedmain.toString();
                Log.d("Accusedrequest",meassage);
                        httpPost.setEntity(new StringEntity(meassage,"UTF8"));
                        httpPost.setHeader("","");
                        HttpResponse response = httpClient.execute(httpPost);
                        if (response != null) {
                            if (response.getStatusLine().getStatusCode() == 204)
                                result = true;
                            Log.d("Status line", "" + response.getStatusLine().getStatusCode());
                            cz.msebera.android.httpclient.HttpEntity hr = response.getEntity();
                            s = EntityUtils.toString(hr);
                            Log.d("SubmitResponse_String", s);
                        }

                Log.d("accusedrequest",accusedmain.toString());


            }catch (Exception e){

            }
            return s;
        }
    }
}
