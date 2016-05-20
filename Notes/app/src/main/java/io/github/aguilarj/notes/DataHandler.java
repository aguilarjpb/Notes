package io.github.aguilarj.notes;

import android.app.AlertDialog;
import android.content.Context;

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
    private Context currentContext;
    private ArrayList<Notebook> mNotebooks;
    private String dataPath;
    private class FileNames {
        public static final String NOTEBOOKS = "/Notebooks.txt";

    }

    public DataHandler(Context c) {
        currentContext = c;
        dataPath = c.getFilesDir().toString();
        mNotebooks = new ArrayList<>();
    }

    public void errorMessage(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(currentContext);
        builder.setMessage(error);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public ArrayList<Notebook> getNotebooks() {
        File notebooksFile = new File(dataPath + FileNames.NOTEBOOKS);
        String notebooksString = "";
        if (!notebooksFile.exists()) {
            notebooksFile = new File(dataPath, FileNames.NOTEBOOKS);
            try {
                Files.write("Empty", notebooksFile, Charsets.UTF_8);
            } catch (IOException exception) {
                errorMessage("Error while creating notebooks' file: " + exception.toString());
            }
            return mNotebooks = null;
        }

        try {
            notebooksString = Files.toString(notebooksFile, Charsets.UTF_8);
        } catch (IOException exception) {
            errorMessage("Error while trying to open notebooks: " + exception.toString());

        }
        try {
            JSONArray data = new JSONArray(notebooksString);

            for (int i = 0; i != data.length(); ++i) {
                JSONObject rawNotebook = data.getJSONObject(i);
                Notebook notebook = new Notebook(
                        rawNotebook.getString("title"), rawNotebook.getString("description"));

                mNotebooks.add(notebook);
            }

        } catch (JSONException exception) {
            errorMessage("Error while parsing notebooks' data: " + exception.toString());
        }
        return mNotebooks;
    }
}
