package com.viintec.btgraphs.presentation.finder;

import android.bluetooth.BluetoothDevice;

import com.viintec.btgraphs.commons.BaseView;

import java.util.ArrayList;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public interface FinderContract {
    interface View extends BaseView{
        void setupDevicesListView(ArrayList<BluetoothDevice> devicesList);
    }

    interface UserActionsListener{
        void getPairedDevices();
    }
}
