package workshop1024.com.xproject.utils;

import android.view.View;

public class ViewUtils {

    public static int getRelativeLeft(View view) {
        if (view.getParent() == view.getRootView()) {
            return view.getLeft();
        } else {
            return view.getLeft() + getRelativeLeft((View) view.getParent());
        }
    }

    public static int getRelativeTop(View view) {
        if (view.getParent() == view.getRootView()) {
            return view.getTop();
        } else {
            return view.getTop() + getRelativeTop((View) view.getParent());
        }
    }

}
