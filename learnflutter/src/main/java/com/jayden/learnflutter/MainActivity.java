package com.jayden.learnflutter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import io.flutter.facade.Flutter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* View addFlutterViewBtn = findViewById(R.id.btn_add_flutter_view);
        addFlutterViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View flutterView = Flutter.createView(
                        MainActivity.this,
                        getLifecycle(),
                        "route1"
                );
                FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(600, 800);
                layout.leftMargin = 100;
                layout.topMargin = 200;
                addContentView(flutterView, layout);
            }
        });*/
    }
}
