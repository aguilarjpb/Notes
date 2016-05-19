package io.github.aguilarj.notes;

public class Notebook {
    private String mTitle;
    private String mDescription;

    public Notebook(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}