package io.github.aguilarj.notes;

/**
 * Created by aguilarjp on 01/06/16.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> mNotes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView contentTextView;
        private Context currentContex;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.note_title);
            contentTextView = (TextView) itemView.findViewById(R.id.note_content);
            currentContex = context;
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
        }
    }


    public NotesAdapter(List<Note> Notes) {
        mNotes = Notes;
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