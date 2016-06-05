package io.github.aguilarj.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

public class ShowNoteActivity extends AppCompatActivity {
    private int notebookId;
    private int POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        Intent intent = getIntent();

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.show_note_toolbar);
        setSupportActionBar(mainToolbar);

        ActionBar actionBar = getSupportActionBar();
        Preconditions.checkArgument(actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        Data data = Data.getInstance(ShowNoteActivity.this);

        notebookId = intent.getIntExtra("notebookId", -1);
        POSITION = intent.getIntExtra("POSITION", -1);

        actionBar.setTitle(data.getNotebook(notebookId).getTitle());

        Preconditions.checkArgument(notebookId != -1 && POSITION != -1);

        Note note = data.getNote(notebookId, POSITION);

        TextView note_title = (TextView) findViewById(R.id.show_note_title);
        TextView note_content = (TextView) findViewById(R.id.show_note_content);

        Preconditions.checkArgument(note_title != null && note_content != null);

        note_title.setText(note.getTitle());
        note_content.setText(note.getContent());
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent intent = NavUtils.getParentActivityIntent(this);
        intent.putExtra("notebookId", notebookId);
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(intent);
                return true;
            case R.id.action_edit:
                Intent edit_intent = new Intent(this, NoteActivity.class);
                edit_intent.putExtra("notebookId", notebookId);
                edit_intent.putExtra("REQUEST", 1); // EDIT
                edit_intent.putExtra("POSITION", POSITION);
                edit_intent.putExtra("FROM_SHOW_NOTE", true);
                startActivity(edit_intent);
                return true;
            case R.id.action_delete:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Data data = Data.getInstance(getApplicationContext());
                                data.deleteNote(notebookId, POSITION);
                                showMessage(getApplicationContext().getString(R.string.note_delete_message));
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete note").setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_note, menu);
        return true;
    }
}
