package com.clevertap.wuunder_android_test.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by palashjain on 11/09/18.
 */

public class IconView extends TextView {


    public IconView(Context context) {
        super(context);
    }

    public IconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "icon_fonts/fontawesome-webfont.ttf"));

    }

}
