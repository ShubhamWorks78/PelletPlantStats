package com.seals.shubham.pelletplant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    Button sav,dis,home;
    RequestQueue req;

    EditText coal,lime,haem,mang;
    EditText da,mon,yea;

    private final String url = "https://ravichoudhary.000webhostapp.com/PelletPlant/insert_data.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        req = Volley.newRequestQueue(FeedActivity.this);

        sav = (Button)findViewById(R.id.Btn_Sav);
        dis = (Button)findViewById(R.id.Btn_Disc);

        coal = (EditText)findViewById(R.id.editCoal);
        lime = (EditText)findViewById(R.id.editLime);
        haem = (EditText)findViewById(R.id.editHaem);
        mang = (EditText)findViewById(R.id.editMang);
        da = (EditText)findViewById(R.id.edit_Day);
        mon = (EditText)findViewById(R.id.edit_Month);
        yea = (EditText)findViewById(R.id.edit_Year);

        home = (Button)findViewById(R.id.Btn_Feed_Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    initialise();
            }
        });

        sav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String coal_d = coal.getText().toString();
                final String lime_d = lime.getText().toString();
                final String haem_d = haem.getText().toString();
                final String mang_d = mang.getText().toString();
                final String day = da.getText().toString();
                final String month = mon.getText().toString();
                final String year = yea.getText().toString();

                boolean ans = true;
                Integer day_a = -1,month_a = -1,year_a = -1;
                if(!day.isEmpty() && !month.isEmpty() && !year.isEmpty()){
                    day_a = Integer.parseInt(day);
                    month_a = Integer.parseInt(month);
                    year_a = Integer.parseInt(year);
                    if(coal_d.isEmpty() || lime_d.isEmpty() || haem_d.isEmpty() || mang_d.isEmpty()){
                        ans = false;
                    }
                }else{
                    ans = false;
                }
                if(ans==false){
                    Toast.makeText(FeedActivity.this,"Data Insufficient ",Toast.LENGTH_LONG).show();
                }else{
                    if(!chck_year(year_a) || !chck_month(month_a) || !chck(day_a,month_a,year_a)){
                        Toast.makeText(FeedActivity.this,"Wrong Date Format",Toast.LENGTH_LONG).show();
                    }else{
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                initialise();
                                Toast.makeText(FeedActivity.this,""+response.toString(),Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FeedActivity.this,""+error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> param = new HashMap<String, String>();
                                param.put("Day",day);
                                param.put("Month",month);
                                param.put("Year",year);
                                param.put("Coal",coal_d);
                                param.put("Limestone",lime_d);
                                param.put("Haematite",haem_d);
                                param.put("Manganese",mang_d);

                                return param;
                            }
                        };
                        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        req.add(request);
                    }
                }
            }
        });
    }

    public static boolean chck_year(int year){
        if(year<1900 || year>2017){
            return false;
        }
        return true;
    }

    public static boolean chck_month(int month){
        if(month>=1 && month<=12){
            return true;
        }
        return false;
    }

    public static boolean chck(int day,int month,int year){
        if(month==2){
            boolean leap = chck_leap(year);
            if(leap){
                if(day>29){
                    return false;
                }
                return true;
            }
            else{
                if(day>28){
                    return false;
                }
                return true;
            }
        }
        else{
            if(month==4 || month == 6 || month == 9 || month == 11){
                if(day>30){
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                if(day>31){
                    return false;
                }
                else{
                    return true;
                }
            }
        }
    }

    public  void initialise(){
        da.setText(null);
        mon.setText(null);
        yea.setText(null);
        coal.setText(null);
        lime.setText(null);
        haem.setText(null);
        mang.setText(null);
    }
    public static boolean chck_leap(int year){
        if(year%4==0 && year!= 1900){
            return true;
        }
        return false;
    }
}
