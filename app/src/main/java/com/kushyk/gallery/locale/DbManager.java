package com.kushyk.gallery.locale;

import com.activeandroid.query.Select;
import com.kushyk.gallery.locale.entity.DbImage;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Iurii Kushyk on 19.02.2017.
 */
public class DbManager {
    private List<DbImage> getAllImages() {
        return new Select().from(DbImage.class).execute();
    }

    private long saveImageLocally(String path) {
        DbImage image = new DbImage(path);
        return image.save();
    }

    public Observable<List<DbImage>> getAllImagesAsync() {
        return Observable.fromCallable(this::getAllImages);
    }

    public Observable<Long> saveImage(String path) {
        return Observable.fromCallable(() -> saveImageLocally(path));
    }
}
