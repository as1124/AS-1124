package com.as1124.architecture.model;

import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

public class User {

    private String firstName;
    private String lastName;
    public final ObservableField<String> addr = new ObservableField<>();

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void testMethodReference(View v) {
        // In method references, the parameters of the method must match the parameters of the event listener.
        Toast.makeText(v.getContext(), "DataBinding: 事件绑定--> [Method References] 方式", Toast.LENGTH_SHORT).show();
    }

    public void testListenerBinding(View v, String str) {
        // In listener bindings, only your return value must match the expected return value of the listener
        Toast.makeText(v.getContext(), "DataBinding: 事件绑定--> [Listener Bindings] 方式, 收到参数==" + str,
                Toast.LENGTH_SHORT).show();
    }
}
