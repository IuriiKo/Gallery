package com.kushyk.gallery;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.kushyk.gallery.locale.DbManager;
import com.kushyk.gallery.locale.entity.DbImage;

/**
 * Created by Iurii Kushyk on 19.02.2017.
 */

public class App extends Application {
    private static Context context;
    private static DbManager dbManager;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initDb();
        dbManager = new DbManager();

    }

    private void initDb() {
        ActiveAndroid.initialize(getDbConfiguration());
    }

    private Configuration getDbConfiguration() {
       return new Configuration.Builder(this)
        .setDatabaseName("gallery.db")
        .setModelClasses(DbImage.class)
        .setDatabaseVersion(1)
        .create();
    }

    public static Context getContext() {
        return context;
    }

    public static DbManager getDbManager() {
        return dbManager;
    }
}
