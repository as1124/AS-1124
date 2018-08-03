package as1124.com.helloworld.lifecycle;

import android.app.Activity;
import android.os.Bundle;

import as1124.com.helloworld.R;

public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }
}
