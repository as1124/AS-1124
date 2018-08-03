package com.as1124.ch7test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends Activity {

    private static final int READ_CONTACTS = 0x1;

    protected ListView contactsList;

    private ArrayAdapter<String> listAdapter;

    protected List<String> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contactsList = findViewById(R.id.ch7_contacts_list);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        contactsList.setAdapter(listAdapter);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS);
                return;
            }
        }

        loadContacts();
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (READ_CONTACTS == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts();
            } else {
                Toast.makeText(this, "拒绝了读取联系人的权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 读取手机联系人信息
     */
    private void loadContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver()
                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String telNO = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contacts.add(displayName.concat("\r\n").concat(telNO));
                }
                listAdapter.notifyDataSetChanged();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
} // class-end
