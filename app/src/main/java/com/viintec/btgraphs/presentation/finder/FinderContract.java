package com.viintec.btgraphs.presentation.finder;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;

import com.viintec.btgraphs.commons.BaseView;

import java.util.ArrayList;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public interface FinderContract {
    interface View extends BaseView{
        void setupDevicesListView(ArrayList<BluetoothDevice> devicesList);
        void throwBluetoothIntent();
    }

    interface UserActionsListener{
        void getPairedDevices(AppCompatActivity activity);
    }
}
