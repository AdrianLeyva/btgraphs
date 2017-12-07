package com.viintec.btgraphs.commons.threads;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.viintec.btgraphs.activities.FinderActivity;
import com.viintec.btgraphs.commons.BTManager;
import com.viintec.btgraphs.commons.BaseActivity;
import com.viintec.btgraphs.domain.BTDevice;
import com.viintec.btgraphs.domain.DataGraph;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by adrianaldairleyvasanchez on 12/2/17.
 */

public class ConnectThread extends Thread {

    private  BluetoothSocket mmSocket;
    private final BTDevice mmDevice;
    private BluetoothAdapter mmAdapter;
    private ArrayList<DataGraph> mmDataList;
    private FinderActivity mActivity;

    public ConnectThread(BTDevice device, BluetoothAdapter adapter, UUID uuid,
                         ArrayList<DataGraph> dataList, FinderActivity activity) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        mmAdapter = adapter;
        mmDataList = dataList;
        mActivity = activity;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
       /* try {
            // MY_UUID is the app's UUID string, also used by the server code
            try{
                if(Build.VERSION.SDK_INT >= 17){
                    Class<?> clazz = mmDevice.getOriginalDevice().getClass();
                    Class<?>[] paramTypes = new Class<?>[] {Integer.TYPE};
                    Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                    Object[] params = new Object[] {Integer.valueOf(1)};

                    tmp = (BluetoothSocket) m.invoke(mmDevice.getOriginalDevice(), params);
                    if(mmSocket != null)
                        Log.e(BaseActivity.TAG, "IS NOT NULL");
                }
                else{
                    tmp = device.getOriginalDevice()
                            .createInsecureRfcommSocketToServiceRecord(
                                    BTManager.DEFAULT_UUID
                            );
                }
            }catch (NoSuchMethodException e){
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            tmp = device.getOriginalDevice()
                    .createInsecureRfcommSocketToServiceRecord(
                            BTManager.DEFAULT_UUID
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
        mmSocket = tmp;
    }

    @Override
    public void run() {
        // Cancel discovery because it will slow down the connection
        mmAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
            mmDevice.setPaired(true);
            Log.e(BaseActivity.TAG, "SOCKET CONNECTED");
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                Log.e(BaseActivity.TAG, "SOCKET NOT CONNECTED");
                connectException.printStackTrace();
                mmSocket.close();
            } catch (IOException closeException) {
                closeException.printStackTrace();
            }
        }
        // Do work to manage the connection (in a separate thread)
        new ManageConnectThread(mmSocket, mmDataList, mActivity).start();
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

    public BluetoothSocket getMmSocket() {
        return mmSocket;
    }
}
