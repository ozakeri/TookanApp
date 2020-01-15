package com.tokan.ir.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.inputmethod.InputMethodManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

public class TokanApplication extends Application {

    private static TokanApplication instance;
    private Typeface typeFaceNormal = null;
    private Typeface typeFaceBold = null;
    private int back_count = 0;
    private InputMethodManager inputMethodManager;

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            try {
                StringWriter errors = new StringWriter();
                throwable.printStackTrace(new PrintWriter(errors));
                String error = errors.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            uncaughtExceptionHandler.uncaughtException(thread, throwable);
        }
    };

    @Override
    public void onCreate() {
        Locale.setDefault(new Locale("fa", "IR"));
        super.onCreate();
        instance = this;

        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);

        inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
    }

    public static synchronized TokanApplication getInstance(){
        return instance;
    }
    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public InputMethodManager getInputMethodManager() {
        return inputMethodManager;
    }

    public Typeface getNormalTypeFace() {
        if (typeFaceNormal == null) {
            typeFaceNormal = Typeface.createFromAsset(getContext().getAssets(), "normal.ttf");
        }

        return typeFaceNormal;
    }

    public Typeface getBoldTypeFace() {
        if (typeFaceBold == null) {
            typeFaceBold = Typeface.createFromAsset(getContext().getAssets(), "bold.ttf");
        }

        return typeFaceBold;
    }

    public void resetBackCount()
    {
        back_count = 0;
    }

    public void increaseBackCount()
    {
        back_count++;
    }

    public int getBackCount() {
        return back_count;
    }
}
