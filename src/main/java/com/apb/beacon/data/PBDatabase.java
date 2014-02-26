package com.apb.beacon.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.apb.beacon.model.Page;
import com.apb.beacon.model.PageAction;
import com.apb.beacon.model.PageChecklist;
import com.apb.beacon.model.PageItem;
import com.apb.beacon.model.PageStatus;
import com.apb.beacon.model.PageTimer;

import java.util.List;

/**
 * Created by aoe on 12/12/13.
 */
public class PBDatabase {

    private static final String TAG = PBDatabase.class.getSimpleName();

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context mContext;

    private static final String DATABASE_NAME = "pb_db";
    private static final int DATABASE_VERSION = 7;


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            PageDbManager.createTable(db);
            PageStatusDbManager.createTable(db);
            PageItemDbManager.createTable(db);
            PageActionDbManager.createTable(db);
            PageTimerDbManager.createTable(db);
            PageChecklistDbManager.createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            PageDbManager.dropTable(db);
            PageStatusDbManager.dropTable(db);
            PageItemDbManager.dropTable(db);
            PageActionDbManager.dropTable(db);
            PageTimerDbManager.dropTable(db);
            PageChecklistDbManager.dropTable(db);

            onCreate(db);
        }
    }

    /**
     * Constructor
     */
    public PBDatabase(Context ctx) {
        mContext = ctx;
    }

    public PBDatabase open() throws SQLException {
        dbHelper = new DatabaseHelper(mContext);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


//    public void insertOrUpdateLocalCachePage(LocalCachePage page) {
//        LocalCacheDbManager.insertOrUpdate(this.db, page);
//    }
//
//    public LocalCachePage retrievePage(int pageNumber) {
//        return LocalCacheDbManager.retrievePage(this.db, pageNumber);
//    }
//
//    public int deletePage(int pageNumber) {
//        return LocalCacheDbManager.deletePage(this.db, pageNumber);
//    }

    public void insertOrUpdateWizardPage(Page page) {
        PageDbManager.insertOrUpdate(this.db, page);

        deletePageActions(page.getId(), page.getLang());
        deletePageItems(page.getId(), page.getLang());
        deletePageStatus(page.getId(), page.getLang());
        deletePageTimer(page.getId(), page.getLang());
        deletePageChecklist(page.getId(), page.getLang());

        if (page.getStatus() != null) {
            for (PageStatus status : page.getStatus())
                insertPageStatus(status, page.getId(), page.getLang());
        }

        if (page.getAction() != null) {
            for (PageAction action : page.getAction())
                insertPageAction(action, page.getId(), page.getLang());
        }

        if (page.getItems() != null) {
            for (PageItem item : page.getItems())
                insertPageItem(item, page.getId(), page.getLang());
        }

        if(page.getTimers() != null){
            insertPageTimer(page.getTimers(), page.getId(), page.getLang());
        }

        if (page.getChecklist() != null) {
            for (PageChecklist cl : page.getChecklist())
                insertPageChecklist(cl, page.getId(), page.getLang());
        }
    }


    public Page retrievePage(String pageId, String lang) {
        return PageDbManager.retrieve(this.db, pageId, lang);
    }

    public List<Page> retrievePages(String lang) {
        return PageDbManager.retrieve(this.db, lang);
    }


    /*
    Page-Action methods
    */
    public void insertPageAction(PageAction action, String pageId, String lang) {
        PageActionDbManager.insert(this.db, action, pageId, lang);
    }

    public List<PageAction> retrievePageAction(String pageId, String lang) {
        return PageActionDbManager.retrieve(this.db, pageId, lang);
    }

    public void deletePageActions(String pageId, String lang){
        PageActionDbManager.delete(this.db, pageId, lang);
    }


    /*
    Page-Item methods
    */
    public void insertPageItem(PageItem item, String pageId, String lang) {
        PageItemDbManager.insert(this.db, item, pageId, lang);
    }

    public List<PageItem> retrievePageItem(String pageId, String lang) {
        return PageItemDbManager.retrieve(this.db, pageId, lang);
    }

    public void deletePageItems(String pageId, String lang){
        PageItemDbManager.delete(this.db, pageId, lang);
    }


    /*
    Page-Status methods
    */
    public void insertPageStatus(PageStatus status, String pageId, String lang) {
        PageStatusDbManager.insert(this.db, status, pageId, lang);
    }

    public List<PageStatus> retrievePageStatus(String pageId, String lang) {
        return PageStatusDbManager.retrieve(this.db, pageId, lang);
    }

    public void deletePageStatus(String pageId, String lang){
        PageStatusDbManager.delete(this.db, pageId, lang);
    }



    /*
    Page-Timers methods
     */
    public void insertPageTimer(PageTimer timer, String pageId, String lang) {
        PageTimerDbManager.insert(this.db, timer, pageId, lang);
    }

    public PageTimer retrievePageTimer(String pageId, String lang) {
        return PageTimerDbManager.retrieve(this.db, pageId, lang);
    }

    public void deletePageTimer(String pageId, String lang){
        PageTimerDbManager.delete(this.db, pageId, lang);
    }


    /*
    Page-Checklist methods
    */
    public void insertPageChecklist(PageChecklist cList, String pageId, String lang) {
        PageChecklistDbManager.insert(this.db, cList, pageId, lang);
    }

    public List<PageChecklist> retrievePageChecklist(String pageId, String lang) {
        return PageChecklistDbManager.retrieve(this.db, pageId, lang);
    }

    public void deletePageChecklist(String pageId, String lang){
        PageChecklistDbManager.delete(this.db, pageId, lang);
    }
}