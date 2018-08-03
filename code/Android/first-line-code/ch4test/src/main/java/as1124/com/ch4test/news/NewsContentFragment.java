package as1124.com.ch4test.news;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import as1124.com.ch4test.R;

public class NewsContentFragment extends Fragment {

    private View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.news_content_fragment, container, false);
        return this.fragmentView;
    }

    public void refresh(NewsModel news) {
        TextView titleView = fragmentView.findViewById(R.id.news_title);
        titleView.setText(news.getTitle());
        TextView contentView = fragmentView.findViewById(R.id.news_content);
        contentView.setText(news.getContent());

        fragmentView.findViewById(R.id.visibility_layout).setVisibility(View.VISIBLE);

    }
}
