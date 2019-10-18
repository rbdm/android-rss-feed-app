package com.feedreader.myapplication.tools;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;

import com.feedreader.myapplication.R;

import org.joda.time.DateTime;

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

    /**
     * Author: Mirhady Dorodjatun
     * This class aims to create fragment DatePickerFragment while using interface DatePickerDialog.OnDateSetListener
     * in order to listens to the date input event by user.
     * onDateSet is not defined here as we need to put it in UI thread
     * @param
     */
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        public DatePickerDialog.OnDateSetListener dateSetListener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Set default date using joda time
            final DateTime now = new DateTime();
            int year = now.getYear();
            int month = now.getMonthOfYear();
            int day = now.getDayOfMonth();
            // Somehow there is a discrepancy of 1 month between Joda library's DateTime class and Java.util.Date
            return new DatePickerDialog(getActivity(), dateSetListener, year, month-1, day);
        }

        public void onDateSet(DatePicker dp, int year, int month, int day) {
            // Must define inside UI Activity, because this class is static
        }
    }
}
