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
        Intent intent = new Intent(getApplicationContext(),NoticeBoardActivity.class);
        switch (view.getId()){
            case R.id.main_btn_board1:
                intent.putExtra("boardNum",1);
                break;
            case R.id.main_btn_board2:
                intent.putExtra("boardNum",2);
                break;
            case R.id.main_btn_board3:
                intent.putExtra("boardNum",3);
                break;
            case R.id.main_btn_board4:
                intent.putExtra("boardNum",4);
                break;
            case R.id.main_btn_board5:
                intent.putExtra("boardNum",5);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
