package com.kushyk.gallery.locale.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by Iurii Kushyk on 19.02.2017.
 */

public class DbImage extends Model{
    public static final String COLUMN_PATH = "path";
    @Column(name = COLUMN_PATH,unique = true,onUniqueConflict = Column.ConflictAction.REPLACE)
    String path;

    public DbImage() {
    }

    public DbImage(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
