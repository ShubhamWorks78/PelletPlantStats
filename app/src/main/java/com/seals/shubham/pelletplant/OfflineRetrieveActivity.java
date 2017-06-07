package com.seals.shubham.pelletplant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class OfflineRetrieveActivity extends AppCompatActivity {

    OfflineStorage db = new OfflineStorage(this);
    EditText day,month,year;
    TextView txt_coal,txt_lime,txt_haem,txt_mang;
    Button reset,home,show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_retrieve);

        day = (EditText)findViewById(R.id.ret_Day_off);
        month = (EditText)findViewById(R.id.ret_Month_off);
        year = (EditText)findViewById(R.id.ret_Year_off);

        txt_coal = (TextView)findViewById(R.id.text_coal_off);
        txt_lime = (TextView)findViewById(R.id.text_lime_off);
        txt_haem = (TextView)findViewById(R.id.text_haem_off);
        txt_mang = (TextView)findViewById(R.id.text_mang_off);

        reset = (Button)findViewById(R.id.Btn_Reset_Off);
        home = (Button)findViewById(R.id.Btn_Home_Off);
        show = (Button)findViewById(R.id.Btn_Show_Off);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_coal.setVisibility(View.GONE);
                txt_lime.setVisibility(View.GONE);
                txt_haem.setVisibility(View.GONE);
                txt_mang.setVisibility(View.GONE);
                initialise();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfflineRetrieveActivity.this,HomeActivity.class);
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
                    Toast.makeText(OfflineRetrieveActivity.this,"Data Insufficient ",Toast.LENGTH_LONG).show();
                }
                else{
                    if (!chck_year(year_a) || !chck_month(month_a) || !chck(day_a, month_a, year_a)) {
                        Toast.makeText(OfflineRetrieveActivity.this, "Wrong Date Format", Toast.LENGTH_LONG).show();
                    }else{
                        if(db.chck(da,mon,yea)==false){
                            Toast.makeText(OfflineRetrieveActivity.this,"Data not Found in database",Toast.LENGTH_LONG).show();
                        }else{
                            HashMap<String,String> hm = db.getData(da,mon,yea);
                            txt_coal.setText("Coal Dumped :- "+hm.get("Coal"));
                            txt_lime.setText("LimeStone Dumped :- "+hm.get("Limestone"));
                            txt_haem.setText("Haematite Dumped :- "+hm.get("Haematite"));
                            txt_mang.setText("Manganese Dumped :- "+hm.get("Manganese"));
                            visualize();
                        }
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
