package io.github.aguilarj.notes;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by aguilarjp on 20/05/16.
 */
public class Data {
    private static Data mInstance = null;
    private DataHandler mData;

    private Data(Context context) {
        mData = new DataHandler(context);
    }

    public static synchronized Data getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Data(context);
        }
        return mInstance;
    }

    public Notebook getNotebook(int notebookId) {
        return mData.getNotebook(notebookId);
    }

    public ArrayList<Notebook> getNotebooks() {
        return mData.getNotebooks();
    }

    public void addNotebook(Notebook notebook) {
        mData.addNotebook(notebook);
    }

    public void deleteNotebook(int notebookId) {
        mData.deleteNotebook(notebookId);
    }

    public ArrayList<Note> getNotes(int notebookId) {
        return mData.getNotes(notebookId);
    }

    public void addNote(Note note, int notebookId) {
        mData.addNote(note, notebookId);
    }


}
