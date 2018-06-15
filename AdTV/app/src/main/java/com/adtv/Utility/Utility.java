package com.adtv.Utility;

import android.content.Context;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.adtv.MyApplication;

/**
 * Created by trayambak on 9/6/18.
 */

public class Utility {

    public static String modifyDropboxUrl(String originalUrl) {
        String newUrl = originalUrl.replace("www.dropbox.", "dl.dropboxusercontent.");

        //just for sure for case if www is missing in url string
        newUrl = newUrl.replace("dropbox.", "dl.dropboxusercontent.");

        return newUrl;
    }

    /**
     * making the Toast in the application
     *
     * @param iMsg string which has to be toasted .
     */
    public static void showToast(String iMsg) {
        if (!TextUtils.isEmpty(iMsg)) {
            Toast toast = Toast.makeText(MyApplication.getMyApplicationContext(), iMsg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Utility to hide soft keypad
     */
    public static void hideSoftKeypad(EditText iEditField) {
        try {
            if (iEditField != null) {
                InputMethodManager imm = (InputMethodManager) MyApplication.getMyApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(iEditField.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
