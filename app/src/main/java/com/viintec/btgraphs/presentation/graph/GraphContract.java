package com.viintec.btgraphs.presentation.graph;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.viintec.btgraphs.commons.BaseView;
import com.viintec.btgraphs.domain.DataGraph;

import java.util.ArrayList;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public class GraphContract {
    interface View extends BaseView{
        void setupLineChart(LineData lineData);
        void updateLineChart(LineData lineData);
    }

    interface UserActionsListener{
        void receiveDataFromBluetooth(String typeOfIncomingData);
        void stopIncomingData();
        void graphsIncomingData();
        void initializeLineChart();
        void updateLineChartFromThread();
        void setInvisibleProgressBar();
    }
}
