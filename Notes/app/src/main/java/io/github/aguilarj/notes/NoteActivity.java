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

public class NoteActivity extends AppCompatActivity {
    private static final int ADD = 0;
    private static final int EDIT = 1;
    private static final Boolean SUCCESS = true;
    private static final Boolean FAIL = false;
    private int REQUEST;
    private int POSITION;
    private int notebookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Toolbar addNotebookToolbar = (Toolbar) findViewById(R.id.note_toolbar);
        setSupportActionBar(addNotebookToolbar);

        ActionBar actionBar = getSupportActionBar();
        Preconditions.checkArgument(actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();
        notebookId = intent.getIntExtra("notebookId", -1);

        Preconditions.checkArgument(notebookId != -1);

        REQUEST = intent.getIntExtra("REQUEST", -1);
        POSITION = intent.getIntExtra("POSITION", -1);

        EditText notebook_title = (EditText) findViewById(R.id.request_note_title);
        EditText notebook_content = (EditText) findViewById(R.id.request_note_content);

        Preconditions.checkArgument(notebook_title != null && notebook_content != null);
        if (REQUEST == ADD) {
            actionBar.setTitle("Add a new note");
            notebook_title.setHint("Title");
            notebook_content.setHint("Content");
        }
        if (REQUEST == EDIT) {
            actionBar.setTitle("Edit note");

            Data data = Data.getInstance(this);

            Notebook notebook = data.getNotebook(notebookId);
            Note note = notebook.getNote(POSITION);

            notebook_title.setText(note.getTitle());
            notebook_content.setText(note.getContent());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.putExtra("notebookId", notebookId);
        switch (item.getItemId()) {
            case R.id.action_create:
                if (processRequest() == SUCCESS) {
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

    private Boolean processRequest() {
        Data data = Data.getInstance(this);
        EditText note_title = (EditText) findViewById(R.id.request_note_title);
        EditText note_content = (EditText) findViewById(R.id.request_note_content);

        Preconditions.checkArgument(note_title != null && note_content != null);

        String title = note_title.getText().toString();
        String content = note_content.getText().toString();

        if (StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            showMessage(this.getString(R.string.no_title_content));
            return FAIL;
        }

        switch (REQUEST) {
            case ADD:
                data.addNote(new Note(title, content), notebookId);
                break;
            case EDIT:
                data.editNote(notebookId, POSITION, title, content);
                break;
        }

        return SUCCESS;
    }
}
