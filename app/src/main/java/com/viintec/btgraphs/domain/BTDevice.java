package com.viintec.btgraphs.domain;

import android.bluetooth.BluetoothDevice;

/**
 * Created by adrianaldairleyvasanchez on 12/2/17.
 */

public class BTDevice {
    private String name;
    private String address;
    private boolean isPaired;
    private BluetoothDevice originalDevice;


    public BTDevice(String name, String address, boolean isPaired, BluetoothDevice device) {
        this.name = name;
        this.address = address;
        this.isPaired = isPaired;
        originalDevice = device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPaired() {
        return isPaired;
    }

    public void setPaired(boolean paired) {
        isPaired = paired;
    }

    public BluetoothDevice getOriginalDevice() {
        return originalDevice;
    }

    public void setOriginalDevice(BluetoothDevice originalDevice) {
        this.originalDevice = originalDevice;
    }
}
