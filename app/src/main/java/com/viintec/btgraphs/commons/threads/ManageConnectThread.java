package com.viintec.btgraphs.commons.threads;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.viintec.btgraphs.activities.FinderActivity;
import com.viintec.btgraphs.commons.BaseActivity;
import com.viintec.btgraphs.domain.DataGraph;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by adrianaldairleyvasanchez on 12/2/17.
 */

public class ManageConnectThread extends Thread{
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private FinderActivity mActivity;

    private ArrayList<DataGraph> mmDataList;

    public ManageConnectThread(BluetoothSocket socket, ArrayList<DataGraph> dataList, FinderActivity activity) {
        mActivity = activity;
        mmSocket = socket;
        mmDataList = dataList;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

     @Override
    public void run() {
        Log.e(BaseActivity.TAG, "IN ManageConnectThread");
        boolean flag = false;
        String valueX = "0";
        int valueY = 1;
        byte[] buffer = new byte[256];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                Log.e(BaseActivity.TAG, "INSIDE");
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                Log.e(BaseActivity.TAG, "DATA SIZE: " + bytes);

                if(!flag){
                    flag = true;
                    valueX = convertBytesToString(buffer, 1, bytes);
                }
                else{
                    flag = false;
                    valueX += convertBytesToString(buffer, 0, bytes);
                    mmDataList.add(new DataGraph(valueX, String.valueOf(valueY)));
                    valueY++;
                    Log.e(BaseActivity.TAG, "INCOMING DATA: " + valueX);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivity.updateChart();
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

         try {
             mmSocket.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
            Log.e(BaseActivity.TAG, "It's writing...");
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

    private String convertBytesToString(byte[] bytes, int status, int size){
        String encoded = "";
        encoded = new String(bytes, 0, size);
        int coded = Integer.valueOf(encoded);

        if(status == 1){
            coded -= 48;
            coded *= 10;
        }
        else{
            coded -= 48;
        }
        return encoded;
    }
}
