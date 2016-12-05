package com.example.cakerouter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.apt.CakeRouterUrl;

@CakeRouterUrl("main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent in = getIntent();
        String strExtra = in.getStringExtra("str");
        int intExtra = in.getIntExtra("int", -1);
        char charExtra = in.getCharExtra("char", 'a');
        NameClass nameClass = (NameClass) in.getSerializableExtra("name");
        TextView textView = (TextView) findViewById(R.id.hello);
        textView.setText(strExtra + " " + intExtra + " " + charExtra + " " + nameClass.getName());
    }
}
