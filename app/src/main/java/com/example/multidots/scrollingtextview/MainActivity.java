package com.example.multidots.scrollingtextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView textview;
    private MainActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = MainActivity.this;
        textview = (TextView) findViewById(R.id.text_view_with_image);

        for (int i = 0; i < 50; i++) {
            ImageSpan spanImage = new ImageSpan(context, R.drawable.ic_launcher);
//            SpannableStringBuilder builder = new SpannableStringBuilder();
//            builder.append("My string. I ", new ImageSpan(context, R.drawable.ic_launcher), 0);
            SpannableString spannableString = new SpannableString("\t\tHello " + i + "\t");
            spannableString.setSpan(spanImage, 0, 2, 0);
            textview.append(spannableString);
        }
    }
}
