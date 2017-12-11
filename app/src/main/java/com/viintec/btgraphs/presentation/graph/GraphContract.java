package com.viintec.btgraphs.presentation.graph;

import com.github.mikephil.charting.data.LineData;
import com.viintec.btgraphs.commons.BaseView;

/**
 * Created by adrianaldairleyvasanchez on 12/8/17.
 */

public interface GraphContract {
    interface View extends BaseView{
        void setupLineChart(LineData lineData);
        void throwOnResume();
    }

    interface UserActionsListener{
        void receiveDataFromBluetooth(String typeOfIncomingData);
        void stopIncomingData();
        void initializeLineChart();
        void updateLineChartFromThread();
        void setInvisibleProgressBar();
    }
}
