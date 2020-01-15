package com.tokan.ir.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import com.tokan.ir.application.TokanApplication;

public class TypefaceSpan extends MetricAffectingSpan {

    private Typeface mTypeface;

    public TypefaceSpan() {

        if (mTypeface == null) {
            mTypeface = TokanApplication.getInstance().getNormalTypeFace();
        }
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        p.setTypeface(mTypeface);
        p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setTypeface(mTypeface);
        tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }
}