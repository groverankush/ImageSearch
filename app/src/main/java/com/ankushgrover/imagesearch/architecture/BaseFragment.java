package com.ankushgrover.imagesearch.architecture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class BaseFragment extends Fragment {

    private Toast toast;

    /**
     * method used to find view of the fragment
     *
     * @param id resource id of the view
     * @return finded view
     */
    public View findView(int id) {
        return getView().findViewById(id);
    }

    /**
     * method sued to call switchActivity method of BaseActivity
     *
     * @param destinationActivity activity to open
     */
    public void switchActivity(Class<?> destinationActivity) {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).switchActivity(destinationActivity);
    }

    /**
     * method sued to call switchActivity method of BaseActivity
     *
     * @param destinationActivity activity to open
     * @param bundle              data to be sent to destination activity
     */
    public void switchActivity(Class<?> destinationActivity, Bundle bundle) {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).switchActivity(destinationActivity, bundle);
    }

    /**
     * method sued to call switchActivity method of BaseActivity
     *
     * @param destinationActivity activity to open
     * @param bundle              data that carry to destination activity
     * @param requestCode         result code
     */
    public void switchActivity(Class<?> destinationActivity, Bundle bundle, int requestCode) {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).switchActivity(destinationActivity, bundle, requestCode);
    }

    /**
     * method sued to call switchActivity method of BaseActivity
     *
     * @param destinationActivity activity to open
     * @param requestCode         result code
     */
    public void switchActivity(Class<?> destinationActivity, int requestCode) {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).switchActivity(destinationActivity, requestCode);
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
        toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
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
