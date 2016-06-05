package io.github.aguilarj.notes;

import java.util.ArrayList;

public class Notebook {
    private String mTitle;
    private String mDescription;
    private ArrayList<Note> mNotes;
    private int mDisplayMode;

    public Notebook(String title, String description) {
        mTitle = title;
        mDescription = description;
        mNotes = new ArrayList<>();
        mDisplayMode = 0; // GRID
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public ArrayList<Note> getNotes() {
        return mNotes;
    }

    public Note getNote(int noteId) {
        return mNotes.get(noteId);
    }

    public void setNote(int noteId, Note note) {
        mNotes.set(noteId, note);
    }

    public void addNote(Note note) {
        mNotes.add(note);
    }

    public void deleteNote(int noteId) {
        mNotes.remove(noteId);
    }

    public int getDisplayMode() {
        return mDisplayMode;
    }
    public void setDisplayMode(int displayMode) {
        mDisplayMode = displayMode;
    }
}