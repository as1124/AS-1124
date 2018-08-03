package as1124.com.ch4test;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RightFragment extends Fragment {

    static final String LOG_TAG = "[RightFragment]";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "Fragment onCreate");
    }

    /*******************************这些方法是Fragment生命周期中特有的，和Activity不同********************************************/
    @Override
    public void onAttach(Context context) {
        // onAttach -> onCreate -> onCreateView -> onActivityCreated -> onStart -> onResume
        // onPause -> onSaveInstanceState -> onStop -> onDestroyView -> onDestroy -> onDetach
        super.onAttach(context);
        Log.i(LOG_TAG, "Fragment onAttach");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "Fragment onCreateView");
        View rightView = inflater.inflate(R.layout.right_fragment, container, false);
        return rightView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(LOG_TAG, "Fragment onActivityCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(LOG_TAG, "Fragment onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(LOG_TAG, "Fragment onDetach");
    }

    /*********************************以上是Fragment生命周期中特有的回调方法****************************************************/

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "Fragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "Fragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "Fragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "Fragment onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "Fragment onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(LOG_TAG, "Fragment onSaveInstanceState");
    }
}
