package com.viintec.btgraphs.commons.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.viintec.btgraphs.R;
import com.viintec.btgraphs.commons.Util;
import com.viintec.btgraphs.presentation.graph.GraphActivity;

import java.util.List;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public class BTDevicesListAdapter extends ArrayAdapter<BluetoothDevice> {
    private AppCompatActivity mActivity;

    public BTDevicesListAdapter(@NonNull Context context, @LayoutRes int resource,
                                @NonNull List<BluetoothDevice> objects, AppCompatActivity activity) {
        super(context, resource, objects);
        mActivity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_device, parent, false);

        final BluetoothDevice btDevice = getItem(position);
        TextView textViewNameDevice = convertView.findViewById(R.id.textView_itemDevice);
        Button buttonSelectDevice = convertView.findViewById(R.id.button_selectDevice);

        if (btDevice.getName() == null  || btDevice.getName().isEmpty())
            textViewNameDevice.setText(btDevice.getAddress());
        else
            textViewNameDevice.setText(btDevice.getName());

        buttonSelectDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(GraphActivity.BLUETOOTH_DEVICE, btDevice);
                Util.sentToWithBundle(mActivity, GraphActivity.class, bundle);
            }
        });



        return convertView;
    }
}
