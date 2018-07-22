package com.ankushgrover.imagesearch.architecture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 11/6/18.
 */
abstract public class BaseActivity extends AppCompatActivity {

    private Toast toast;

    public void initializeActionBar(String title, boolean isBackEnabled) {
        if (getSupportActionBar() != null) {
            setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isBackEnabled);
        }
    }

    public void initializeActionBar(int titleId) {
        initializeActionBar(getString(titleId), false);
    }

    public void initializeActionBar(int titleId, boolean isBackEnabled) {
        initializeActionBar(getString(titleId), isBackEnabled);
    }


    /**
     * Method used to switch from current activity to other
     *
     * @param destinationActivity activity to open
     */
    public void switchActivity(Class<?> destinationActivity) {
        startActivity(new Intent(this, destinationActivity));
    }

    /**
     * Method used to switch from current activity to other with data
     *
     * @param destinationActivity activity to open
     * @param bundle              data that carry to destination activity
     */
    public void switchActivity(Class<?> destinationActivity, Bundle bundle) {
        Intent intent = new Intent(this, destinationActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * method used to starting another activity for result
     *
     * @param destinationActivity activity to open
     * @param bundle              data that carry to destination activity
     * @param requestCode         result code
     */
    public void switchActivity(Class<?> destinationActivity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, destinationActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }


    /**
     * method used to starting another activity for result
     *
     * @param destinationActivity activity to open
     * @param requestCode         result code
     */
    public void switchActivity(Class<?> destinationActivity, int requestCode) {
        Intent intent = new Intent(this, destinationActivity);
        startActivityForResult(intent, requestCode);
    }


    /**
     * Method used to display short duration toast
     *
     * @param message message to be displayed
     */
    public void displayToast(String message) {
        if (TextUtils.isEmpty(message) || message.equalsIgnoreCase("null"))
            return;
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }


    /**
     * Method used to display short duration toast
     *
     * @param resId resource id of the message string to be displayed
     */
    public void displayToast(int resId) {
        displayToast(getString(resId));
    }
}
