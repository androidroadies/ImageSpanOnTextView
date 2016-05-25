package com.example.multidots.scrollingtextview.scrolling_textview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by multidots on 25-May-16.
 */
public class ScrollTextView extends TextView {

    // scrolling feature
    public Scroller mSlr;
    int scrollingLen;
//    Display mDisplay = getContext().getWindowManager().getDefaultDisplay();
//    final int width  = mDisplay.getWidth();
//    final int height = mDisplay.getHeight();

    DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
    int width = displayMetrics.widthPixels;
    int height = displayMetrics.heightPixels;

    // milliseconds for a round of scrolling
    private int mRndDuration = 5000;

    // the X offset when paused
    private int mXPaused = 0;

    // whether it's being paused
    private boolean mPaused = true;

    SharedPreferences shre1 = PreferenceManager.getDefaultSharedPreferences(getContext());

    String previouslyEncodedImagep1 = shre1.getString("image_datap1", "");
    String previouslyEncodedImagep2 = shre1.getString("image_datap2", "");
    String previouslyEncodedImagep3 = shre1.getString("image_datap3", "");
    String previouslyEncodedImagep4 = shre1.getString("image_datap4", "");

    /*
    * constructor
    */
    public ScrollTextView(Context context) {
        this(context, null);
        // customize the TextView
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }

    /*
    * constructor
    */
    public ScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
        // customize the TextView
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }

    /*
    * constructor
    */
    public ScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // customize the TextView
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }

    /**
     * begin to scroll the text from the original position
     */
    public void startScroll() {
        // begin from the very right side
        mXPaused = -width;

        // assume it's paused
        mPaused = true;
        System.out.println("111 start :" + width);
        resumeScroll();
    }

    /**
     * resume the scroll from the pausing point
     */
    public void resumeScroll() {

        if (!mPaused)
            return;

        System.out.println("111 resume");

        // Do not know why it would not scroll sometimes
        // if setHorizontallyScrolling is called in constructor.
        setHorizontallyScrolling(true);

        // use LinearInterpolator for steady scrolling
        mSlr = new Scroller(this.getContext(), new LinearInterpolator());
        setScroller(mSlr);

        scrollingLen = calculateScrollingLen();
        System.out.println("111 resume new" + getWidth() + "paused" + mXPaused);
        int distance = scrollingLen - (getWidth() + mXPaused);
        int duration = (new Double(mRndDuration * distance * 1.00000
                / scrollingLen)).intValue();
        System.out.println("111 resume 11 : " + mXPaused + " distance" + distance + "duration :" + duration + "scrolling length :" + scrollingLen);
        setVisibility(VISIBLE);
//        mSlr.startScroll(mXPaused, 0, distance, 0, duration);//Actual remove comment
        mSlr.startScroll(mXPaused, 0, distance, 0, 4000);
        invalidate();
        mPaused = false;
    }

    /**
     * calculate the scrolling length of the text in pixel
     *
     * @return the scrolling length in pixels
     */
    private int calculateScrollingLen() {


        TextPaint tp = getPaint();
        Rect rect = new Rect();
        String strTxt = getText().toString();
        tp.getTextBounds(strTxt, 0, strTxt.length(), rect);
        int scrollingLen = rect.width() + getWidth();
        rect = null;
        System.out.println("111 calculate :" + scrollingLen);
        return scrollingLen;
    }

    /**
     * pause scrolling the text
     */
    public void pauseScroll() {

        System.out.println("111 pause");
        if (null == mSlr)
            return;

        if (mPaused)
            return;

        mPaused = true;

        // abortAnimation sets the current X to be the final X,
        // and sets isFinished to be true
        // so current position shall be saved
        mXPaused = mSlr.getCurrX();

        mSlr.abortAnimation();
    }

    @Override
     /*
     * override the computeScroll to restart scrolling when finished so as that
     * the text is scrolled forever
     */
    public void computeScroll() {
        super.computeScroll();

        System.out.println("111 compute" + mSlr.getCurrX());
        if (mSlr.getCurrX()==0){
            System.out.println("----------------------");
        }
        if (mSlr.getCurrX() == scrollingLen) {
            //pauseScroll(); // Not required as of now it puase automatically.
            this.startScroll();
        }

        if (null == mSlr) return;

        if (mSlr.isFinished() && (!mPaused)) {
        }
    }

    public int getRndDuration() {
        return mRndDuration;
    }

    public void setRndDuration(int duration) {
        this.mRndDuration = duration;
    }

    public boolean isPaused() {
        return mPaused;
    }
}
