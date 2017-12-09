package com.viintec.btgraphs.presentation.finder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.viintec.btgraphs.commons.BasePresenter;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public class FinderPresenter extends BasePresenter<FinderContract.View> implements FinderContract.UserActionsListener {
    BluetoothAdapter mBTAdapter;

    public FinderPresenter(FinderContract.View mView) {
        super(mView);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void getPairedDevices() {
        ArrayList<BluetoothDevice> devicesList = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();

        for(BluetoothDevice device : pairedDevices){
            devicesList.add(device);
        }

        mView.setupDevicesListView(devicesList);
    }
}
