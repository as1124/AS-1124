package as1124.com.ch4test.news;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import as1124.com.ch4test.R;

public class NewsTitleFragment extends Fragment {

    private boolean isTwoPanel = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.news_title_fragment, container, false);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (getActivity().findViewById(R.id.news_content_layout) != null) {
//            isTwoPanel = true;
//        } else {
//            isTwoPanel = false;
//        }
    }
}
