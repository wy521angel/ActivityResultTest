package com.example.activityresulttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class OtherActivity extends AppCompatActivity {


    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Toast.makeText(OtherActivity.this, getIntent().getStringExtra("DATA2"),
                Toast.LENGTH_SHORT).show();


        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = ((EditText) findViewById(R.id.edit)).getText().toString();
                if (TextUtils.isEmpty(message)) {
                    message = "您没有输入信息！";
                }
                setResult(Activity.RESULT_OK, new Intent().putExtra("DATA", message));
                finish();
            }
        });
    }


}
