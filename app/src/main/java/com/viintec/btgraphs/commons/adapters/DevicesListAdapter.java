package com.viintec.btgraphs.commons.adapters;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.viintec.btgraphs.R;
import com.viintec.btgraphs.activities.FinderActivity;
import com.viintec.btgraphs.commons.BTManager;
import com.viintec.btgraphs.commons.threads.ConnectThread;
import com.viintec.btgraphs.domain.BTDevice;
import com.viintec.btgraphs.domain.DataGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrianaldairleyvasanchez on 12/1/17.
 */

public class DevicesListAdapter extends ArrayAdapter<BTDevice> {
    private BluetoothAdapter mBTAdapter;
    private ArrayList<DataGraph> mDataList;
    private FinderActivity mActivity;

    public DevicesListAdapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull List<BTDevice> objects, BluetoothAdapter btAdapter,
                              ArrayList<DataGraph> dataList, FinderActivity activity) {
        super(context, resource, objects);
        mBTAdapter = btAdapter;
        mDataList = dataList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_list_device, parent, false);
        }

        //Gets btDevice from list
        final BTDevice btDevice = getItem(position);

        final ConnectThread connectThread = new ConnectThread(
                btDevice, mBTAdapter, BTManager.DEFAULT_UUID,
                mDataList, mActivity
        );

        //Binds views
        TextView textViewNameDevice = convertView.findViewById(R.id.textView_itemDevice);
        Button buttonCatchData = convertView.findViewById(R.id.button_catchData);

        //Sets item data
        if (btDevice.getName() == null  || btDevice.getName().isEmpty())
            textViewNameDevice.setText(btDevice.getAddress());
        else
            textViewNameDevice.setText(btDevice.getName());


        buttonCatchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectThread.start();
            }
        });

        return convertView;
    }
}
