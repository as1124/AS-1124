package as1124.com.helloworld;

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
        findViewById(R.id.button_finish_self).setOnClickListener(v -> {
            FirstActivity.this.finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (inflater != null) {
            inflater.inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case R.id.add_item: {
                Toast.makeText(this, "U click add", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.remove_item: {
                Toast.makeText(this, "U click remove", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
        }
        return true;
    }
}
