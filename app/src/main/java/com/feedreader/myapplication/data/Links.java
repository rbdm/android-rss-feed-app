package com.feedreader.myapplication.data;

import org.litepal.crud.LitePalSupport;

public class Links extends LitePalSupport {

    public int _ID;
    public String COLUMN_WEBLINK;

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public void setCOLUMN_PET_WEBLINK(String COLUMN_PET_WEBLINK) {
        this.COLUMN_WEBLINK = COLUMN_PET_WEBLINK;
    }

    public int get_ID() {
        return _ID;
    }

    public void setCOLUMN_WEBLINK(String COLUMN_WEBLINK) {
        this.COLUMN_WEBLINK = COLUMN_WEBLINK;
    }
}
