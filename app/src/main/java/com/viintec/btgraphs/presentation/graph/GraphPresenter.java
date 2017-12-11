package com.viintec.btgraphs.presentation.graph;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.viintec.btgraphs.commons.handlers.BluetoothHandler;
import com.viintec.btgraphs.R;
import com.viintec.btgraphs.commons.BasePresenter;
import com.viintec.btgraphs.domain.DataGraph;

import java.util.ArrayList;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public class GraphPresenter extends BasePresenter<GraphContract.View> implements GraphContract.UserActionsListener {
    private Context mContex;
    private BluetoothHandler mBTHandler;
    private ArrayList<DataGraph> dataList;
    private ArrayList<Entry> entries;
    private boolean isTryingAgain;

    public GraphPresenter(GraphContract.View mView, Context context, BluetoothDevice bluetoothDevice,
                          ArrayList<DataGraph> dataList, ArrayList<Entry> entries) {
        super(mView);
        mContex = context;
        this.dataList = dataList;
        this.entries = entries;
        mBTHandler = new BluetoothHandler(bluetoothDevice, this, dataList);
    }

    @Override
    public void receiveDataFromBluetooth(final String typeOfIncomingData) {
        if(isTryingAgain){
            mView.throwOnResume();
        }
        else{
            mView.setProgressIndicator(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mBTHandler.connectSocket();
                    mBTHandler.readIncomingData(typeOfIncomingData);
                }
            }).start();
            isTryingAgain = true;
        }

    }

    @Override
    public void stopIncomingData() {
        mView.setProgressIndicator(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBTHandler.closeSocket();
            }
        }).start();

    }

    @Override
    public void initializeLineChart() {
        if(!dataList.isEmpty()){
            for(DataGraph data : dataList){
                entries.add(
                        new Entry(Float.valueOf(data.getValueTime()),
                                Float.valueOf(data.getValueTemperature()))
                );
            }

            LineDataSet dataSet = new LineDataSet(entries, "Temperature");
            dataSet.setColor(ContextCompat.getColor(mContex, R.color.red_flat));
            LineData lineData = new LineData(dataSet);

            mView.setupLineChart(lineData);
        }
    }

    @Override
    public void updateLineChartFromThread() {
        entries.clear();
        initializeLineChart();
    }

    @Override
    public void setInvisibleProgressBar() {
        mView.setProgressIndicator(false);
    }

}
