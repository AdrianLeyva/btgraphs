package com.viintec.btgraphs.commons.handlers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.viintec.btgraphs.commons.BaseActivity;
import com.viintec.btgraphs.commons.BasePresenter;
import com.viintec.btgraphs.domain.DataGraph;
import com.viintec.btgraphs.presentation.graph.GraphActivity;
import com.viintec.btgraphs.presentation.graph.GraphPresenter;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public class BluetoothHandler {
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mBluetoothSocket;
    private BluetoothDevice mBluetoothDevice;

    private BasePresenter mBasePresenter;
    private ArrayList<DataGraph> dataList;

    public static final UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothHandler(BluetoothDevice mBluetoothDevice, BasePresenter basePresenter,
                            ArrayList<DataGraph> dataGraphs) {
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothDevice = mBluetoothDevice;
        mBasePresenter = basePresenter;
        dataList = dataGraphs;
        createSocket();
    }

    public void connectSocket(){
        mBluetoothAdapter.cancelDiscovery();
        try {
            GraphPresenter graphPresenter = (GraphPresenter) mBasePresenter;
            mBluetoothSocket.connect();
            setInvisibleProgressBarInUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket(){
        try {
            GraphPresenter graphPresenter = (GraphPresenter) mBasePresenter;
            mBluetoothSocket.close();
            setInvisibleProgressBarInUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readIncomingData(String typeOfIncomingData){
        GraphPresenter graphPresenter = (GraphPresenter) mBasePresenter;
        InputStream inputStream = getInputStream();
        String valueX;
        int valueY = 1;
        byte[] buffer = new byte[256];
        int bytes;

        while (true){
            try {
                bytes = inputStream.read(buffer);

                if(typeOfIncomingData.equals(GraphActivity.DATA_DEFAULT))
                    valueX = convertBytesToASCII(buffer, bytes);
                else
                    valueX = convertBytesToCustomFormat(buffer, bytes);

                dataList.add(new DataGraph(valueX, String.valueOf(valueY)));
                valueY++;
                updateLineChartInUI();
                Log.e(BaseActivity.TAG, "INCOMING DATA: " + valueX);
            } catch (IOException e) {
                e.printStackTrace();
                closeSocket();
            }
        }
    }

    public void writeData(byte[] bytes){
        OutputStream outputStream = getOutPutStream();
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSocket(){
        if(mBluetoothSocket != null)
            return;

        try{
            mBluetoothSocket = mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(DEFAULT_UUID);
        } catch (IOException e){
            e.printStackTrace();
            mBluetoothSocket = null;
        }
    }

    private InputStream getInputStream(){
        InputStream inputStream = null;
        try {
            inputStream = mBluetoothSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }

    private OutputStream getOutPutStream(){
        OutputStream outputStream = null;
        try {
            outputStream = mBluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream;
    }

    private String convertBytesToASCII(byte[] bytes, int size){
        return new String(bytes, 0, size);
    }

    private String convertBytesToCustomFormat(byte[] bytes, int size){
        String enconded;
        int addressL;

        enconded = new String(bytes, 0, size);
        addressL = Integer.valueOf(enconded);
        addressL += 48;
        addressL /= 2;
        enconded = String.valueOf(addressL);

        return enconded;
    }

    private void setInvisibleProgressBarInUI(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                GraphPresenter graphPresenter = (GraphPresenter) mBasePresenter;
                graphPresenter.setInvisibleProgressBar();
            }
        });
    }

    private void updateLineChartInUI(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                GraphPresenter graphPresenter = (GraphPresenter) mBasePresenter;
                graphPresenter.updateLineChartFromThread();
            }
        });
    }
}
