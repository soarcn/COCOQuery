package com.cocosw.query.ext;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.cocosw.query.AbstractViewQuery;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 14-2-4.
 */
public class ExtViewQuery extends AbstractViewQuery<ExtViewQuery> {

    /**
     * Instantiates a new AQuery object.
     *
     * @param root View container that's the parent of the to-be-operated views.
     */
    public ExtViewQuery(View root) {
        super(root);
    }


    /**
     * Load network image to current ImageView with cache
     * @param url
     * @return
     */
    public ExtViewQuery image(String url) {
        if (!TextUtils.isEmpty(url) && view instanceof ImageView) {
            Picasso.with(context).load(url).into((ImageView) view);
        }
        return self();
    }

    @Override
    public ExtViewQuery image(int imgId) {
        if (view instanceof ImageView) {
            Picasso.with(context).load(imgId).into((ImageView) view);
        }
        return self();
    }


    /**
     * Load network image to current ImageView with cache control
     * @param url
     * @param cache
     * @return
     */
    public ExtViewQuery image(String url, boolean cache) {
        if (cache) {
            image(url);
        } else {
            Picasso.with(context).load(url).skipMemoryCache().into((ImageView) view);
        }
        return this;
    }

    /**
     * Load network image to current ImageView with holder image
     * @param url
     * @param holder
     * @return
     */
    public ExtViewQuery image(String url, int holder) {
        if (!TextUtils.isEmpty(url) && view instanceof ImageView) {
            Picasso.with(context).load(url).placeholder(holder).into((ImageView) view);
        }
        return self();
    }

    /**
     * Load network image to current ImageView with holder image
     *
     * @param url
     * @param holder
     * @return
     */
    public ExtViewQuery image(String url, Drawable holder) {
        if (!TextUtils.isEmpty(url) && view instanceof ImageView) {
            Picasso.with(context).load(url).placeholder(holder).into((ImageView) view);
        }
        return self();
    }

    /**
     * Load network image to current ImageView with holder image and fallback image
     * @param url
     * @param holder
     * @param fallbackId
     * @return
     */
    public ExtViewQuery image(String url, Drawable holder,int fallbackId) {
        if (!TextUtils.isEmpty(url) && view instanceof ImageView) {
            Picasso.with(context).load(url).error(fallbackId).placeholder(holder).into((ImageView) view);
        }
        return self();
    }

    /**
     * Load network image to current ImageView with holder image and fallback image
     * @param url
     * @param holder
     * @param fallbackId
     * @return
     */
    public ExtViewQuery image(String url, Drawable holder,Drawable fallbackId) {
        if (!TextUtils.isEmpty(url) && view instanceof ImageView) {
            Picasso.with(context).load(url).error(fallbackId).placeholder(holder).into((ImageView) view);
        }
        return self();
    }


    /**
     * Load network image to current ImageView with callback
     * @param url
     * @param callback
     * @return
     */
    public ExtViewQuery image(String url, Callback callback) {
        if (!TextUtils.isEmpty(url) && view instanceof ImageView) {
            Picasso.with(context).load(url).into((ImageView) view, callback);
        }
        return self();
    }

}
