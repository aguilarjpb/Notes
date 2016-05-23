package io.github.aguilarj.notes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        RecyclerView rvNotebooks = (RecyclerView) findViewById(R.id.notebooks_list);

        Data data = Data.getInstance(MainActivity.this);
        ArrayList<Notebook> notebooks = data.getNotebooks();

        Preconditions.checkArgument(rvNotebooks != null && main_layout != null);

        if (!notebooks.isEmpty()) {
            NotebooksAdapter adapter = new NotebooksAdapter(notebooks);
            rvNotebooks.setAdapter(adapter);
            rvNotebooks.setLayoutManager(new LinearLayoutManager(this));

        } else {
            rvNotebooks.setVisibility(View.INVISIBLE);

            TextView createNew = new TextView(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            createNew.setLayoutParams(params);
            createNew.setText(R.string.no_notebooks_message);

            main_layout.addView(createNew);
        }

        FloatingActionButton add_notebook_FAB = (FloatingActionButton) findViewById(R.id.add_notebook_FAB);
        Preconditions.checkArgument(add_notebook_FAB != null);
        add_notebook_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 23/05/16 Implement an activity to add a new notebook 
            }
        });
    }
}
