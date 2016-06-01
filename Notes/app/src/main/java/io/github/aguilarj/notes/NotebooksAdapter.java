package io.github.aguilarj.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class NotebooksAdapter extends RecyclerView.Adapter<NotebooksAdapter.ViewHolder> {

    private List<Notebook> mNotebooks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public Button optionsButton;
        private Context currentContext;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.notebook_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.notebook_description);
            currentContext = context;
            optionsButton = (Button) itemView.findViewById(R.id.options_button);
        }

        public void bind(Notebook notebook, final Integer position) {
            titleTextView.setText(notebook.getTitle());
            descriptionTextView.setText(notebook.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(currentContext, ShowNotesActivity.class);
                    intent.putExtra("notebookId", position);
                    currentContext.startActivity(intent);
                }
            });

            optionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(currentContext, optionsButton);
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            // Data data = Data.getInstance(currentContext);
                            switch (item.getItemId()) {
                                // TODO: 01/06/16 Edit and delete notebook feature
                                case R.id.edit:
                                    // data.editNotebook(position);
                                    break;
                                case R.id.delete:
                                    // data.deleteNotebook(position);
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


    public NotebooksAdapter(List<Notebook> notebooks) {
        mNotebooks = notebooks;
    }

    @Override
    public NotebooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View notebookView = inflater.inflate(R.layout.item_notebook, parent, false);

        return new ViewHolder(notebookView, context);
    }

    @Override
    public void onBindViewHolder(NotebooksAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(mNotebooks.get(position), position);

    }

    @Override
    public int getItemCount() {
        return mNotebooks.size();
    }
}
