package io.github.aguilarj.notes;

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

public class NotebooksAdapter extends RecyclerView.Adapter<NotebooksAdapter.ViewHolder> {

    private List<Notebook> mNotebooks;
    private Context currentContext;

    public NotebooksAdapter(List<Notebook> notebooks) {
        mNotebooks = notebooks;
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
                        data.deleteNotebook(position);
                        notifyItemRemoved(position);
                        showMessage(currentContext.getString(R.string.notebook_delete_message));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(currentContext);
        builder.setTitle("Delete notebook").setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void edit(int position) {
        Intent intent = new Intent(currentContext, NotebookActivity.class);
        intent.putExtra("REQUEST", 1); // EDIT
        intent.putExtra("POSITION", position);
        currentContext.startActivity(intent);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView optionsButton;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.notebook_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.notebook_description);
            currentContext = context;
            optionsButton = (ImageView) itemView.findViewById(R.id.options_button);
        }

        public void bind(Notebook notebook, final int position) {
            titleTextView.setText(notebook.getTitle());
            descriptionTextView.setText(notebook.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(currentContext, ShowNotesActivity.class);
                    intent.putExtra("notebookId", getAdapterPosition());
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
