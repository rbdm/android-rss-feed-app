package com.feedreader.myapplication.tools;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;

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
        Button button = new Button(getApplicationContext());
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setText(this_box.getText());
        button.setTextColor(Color.WHITE);
        button.setY(this_box.getY());
        button.setTag(this_box.getTag());
        return button;
    }

}
