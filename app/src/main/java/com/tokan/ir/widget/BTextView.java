package com.tokan.ir.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class BTextView extends AppCompatTextView {

    public BTextView(Context context) {
        super(context);
        init();
    }

    public BTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void removeTypeface() {
        if (!isInEditMode())
            setTypeface(null);
    }
}
