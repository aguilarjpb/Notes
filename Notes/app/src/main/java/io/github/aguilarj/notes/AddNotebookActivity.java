package io.github.aguilarj.notes;

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

public class AddNotebookActivity extends AppCompatActivity {
    private static final Boolean SUCCESS = true;
    private static final Boolean FAIL = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notebook);

        Toolbar addNotebookToolbar = (Toolbar) findViewById(R.id.add_notebook_toolbar);
        setSupportActionBar(addNotebookToolbar);

        ActionBar actionBar = getSupportActionBar();
        Preconditions.checkArgument(actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                Boolean result = addNotebook();
                if (result == SUCCESS) {
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

    private Boolean addNotebook() {
        Data data = Data.getInstance(this);
        EditText add_notebook_title = (EditText) findViewById(R.id.add_notebook_title);
        EditText add_notebook_description = (EditText) findViewById(R.id.add_notebook_description);

        Preconditions.checkArgument(add_notebook_title != null && add_notebook_description != null);

        String title = add_notebook_title.getText().toString();
        String description = add_notebook_description.getText().toString();

        if (StringUtils.isBlank(title) || StringUtils.isBlank(description)) {
            showMessage(this.getString(R.string.no_title_description));
            return FAIL;
        }

        data.addNotebook(new Notebook(title, description));
        return SUCCESS;
    }
}
