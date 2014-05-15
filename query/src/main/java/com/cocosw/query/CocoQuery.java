package com.cocosw.query;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.util.WeakHashMap;

/**
 * Bring chain style programming to Android UI development
 */
public class CocoQuery<T extends AbstractViewQuery<T>> {

    protected View root;
    protected Activity act;
    private Context context;

    private static Class query = AbstractViewQuery.DefaultQuery.class;

    /**
     * Set your customized ViewQuery class
     *
     * @param myclass
     */
    public static void setQueryClass(Class myclass) {
        query = myclass;
    }


    /**
     * Instantiates a new AQuery object.
     *
     * @param act Activity that's the parent of the to-be-operated views.
     */
    public CocoQuery(Activity act) {
        this.act = act;
    }

    /**
     * Instantiates a new AQuery object.
     *
     * @param root View container that's the parent of the to-be-operated views.
     */
    public CocoQuery(View root) {
        this.root = root;
    }

    /**
     * Instantiates a new AQuery object. This constructor should be used for Fragments.
     *
     * @param act  Activity
     * @param root View container that's the parent of the to-be-operated views.
     */
    public CocoQuery(Activity act, View root) {
        this.root = root;
        this.act = act;
    }


    /**
     * Instantiates a new AQuery object.
     *
     * @param context Context that will be used in async operations.
     */

    public CocoQuery(Context context) {
        this.context = context;
    }


    /**
     * Return the context of activity or view.
     *
     * @return Context
     */

    protected Context getContext() {
        if (act != null) {
            return act;
        }
        if (root != null) {
            return root.getContext();
        }
        return context;
    }

    /**
     * Select view, and start the chain
     *
     * @param view
     * @return
     */
    public T v(View view) {
        return create(view);
    }

    /**
     * Select view of id, and start the chain
     *
     * @param id
     * @return
     */
    public T v(int id) {
        return create(findView(id));
    }

    /**
     * replace root view with given view
     * @param view
     * @return
     */
    public CocoQuery<T> recycle(View view) {
        this.root = view;
        return this;
    }


    /**
     * Select view, and start the chain
     *
     * @param id
     * @return
     */
    public T id(int id) {
        return v(id);
    }


    private T create(View view) {

        T result = null;

        try {
            Constructor<T> c = getConstructor();
            result = (T) c.newInstance(view);
            result.act = act;
            result.context = getContext();
            result.root = root;
            result.query = this;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Please set your own ViewQuery by setViewQuery() method");
        }
        return result;

    }

    private Constructor getConstructor() {
        try {
            return (Constructor<T>) query.getConstructor(View.class);
        } catch (Exception e) {
            //should never happen
            e.printStackTrace();
        }
        return null;
    }

    private View findView(int id) {
        View result = null;
        if (root != null) {
            result = root.findViewById(id);
        } else if (act != null) {
            result = act.findViewById(id);
        }
        return result;
    }

    /**
     * Show toast message
     *
     * @param message
     */
    public void toast(final CharSequence message) {
        if (act == null)
            return;

        act.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(act, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Show toast message
     *
     * @param strId
     */
    public void toast(final int strId) {
        if (act == null)
            return;

        act.runOnUiThread(new Runnable() {

            public void run() {
                Toast.makeText(act, strId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Show toast message with long duration
     *
     * @param cha
     */
    public void longToast(final CharSequence cha) {
        if (act == null)
            return;

        act.runOnUiThread(new Runnable() {

            public void run() {
                Toast.makeText(act, cha, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Show toast message with long duration
     *
     * @param strId
     */
    public void longToast(final int strId) {
        if (act == null)
            return;

        act.runOnUiThread(new Runnable() {

            public void run() {
                Toast.makeText(act, strId, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Open a alert dialog with title and message
     *
     * @param title
     * @param message
     */
    public void alert(final String title, final CharSequence message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,
                                        final int which) {
                        final AlertDialog ad = builder.create();
                        ad.cancel();
                    }
                });
        builder.show();
    }

    /**
     * Open a alert dialog with title and message
     *
     * @param title
     * @param message
     */
    public void alert(final int title, final int message) {
        new AlertDialog.Builder(getContext());
        alert(getContext().getString(title),
                getContext().getString(message));
    }

    /**
     * Open a confirm dialog with title and message
     *
     * @param title
     * @param message
     */
    public void confirm(final int title, final int message,
                        final DialogInterface.OnClickListener onClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                getContext());
        builder.setTitle(title).setIcon(android.R.drawable.ic_dialog_info).setMessage(message);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog,
                                        final int which) {
                        if (onClickListener != null) {
                            onClickListener.onClick(dialog, which);
                        }

                    }
                });
        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog,
                                        final int which) {
                        if (onClickListener != null) {
                            onClickListener.onClick(dialog, which);
                        }
                    }
                });
        builder.show();
    }


    /**
     * Open a alert dialog with title and message
     *
     * @param title
     * @param message
     */
    public void alert(final int title, final int message,
                      final DialogInterface.OnClickListener onClickListener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(
                getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,
                                        final int which) {
                        if (onClickListener != null) {
                            onClickListener.onClick(dialog, which);
                        }
                        final AlertDialog ad = builder.create();
                        ad.cancel();
                    }
                });
        builder.show();
    }

    /**
     * Open a dialog with single choice list
     *
     * @param title
     * @param list
     * @param listener
     */
    public void dialog(final int title, int list, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title)
                .setItems(list, listener);
        builder.create().show();
    }

    /**
     * Open a dialog with single choice list
     *
     * @param title
     * @param list
     * @param listener
     */
    public void dialog(final int title, CharSequence[] list, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title)
                .setItems(list, listener);
        builder.create().show();
    }

    public CocoQuery task(final CocoTask<?> task) {
        taskpool.put(task.hashCode(), task);
        if (act!=null)
            task.async(act);
        else
            task.async(getContext());
        return this;
    }

    private final WeakHashMap<Integer, CocoTask<?>> taskpool = new WeakHashMap<Integer, CocoTask<?>>();

    /**
     * Cancle all the task in pool
     *
     * @return
     */
    private CocoQuery CleanAllTask() {
        for (final CocoTask<?> reference : taskpool.values()) {
            if (reference != null) {
                reference.cancel();
            }
        }
        return this;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        CleanAllTask();
    }

    /**
     * Launch a new activity
     * @param clz
     */
    public void startActivity(Class clz) {
        act.startActivity(new Intent(getContext(),clz));
    }

    /**
     * Launch an activity for which you would like a result when it finished
     *
     * @param clz
     * @param requestCode
     */
    public void startActivityForResult(Class clz,int requestCode) {
        act.startActivityForResult(new Intent(getContext(),clz),requestCode);
    }
}
