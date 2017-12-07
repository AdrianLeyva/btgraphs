package com.viintec.btgraphs.commons;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by adrianaldairleyvasanchez on 12/3/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "TAG";

    protected void bindViews(){}
    protected void setupData(){}
    protected void activateListeners(){}
}
