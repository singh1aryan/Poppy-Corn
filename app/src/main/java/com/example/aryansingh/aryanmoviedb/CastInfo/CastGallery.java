package com.example.aryansingh.aryanmoviedb.CastInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aryansingh.aryanmoviedb.R;

public class CastGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_gallery);

        Intent i = getIntent();
        i.getLongExtra("id",0);

        // take out the pics and put them in the gallery
    }
}
