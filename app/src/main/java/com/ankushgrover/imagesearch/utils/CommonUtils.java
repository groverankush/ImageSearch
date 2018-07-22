package com.ankushgrover.imagesearch.utils;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 22/7/18.
 */
public class CommonUtils {

    public static void setText(Activity activity, @IdRes int viewId, String text) {
        ((TextView) activity.findViewById(viewId)).setText(text);
    }

    /**
     * set text on a text view
     *
     * @param viewId
     * @param text
     * @return
     */
    public static void setTextForView(View view, int viewId, String text, boolean goneIfEmpty) {
        TextView tv = (TextView) view.findViewById(viewId);
        if (isEmpty(text)) {
            if (goneIfEmpty)
                tv.setVisibility(View.GONE);
        } else
            tv.setText(text);
    }

    public static boolean isEmpty(String s) {

        return android.text.TextUtils.isEmpty(s);
    }

}
