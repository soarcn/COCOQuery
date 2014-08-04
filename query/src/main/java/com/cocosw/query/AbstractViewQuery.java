package com.cocosw.query;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

/**
 * View query abstract class
 */
public abstract class AbstractViewQuery<T extends AbstractViewQuery<T>> {

    protected View root;
    protected Activity act;
    protected Context context;
    protected CocoQuery<?> query;

    protected View view;
    protected Object progress;



    public static class DefaultQuery extends AbstractViewQuery<DefaultQuery> {
        /**
         * Instantiates a new AQuery object.
         *
         * @param root View container that's the parent of the to-be-operated views.
         */
        public DefaultQuery(View root) {
            super(root);
        }
    }


    /**
     * Instantiates a new AQuery object.
     *
     * @param root View container that's the parent of the to-be-operated views.
     */
    public AbstractViewQuery(View root) {
        this.view = root;
    }


    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }


    /**
     * Set the text of a TextView.
     *
     * @param resid the resid
     * @return self
     */
    public T text(int resid) {

        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setText(resid);
        }
        return self();
    }

    /**
     * Set the text of a TextView with localized formatted string
     * from application's package's default string table
     *
     * @param resid the resid
     * @return self
     * @see Context#getString(int, Object...)
     */
    public T text(int resid, Object... formatArgs) {
        if (context != null) {
            CharSequence text = context.getString(resid, formatArgs);
            text(text);
        }
        return self();
    }

    /**
     * set the text of a TextView with extraInfo from intent
     *
     * @param intent
     * @param extraName
     * @return
     */
    public T text(Intent intent, String extraName) {
        if (intent!=null) {
            return text(intent.getStringExtra(extraName));
        }
        return self();
    }

    /**
     * Set the text of a TextView.
     *
     * @param text the text
     * @return self
     */
    public T text(CharSequence text) {

        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setText(text);
        }

        return self();
    }

    /**
     * Set the text of a TextView. Hide the view (gone) if text is empty.
     *
     * @param text        the text
     * @param goneIfEmpty hide if text is null or length is 0
     * @return self
     */

    public T text(CharSequence text, boolean goneIfEmpty) {

        if (goneIfEmpty && (text == null || text.length() == 0)) {
            return gone();
        } else {
            return text(text);
        }
    }

    /**
     * Change the custom IME action associated with the text view. click the lable will trigger the associateView's onClick method
     * @param lable
     * @param associateView
     */
    public T imeAction(int lable, final View associateView) {
        if (view instanceof EditText) {
            ((EditText)view).setImeActionLabel(context.getString(lable),view.getId());
            ((EditText)view).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == view.getId() || id == EditorInfo.IME_NULL) {
                        associateView.performClick();
                        return true;
                    }
                    return false;
                }
            });
        }
        return self();
    }

    /**
     * Change the custom IME action associated with the text view. click the lable will trigger the associateView's onClick method
     * @param listener
     */
    public T imeAction(TextView.OnEditorActionListener listener) {
        if (view instanceof EditText) {
            ((EditText)view).setOnEditorActionListener(listener);
        }
        return self();
    }


    /**
     * Set the text of a TextView.
     *
     * @param text the text
     * @return self
     */
    public T text(Spanned text) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setText(text);
        }
        return self();
    }

    /**
     * Set the text color of a TextView. Note that it's not a color resource id.
     *
     * @param color color code in ARGB
     * @return self
     */
    public T textColor(int color) {

        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextColor(color);
        }
        return self();
    }

    /**
     * Set the text color of a TextView from  a color resource id.
     *
     * @param id color resource id
     * @return self
     */
    public T textColorId(int id) {

        return textColor(context.getResources().getColor(id));
    }


    /**
     * Set the text typeface of a TextView.
     *
     * @param tf typeface
     * @return self
     */
    public T typeface(Typeface tf) {

        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTypeface(tf);
        }
        return self();
    }

    /**
     * Set the text size (in sp) of a TextView.
     *
     * @param size size
     * @return self
     */
    public T textSize(float size) {

        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextSize(size);
        }
        return self();
    }


    /**
     * Set the adapter of an AdapterView.
     *
     * @param adapter adapter
     * @return self
     */

    @SuppressWarnings({"unchecked", "rawtypes"})
    public T adapter(Adapter adapter) {

        if (view instanceof AdapterView) {
            AdapterView av = (AdapterView) view;
            av.setAdapter(adapter);
        }
        return self();
    }

    /**
     * Set the image of an ImageView.
     *
     * @param resid the resource id
     * @return self
     */
    public T image(int resid) {

        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            if (resid == 0) {
                iv.setImageBitmap(null);
            } else {
                iv.setImageResource(resid);
            }
        }

        return self();
    }

    /**
     * Set the image of an ImageView.
     *
     * @param drawable the drawable
     * @return self
     * @see drawable
     */
    public T image(Drawable drawable) {

        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            iv.setImageDrawable(drawable);
        }

        return self();
    }

    /**
     * Set the image of an ImageView.
     *
     * @param bm Bitmap
     * @return self
     */
    public T image(Bitmap bm) {

        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            iv.setImageBitmap(bm);
        }

        return self();
    }

    /**
     * Set tag object of a view.
     *
     * @param tag
     * @return self
     */
    public T tag(Object tag) {

        if (view != null) {
            view.setTag(tag);
        }

        return self();
    }

    /**
     * Set tag object of a view.
     *
     * @param key
     * @param tag
     * @return self
     */
    public T tag(int key, Object tag) {

        if (view != null) {
            view.setTag(key, tag);
        }

        return self();
    }


    /**
     * Enable a view.
     *
     * @param enabled state
     * @return self
     */
    public T enabled(boolean enabled) {

        if (view != null) {
            view.setEnabled(enabled);
        }

        return self();
    }

    /**
     * Set checked state of a compound button.
     *
     * @param checked state
     * @return self
     */
    public T checked(boolean checked) {

        if (view instanceof CompoundButton) {
            CompoundButton cb = (CompoundButton) view;
            cb.setChecked(checked);
        }

        return self();
    }

    /**
     * Get checked state of a compound button.
     *
     * @return checked
     */
    public boolean isChecked() {

        boolean checked = false;

        if (view instanceof CompoundButton) {
            CompoundButton cb = (CompoundButton) view;
            checked = cb.isChecked();
        }

        return checked;
    }

    /**
     * Set clickable for a view.
     *
     * @param clickable
     * @return self
     */
    public T clickable(boolean clickable) {

        if (view != null) {
            view.setClickable(clickable);
        }

        return self();
    }


    /**
     * Set view visibility to View.GONE.
     *
     * @return self
     */
    public T gone() {
        return visibility(View.GONE);
    }


    /**
     * Set view visibility to View.GONE.
     *
     * @return self
     */
    public T gone(boolean b) {
        if (b)
            return visibility(View.GONE);
        else
            return visibility(View.VISIBLE);
    }


    /**
     * Set view visibility to View.INVISIBLE.
     *
     * @return self
     */
    public T invisible() {

		/*
        if(view != null && view.getVisibility() != View.INVISIBLE){
			view.setVisibility(View.INVISIBLE);
		}

		return self();
		*/
        return visibility(View.INVISIBLE);
    }

    /**
     * Set view visibility to View.VISIBLE.
     *
     * @return self
     */
    public T visible() {

		/*
		if(view != null && view.getVisibility() != View.VISIBLE){
			view.setVisibility(View.VISIBLE);
		}

		return self();
		*/
        return visibility(View.VISIBLE);
    }

    /**
     * Set view visibility, such as View.VISIBLE.
     *
     * @return self
     */
    public T visibility(int visibility) {

        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }

        return self();
    }


    /**
     * Set view background.
     *
     * @param id the id
     * @return self
     */
    public T background(int id) {
        if (view != null) {
            if (id != 0) {
                view.setBackgroundResource(id);
            } else {
                if (Build.VERSION.SDK_INT<9) {
                    view.setBackgroundDrawable(null);
                }
                else
                    view.setBackground(null);
            }
        }
        return self();
    }



    /**
     * Set view background color.
     *
     * @param color color code in ARGB
     * @return self
     */
    public T backgroundColor(int color) {
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return self();
    }

    /**
     * Set view background color.
     *
     * @param colorId color code in resource id
     * @return self
     */
    public T backgroundColorId(int colorId) {

        if (view != null) {
            view.setBackgroundColor(context.getResources().getColor(colorId));
        }

        return self();
    }

    /**
     * Notify a ListView that the data of it's adapter is changed.
     *
     * @return self
     */
    public T dataChanged() {
        if (view instanceof AdapterView) {
            AdapterView<?> av = (AdapterView<?>) view;
            Adapter a = av.getAdapter();

            if (a instanceof BaseAdapter) {
                BaseAdapter ba = (BaseAdapter) a;
                ba.notifyDataSetChanged();
            }

        }


        return self();
    }


    /**
     * Checks if the current view exist.
     *
     * @return true, if is exist
     */
    public boolean isExist() {
        return view != null;
    }

    /**
     * Gets the tag of the view.
     *
     * @return tag
     */
    public Object getTag() {
        Object result = null;
        if (view != null) {
            result = view.getTag();
        }
        return result;
    }

    /**
     * Gets the tag of the view.
     *
     * @param id the id
     * @return tag
     */
    public Object getTag(int id) {
        Object result = null;
        if (view != null) {
            result = view.getTag(id);
        }
        return result;
    }

    /**
     * Register a callback method for when the view is clicked.
     *
     * @param listener The callback method.
     * @return self
     */
    public T clicked(View.OnClickListener listener) {

        if (view != null) {
            view.setOnClickListener(listener);
        }

        return self();
    }


    /**
     * Register a callback method for when the view is long clicked.
     *
     * @param listener The callback method.
     * @return self
     */
    public T longClicked(View.OnLongClickListener listener) {

        if (view != null) {
            view.setOnLongClickListener(listener);
        }

        return self();
    }

    /**
     * Register a callback method for when an item is clicked in the ListView.
     *
     * @param listener The callback method.
     * @return self
     */
    public T itemClicked(AdapterView.OnItemClickListener listener) {

        if (view instanceof AdapterView) {

            AdapterView<?> alv = (AdapterView<?>) view;
            alv.setOnItemClickListener(listener);

        }

        return self();

    }

    /**
     * Register a callback method for when an item is long clicked in the ListView.
     *
     * @param listener The callback method.
     * @return self
     */
    public T itemLongClicked(AdapterView.OnItemLongClickListener listener) {

        if (view instanceof AdapterView) {

            AdapterView<?> alv = (AdapterView<?>) view;
            alv.setOnItemLongClickListener(listener);

        }

        return self();

    }

    /**
     * Register a callback method for when an item is selected.
     *
     * @param listener The item selected listener.
     * @return self
     */
    public T itemSelected(AdapterView.OnItemSelectedListener listener) {

        if (view instanceof AdapterView) {
            AdapterView<?> alv = (AdapterView<?>) view;
            alv.setOnItemSelectedListener(listener);
        }

        return self();

    }


    /**
     * Set selected item of an AdapterView.
     *
     * @param position The position of the item to be selected.
     * @return self
     */
    public T setSelection(int position) {

        if (view instanceof AdapterView) {
            AdapterView<?> alv = (AdapterView<?>) view;
            alv.setSelection(position);
        }

        return self();

    }

    /**
     * Clear a view. Applies to ImageView, WebView, and TextView.
     *
     * @return self
     */
    public T clear() {

        if (view != null) {

            if (view instanceof ImageView) {
                ImageView iv = ((ImageView) view);
                iv.setImageBitmap(null);
            } else if (view instanceof WebView) {
                WebView wv = ((WebView) view);
                wv.stopLoading();
                wv.clearView();
            } else if (view instanceof TextView) {
                TextView tv = ((TextView) view);
                tv.setText("");
            }


        }

        return self();
    }


    /**
     * Set the margin of a view. Notes all parameters are in DIP, not in pixel.
     *
     * @param leftDip   the left dip
     * @param topDip    the top dip
     * @param rightDip  the right dip
     * @param bottomDip the bottom dip
     * @return self
     */
    public T margin(float leftDip, float topDip, float rightDip, float bottomDip) {

        if (view != null) {

            ViewGroup.LayoutParams lp = view.getLayoutParams();

            if (lp instanceof ViewGroup.MarginLayoutParams) {

                int left = dip2pixel(leftDip);
                int top = dip2pixel(topDip);
                int right = dip2pixel(rightDip);
                int bottom = dip2pixel(bottomDip);

                ((ViewGroup.MarginLayoutParams) lp).setMargins(left, top, right, bottom);
                view.setLayoutParams(lp);
            }

        }

        return self();
    }

    /**
     * Set the width of a view in dip.
     * Can also be ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, or ViewGroup.LayoutParams.MATCH_PARENT.
     *
     * @param dip width in dip
     * @return self
     */

    public T width(int dip) {
        size(true, dip, true);
        return self();
    }

    /**
     * Set the height of a view in dip.
     * Can also be ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, or ViewGroup.LayoutParams.MATCH_PARENT.
     *
     * @param dip height in dip
     * @return self
     */

    public T height(int dip) {
        size(false, dip, true);
        return self();
    }

    /**
     * Set the width of a view in dip or pixel.
     * Can also be ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, or ViewGroup.LayoutParams.MATCH_PARENT.
     *
     * @param width width
     * @param dip   dip or pixel
     * @return self
     */

    public T width(int width, boolean dip) {
        size(true, width, dip);
        return self();
    }

    /**
     * Set the height of a view in dip or pixel.
     * Can also be ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, or ViewGroup.LayoutParams.MATCH_PARENT.
     *
     * @param height height
     * @param dip    dip or pixel
     * @return self
     */

    public T height(int height, boolean dip) {
        size(false, height, dip);
        return self();
    }

    private void size(boolean width, int n, boolean dip) {

        if (view != null) {

            ViewGroup.LayoutParams lp = view.getLayoutParams();

            if (n > 0 && dip) {
                n = dip2pixel(n);
            }

            if (width) {
                lp.width = n;
            } else {
                lp.height = n;
            }

            view.setLayoutParams(lp);

        }

    }


    /**
     * Starts an animation on the view.
     * <p/>
     * <br>
     * contributed by: marcosbeirigo
     *
     * @param animId Id of the desired animation.
     * @return self
     */
    public T animate(int animId) {
        return animate(animId, null);
    }

    /**
     * Starts an animation on the view.
     * <p/>
     * <br>
     * contributed by: marcosbeirigo
     *
     * @param animId   Id of the desired animation.
     * @param listener The listener to recieve notifications from the animation on its events.
     * @return self
     */
    public T animate(int animId, Animation.AnimationListener listener) {
        Animation anim = AnimationUtils.loadAnimation(context, animId);
        anim.setAnimationListener(listener);
        return animate(anim);
    }

    /**
     * Starts an animation on the view.
     * <p/>
     * <br>
     * contributed by: marcosbeirigo
     *
     * @param anim The desired animation.
     * @return self
     */
    public T animate(Animation anim) {
        if (view != null && anim != null) {
            view.startAnimation(anim);
        }
        return self();
    }

    /**
     * Trigger click event
     * <p/>
     * <br>
     * contributed by: neocoin
     *
     * @return self
     * @see View#performClick()
     */
    public T click() {
        if (view != null) {
            view.performClick();
        }
        return self();
    }

    /**
     * Trigger long click event
     * <p/>
     * <br>
     * contributed by: neocoin
     *
     * @return self
     * @see View#performClick()
     */
    public T longClick() {
        if (view != null) {
            view.performLongClick();
        }
        return self();
    }

    /**
     * Sets the right-hand compound drawable of the TextView to the specified icon and sets an error message
     * @param error
     * @return
     */
    public T error(CharSequence error) {
        if (view!=null && view instanceof TextView) {
            ((TextView) view).setError(error);
        }
        return self();
    }

    /**
     * Sets the right-hand compound drawable of the TextView to the specified icon and sets an error message
     * @param errorRes
     * @return
     */
    public T error(int errorRes) {
        if (view!=null && view instanceof TextView) {
            ((TextView) view).setError(context.getString(errorRes));
        }
        return self();
    }

    /**
     * Sets the right-hand compound drawable of the TextView to the specified icon and sets an error message
     * @param error
     * @param icon
     * @return
     */
    public T error(CharSequence error,Drawable icon) {
        if (view!=null && view instanceof TextView) {
            ((TextView) view).setError(error, icon);
        }
        return self();
    }

    /**
     * set html content to TextView
     * @param text
     * @return
     */
    public T html(final String text) {
        if (TextUtils.isEmpty(text)) {
            return text("");
        }
        if (text.contains("<") && text.contains(">")) {
            if (view instanceof TextView)
                ((TextView)view).setMovementMethod(LinkMovementMethod.getInstance());
            return text(Html.fromHtml(text));

        } else {
            return text(text);
        }
    }


    /**
     * Register listener on double-tap gesture for view
     *
     * @param listener
     * @return view
     */
    public T doubleTap(final GestureDetector.OnDoubleTapListener listener) {
        if (view != null) {
            final GestureDetector detector = new GestureDetector(view.getContext(),
                    new GestureDetector.SimpleOnGestureListener());
            detector.setOnDoubleTapListener(listener);
            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return detector.onTouchEvent(event);
                }
            });
        }
        return self();
    }


    /**
     * Increases the hit rect of a view. This should be used when an icon is small and cannot be easily tapped on.
     * Source: http://stackoverflow.com/a/1343796/5210
     *
     * @param amount The amount of dp's to be added to all four sides of the view hit purposes.
     */
    public T increaseHitRect(final int amount) {
        return increaseHitRect(amount, amount, amount, amount);
    }

    /**
     * Increases the hit rect of a view. This should be used when an icon is small and cannot be easily tapped on.
     * Source: http://stackoverflow.com/a/1343796/5210
     *
     * @param top    The amount of dp's to be added to the top for hit purposes.
     * @param left   The amount of dp's to be added to the left for hit purposes.
     * @param bottom The amount of dp's to be added to the bottom for hit purposes.
     * @param right  The amount of dp's to be added to the right for hit purposes.
     */
    public T increaseHitRect(final int top, final int left, final int bottom, final int right) {
        final View parent = (View) view.getParent();
        if (parent != null && view.getContext() != null) {
            parent.post(new Runnable() {
                // Post in the parent's message queue to make sure the parent
                // lays out its children before we call getHitRect()
                public void run() {
                    final Rect r = new Rect();
                    view.getHitRect(r);
                    r.top -= dip2pixel(top);
                    r.left -= dip2pixel(left);
                    r.bottom += dip2pixel(bottom);
                    r.right += dip2pixel(right);
                    parent.setTouchDelegate(new TouchDelegate(r, view));
                }
            });
        }
        return self();
    }

    private int dip2pixel(float n) {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, n, context.getResources().getDisplayMetrics());
        return value;
    }

    private static final Map<String, Typeface> TYPEFACES = new HashMap<String, Typeface>(
            4);

    /**
     * Set typeface with name on given text view
     *
     * @param name
     * @return view
     */
    public T typeface(final String name) {
        if (view != null)
            typeface(getTypeface(name, context));
        return self();
    }

    /**
     * Get typeface with name
     *
     * @param name
     * @param context
     * @return typeface, either cached or loaded from the assets
     */
    private static Typeface getTypeface(final String name, final Context context) {
        Typeface typeface = TYPEFACES.get(name);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), name);
            TYPEFACES.put(name, typeface);
        }
        return typeface;
    }

    /**
     * input event for editText
     *
     * @param runnable
     * @return
     */
    public T input(final BooleanRunnable runnable) {
        if (view != null && view instanceof EditText) {
            EditText editText = (EditText) view;
            if ((editText.getInputType() & TYPE_TEXT_FLAG_MULTI_LINE) == 0)
                editText.setOnKeyListener(new View.OnKeyListener() {

                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode != KEYCODE_ENTER)
                            return false;
                        if (event == null)
                            return false;
                        if (event.getAction() != ACTION_DOWN)
                            return false;

                        return runnable.run();
                    }
                });

            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == IME_ACTION_DONE)
                        return runnable.run();
                    else
                        return false;
                }
            });
        }
        return self();
    }

    /**
     * A {@link Runnable} that returns a boolean
     */
    public static interface BooleanRunnable {

        /**
         * Runnable that returns true when run, false when not run
         *
         * @return true if run, false otherwise
         */
        boolean run();
    }

    /**
     * Get Bitmap for current view
     * @return
     */
    public Bitmap getViewBitmap() {
        if (view!=null)
            return null;
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);

        return bitmap;
    }

    /**
     * apply fadein animation for view
     * @return
     */
    public T fadeIn() {
        if (view!=null) {
            view.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.fade_in));
        }
        return self();
    }

    /**
     * apply fadeout animation for view
     * @return
     */
    public T fadeOut() {
        if (view!=null) {
            view.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.fade_out));
        }
        return self();
    }

    /**
     * fadeIn/fadeOut the view
     * @param show
     * @return
     */
    public T fade(boolean show) {
        if (show)
            return fadeIn();
        else
            return fadeOut();
    }

    /**
     * Set url for WebView or create a url link for Textview
     * @param url
     * @return
     */
    public T url(String url) {
        if (view!=null) {
            if (view instanceof WebView) {
                ((WebView)view).loadUrl(url);
            }
            if (view instanceof TextView) {
                ((TextView)view).setAutoLinkMask(Linkify.ALL);
                text(url);
            }
        }
        return self();
    }


    public T smoothScrollTo(final int position) {
        if (view instanceof ListView) {
            final ListView listView = (ListView) view;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                listView.smoothScrollToPositionFromTop(position, 0);
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Mock touchEvent to stop listView Scrolling.
                        listView.onTouchEvent(MotionEvent.obtain(System.currentTimeMillis(),
                                System.currentTimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                    }
                }, 150 - 20);

                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.setSelectionFromTop(position, 0);
                    }
                }, 150);
            } else {
                listView.setSelectionFromTop(position, 0);
            }
        }
        return self();
    }

    /**
     * Click this view will trigger a CocoTask
     * @param task
     * @return
     */
    public T clicked(final CocoTask<?> task) {
        return clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.task(task);
            }
        });
    }


    public T scrolled(AbsListView.OnScrollListener listener) {
        if (view instanceof AbsListView) {
            ((AbsListView)view).setOnScrollListener(listener);
        }
        return self();
    }

    /**
     * Return current view
     * @param <E>
     * @return
     */
    public final <E extends View> E getView () {
        return (E) view;
    }
}
