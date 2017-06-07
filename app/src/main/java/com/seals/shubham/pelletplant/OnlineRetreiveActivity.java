package com.seals.shubham.pelletplant;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class OnlineRetreiveActivity extends AppCompatActivity {

    OfflineStorage db = new OfflineStorage(this);
    EditText day,month,year;
    RequestQueue req;
    TextView txt_coal,txt_lime,txt_haem,txt_mang;
    Button show,home,reset,save;
    private final String url = "https://ravichoudhary.000webhostapp.com/PelletPlant/fetch_data.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_retreive);
        req = Volley.newRequestQueue(OnlineRetreiveActivity.this);

        txt_coal = (TextView)findViewById(R.id.text_coal);
        txt_lime = (TextView)findViewById(R.id.text_lime);
        txt_haem = (TextView)findViewById(R.id.text_haem);
        txt_mang = (TextView)findViewById(R.id.text_mang);
        day = (EditText)findViewById(R.id.ret_Day);
        month = (EditText)findViewById(R.id.ret_Month);
        year = (EditText)findViewById(R.id.ret_Year);
        home = (Button)findViewById(R.id.Btn_Home);
        show = (Button)findViewById(R.id.Btn_Show);
        reset = (Button)findViewById(R.id.Btn_Reset);

        save = (Button)findViewById(R.id.Btn_Save_Local);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coal_w = txt_coal.getText().toString();
                String lime_w = txt_lime.getText().toString();
                String haem_w = txt_haem.getText().toString();
                String mang_w = txt_mang.getText().toString();
                String coal_s = coal_w.substring(coal_w.lastIndexOf(" "));
                String lime_s = lime_w.substring(lime_w.lastIndexOf(" "));
                String haem_s = haem_w.substring(haem_w.lastIndexOf(" "));
                String mang_s = mang_w.substring(mang_w.lastIndexOf(" "));

                db.insertData(day.getText().toString(),month.getText().toString(),year.getText().toString(),coal_s,lime_s,haem_s,mang_s);
                if(db.chck(day.getText().toString(),month.getText().toString(),year.getText().toString())){
                    Toast.makeText(OnlineRetreiveActivity.this,"Data Saved Locally ",Toast.LENGTH_LONG).show();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_coal.setVisibility(View.GONE);
                txt_lime.setVisibility(View.GONE);
                txt_haem.setVisibility(View.GONE);
                txt_mang.setVisibility(View.GONE);
                save.setVisibility(View.GONE);
                initialise();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnlineRetreiveActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String da = day.getText().toString();
                final String mon = month.getText().toString();
                final String yea = year.getText().toString();

                boolean ans = true;
                Integer day_a = -1,month_a = -1,year_a = -1;

                if(!da.isEmpty() && !mon.isEmpty() && !yea.isEmpty()){
                    day_a = Integer.parseInt(da);
                    month_a = Integer.parseInt(mon);
                    year_a = Integer.parseInt(yea);
                }else{
                    ans = false;
                }
                if(ans==false){
                    Toast.makeText(OnlineRetreiveActivity.this,"Data Insufficient ",Toast.LENGTH_LONG).show();
                }else {
                    if (!chck_year(year_a) || !chck_month(month_a) || !chck(day_a, month_a, year_a)) {
                        Toast.makeText(OnlineRetreiveActivity.this, "Wrong Date Format", Toast.LENGTH_LONG).show();
                    }else{
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                boolean found = false;
                                for(int i = 0;i<response.length();i++){
                                    try{
                                        JSONObject object = response.getJSONObject(i);
                                        String day_o = object.getString("Day");
                                        String month_o = object.getString("Month");
                                        String year_o = object.getString("Year");
                                        if(day_o.equals(da) && month_o.equals(mon) && year_o.equals(yea)){
                                            txt_coal.setText("Coal Dumped :- "+object.getString("Coal"));
                                            txt_lime.setText("LimeStone Dumped :- "+object.getString("Limestone"));
                                            txt_haem.setText("Haematite Dumped :- "+object.getString("Haematite"));
                                            txt_mang.setText("Manganese Dumped :- "+object.getString("Manganese"));
                                            visualize();
                                            found = true;
                                            break;
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                                if(found==false){
                                    Toast.makeText(OnlineRetreiveActivity.this,"Data not found",Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(OnlineRetreiveActivity.this,"hhh"+error.toString(),Toast.LENGTH_LONG).show();
                            }
                        });
                        req.add(jsonArrayRequest);
                    }
                }
            }
        });

    }
    public void visualize(){
        txt_coal.setVisibility(View.VISIBLE);
        txt_lime.setVisibility(View.VISIBLE);
        txt_haem.setVisibility(View.VISIBLE);
        txt_mang.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
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
        day.setText(null);
        month.setText(null);
        year.setText(null);
    }
    public static boolean chck_leap(int year){
        if(year%4==0 && year!= 1900){
            return true;
        }
        return false;
    }
}
