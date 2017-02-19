package com.kushyk.gallery.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kushyk.gallery.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Iurii Kushyk on 19.02.2017.
 */
class ItemVH extends RecyclerView.ViewHolder {
    @BindView(R.id.galleryView)
    ImageView item;

    ItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
