package com.cocosw.query.ext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;


import java.lang.ref.WeakReference;

/**
 * Ext CocoQuery
 */
public class CocoQuery extends com.cocosw.query.CocoQuery<ExtViewQuery> {

  //  private WeakReference<Object> progress;

    public CocoQuery(Activity act) {
        super(act);
    }

    public CocoQuery(View root) {
        super(root);
    }

    public CocoQuery(Activity act, View root) {
        super(act, root);
    }

    public CocoQuery(Context context) {
        super(context);
    }

//
//    public CocoQuery http(HttpRequest request) {
//        task(new DownloadTask(request));
//        return this;
//    }
//
//
//    private static class DownloadTask extends CocoTask<HttpRequest> {
//
//        private final HttpRequest request;
//
//        public DownloadTask(HttpRequest request) {
//            this.request = request;
//        }
//
//        @Override
//        public HttpRequest backgroundWork() throws Exception {
//            request.ok();
//            return request;
//        }
//    }



}
