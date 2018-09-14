package com.clevertap.wuunder_android_test.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by palashjain on 11/09/18.
 */

public class AppTextViewBold extends TextView {


    public AppTextViewBold(Context context) {
        super(context);
    }

    public AppTextViewBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));

    }

}
