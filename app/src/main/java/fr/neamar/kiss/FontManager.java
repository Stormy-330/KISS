package fr.neamar.kiss;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FontManager {
    private static final String FONT_FILE_NAME = "custom_font.ttf";
    private static Typeface cachedTypeface = null;

    public static File getFontFile(Context context) {
        return new File(context.getFilesDir(), FONT_FILE_NAME);
    }

    public static boolean saveFontFromUri(Context context, Uri uri) {
        try (InputStream in = context.getContentResolver().openInputStream(uri);
             FileOutputStream out = new FileOutputStream(getFontFile(context))) {
            if (in == null) return false;
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            cachedTypeface = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void resetFont(Context context) {
        File file = getFontFile(context);
        if (file.exists()) {
            file.delete();
        }
        cachedTypeface = null;
    }

    public static Typeface getCustomTypeface(Context context) {
        if (cachedTypeface != null) {
            return cachedTypeface;
        }
        File file = getFontFile(context);
        if (file.exists()) {
            try {
                cachedTypeface = Typeface.createFromFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cachedTypeface;
    }
}