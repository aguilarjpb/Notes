package io.github.aguilarj.notes;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import org.apache.commons.lang3.StringUtils;

public class AddNoteActivity extends AppCompatActivity {
    private static final Boolean SUCCESS = true;
    private static final Boolean FAIL = false;
    private int notebookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Toolbar addNotebookToolbar = (Toolbar) findViewById(R.id.add_note_toolbar);
        setSupportActionBar(addNotebookToolbar);

        ActionBar actionBar = getSupportActionBar();
        Preconditions.checkArgument(actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        notebookId = intent.getIntExtra("notebookId", -1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.putExtra("notebookId", notebookId);
        switch (item.getItemId()) {
            case R.id.action_create:
                Boolean result = addNote();
                if (result == SUCCESS) {
                    startActivity(intent);
                }
                return true;
            case android.R.id.home:
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean addNote() {
        Data data = Data.getInstance(this);
        EditText add_note_title = (EditText) findViewById(R.id.add_note_title);
        EditText add_note_content = (EditText) findViewById(R.id.add_note_content);

        Preconditions.checkArgument(add_note_title != null && add_note_content != null);

        String title = add_note_title.getText().toString();
        String content = add_note_content.getText().toString();

        if (StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            showMessage(this.getString(R.string.no_title_content));
            return FAIL;
        }

        data.addNote(new Note(title, content), notebookId);
        return SUCCESS;
    }
}
