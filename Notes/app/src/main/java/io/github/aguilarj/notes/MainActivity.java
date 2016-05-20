package io.github.aguilarj.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        Data data = Data.getInstance(MainActivity.this);
        ArrayList<Notebook> notebooks = data.getNotebooks();

        if (!notebooks.isEmpty()) {
            RecyclerView rvNotebooks = new RecyclerView(this);

            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rvNotebooks.setLayoutParams(params);

            NotebooksAdapter adapter = new NotebooksAdapter(notebooks);
            rvNotebooks.setAdapter(adapter);
            rvNotebooks.setLayoutManager(new LinearLayoutManager(this));

            main_layout.addView(rvNotebooks);
        } else {
            TextView CreateNew = new TextView(this);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            CreateNew.setLayoutParams(params);
            CreateNew.setText("No notebooks by the moment. Create a new one!");

            main_layout.addView(CreateNew);
        }

    }
}
