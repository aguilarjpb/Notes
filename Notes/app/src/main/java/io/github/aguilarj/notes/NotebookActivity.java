package io.github.aguilarj.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import org.apache.commons.lang3.StringUtils;

public class NotebookActivity extends AppCompatActivity {
    private static final int ADD = 0;
    private static final int EDIT = 1;
    private static final Boolean SUCCESS = true;
    private static final Boolean FAIL = false;
    private int REQUEST;
    private int POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);

        Toolbar addNotebookToolbar = (Toolbar) findViewById(R.id.notebook_toolbar);
        setSupportActionBar(addNotebookToolbar);

        ActionBar actionBar = getSupportActionBar();
        Preconditions.checkArgument(actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();
        REQUEST = intent.getIntExtra("REQUEST", -1);
        POSITION = intent.getIntExtra("POSITION", -1);

        EditText notebook_title = (EditText) findViewById(R.id.request_notebook_title);
        EditText notebook_description = (EditText) findViewById(R.id.request_notebook_description);

        Preconditions.checkArgument(notebook_title != null && notebook_description != null);
        if (REQUEST == ADD) {
            actionBar.setTitle("Add a new notebook");
            notebook_title.setHint("Title");
            notebook_description.setHint("Description");
        }
        if (REQUEST == EDIT) {
            actionBar.setTitle("Edit notebook");

            Data data = Data.getInstance(this);
            Notebook notebook = data.getNotebook(POSITION);
            notebook_title.setText(notebook.getTitle());
            notebook_description.setText(notebook.getDescription());
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                if (processRequest() == SUCCESS) {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
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
        EditText notebook_title = (EditText) findViewById(R.id.request_notebook_title);
        EditText notebook_description = (EditText) findViewById(R.id.request_notebook_description);

        Preconditions.checkArgument(notebook_title != null && notebook_description != null);

        String title = notebook_title.getText().toString();
        String description = notebook_description.getText().toString();

        if (StringUtils.isBlank(title) || StringUtils.isBlank(description)) {
            showMessage(this.getString(R.string.no_title_description));
            return FAIL;
        }

        switch (REQUEST) {
            case ADD:
                data.addNotebook(new Notebook(title, description));
                break;
            case EDIT:
                data.editNotebook(POSITION, title, description);
                break;
        }

        return SUCCESS;
    }
}
