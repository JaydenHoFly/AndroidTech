package com.jaydenho.androidtech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaydenho.androidtech.databinding.DataBindingAty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mDataBindingBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mDataBindingBtn = (TextView) findViewById(R.id.btn_data_binding);
        mDataBindingBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_data_binding:
                startActivity(new Intent(this, DataBindingAty.class));
                break;
        }
    }
}
