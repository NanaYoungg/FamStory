package com.hongsam.famstrory.animation;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.65f;
    private static final float MIN_AlPHA = 0.5f;
    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();
        if(position < -1){
            page.setAlpha(0f);
        }else if (position <= 1){
            float scaleFactor = Math.max(MIN_SCALE,1-Math.abs(position));
            float verMargin = pageWidth * (1 - scaleFactor)/2;
            float horMargin = pageHeight * (1 - scaleFactor)/2;
            if (position < 0){
                page.setTranslationX(horMargin-verMargin/2);
                page.setTranslationY(-horMargin+verMargin/2);
            }
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            page.setAlpha(
                    MIN_AlPHA+ (scaleFactor - MIN_SCALE) /
                            (1-MIN_SCALE) * (1-MIN_SCALE));
        }
        else {
            page.setAlpha(0f);
        }
    }
}
