package io.github.aguilarj.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        RecyclerView rvNotebooks = (RecyclerView) findViewById(R.id.notebooks_list);
        TextView noNotebooks = (TextView) findViewById(R.id.no_notebooks);

        Data data = Data.getInstance(MainActivity.this);
        ArrayList<Notebook> notebooks = data.getNotebooks();

        Preconditions.checkArgument(rvNotebooks != null && mainLayout != null && noNotebooks != null);

        if (!notebooks.isEmpty()) {
            noNotebooks.setVisibility(View.INVISIBLE);
            NotebooksAdapter adapter = new NotebooksAdapter(notebooks);
            rvNotebooks.setAdapter(adapter);
            rvNotebooks.setLayoutManager(new LinearLayoutManager(this));

        } else {
            rvNotebooks.setVisibility(View.INVISIBLE);
            noNotebooks.setText(R.string.no_notebooks_message);
        }

        FloatingActionButton add_notebook_FAB = (FloatingActionButton) findViewById(R.id.add_notebook_FAB);
        Preconditions.checkArgument(add_notebook_FAB != null);
        add_notebook_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotebookActivity.class);
                intent.putExtra("REQUEST", 0); // ADD
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
