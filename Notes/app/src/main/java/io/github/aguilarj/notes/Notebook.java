package io.github.aguilarj.notes;

import java.util.ArrayList;

public class Notebook {
    private String mTitle;
    private String mDescription;
    private ArrayList<Note> mNotes;

    public Notebook(String title, String description) {
        mTitle = title;
        mDescription = description;
        mNotes = new ArrayList<>();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public ArrayList<Note> getNotes() {
        return mNotes;
    }

    public void addNote(Note note) {
        mNotes.add(note);
    }
}