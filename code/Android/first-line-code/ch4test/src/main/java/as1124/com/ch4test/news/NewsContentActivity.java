package as1124.com.ch4test.news;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import as1124.com.ch4test.R;

public class NewsContentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        Intent intent = getIntent();
        if (intent != null) {
            String newsTitle = intent.getStringExtra("news_title");
            String newsContent = intent.getStringExtra("news_content");
            Fragment fragment = getFragmentManager().findFragmentById(R.id.news_content_fragment);
            ((NewsContentFragment) fragment).refresh(new NewsModel(newsTitle, newsContent));
        }
    }

    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }
}
