package com.cocosw.query;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;


/**
 * CocoTask is a usage simplified AsyncTask, with progress dialog support and callback activity check(in order to prevent NPE in some case)
 * Don't overuse this as AsyncTask has many drawbacks.
 *
 * @param <T>
 */

public abstract class CocoTask<T> implements OnCancelListener {

    private WeakReference<Object> progress;

    private T result;

    private View view;
    private boolean running;

    public abstract T backgroundWork() throws Exception;

    private AsyncTask<?, ?, ?> task;

    private ProgressDialog dialog;

    private int dialogreid = -1;

    private boolean dialogcancel = false;

    private boolean dialogdeterminate = true;

    private WeakReference<Activity> act;


    @Override
    public int hashCode() {
        int result = task != null ? task.hashCode() : (int) (Math.random() * 1000);
        result = 31 * result + (act != null ? act.hashCode() : 0);
        return result;
    }

    /**
     * This method will be trigger before the background start to run.
     */
    public void pre() {

    }

    /**
     * Callback when background task finished without any exception
     *
     * @param result the object
     */
    public void callback(final T result) {

    }

    /**
     * Callback when background task failed with exception
     *
     * @param result the object
     */
    public void failcallback(final T result, final Exception e) {

    }

    /**
     * This will be trigger after callback function been called no matter the task is failed or success.
     */
    public void end() {

    }

    private boolean isActive() {
        if (act == null) {
            return true;
        }

        final Activity a = act.get();

        if (a == null || a.isFinishing()) {
            return false;
        }

        return true;
    }


    void run() {
        try {
            result = backgroundWork();
            showProgress(false);
            callback(result);
        } catch (final Exception e) {
            e.printStackTrace();
            failcallback(result, e);
        }
    }

    /**
     * Progress view will only be shown during task is executing
     *
     * @param progress
     * @return
     */
    public CocoTask<T> progress(final View progress) {
        if (progress != null) {
            this.progress = new WeakReference<Object>(progress);
        }
        return this;
    }


    /**
     * Progress view will only be shown during task is executing
     *
     * @param progress
     * @return
     */
    public CocoTask<T> progress(final Dialog progress) {
        if (progress != null) {
            this.progress = new WeakReference<Object>(progress);
        }
        return this;
    }

    /**
     * Progress view will only be shown during task is executing
     *
     * @param progress
     * @return
     */
    public CocoTask<T> progress(final Activity progress) {
        if (progress != null) {
            this.progress = new WeakReference<Object>(progress);
        }
        return this;
    }

    protected void progressUpdate(final String... values) {
        if (null != dialog && !dialog.isIndeterminate()) {
            dialog.setProgress(Integer.valueOf(values[0]));
        }
    }


    void async(final Activity act) {
        this.act = new WeakReference<Activity>(act);
        if (act.isFinishing()) {
            return;
        }

        runtask(act);
    }

    private void runtask(final Context act) {
        task = new AsyncTask<Object, String, T>() {

            private Exception e;

            @Override
            protected void onPostExecute(final T result) {
                try {
                    if (e == null) {
                        if (!isCancelled()) {
                            if (isActive()) {
                                callback(result);
                            }

                        }
                    } else {
                        e.printStackTrace();
                        if (isActive()) {
                            failcallback(result, e);
                        }
                    }
                } finally {
                    // 无论如何，关闭progress
                    showProgress(false);
                    end();
                }
                running = false;
            }

            @Override
            protected T doInBackground(final Object... params) {
                try {
                    return backgroundWork();
                } catch (final Exception e) {
                    this.e = e;
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                if (dialogreid != -1) {
                    dialog = new ProgressDialog(act);
                    dialog.setCancelable(dialogcancel);
                    dialog.setIndeterminate(dialogdeterminate);
                    dialog.setMessage(act.getText(dialogreid));
                    if (dialogcancel) {
                        dialog.setOnCancelListener(CocoTask.this);
                    }
                }
                showProgress(true);
                pre();
            }

            @Override
            protected void onProgressUpdate(final String... values) {
                progressUpdate(values);
            }

        };
        execute(task);
        running = true;
    }

    protected void showProgress(final boolean show) {
        if (act.get() == null || act.get().isFinishing()) {
            return;
        }
        if (progress != null) {
            showProgress(progress.get(), null, show);
        }
        if (dialog != null) {
            try {
                if (show) {
                    if (!dialog.isShowing())
                        dialog.show();
                } else {
                    if (dialog.isShowing())
                        dialog.dismiss();
                }
            }catch (Throwable e) {

            }
        }
        if (view != null) {
            view.setVisibility(!show ? View.VISIBLE
                    : View.INVISIBLE);
        }
    }


    /**
     * This fore view will be hide during task is executing, and visible after task done.
     *
     * @param view
     */
    public CocoTask<T> view(final View view) {
        this.view = view;
        return this;
    }

    /**
     * Cancel the task
     */
    public void cancel() {
        if (task != null && !task.isCancelled()
                && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(true);
        }
    }

    @Override
    public void onCancel(final DialogInterface arg0) {
        cancel();
    }

    /**
     * Change the message in progress dialog
     *
     * @param resId
     */
    public void updateDialogMsg(final int resId) {
        if (dialog != null) {
            this.dialog.setMessage(dialog.getContext().getText(resId));
        }
    }

    /**
     * Task with a progress dialog showing message
     *
     * @param resId
     * @return
     */
    public CocoTask<T> dialog(final int resId) {
        this.dialogreid = resId;
        return this;
    }

    /**
     * This task can be cancel by press back key
     *
     * @return
     */
    public CocoTask<T> cancelable() {
        this.dialogcancel = true;
        return this;
    }

    /**
     * Progress bar is determinate
     *
     * @return
     */
    public CocoTask<T> determinate() {
        this.dialogdeterminate = false;
        return this;
    }

    private static void showProgress(Object p, String url, boolean show) {

        if (p != null) {

            if (p instanceof View) {
                View pv = (View) p;
                ProgressBar pbar = null;

                if (p instanceof ProgressBar) {
                    pbar = (ProgressBar) p;
                }

                if (show) {
                    pv.setVisibility(View.VISIBLE);
                    if (pbar != null) {
                        pbar.setProgress(0);
                        pbar.setMax(100);
                    }
                } else {
                    if (pbar == null || pbar.isIndeterminate()) {
                        pv.setVisibility(View.GONE);
                    }
                }
            } else if (p instanceof Dialog) {
                Dialog pd = (Dialog) p;
                if (show) {
                    pd.show();
                } else {
                    if (pd.isShowing())
                        pd.dismiss();
                }
            } else if (p instanceof Activity) {
                Activity act = (Activity) p;
                act.setProgressBarIndeterminateVisibility(show);
                act.setProgressBarVisibility(show);

                if (show) {
                    act.setProgress(0);
                }
            }
        }

    }

    /**
     * Execute an {@link AsyncTask} on a thread pool.
     *
     * @param task Task to execute.
     * @param <T>  Task argument type.
     */
    private static <T> void execute(AsyncTask<T, ?, ?> task) {
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public boolean isRunning() {
        return running;
    }

    public void async(Context context) {
        runtask(context);
    }
}
