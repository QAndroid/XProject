package workshop1024.com.xproject.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import workshop1024.com.xproject.R;

/**
 * 保存Fragment
 */
public class SavedFragment extends TopFragment {
    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance() {
        SavedFragment fragment = new SavedFragment();
        fragment.setNavigationItemId(R.id.leftnavigator_menu_saved);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.saved_fragment, container, false);
    }
}
