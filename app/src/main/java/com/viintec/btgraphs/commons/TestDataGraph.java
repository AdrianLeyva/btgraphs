package com.viintec.btgraphs.commons;

import android.os.Handler;
import android.util.Log;

import com.viintec.btgraphs.activities.FinderActivity;
import com.viintec.btgraphs.domain.DataGraph;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by adrianaldairleyvasanchez on 12/6/17.
 */

public class TestDataGraph {

    public static ArrayList<DataGraph> returnDummyData(ArrayList<DataGraph> dummyList){
        dummyList.add(new DataGraph("25","1"));
        dummyList.add(new DataGraph("26","2"));
        dummyList.add(new DataGraph("27","3"));
        dummyList.add(new DataGraph("25","4"));
        dummyList.add(new DataGraph("25","5"));
        dummyList.add(new DataGraph("25","6"));
        dummyList.add(new DataGraph("21","7"));

        return dummyList;
    }

    public static void emulateIncomingData(final ArrayList<DataGraph> dummyList, final FinderActivity activity){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final int TOTAL_CICLES = 10;
                int temperature = 20;
                int time = 1;
                for(int i = 0; i < TOTAL_CICLES; i++){
                    dummyList.add(new DataGraph(String.valueOf(temperature), String.valueOf(time)));
                    temperature++;
                    time++;

                    for(int j = 0; j < 1000000; j++){
                        j = j;
                    }
                }

                Log.e(BaseActivity.TAG, "Data size: " + dummyList.size());
                activity.updateChart();
            }
        });
        thread.start();
    }
}
