package com.kushyk.gallery.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kushyk.gallery.R;
import com.kushyk.gallery.locale.entity.DbImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iurii Kushyk on 19.02.2017.
 */

public class MainAdapter extends RecyclerView.Adapter<ItemVH> {
    private List<DbImage> items = new ArrayList<>();
    @Override
    public ItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemVH holder, int position) {
        loadImage(holder.item, items.get(position));
    }

    private void loadImage(ImageView image, DbImage item) {
        Glide.with(image.getContext())
                .load(item.getPath())
                .into(image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addAll(List<DbImage> items) {
        this.items.addAll(items);
    }

    public void add(DbImage item) {
        items.add(item);
    }

    public void clear() {
        items.clear();
    }
}
