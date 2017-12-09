package com.viintec.btgraphs.presentation.graph;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.viintec.btgraphs.R;
import com.viintec.btgraphs.commons.BaseActivity;
import com.viintec.btgraphs.commons.Util;
import com.viintec.btgraphs.domain.DataGraph;

import java.util.ArrayList;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public class GraphActivity extends BaseActivity implements GraphContract.View {
    private LineChart mLineChart;
    private ProgressBar mProgress;
    private Button mButtonCatchData;
    private RadioGroup mRadioGroup;
    private TextView mTextViewDevice;
    private GraphContract.UserActionsListener mActionsListener;
    private BluetoothDevice mCurrentBTDevice;

    private ArrayList<DataGraph> dataList;
    private ArrayList<Entry> entries = new ArrayList<>();
    private boolean isReceivingData;

    public static final String BLUETOOTH_DEVICE = "BLUETOOTH_DEVICE";
    public static final String DATA_DEFAULT = "DATA_DEFAULT";
    public static final String DATA_CUSTOM = "DATA_CUSTOM";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentBTDevice = getIntent().getExtras().getParcelable(BLUETOOTH_DEVICE);
        setView(R.layout.activity_graph);
        setupPresenter();
        mActionsListener.initializeLineChart();
    }

    @Override
    public void setupLineChart(LineData lineData) {
        mLineChart.setData(lineData);
        mLineChart.invalidate();
        mLineChart.notifyDataSetChanged();
    }

    @Override
    public void updateLineChart(LineData lineData) {
        entries.clear();
        mLineChart.setData(lineData);
        mLineChart.invalidate();
        mLineChart.notifyDataSetChanged();
    }

    @Override
    protected void bindViews() {
        mLineChart = (LineChart) findViewById(R.id.lineChart_graph);
        mProgress = (ProgressBar) findViewById(R.id.progressBar_graph);
        mButtonCatchData = (Button) findViewById(R.id.button_catchData);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup_data);
        mTextViewDevice = (TextView) findViewById(R.id.textView_deviceID);
    }

    @Override
    protected void setupData() {
        dataList = new ArrayList<>();
        entries = new ArrayList<>();
        mTextViewDevice.setText(mCurrentBTDevice.getName());
    }

    @Override
    protected void activateListeners() {
        mButtonCatchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isReceivingData){
                    mActionsListener.stopIncomingData();
                    mButtonCatchData.setText(getString(R.string.catch_data));
                    isReceivingData = false;
                }
                else {
                    mActionsListener.receiveDataFromBluetooth(getTypeOfIncomingData());
                    mButtonCatchData.setText(getString(R.string.stop_catch));
                    isReceivingData = true;
                }
            }
        });
    }

    @Override
    protected void setupPresenter() {
        mActionsListener = new GraphPresenter(this, getApplicationContext(), mCurrentBTDevice,
                dataList, entries);
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

    private String getTypeOfIncomingData(){
        int optionSelected = mRadioGroup.getCheckedRadioButtonId();

        if(optionSelected == R.id.radioButton_custom)
            return DATA_CUSTOM;
        else
            return DATA_DEFAULT;
    }
}
