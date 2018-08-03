package as1124.com.ch4test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 如果Activity没有继承自兼容（support库）的Activity，那么响应的Fragment就不能使用support库中的类，否则类型
 * 不匹配就会闪退
 *
 * @author as-1124 (mailto:as1124huang@gmail.com)
 */
public class LeftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View leftView = inflater.inflate(R.layout.left_fragment, container, false);
        return leftView;
    }

}
