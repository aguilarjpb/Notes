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
        public static final String NOTEBOOKS = "/data.txt";
    }

    public DataHandler(Context context) {
        dataPath = context.getFilesDir().toString();
        mNotebooks = new ArrayList<>();
        isLoaded = false;
    }


    /*
        Converts fileName file in dataPath to string.
        If file does not exists, it is created.
     */

    private String getFileAsString(String fileName) {
        File file = new File(dataPath + fileName);

        if (!file.exists()) {
            file = new File(dataPath, fileName);
            try {
                Files.write("Empty", file, Charsets.UTF_8);
            } catch (IOException exception) {
                exception.printStackTrace();
                Log.e(tag, "Error while writing to file");
            }

        }

        String fileString = new String();
        try {
            fileString = Files.toString(file, Charsets.UTF_8);
        } catch (IOException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while converting file to string");
        }
        return fileString;
    }

    /*
        Writes a JSONArray as a String to a file
     */
    private void writeJSONtoFile(JSONArray data, File notebooksFile) {
        String toWrite = data.length() == 0 ? "Empty" : data.toString();
        try {
            Files.write(toWrite, notebooksFile, Charsets.UTF_8);
        } catch (IOException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while writing to notebooks' file");
        }
    }

    /*
        Loads notebooks from Filenames.NOTEBOOKS in dataPath.
        It just parses a JSONArray.

     */
    private void loadNotebooks() {
        String notebooksString = getFileAsString(FileNames.NOTEBOOKS);

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

    /*
        Returns notebooks array. It loads them from file if it is necessary.
     */
    public ArrayList<Notebook> getNotebooks() {
        if (!isLoaded) {
            loadNotebooks();
            isLoaded = true;
        }
        return mNotebooks;
    }

    public Notebook getNotebook(int notebookId) {
        return mNotebooks.get(notebookId);
    }

    /*
        Adds a new notebook in both the local copy and file.
     */
    public void addNotebook(Notebook notebook) {
        // Adding notebook to our local array
        mNotebooks.add(notebook);

        // Adding notebook to file
        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        String notebooksString = getFileAsString(FileNames.NOTEBOOKS);

        try {
            JSONObject newObject = new JSONObject();
            JSONArray data = notebooksString.equals("Empty") ? new JSONArray() : new JSONArray(notebooksString);
            newObject.put("title", notebook.getTitle());
            newObject.put("description", notebook.getDescription());
            newObject.put("notes", new JSONArray());

            data.put(newObject);
            writeJSONtoFile(data, notebooksFile);

        } catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while parsing notebooks' data");

        }
        Log.i(tag, "A new notebook was added successfully");
    }

    /*
        Private function to remove a element from a JSONArray.
        This was implemented because the remove method is not compatible with the
        current min API level (15).
     */
    private JSONArray removeFromJSONArray(JSONArray array, int id) {
        ArrayList<JSONObject> list = new ArrayList<>();
        try {
            for (int i = 0; i != array.length(); i++) {
                list.add(array.getJSONObject(i));
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while removing element from a JSONArray");
        }
        list.remove(id);
        return new JSONArray(list);
    }

    /*
        Deletes a notebook in both the local copy and file.
     */
    public void deleteNotebook(int notebookId) {
        // Deleting from our local array
        mNotebooks.remove(notebookId);

        // Deleting from file
        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        String notebooksString = getFileAsString(FileNames.NOTEBOOKS);

        try {
            JSONArray data = removeFromJSONArray(new JSONArray(notebooksString), notebookId);
            writeJSONtoFile(data, notebooksFile);

        } catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while deleting a notebook");

        }
        Log.i(tag, "A notebook was deleted successfully");
    }

    /*
        Edits notebook's title and description
     */
    public void editNotebook(int notebookId, String title, String description) {
        Notebook toModify = mNotebooks.get(notebookId);

        toModify.setTitle(title);
        toModify.setDescription(description);

        mNotebooks.set(notebookId, toModify);

        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        String notebooksString = getFileAsString(FileNames.NOTEBOOKS);

        try {
            JSONArray data = new JSONArray(notebooksString);
            JSONObject toEdit = data.getJSONObject(notebookId);

            toEdit.put("title", title);
            toEdit.put("description", description);

            data.put(notebookId, toEdit);
            writeJSONtoFile(data, notebooksFile);

        } catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while editing a notebook");

        }
        Log.i(tag, "A notebook was edited successfully");
    }

    public ArrayList<Note> getNotes(int notebookId) {
        return mNotebooks.get(notebookId).getNotes();
    }

    /*
        Add a new note to a notebook in both the local copy and file.
     */
    public void addNote(Note note, int notebookId) {
        // Adding note to our local array
        mNotebooks.get(notebookId).addNote(note);

        // Adding note to file
        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        String notebooksString = getFileAsString(FileNames.NOTEBOOKS);

        try {
            JSONArray data = new JSONArray(notebooksString);
            JSONObject notebook = data.getJSONObject(notebookId);

            JSONObject noteObject = new JSONObject();

            noteObject.put("title", note.getTitle());
            noteObject.put("content", note.getContent());

            notebook.accumulate("notes", noteObject);

            writeJSONtoFile(data, notebooksFile);

        } catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while adding a new note to a notebook");

        }
        Log.i(tag, "A new note was added successfully");
    }

    /*
        Deletes a note from a notebook in both the local copy and file.
     */
    public void deleteNote(int notebookId, int noteId) {
        Notebook toModify = mNotebooks.get(notebookId);

        toModify.deleteNote(noteId);

        mNotebooks.set(notebookId, toModify);

        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        String notebooksString = getFileAsString(FileNames.NOTEBOOKS);

        try {
            JSONArray data = new JSONArray(notebooksString);
            JSONObject toEdit = data.getJSONObject(notebookId);

            toEdit.put("notes", removeFromJSONArray(toEdit.getJSONArray("notes"), noteId));

            data.put(notebookId, toEdit);
            writeJSONtoFile(data, notebooksFile);

        } catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while deleting a note");

        }
        Log.i(tag, "A note was deleted successfully");
    }

    /*
        Edits note's title and content.
     */
    public void editNote(int notebookId, int noteId, String title, String content) {
        Notebook notebook = mNotebooks.get(notebookId);

        Note toModify = notebook.getNote(noteId);

        toModify.setTitle(title);
        toModify.setContent(content);

        notebook.setNote(noteId, toModify);

        mNotebooks.set(notebookId, notebook);

        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        String notebooksString = getFileAsString(FileNames.NOTEBOOKS);

        try {
            JSONArray data = new JSONArray(notebooksString);
            JSONObject notebookToEdit = data.getJSONObject(notebookId);

            JSONArray notes = notebookToEdit.getJSONArray("notes");

            JSONObject toEdit = notes.getJSONObject(noteId);

            toEdit.put("title", title);
            toEdit.put("content", content);

            notes.put(noteId, toEdit);

            data.put(notebookId, notebookToEdit);
            writeJSONtoFile(data, notebooksFile);

        } catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(tag, "Error while deleting a note");

        }
        Log.i(tag, "A note was deleted successfully");
    }
}
