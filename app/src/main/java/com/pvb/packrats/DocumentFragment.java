package com.pvb.packrats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;

/**
 * A placeholder fragment containing a simple view.
 */
public class DocumentFragment extends Fragment {

//        private final static String FILENAME = "document";
    private final static String LOG_TAG = DocumentFragment.class.getSimpleName();

    EditText mEditTextTitle;
    EditText mEditTextContents;
    DatabaseHelper mDBHelper = null;

    public DocumentFragment() {
    }

    public DatabaseHelper getmDBHelper() {
        if (mDBHelper == null){
            mDBHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return mDBHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_document, container, false);
        wireUpViews(rootView);
        Document document = retrieveFirstDocument();
        displayDocument(document);
        prepareButton(rootView);
        return rootView;
    }

    private void displayDocument(Document document) {
        if (document!=null){
            mEditTextTitle.setText(document.getmTitle());
            mEditTextContents.setText(document.getmContent());
        }
    }

    private Document retrieveFirstDocument() {
        Document document = null;
        try {
            Dao<Document,Integer> documentDao = getmDBHelper().getmDocumentDao();
            QueryBuilder<Document,Integer> queryBuilder = documentDao.queryBuilder();
            document = documentDao.queryForFirst(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return document;
    }

    private void prepareButton(View rootView) {
        Button buttonSave = (Button) rootView.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Document document = createDocumentFromData();
                saveDocument(document);
            }

            private void saveDocument(Document document) {
//                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(FILENAME, MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString(Document.TITLE, document.getmTitle());
//                    editor.putString(Document.CONTENTS, document.getmContent());
//                    editor.apply();
                try {
                    Dao<Document,Integer> dao = getmDBHelper().getmDocumentDao();
                    dao.create(document);
                } catch (SQLException e) {
                    Log.e(LOG_TAG, "Failed to create DAO.", e);
                }

            }

            private Document createDocumentFromData() {
                Document document = new Document();
                document.setmTitle(mEditTextTitle.getText().toString());
                document.setmContent(mEditTextContents.getText().toString());
                return document;
            }
        });
    }

    private void wireUpViews(View rootView) {
        mEditTextTitle = (EditText) rootView.findViewById(R.id.edit_text_title);
        mEditTextContents = (EditText) rootView.findViewById(R.id.edit_text_contents);
    }

    @Override
    public void onDestroy() {
        if (mDBHelper!=null){
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
        super.onDestroy();
    }
}
