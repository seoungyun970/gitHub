package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

public class diary extends Activity {
    TextView textView2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView2=(TextView)findViewById(R.id.textView2);
        setContentView(R.layout.diary);

    }
}
