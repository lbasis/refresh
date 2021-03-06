package com.bcq.refresh.xrecycle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bcq.refresh.R;
import com.bcq.refresh.RefreshHelper;
import com.bcq.refresh.progress.IndicatorView;
import com.bcq.refresh.progress.Style;

public class LoadingMoreFooter extends LinearLayout {

    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    private TextView mText;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;

    private IndicatorView progressView;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void destroy() {
        progressCon = null;
        if (progressView != null) {
            progressView.destroy();
            progressView = null;
        }
    }

    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
    }

    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }

    public void initView() {
        int margin = getResources().getDimensionPixelSize(R.dimen.re_margin);
        setGravity(Gravity.CENTER);
        setPadding(0, margin, 0, margin);
        setBackgroundColor(getResources().getColor(R.color.re_color_bg));
        setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        progressCon = new SimpleViewSwitcher(getContext());
        progressCon.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        createIndicator();
        progressCon.setView(progressView);

        addView(progressCon);
        mText = new TextView(getContext());
        mText.setText(getContext().getString(R.string.re_loading));
        mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.re_text_size));
        mText.setTextColor(getResources().getColor(R.color.re_color_default));
        if (loadingHint == null || loadingHint.equals("")) {
            loadingHint = (String) getContext().getText(R.string.re_loading);
        }
        if (noMoreHint == null || noMoreHint.equals("")) {
            noMoreHint = (String) getContext().getText(R.string.re_nomore);
        }
        if (loadingDoneHint == null || loadingDoneHint.equals("")) {
            loadingDoneHint = (String) getContext().getText(R.string.re_load_done);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(margin, 0, 0, 0);
        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    public void setProgressStyle(Style style) {
        if (style == Style.Sys) {
            progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        } else {
            createIndicator();
            progressView.setStyle(style);
            progressCon.setView(progressView);
        }
    }

    private void createIndicator() {
        if (progressView == null) {
            progressView = (IndicatorView) LayoutInflater.from(getContext()).inflate(R.layout.re_default_indicator, null, false);
            progressView.setStyle(RefreshHelper.getStyle());
        }
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                mText.setText(loadingHint);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mText.setText(loadingDoneHint);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText(noMoreHint);
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }
}
