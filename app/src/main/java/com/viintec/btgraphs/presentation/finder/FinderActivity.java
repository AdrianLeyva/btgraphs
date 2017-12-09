package com.viintec.btgraphs.presentation.finder;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.viintec.btgraphs.R;
import com.viintec.btgraphs.commons.BaseActivity;
import com.viintec.btgraphs.commons.Util;
import com.viintec.btgraphs.commons.adapters.BTDevicesListAdapter;

import java.util.ArrayList;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public class FinderActivity extends BaseActivity implements FinderContract.View {
    private ListView mListView;
    private ProgressBar mProgress;
    private FinderContract.UserActionsListener mActionsListener;
    private BTDevicesListAdapter mAdapterDevices;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_finder);
        mActionsListener.getPairedDevices();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActionsListener.getPairedDevices();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if(active) {
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mProgress.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showFailedMessage(String message) {
        Util.showToastLong(this, message);
    }

    @Override
    protected void bindViews() {
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mListView = (ListView) findViewById(R.id.listView_devices);
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected void activateListeners() {

    }

    @Override
    protected void setupPresenter() {
        mActionsListener = new FinderPresenter(this);
    }

    @Override
    public void setupDevicesListView(ArrayList<BluetoothDevice> devicesList) {
        mAdapterDevices = new BTDevicesListAdapter(this, R.layout.item_list_device, devicesList, this);
        mListView.setAdapter(mAdapterDevices);
    }
}
