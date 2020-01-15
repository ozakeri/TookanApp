package com.tokan.ir.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tokan.ir.R;
import com.tokan.ir.application.TokanApplication;
import com.tokan.ir.utils.MyPasswordTransformationMethod;
import com.tokan.ir.utils.TypefaceSpan;

public class BEditTextView extends RelativeLayout {
    private TextInputEditText editText;
    private ImageView line;
    private TextInputLayout textInputLayout;
    private Context context;

    public BEditTextView(Context context) {
        super(context);
        this.context = context;
    }

    public BEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public BEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View root = LayoutInflater.from(context).inflate(R.layout.widget_edittextview, this, true);
        textInputLayout = (TextInputLayout) root.findViewById(R.id.text_input);
        editText = (TextInputEditText) root.findViewById(R.id.edittext);
        if (!isInEditMode()) {
            editText.setTypeface(TokanApplication.getInstance().getNormalTypeFace());
            textInputLayout.setTypeface(TokanApplication.getInstance().getNormalTypeFace());
            textInputLayout.setBackground(getResources().getDrawable(R.drawable.border_edittext));
        }
        line = (ImageView) root.findViewById(R.id.line);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                focus();
            }
        });

    }

    public void setLines(int lines) {
        editText.setLines(lines);
    }

    public void focus() {
        editText.requestFocus();
        TokanApplication.getInstance().getInputMethodManager().toggleSoftInput(InputMethodManager.SHOW_FORCED, editText.length());
    }

    @SuppressLint("ResourceType")
    public void setHint(String text) {
        textInputLayout.setHint(text);
    }

    public void setInputType(int inputType) {
        editText.setInputType(inputType);
    }

    /*public void setMax(int max) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(max);
        editText.setFilters(FilterArray);
    }*/

    public void setPassword() {
        editText.setTransformationMethod(MyPasswordTransformationMethod.getInstance());
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setText(final String s) {
        editText.setText(s);
    }

    public void setError(String s) {
        SpannableString title = new SpannableString(Html.fromHtml("<font color='red'>" + s + "</font>"));
        title.setSpan(new TypefaceSpan(), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setError(title);
        editText.requestFocus();
    }

    public void disableEdit() {
        editText.setFocusable(false);
        editText.setClickable(true);
    }

    public void removeHintTypeface() {
        if (!isInEditMode())
            textInputLayout.setTypeface(null);
    }

    public void removeTextTypeface() {
        if (!isInEditMode())
            editText.setTypeface(null);
    }

    public void setSelection(int selection) {
        editText.setSelection(selection);
    }

    public void setGravity() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editText.setGravity(Gravity.RIGHT);
                textInputLayout.setGravity(Gravity.RIGHT);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.getText().toString().length() == 0) {
                    editText.setGravity(Gravity.RIGHT);
                } else {
                    editText.setGravity(Gravity.NO_GRAVITY);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
