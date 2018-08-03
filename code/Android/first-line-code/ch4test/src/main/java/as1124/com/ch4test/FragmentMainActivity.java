package as1124.com.ch4test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FragmentMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        findViewById(R.id.ch4_but_simple_fragment).setOnClickListener(v -> {
            Intent intent = new Intent(FragmentMainActivity.this, ActivitySimpleFragment.class);
            startActivity(intent);
        });
    }


}
