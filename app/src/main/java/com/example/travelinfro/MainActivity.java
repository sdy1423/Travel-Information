package com.example.travelinfro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mBtnTravel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnTravel = findViewById(R.id.main_btn_travel);
        mBtnTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TravelActivity.class);
                startActivity(intent);
            }
        });


    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_btn_board1:
                Intent intent = new Intent(getApplicationContext(),NoticeBoardActivity.class);
                startActivity(intent);
                break;
            case R.id.main_btn_board2:

                break;
            case R.id.main_btn_board3:

                break;
            case R.id.main_btn_board4:

                break;
            case R.id.main_btn_board5:

                break;
            default:
                break;
        }
    }
}
