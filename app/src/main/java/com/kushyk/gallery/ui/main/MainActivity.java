package com.kushyk.gallery.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kushyk.gallery.App;
import com.kushyk.gallery.R;
import com.kushyk.gallery.locale.entity.DbImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 13;
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private Unbinder unbinder;

    @BindView(R.id.content)
    RecyclerView content;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initView();
        getImages();
    }

    private void initView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        adapter = new MainAdapter();
        content.setLayoutManager(manager);
        content.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addImage:
                getImageFromGallery();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(
                Intent.createChooser(intent, getString(R.string.select_picture)),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            Uri uri = data.getData();
            saveImage(uri.toString());
        }
    }


    private void saveImage(String path) {
        App.getDbManager().saveImage(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessSaveImage, this::onErrorSaveImage);

    }

    private void onSuccessSaveImage(Long aLong) {
        getImages();
        showToast(R.string.image_loaded);
    }

    private void onErrorSaveImage(Throwable throwable) {
        Log.e(LOG_TAG, "onErrorSaveImage()", throwable);
    }

    private void getImages() {
        App.getDbManager().getAllImagesAsync()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessGetImages,this::onErrorGetImages);
    }

    private void onSuccessGetImages(List<DbImage> dbImages) {
        adapter.clear();
        adapter.addAll(dbImages);
        adapter.notifyDataSetChanged();
    }

    private void onErrorGetImages(Throwable throwable) {
        Log.e(LOG_TAG, "onErrorGetImages()", throwable);
    }

    private void showToast(@StringRes int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
