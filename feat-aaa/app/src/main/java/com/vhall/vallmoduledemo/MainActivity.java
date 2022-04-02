package com.vhall.vallmoduledemo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vhall.aaademo.R;
import com.vhall.httpclient.VHMultiPartType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.subtest)).setText(VHMultiPartType.MIXED.getType());
    }
}
