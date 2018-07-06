package com.fivefivelike.mybaselibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fivefivelike.mybaselibrary.R;


/**
 * Toast
 */
public class ToastUtil {
    @ColorInt
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    @ColorInt
    private static final int ERROR_COLOR = Color.parseColor("#FD4C5B");

    @ColorInt
    private static final int INFO_COLOR = Color.parseColor("#3F51B5");

    @ColorInt
    private static final int SUCCESS_COLOR = Color.parseColor("#388E3C");

    @ColorInt
    private static final int WARNING_COLOR = Color.parseColor("#FFA900");
    @ColorInt
    private static final int NOMAR_COLOR = Color.parseColor("#80000000");

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";
    private static Toast toast;

    private static Context getContext() {
        return GlobleContext.getInstance().getApplicationContext();
    }

    public static void show(@NonNull String message) {
        custom(getContext(), message, null, DEFAULT_TEXT_COLOR, NOMAR_COLOR, Toast.LENGTH_SHORT, false, true).show();
    }

    public static void warning(@NonNull String message) {
        getCustome(message, R.drawable.ic_error_outline_white_48dp, WARNING_COLOR).show();
    }

    public static void info(@NonNull String message) {
        getCustome(message, R.drawable.ic_info_outline_white_48dp, INFO_COLOR).show();
    }


    public static void success(@NonNull String message) {
        getCustome(message, R.drawable.ic_check_white_48dp, SUCCESS_COLOR).show();
    }

    public static void error(@NonNull String message) {
        getCustome(message, R.drawable.ic_clear_white_48dp, ERROR_COLOR).show();
    }

    @CheckResult
    private static Toast getCustome(@NonNull String message, int drawableRes, int bgColor) {
        Context context = getContext();
        return custom(context, message, getDrawable(context, drawableRes), DEFAULT_TEXT_COLOR, bgColor, Toast.LENGTH_SHORT, true, true);
    }


    @CheckResult
    public static Toast custom(@NonNull Context context,
                               @NonNull String message,
                               Drawable icon,
                               @ColorInt int textColor,
                               @ColorInt int tintColor,
                               int duration,
                               boolean withIcon,
                               boolean shouldTint) {
        if (toast == null) {
            toast = new Toast(context);
        }
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_toast, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint) {
            drawableFrame = tint9PatchDrawableFrame(context, tintColor);
        } else {
            drawableFrame = getDrawable(context, R.drawable.toast_frame);
        }
        setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            setBackground(toastIcon, icon);
        } else
            toastIcon.setVisibility(View.GONE);

        toastTextView.setTextColor(textColor);
        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        toast.setView(toastLayout);
        toast.setDuration(duration);
        return toast;
    }


    public static final Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, R.drawable.toast_frame);
        toastDrawable.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        return toastDrawable;
    }

    public static final void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else
            view.setBackgroundDrawable(drawable);
    }

    public static final Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(id);
        else
            return context.getResources().getDrawable(id);
    }

}
