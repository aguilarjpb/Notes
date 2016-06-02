package io.github.aguilarj.notes;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.common.base.Preconditions;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(mainToolbar);

        ActionBar actionBar = getSupportActionBar();
        Preconditions.checkArgument(actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
    }
}
