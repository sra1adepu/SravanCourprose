package com.logicshore.courtsandprosecution;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public class CourtCaseDiaryHome extends AppCompatActivity {

    ConnectionDetector connectionDetector;
    JSONObject main_req_obj;
    ListView recycler_view_home_list;
    CourtCaseDiaryRecyclerView courtCaseDiaryRecyclerView;
    TextView count_tv;
    EditText crime_number,charge_sheet,court_case,nextadt_date;
    Spinner case_type_sp,court_name_sp;
    Button search_bt;
    String courtname_code_selected;
    String case_type_code_selected;
    DatePickerDialog nextadt_date_DatePickerDialog;
    Calendar calendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_case_diary);
        recycler_view_home_list=(ListView)findViewById(R.id.recycler_view) ;
        //recycler_view_home_list.setHasFixedSize(true);

//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
//        recycler_view_home_list.setLayoutManager(mLayoutManager);
        crime_number=(EditText)findViewById(R.id.crime_number_et);
        charge_sheet=(EditText)findViewById(R.id.charge_sheet_et);
        court_case=(EditText)findViewById(R.id.court_case_et);
        nextadt_date=(EditText)findViewById(R.id.next_edit_dt);
        case_type_sp=(Spinner)findViewById(R.id.crime_type_sp);
        court_name_sp=(Spinner)findViewById(R.id.court_name_sp);
        search_bt=(Button)findViewById(R.id.ccd_search_bt);
        count_tv=(TextView)findViewById(R.id.count_tv);
        calendar=Calendar.getInstance();
        nextadt_date_DatePickerDialog=new DatePickerDialog(CourtCaseDiaryHome.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar= Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                nextadt_date.setText(sdf.format(calendar.getTime()));
                //from_dt_req_values=from_date_et.getText().toString();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        nextadt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextadt_date_DatePickerDialog.show();
            }
        });
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectionDetector.isConnectingToInternet()) {
                    get_ccd_home_search();
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        connectionDetector=new ConnectionDetector(CourtCaseDiaryHome.this);
        if (connectionDetector.isConnectingToInternet()) {
            get_ccd_home_list();
            get_courtname_type_sp();
            get_case_type_sp();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
    private void get_courtname_type_sp() {
        String req_url = "http://192.168.1.5:81/LogicShore.svc/DDCOURTSDetails";
        final ProgressDialog progressDialog = new ProgressDialog(CourtCaseDiaryHome.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        JsonObjectRequest parkingAdd_jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, req_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("response", response.toString());
                ArrayList<String> courtname_ArrayList=new ArrayList<>();
                final HashMap<String,String> courtname_StringStringHashMap=new HashMap<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("Details");
                    courtname_ArrayList.add("Select");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject inner_json_obj = jsonArray.getJSONObject(i);
                        courtname_ArrayList.add(inner_json_obj.getString("COURT"));
                        courtname_StringStringHashMap.put(inner_json_obj.getString("COURT"), inner_json_obj.getString("COURT_CD"));
                    }

                    ArrayAdapter<String> servicesp_adapter = new ArrayAdapter<String>(CourtCaseDiaryHome.this, android.R.layout.simple_spinner_item, courtname_ArrayList);
                    court_name_sp.setAdapter(servicesp_adapter);
                    court_name_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (adapterView.getSelectedItem().toString().contains("Select")) {
                                courtname_code_selected = "";
                            } else {
                                if (courtname_StringStringHashMap.size() > 0) {
                                    courtname_code_selected = courtname_StringStringHashMap.get(adapterView.getSelectedItem().toString());
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (JSONException e) {
                    Log.d("jsonexcepton", String.valueOf(e.getMessage()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Volley Error", error.toString());
                Toast.makeText(getApplicationContext(), "Network Glitch Please try again", Toast.LENGTH_SHORT).show();
            }
        });
        parkingAdd_jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(parkingAdd_jsonObjectRequest);
    }
    private void get_case_type_sp() {
        String req_url = "http://192.168.1.5:81/LogicShore.svc/DDCASETYPESDetails";
        final ProgressDialog progressDialog = new ProgressDialog(CourtCaseDiaryHome.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        JsonObjectRequest parkingAdd_jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, req_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("response", response.toString());
                ArrayList<String> casetype_ArrayList=new ArrayList<>();
                final HashMap<String,String> casetype_StringStringHashMap=new HashMap<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("Details");
                    casetype_ArrayList.add("Select");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject inner_json_obj = jsonArray.getJSONObject(i);
                        casetype_ArrayList.add(inner_json_obj.getString("MASTER_VALUE"));
                        casetype_StringStringHashMap.put(inner_json_obj.getString("MASTER_VALUE"), inner_json_obj.getString("MASTER_CD"));
                    }

                    ArrayAdapter<String> casetype_adapter = new ArrayAdapter<String>(CourtCaseDiaryHome.this, android.R.layout.simple_spinner_item, casetype_ArrayList);
                    case_type_sp.setAdapter(casetype_adapter);
                    case_type_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (adapterView.getSelectedItem().toString().contains("Select")) {
                                case_type_code_selected = "";
                            } else {
                                if (casetype_StringStringHashMap.size() > 0) {
                                    case_type_code_selected = casetype_StringStringHashMap.get(adapterView.getSelectedItem().toString());
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (JSONException e) {
                    Log.d("jsonexcepton", String.valueOf(e.getMessage()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Volley Error", error.toString());
                Toast.makeText(getApplicationContext(), "Network Glitch Please try again", Toast.LENGTH_SHORT).show();
            }
        });
        parkingAdd_jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(parkingAdd_jsonObjectRequest);
    }
    private void get_ccd_home_list(){

        String req_url="http://192.168.1.5:81/LogicShore.svc/CcdHomeDetails";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("P_PS_CD","2022026");
            jsonObject.put("P_LANG_CD","99");
            main_req_obj = new JSONObject();
            main_req_obj.put("request", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Log.d("mainreq_obj",main_req_obj.toString());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,req_url, main_req_obj, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("ccd_home_ list response",response.toString());

                CourtCaseHomeListDetails courtCaseHomeListDetails = null;
                try {
                   JSONArray json_arr_details= response.getJSONArray("details");
                    if(json_arr_details.length()>0) {
                      //  count_tv.setText("Court cases count: " + json_arr_details.length());
                      //  HashMap<String,JSONObject>  stringJSONObjectHashMap=new HashMap<>();
                        MultiMap multiMap=new MultiValueMap();
                        for (int i = 0; i < json_arr_details.length(); i++) {
                            JSONObject jsonObject2 = json_arr_details.getJSONObject(i);
                            String uniquecasevalue_st= "ccsno"+jsonObject2.getString("COURT_CASE_SRNO")+"firregnum"+jsonObject2.getString("FIR_REG_NUM")+"csfinalressrno"+jsonObject2.getString("CS_FINAL_REP_SRNO");
                            multiMap.put(uniquecasevalue_st,jsonObject2);

                       }
                        Set<String> keys = multiMap.keySet();
                        //SortedSet<String> strings=multiMap.keySet();
                        Log.d("keys.size()", String.valueOf(keys.size()));
                        count_tv.setText("Court cases count: " + String.valueOf(keys.size()));
                        // iterate through the key set and display key and values
                        ArrayList<String> casestages_ArrayList = null;
                        ArrayList<ArrayList> all_casestages_ArrayList=new ArrayList<>();
                        HashMap<Integer,ArrayList> hashdata=new HashMap<>();
                        for (String key : keys) {
                            Log.d("keyyy values",multiMap.get(key).toString());
                        }
                            int i=0;

                        for (String key : keys) {
                          //  for(int j=0;j<keys.size();j++)
                            ArrayList<CourtCaseHomeListDetails> courtCaseHomeListDetailsArrayList=new ArrayList<>();
                            Log.d("keys values",key);
                            Log.d("keyyy values",multiMap.get(key).toString());
                            casestages_ArrayList=new ArrayList<>();
                            JSONArray jsonArray=new JSONArray(multiMap.get(key).toString());
                            Log.d("jsonArraylength", String.valueOf(jsonArray.length()));
                            for (int j=0;j<jsonArray.length();j++) {
                                    courtCaseHomeListDetails = new CourtCaseHomeListDetails();
                                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                                    courtCaseHomeListDetails.setCrime_number(jsonObject.getString("FIR_NO"));
                                    courtCaseHomeListDetails.setCharge_sheet_number(jsonObject.getString("CHARGESHEET_NO"));
                                    courtCaseHomeListDetails.setCharge_sheet_dt(jsonObject.getString("CHARGESHEET_DT"));
                                    courtCaseHomeListDetails.setCourtcase_number(jsonObject.getString("COURT_CASE_NUM"));
                                    courtCaseHomeListDetails.setCourt_name(jsonObject.getString("COURT_NAME"));
//                                    casestages_ArrayList.add(jsonObject.getString("ADJOURNMENT_NUM"));
//                                    all_casestages_ArrayList.add(casestages_ArrayList);
                                    courtCaseHomeListDetails.setAdjumenentno(jsonObject.getString("ADJOURNMENT_NUM"));
                                    courtCaseHomeListDetails.setStageofthe_case(jsonObject.getString("PRESENT_CASE_STAGE"));
                                    courtCaseHomeListDetails.setNextadjence_dt(jsonObject.getString("NEXT_ADJOURNMENT_DT"));
                                    courtCaseHomeListDetails.setAction(jsonObject.getString("CCD_STATUS"));
                                    courtCaseHomeListDetails.setCourtcase_sr_number(jsonObject.getString("COURT_CASE_SRNO"));
                                    courtCaseHomeListDetails.setPolicestation(jsonObject.getString("PS_NAME"));
                                    courtCaseHomeListDetails.setSdpo_name(jsonObject.getString("SDPO_NAME"));
                                    //courtCaseHomeListDetails.setCaseStages_arraylist(casestages_ArrayList);
                                    courtCaseHomeListDetails.setFir_reg_number(jsonObject.getString("FIR_REG_NUM"));
                                    courtCaseHomeListDetails.setTrialserialno(jsonObject.getString("TRIAL_SRNO"));
                                    courtCaseHomeListDetailsArrayList.add(courtCaseHomeListDetails);
                           }
                            hashdata.put(i,courtCaseHomeListDetailsArrayList);
                            i++;

                        }
                        CourtCaseDairyListView courtCaseDiaryRecyclerView = new CourtCaseDairyListView(hashdata, CourtCaseDiaryHome.this);
                        recycler_view_home_list.setAdapter(courtCaseDiaryRecyclerView);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No values found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Volley Error",error.toString());
                Toast.makeText(getApplicationContext(),"Network Glitch Please try again",Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(25000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
    private void get_ccd_home_search(){
        String req_url="http://192.168.1.5:81/LogicShore.svc/CcdHomeDetails";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("P_FIR_NO",crime_number.getText().toString());
            jsonObject.put("P_CS_NO",charge_sheet.getText().toString());
            jsonObject.put("P_CC_TYPE_CD",case_type_code_selected);
            jsonObject.put("P_CC_NO",court_case.getText().toString());
            jsonObject.put("P_COURT_CD",courtname_code_selected);
            jsonObject.put("P_NXT_ADJ_DT",nextadt_date.getText().toString());
            jsonObject.put("P_PS_CD","2022026");
            jsonObject.put("P_LANG_CD","99");
            main_req_obj = new JSONObject();
            main_req_obj.put("request", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDialog=new ProgressDialog(CourtCaseDiaryHome.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Log.d("mainreq_obj",main_req_obj.toString());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,req_url, main_req_obj, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("ccd_home_ list response",response.toString());
                CourtCaseHomeListDetails courtCaseHomeListDetails = null;
                try {
                    JSONArray json_arr_details= response.getJSONArray("details");
                    if(json_arr_details.length()>0) {
                        //  count_tv.setText("Court cases count: " + json_arr_details.length());
                        //  HashMap<String,JSONObject>  stringJSONObjectHashMap=new HashMap<>();
                        MultiMap multiMap=new MultiValueMap();
                        for (int i = 0; i < json_arr_details.length(); i++) {
                            JSONObject jsonObject2 = json_arr_details.getJSONObject(i);
                            String uniquecasevalue_st= "ccsno"+jsonObject2.getString("COURT_CASE_SRNO")+"firregnum"+jsonObject2.getString("FIR_REG_NUM")+"csfinalressrno"+jsonObject2.getString("CS_FINAL_REP_SRNO");
                            multiMap.put(uniquecasevalue_st,jsonObject2);

                        }
                        Set<String> keys = multiMap.keySet();
                        Log.d("keys.size()", String.valueOf(keys.size()));
                        count_tv.setText("Court cases count: " + String.valueOf(keys.size()));
                        // iterate through the key set and display key and values
                        ArrayList<String> casestages_ArrayList = null;
                        ArrayList<ArrayList> all_casestages_ArrayList=new ArrayList<>();
                        HashMap<Integer,ArrayList> hashdata=new HashMap<>();
                        int i=0;
                        for (String key : keys) {
                            //  for(int j=0;j<keys.size();j++)
                            ArrayList<CourtCaseHomeListDetails> courtCaseHomeListDetailsArrayList=new ArrayList<>();
                            Log.d("keys values",key);
                            Log.d("keyyy values",multiMap.get(key).toString());
                            casestages_ArrayList=new ArrayList<>();
                            JSONArray jsonArray=new JSONArray(multiMap.get(key).toString());
                            Log.d("jsonArraylength", String.valueOf(jsonArray.length()));
                            for (int j=0;j<jsonArray.length();j++) {
                                courtCaseHomeListDetails = new CourtCaseHomeListDetails();
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                courtCaseHomeListDetails.setCrime_number(jsonObject.getString("FIR_NO"));
                                courtCaseHomeListDetails.setCharge_sheet_number(jsonObject.getString("CHARGESHEET_NO"));
                                courtCaseHomeListDetails.setCharge_sheet_dt(jsonObject.getString("CHARGESHEET_DT"));
                                courtCaseHomeListDetails.setCourtcase_number(jsonObject.getString("COURT_CASE_NUM"));
                                courtCaseHomeListDetails.setCourt_name(jsonObject.getString("COURT_NAME"));
//                                    casestages_ArrayList.add(jsonObject.getString("ADJOURNMENT_NUM"));
//                                    all_casestages_ArrayList.add(casestages_ArrayList);
                                courtCaseHomeListDetails.setAdjumenentno(jsonObject.getString("ADJOURNMENT_NUM"));
                                courtCaseHomeListDetails.setStageofthe_case(jsonObject.getString("PRESENT_CASE_STAGE"));
                                courtCaseHomeListDetails.setNextadjence_dt(jsonObject.getString("NEXT_ADJOURNMENT_DT"));
                                courtCaseHomeListDetails.setAction(jsonObject.getString("CCD_STATUS"));
                                courtCaseHomeListDetails.setCourtcase_sr_number(jsonObject.getString("COURT_CASE_SRNO"));
                                courtCaseHomeListDetails.setPolicestation(jsonObject.getString("PS_NAME"));
                                courtCaseHomeListDetails.setSdpo_name(jsonObject.getString("SDPO_NAME"));
                                //courtCaseHomeListDetails.setCaseStages_arraylist(casestages_ArrayList);
                                courtCaseHomeListDetailsArrayList.add(courtCaseHomeListDetails);
                            }
                            hashdata.put(i,courtCaseHomeListDetailsArrayList);
                            i++;

                        }
                        CourtCaseDairyListView courtCaseDiaryRecyclerView = new CourtCaseDairyListView(hashdata, CourtCaseDiaryHome.this);
                        recycler_view_home_list.setAdapter(courtCaseDiaryRecyclerView);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No values found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Volley Error",error.toString());
                Toast.makeText(getApplicationContext(),"Network Glitch Please try again",Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(25000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

}
