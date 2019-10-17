package com.feedreader.myapplication;

import android.widget.Button;
import android.widget.CheckBox;

import com.feedreader.myapplication.tools.FunctionContainer;

import org.junit.Test;

import static com.facebook.FacebookSdk.getApplicationContext;
import static org.junit.Assert.assertTrue;
/*
 * Author: Mingzhen Ao
 * This class aims to test an button information if it corresponding to checkbox;
 */
public class ButtonCreateTest {
    @Test
    public void testCreateButton() {
        CheckBox checkBox=new CheckBox(getApplicationContext());
        FunctionContainer a=new FunctionContainer();
        Button testbutton=a.createButton(checkBox);
        assertTrue(testbutton.getText().toString()=="");
        assertTrue(testbutton.getTag()==null);
        checkBox.setTag("123");
        checkBox.setText("456");
        testbutton=a.createButton(checkBox);
        assertTrue(testbutton.getTag().toString()=="123");
        assertTrue(testbutton.getText().toString()=="456");
    }
}
