package com.viintec.btgraphs.presentation.finder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.viintec.btgraphs.R;
import com.viintec.btgraphs.commons.BaseActivity;
import com.viintec.btgraphs.commons.Util;
import com.viintec.btgraphs.commons.adapters.BTDevicesListAdapter;
import com.viintec.btgraphs.presentation.graph.GraphActivity;
import com.viintec.btgraphs.presentation.graph.GraphPresenter;

import java.util.ArrayList;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public class FinderActivity extends BaseActivity implements FinderContract.View {
    private ListView mListView;
    private TextView mEmptyListView;
    private ProgressBar mProgress;
    private FinderContract.UserActionsListener mActionsListener;
    private BTDevicesListAdapter mAdapterDevices;

    private static final int BLUETOOTH_REQUEST_INTENT = 1;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_finder);
        mActionsListener.getPairedDevices(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActionsListener.getPairedDevices(this);
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
        mEmptyListView = (TextView) findViewById(R.id.item_empty_listview);
    }

    @Override
    protected void setupData() {
        mListView.setEmptyView(mEmptyListView);
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

    @Override
    public void throwBluetoothIntent() {
        Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableAdapter, BLUETOOTH_REQUEST_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == BLUETOOTH_REQUEST_INTENT){
            if(resultCode == 0){
                showFailedMessage(getString(R.string.bluetooth_request_cancelled));
            }
            else{
                mActionsListener.getPairedDevices(this);
            }
        }
    }
}
