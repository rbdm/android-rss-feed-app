package com.feedreader.myapplication.tools;

import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;

import com.feedreader.myapplication.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FunctionContainer {

    /**
     * Author: Mingzhen Ao
     * This function aims to create an button which has same information with the corresponding checkBox
     * return button
     * @param checkBox
     * @return button
     */
    public Button createButton(CheckBox checkBox) {
        CheckBox this_box = checkBox;
        Button button = new Button(new ContextThemeWrapper(getApplicationContext(),R.style.NewsSourceButton));
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setWidth(725);
        button.setText(this_box.getText());
        button.setTextColor(Color.BLACK);
        button.setY(this_box.getY());
        button.setTag(this_box.getTag());
        button.setBackgroundColor(Color.GRAY);
        return button;
    }

}
