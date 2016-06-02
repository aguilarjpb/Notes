package io.github.aguilarj.notes;

/**
 * Created by aguilarjp on 01/06/16.
 */
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> mNotes;
    private int mNotebookId;
    private Context currentContext;

    public NotesAdapter(List<Note> Notes, int notebookId) {
        mNotes = Notes;
        mNotebookId = notebookId;
    }

    public void delete(int position) {
        Data data = Data.getInstance(currentContext);
        data.deleteNote(mNotebookId, position);
        notifyItemRemoved(position);
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
//                    Intent intent = new Intent(currentContex, ShowFullNoteActivity.class);
//                    intent.putExtra("NoteId", position);
//                    currentContex.startActivity(intent);
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
                                    // edit(getAdapterPosition());
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