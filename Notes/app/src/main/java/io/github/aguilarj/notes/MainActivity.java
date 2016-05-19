package io.github.aguilarj.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Notebook> notebooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvNotebooks = (RecyclerView) findViewById(R.id.rvNotebooks);
        notebooks = parseUserData(); // TODO: 19/05/16  Parser
        if (notebooks != null) {
            NotebooksAdapter adapter = new NotebooksAdapter(notebooks);
            rvNotebooks.setAdapter(adapter);
            rvNotebooks.setLayoutManager(new LinearLayoutManager(this));
        } else {
            // TODO: 19/05/16  Show a message asking the user to create a new notebook
        }

    }
}
