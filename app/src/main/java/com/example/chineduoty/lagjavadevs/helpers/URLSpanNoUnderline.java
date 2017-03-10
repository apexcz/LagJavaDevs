package com.example.chineduoty.lagjavadevs.helpers;

import android.annotation.SuppressLint;
import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by chineduoty on 3/8/17.
 */

@SuppressLint("ParcelCreator")
public class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String url) {
        super(url);
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }
}
