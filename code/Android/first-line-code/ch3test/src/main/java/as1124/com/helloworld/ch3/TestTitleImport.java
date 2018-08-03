package as1124.com.helloworld.ch3;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import as1124.com.helloworld.R;

public class TestTitleImport extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_title_import);

        ActionBar actionBar = this.getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
