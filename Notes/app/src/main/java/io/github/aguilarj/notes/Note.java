package io.github.aguilarj.notes;

/**
 * Created by aguilarjp on 01/06/16.
 */
public class Note {
    private String mTitle;
    private String mContent;

    public Note(String title, String content) {
        mTitle = title;
        mContent = content;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }
}
