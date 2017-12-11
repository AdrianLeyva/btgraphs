package com.viintec.btgraphs.presentation.finder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.viintec.btgraphs.R;
import com.viintec.btgraphs.commons.BasePresenter;
import com.viintec.btgraphs.commons.Util;

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
    public void getPairedDevices(AppCompatActivity activity) {
        //Does device support bluetooth?
        if(mBTAdapter == null){
            mView.showFailedMessage(activity.getString(R.string.bluetooth_dont_supported));
            return;
        }

        //is bluetooth enabled?
        if(!mBTAdapter.isEnabled()){
            mView.throwBluetoothIntent();
            return;
        }

        ArrayList<BluetoothDevice> devicesList = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();

        for(BluetoothDevice device : pairedDevices){
            devicesList.add(device);
        }

        mView.setupDevicesListView(devicesList);
    }
}
