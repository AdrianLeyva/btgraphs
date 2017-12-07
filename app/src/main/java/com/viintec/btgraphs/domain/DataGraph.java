package com.viintec.btgraphs.domain;

/**
 * Created by adrianaldairleyvasanchez on 12/3/17.
 */

public class DataGraph {
    private String valueTemperature;
    private String valueTime;

    public DataGraph(String valueTemperature, String valueTime) {
        this.valueTemperature = valueTemperature;
        this.valueTime = valueTime;
    }

    public String getValueTemperature() {
        return valueTemperature;
    }

    public void setValueTemperature(String valueTemperature) {
        this.valueTemperature = valueTemperature;
    }

    public String getValueTime() {
        return valueTime;
    }

    public void setValueTime(String valueTime) {
        this.valueTime = valueTime;
    }
}
