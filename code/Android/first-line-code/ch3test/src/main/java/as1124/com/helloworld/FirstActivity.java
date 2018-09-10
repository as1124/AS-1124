package as1124.com.helloworld;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ActionBar bar = getActionBar();
        if (bar == null) {
            android.util.Log.i(getClass().getSimpleName(), "ActionBar is null");
        }
        findViewById(R.id.button_finish_self).setOnClickListener(v -> {
            FirstActivity.this.finish();
        });

        String action = getIntent().getAction();
        android.util.Log.i(getClass().getSimpleName(), "启动action==" + action);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case R.id.add_item:
                Toast.makeText(this, "U click add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "U click remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
