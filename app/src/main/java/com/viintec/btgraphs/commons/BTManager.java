package com.viintec.btgraphs.commons;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.util.Log;
import android.widget.ProgressBar;

import com.viintec.btgraphs.R;
import com.viintec.btgraphs.activities.FinderActivity;
import com.viintec.btgraphs.domain.BTDevice;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by adrianaldairleyvasanchez on 12/2/17.
 */

public class BTManager {
    public static final UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private Context mContext;
    private FinderActivity mActivity;
    private BluetoothAdapter btAdapter;
    private ArrayList<BTDevice> devicesList;
    private final BroadcastReceiver mBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(BaseActivity.TAG, "It's onReceive() --> " + action);

            switch (action){
                case BluetoothDevice.ACTION_FOUND:
                    Log.e(BaseActivity.TAG, "Device found");
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    devicesList.add(
                            new BTDevice(device.getName(), device.getAddress(), false,
                                    device)
                    );
                    break;

                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    mActivity.setStatus(mContext.getString(R.string.stop_search),
                            ProgressBar.VISIBLE);
                    break;

                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    mActivity.setStatus(mContext.getString(R.string.search_devices),
                            ProgressBar.INVISIBLE);
                    break;
            }

        }
    };

    public BTManager(Context context, FinderActivity activity, ArrayList<BTDevice> deviceList) {
        this.mContext = context;
        this.mActivity = activity;
        this.btAdapter = BluetoothAdapter.getDefaultAdapter();
        this.devicesList = deviceList;
    }

    public boolean isBTSupported(){
        if(btAdapter == null)
            return false;
        else
            return true;
    }

    public ArrayList<BTDevice> getPairedDevices(){
        Set<BluetoothDevice> bondedDevices = btAdapter.getBondedDevices();

        for (BluetoothDevice device: bondedDevices){
            devicesList.add(
                    new BTDevice(device.getName(), device.getAddress(), true, device)
            );
        }

        return devicesList;
    }

    public void searchDevices(){
        devicesList.clear();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mContext.registerReceiver(mBroadcast, filter);
        btAdapter.startDiscovery();
        mActivity.setStatus(mContext.getString(R.string.stop_search),
                ProgressBar.VISIBLE);
    }

    public void stopSearch(){
        Log.e(BaseActivity.TAG, "List size: " + devicesList.size());
        mContext.unregisterReceiver(mBroadcast);
        btAdapter.cancelDiscovery();
        mActivity.setStatus(mContext.getString(R.string.search_devices),
                ProgressBar.INVISIBLE);
    }

    public void cancelDiscovery(){
        btAdapter.cancelDiscovery();
        mContext.unregisterReceiver(mBroadcast);
    }

    public BluetoothAdapter getBtAdapter() {
        return btAdapter;
    }
}
