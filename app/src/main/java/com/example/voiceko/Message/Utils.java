package com.example.voiceko.Message;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {
    private Utils() {

    }

    /**
     * Gets timestamp in millis and converts it to HH:mm (e.g. 16:44).
     */
    public static String formatTime(long timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(timeInMillis);
    }
}
