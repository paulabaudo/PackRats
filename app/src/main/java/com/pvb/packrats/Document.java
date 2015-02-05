package com.pvb.packrats;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by Paula on 05/02/2015.
 */
public class Document {

    public final static String TITLE = "title";
    public final static String CONTENTS = "contents";
    public final static String CREATION_DATE = "creation_date";
    public final static String ID = "_id";

    @DatabaseField (generatedId = true, columnName = ID) private int id;
    @DatabaseField (columnName = TITLE) private String mTitle;
    @DatabaseField (columnName = CONTENTS) private String mContent;
    @DatabaseField (columnName = CREATION_DATE) private Date mCreation = new Date();

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmCreation() {
        return mCreation;
    }

    public void setmCreation(Date mCreation) {
        this.mCreation = mCreation;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String toString(){
        return mTitle + ": " + mContent;
    }
}
