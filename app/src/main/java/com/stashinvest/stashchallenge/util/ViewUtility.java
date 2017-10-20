package com.stashinvest.stashchallenge.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewUtility {

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
