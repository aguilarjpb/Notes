package io.github.aguilarj.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NotebooksAdapter extends RecyclerView.Adapter<NotebooksAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
        }
    }
    private List<Notebook> mNotebooks;

    public NotebooksAdapter(List<Notebook> notebooks) {
        mNotebooks = notebooks;
    }

    @Override
    public NotebooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View notebookView = inflater.inflate(R.layout.item_notebook, parent, false);

        return new ViewHolder(notebookView);
    }

    @Override
    public void onBindViewHolder(NotebooksAdapter.ViewHolder viewHolder, int position) {
        Notebook notebook = mNotebooks.get(position);

        TextView titleTextView = viewHolder.titleTextView;
        titleTextView.setText(notebook.getTitle());

        TextView descriptionTextView = viewHolder.descriptionTextView;
        descriptionTextView.setText(notebook.getDescription());
    }

    @Override
    public int getItemCount() {
        return mNotebooks.size();
    }
}