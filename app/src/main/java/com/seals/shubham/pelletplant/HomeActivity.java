package com.seals.shubham.pelletplant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btnFeed,btnRet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnFeed = (Button)findViewById(R.id.btnFeed);
        btnRet = (Button)findViewById(R.id.btnRetrieve);

        btnFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });


        btnRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RetrieveActivity.class);
                startActivity(intent);
            }
        });
    }
}
