package io.github.aguilarj.notes;

import android.content.Context;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by aguilarjp on 19/05/16.
 */

public class DataHandler {
    private static final String tag = "DataHandler";

    private ArrayList<Notebook> mNotebooks;
    private String dataPath;
    private Boolean isLoaded;
    private class FileNames {
        public static final String NOTEBOOKS = "/Notebooks.txt";
    }

    public DataHandler(Context context) {
        dataPath = context.getFilesDir().toString();
        mNotebooks = new ArrayList<>();
        isLoaded = false;
    }

    private void loadNotebooks() {
        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        String notebooksString = new String();


        if (!notebooksFile.exists()) {
            notebooksFile = new File(dataPath, FileNames.NOTEBOOKS);
            try {
                Files.write("Empty", notebooksFile, Charsets.UTF_8);
            } catch (IOException exception) {
                exception.printStackTrace();
                Log.e(tag, "Error while writing to notebooks' file");
            }

        }

        try {
            notebooksString = Files.toString(notebooksFile, Charsets.UTF_8);
        } catch (IOException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while converting notebook file to string");

        }

        if (!notebooksString.equals("Empty")) {
            try {
                JSONArray data = new JSONArray(notebooksString);

                for (int i = 0; i != data.length(); ++i) {
                    JSONObject rawNotebook = data.getJSONObject(i);

                    Notebook notebook = new Notebook(
                            rawNotebook.getString("title"), rawNotebook.getString("description"));

                    JSONArray notes = rawNotebook.getJSONArray("notes");

                    for (int j = 0; j != notes.length(); ++j) {
                        JSONObject rawNote = notes.getJSONObject(j);
                        Note note = new Note(rawNote.getString("title"), rawNote.getString("content"));
                        notebook.addNote(note);
                    }
                    mNotebooks.add(notebook);
                }

            } catch (JSONException exception) {
                exception.printStackTrace();
                Log.e(tag, "Error while parsing notebooks' data");
            }
        }
        Log.i(tag, "Notebooks' data loaded successfully");
    }

    public ArrayList<Notebook> getNotebooks() {
        if (!isLoaded) {
            loadNotebooks();
            isLoaded = true;
        }
        return mNotebooks;
    }

    public void addNotebook(Notebook notebook) {
        // Adding notebook to our local array
        mNotebooks.add(notebook);

        // Adding notebook to file
        String notebooksString = new String();
        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        try {
            notebooksString = Files.toString(notebooksFile, Charsets.UTF_8);
        } catch (IOException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while converting notebook file to string");
        }
        try {
            JSONObject newObject = new JSONObject();
            JSONArray data = notebooksString.equals("Empty") ? new JSONArray() : new JSONArray(notebooksString);
            newObject.put("title", notebook.getTitle());
            newObject.put("description", notebook.getDescription());
            newObject.put("notes", new JSONArray());

            data.put(newObject);

            try {
                Files.write(data.toString(), notebooksFile, Charsets.UTF_8);
            } catch (IOException exception) {
                exception.printStackTrace();
                Log.e(tag, "Error while writing to notebooks' file");
            }

        } catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while parsing notebooks' data");

        }
        Log.i(tag, "A new notebook was added successfully");
    }

    public ArrayList<Note> getNotes(Integer notebookId) {
        return mNotebooks.get(notebookId).getNotes();
    }

    public void addNote(Note note, Integer notebookId) {
        // Adding note to our local array
        mNotebooks.get(notebookId).addNote(note);

        // Adding note to file
        String notebooksString = new String();
        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        try {
            notebooksString = Files.toString(notebooksFile, Charsets.UTF_8);
        } catch (IOException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while converting notebook file to string");
        }
        try {
            JSONArray data = new JSONArray(notebooksString);
            JSONObject notebook = data.getJSONObject(notebookId);

            JSONObject noteObject = new JSONObject();

            noteObject.put("title", note.getTitle());
            noteObject.put("content", note.getContent());

            notebook.accumulate("notes", noteObject);

            try {
                Files.write(data.toString(), notebooksFile, Charsets.UTF_8);
            } catch (IOException exception) {
                exception.printStackTrace();
                Log.e(tag, "Error while writing to notebooks' file");
            }

        } catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while adding a new note to a notebook");

        }
        Log.i(tag, "A new note was added successfully");
    }
}
