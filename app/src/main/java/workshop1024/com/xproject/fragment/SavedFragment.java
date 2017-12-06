package workshop1024.com.xproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import workshop1024.com.xproject.R;

/**
 * 保存Fragment
 */
public class SavedFragment extends Fragment {
    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance() {
        SavedFragment fragment = new SavedFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }
}
