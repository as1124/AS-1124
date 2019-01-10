package com.as1124.googledoc.connectivity.wifi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServerAsyncTask extends AsyncTask {

    private String packageName;
    private TextView mStatusText;

    public FileServerAsyncTask(Context context, TextView statusText) {
        this.packageName = context.getPackageName();
        this.mStatusText = statusText;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        ServerSocket serverSocket = null;
        try {
            // Create a ServerSocket and wait for client connections. This call blocks until a connection is
            // accepted from a client.
            serverSocket = new ServerSocket(8888);
            Socket client = serverSocket.accept();

            final File file = new File(Environment.getExternalStorageDirectory() + "/"
                    + packageName + "/" + System.currentTimeMillis() + ".jpg");
            File dirs = file.getParentFile();
            if (!dirs.exists()) {
                dirs.mkdirs();
            }

            IOUtils.copy(client.getInputStream(), new FileOutputStream(file));
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object file) {
        if (file != null) {
            mStatusText.setText("File copied: " + ((File) file).getAbsolutePath());
        }
    }

}
