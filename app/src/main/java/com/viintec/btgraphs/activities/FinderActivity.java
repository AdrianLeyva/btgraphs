package com.viintec.btgraphs.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.viintec.btgraphs.R;
import com.viintec.btgraphs.commons.TestDataGraph;
import com.viintec.btgraphs.commons.adapters.DevicesListAdapter;
import com.viintec.btgraphs.commons.BTManager;
import com.viintec.btgraphs.commons.BaseActivity;
import com.viintec.btgraphs.commons.Util;
import com.viintec.btgraphs.domain.BTDevice;
import com.viintec.btgraphs.domain.DataGraph;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class FinderActivity extends BaseActivity {
    private ListView mListView;
    private ProgressBar mProgressBar;
    private LineChart mLineChart;

    private ArrayList<BTDevice> detectedDevices;
    private DevicesListAdapter mAdapterDevices;
    private BTManager mBTManager;

    private ArrayList<DataGraph> dataList;
    private ArrayList<Entry> entries = new ArrayList<>();

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        bindViews();
        setupData();
        activateListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        detectedDevices.clear();
        mBTManager.getPairedDevices();
    }

    @Override
    protected void bindViews(){
        mListView = (ListView) findViewById(R.id.listView_devices);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLineChart = (LineChart) findViewById(R.id.lineChartGraph);
    }

    @Override
    protected void setupData(){
        detectedDevices = new ArrayList<>();
        dataList = new ArrayList<>();
        entries = new ArrayList<>();
        mBTManager = new BTManager(getApplicationContext(), this, detectedDevices);
        mAdapterDevices = new DevicesListAdapter(
                getApplicationContext(),
                R.layout.item_list_device,
                detectedDevices,
                mBTManager.getBtAdapter(),
                dataList,
                this
        );
        mListView.setAdapter(mAdapterDevices);
        mBTManager.getPairedDevices();
        setupLineChart();
    }

    @Override
    protected void activateListeners(){

    }

    public void setStatus(String message, int status){
        mProgressBar.setVisibility(status);
    }


    private void setupLineChart(){
        //TestDataGraph.emulateIncomingData(dataList, this);
        if(!dataList.isEmpty()){
            for(DataGraph data : dataList){
                entries.add(
                        new Entry(Float.valueOf(data.getValueTime()),
                                Float.valueOf(data.getValueTemperature()))
                );
            }
            LineDataSet dataSet = new LineDataSet(entries, "Temperature");
            dataSet.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red_flat));
            LineData lineData = new LineData(dataSet);

            mLineChart.setData(lineData);
            mLineChart.invalidate();
            mLineChart.notifyDataSetChanged();
        }
    }

    public void updateChart(){
        Log.e(TAG, "DATALIST SIZE: " + dataList.size());

        entries.clear();
        if(!dataList.isEmpty()){
            for(DataGraph data : dataList){
                entries.add(
                        new Entry(Float.valueOf(data.getValueTime()),
                                Float.valueOf(data.getValueTemperature()))
                );
            }
            LineDataSet dataSet = new LineDataSet(entries, "Temperature");
            dataSet.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red_flat));
            LineData lineData = new LineData(dataSet);

            mLineChart.setData(lineData);
            mLineChart.animate();
            mLineChart.invalidate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT){
            if (resultCode == 0)
                Util.showToastLong(getApplicationContext(), "The user decided to deny bluetooth access");
            else
                Log.i(TAG, "User allowed bluetooth access!");
        }

        if(requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION && resultCode != 0){
            mBTManager.searchDevices();
        }
    }


}
