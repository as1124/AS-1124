package as1124.com.ch4test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class ActivitySimpleFragment extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_simple_fragment);

        this.findViewById(R.id.ch4_but_switch_fragment).setOnClickListener(this);
        replaceFragment(new RightFragment());
    }

    private void replaceFragment(Fragment fragmentToShow) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        String fragmentTag = fragmentToShow.getClass().getSimpleName();
        ft.replace(R.id.right_fragment_container, fragmentToShow, fragmentTag);
        // 将Fragment添加到返回栈中，这样按下系统返回键就可以实现Fragment返回的效果
        ft.addToBackStack(fragmentTag);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        int viewid = v.getId();
        switch (viewid) {
            case R.id.ch4_but_switch_fragment:
                replaceFragment(new AnotherRightFragment());
                break;
            default:
                break;
        }
    }
}
