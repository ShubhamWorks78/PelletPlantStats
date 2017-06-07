package com.seals.shubham.pelletplant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RetrieveActivity extends AppCompatActivity {

    Button btnOn,btnOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        btnOn = (Button)findViewById(R.id.btnOnline);
        btnOff = (Button)findViewById(R.id.btnOffline);

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetrieveActivity.this,OnlineRetreiveActivity.class);
                startActivity(intent);
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetrieveActivity.this,OfflineRetrieveActivity.class);
                startActivity(intent);
            }
        });
    }
}
