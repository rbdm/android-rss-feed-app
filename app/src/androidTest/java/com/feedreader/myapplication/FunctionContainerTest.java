package com.feedreader.myapplication;

import android.graphics.Color;
import android.widget.Button;
import android.widget.CheckBox;

import org.junit.Test;

import com.feedreader.myapplication.AddSitesShowActivity;
import com.feedreader.myapplication.tools.FunctionContainer;

import static com.facebook.FacebookSdk.getApplicationContext;
import static org.junit.Assert.*;

public class FunctionContainerTest {
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
