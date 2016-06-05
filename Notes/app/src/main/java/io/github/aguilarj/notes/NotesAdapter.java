package io.github.aguilarj.notes;

/**
 * Created by aguilarjp on 01/06/16.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> mNotes;
    private int mNotebookId;
    private Context currentContext;

    public NotesAdapter(List<Note> Notes, int notebookId) {
        mNotes = Notes;
        mNotebookId = notebookId;
    }

    private void showMessage(String message) {
        Toast.makeText(currentContext, message, Toast.LENGTH_SHORT).show();
    }

    public void delete(final int position) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Data data = Data.getInstance(currentContext);
                        data.deleteNote(mNotebookId, position);
                        notifyItemRemoved(position);
                        showMessage(currentContext.getString(R.string.note_delete_message));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(currentContext);
        builder.setTitle("Delete note").setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void edit(int position) {
        Intent intent = new Intent(currentContext, NoteActivity.class);
        intent.putExtra("notebookId", mNotebookId);
        intent.putExtra("REQUEST", 1); // EDIT
        intent.putExtra("POSITION", position);
        currentContext.startActivity(intent);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView contentTextView;
        public ImageView optionsButton;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.note_title);
            contentTextView = (TextView) itemView.findViewById(R.id.note_content);
            currentContext = context;
            optionsButton = (ImageView) itemView.findViewById(R.id.note_options_button);
        }

        public void bind(Note Note, final int position) {
            titleTextView.setText(Note.getTitle());
            contentTextView.setText(Note.getContent());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(currentContext, ShowNoteActivity.class);
                    intent.putExtra("notebookId", mNotebookId);
                    intent.putExtra("POSITION", position);
                    currentContext.startActivity(intent);
                }
            });

            optionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(currentContext, optionsButton);
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                    edit(getAdapterPosition());
                                    break;
                                case R.id.delete:
                                    delete(getAdapterPosition());
                                    break;
                                default:
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });
        }
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View NoteView = inflater.inflate(R.layout.item_note, parent, false);

        return new ViewHolder(NoteView, context);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(mNotes.get(position), position);

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}